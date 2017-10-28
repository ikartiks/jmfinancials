package com.kartikshah.reddit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.kartikshah.reddit.adapters.SimpleItemRecyclerViewAdapter;
import com.kartikshah.reddit.pojos.RedditMain;
import com.kartikshah.reddit.pojos.T3Data;
import com.kartikshah.reddit.pojos.T3KindData;
import com.kartikshah.reddit.ui.GifImageView;
import com.kartikshah.reddit.utility.ApiCalls;
import com.kartikshah.reddit.utility.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

//todo original reddit app has a feature to change theme based on users desire,
// currently that code is not in this app, but in my personal app which is live on playstore
// its code is on my bitbucket and can share the same if given mail

public class ItemListActivity extends ActivityBase {

    //todo currently written code for both mobile & tablets but optimised for mobile due to time constraints
    private boolean mTwoPane;

    public boolean ismTwoPane() {
        return mTwoPane;
    }

    //todo this is my own library for showing gifs, can be found on github
    //todo this may not run well in emulator
    GifImageView gifImageView;


    SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter;
    List<T3Data> t3DataList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE},readWriteExternalStorage);

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        gifImageView =(GifImageView)findViewById(R.id.gifImage);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        if(isConnected()){
            gifImageView.setVisibility(View.VISIBLE);
            ApiCalls apiCalls = Constants.getRetrofitInstance();
            apiCalls.getReddits().enqueue(new ReditCallback(gifImageView,simpleItemRecyclerViewAdapter,t3DataList));
        }else
            showCustomMessage(getResources().getString(R.string.noNet));

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        simpleItemRecyclerViewAdapter =new SimpleItemRecyclerViewAdapter(this,t3DataList);
        recyclerView.setAdapter(simpleItemRecyclerViewAdapter);
    }



    private static class ReditCallback implements Callback<RedditMain> {

        //todo added weak refrences to avoid MEMORY LEAKS
        private final WeakReference<GifImageView> gifImageViewWeakReference;
        private final WeakReference<SimpleItemRecyclerViewAdapter> simpleItemRecyclerViewAdapterWeakReference;
        List<T3Data> t3DataList;

        private ReditCallback(GifImageView gifImageView, SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter,List<T3Data> t3DataList) {
            this.gifImageViewWeakReference = new WeakReference<>(gifImageView);
            this.simpleItemRecyclerViewAdapterWeakReference = new WeakReference<>(simpleItemRecyclerViewAdapter);
            this.t3DataList = t3DataList;
        }

        @Override
        public void onResponse(Response<RedditMain> response, Retrofit retrofit) {

            GifImageView view = gifImageViewWeakReference.get();
            SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter =simpleItemRecyclerViewAdapterWeakReference.get();
            if (view != null && simpleItemRecyclerViewAdapter!=null) {

                RedditMain list=response.body();
                for (T3KindData t3KindData:list.getData().getChildren()) {
                    t3DataList.add(t3KindData.getData());
                }
                simpleItemRecyclerViewAdapter.notifyDataSetChanged();
                view.setVisibility(View.GONE);
            }
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e("failure","failure");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case readWriteExternalStorage: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    //todo this could have been a snackbar
                    showToast(getResources().getString(R.string.permissionDenied));
                }
                return;
            }
        }
    }

}

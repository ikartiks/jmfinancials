package com.kartikshah.reddit.mvp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kartikshah.reddit.ActivityBase;
import com.kartikshah.reddit.R;
import com.kartikshah.reddit.adapters.SimpleItemRecyclerViewAdapterMVP;
import com.kartikshah.reddit.pojos.T3Data;
import com.kartikshah.reddit.ui.GifImageView;

import java.util.ArrayList;
import java.util.List;


//todo only adding mvp for 1st activity, can be implemented similarly for second one
public class ItemListActivityWithMVP extends ActivityBase implements IListUpdater{

    //todo can use butterknife as well to avoid all findViewID and click listeners,sample on my github
    private boolean mTwoPane;

    public boolean ismTwoPane() {
        return mTwoPane;
    }

    GifImageView gifImageView;


    SimpleItemRecyclerViewAdapterMVP simpleItemRecyclerViewAdapter;
    //todo rather than creating T3Data T1Data we can create a base class to reuse code for common items like id
    List<T3Data> t3DataList=new ArrayList<>();

    ListPresenter listPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                readWriteExternalStorage);


        //todo caching and infinite scrolling can be added to this view
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.item_list);

        simpleItemRecyclerViewAdapter =new SimpleItemRecyclerViewAdapterMVP(this,t3DataList);
        recyclerView.setAdapter(simpleItemRecyclerViewAdapter);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        gifImageView =(GifImageView)findViewById(R.id.gifImage);
        gifImageView.setVisibility(View.VISIBLE);
        listPresenter = new ListPresenter(this);
        if(isConnected()){
           listPresenter.loadSubReddits();
        }else
            showCustomMessage(getResources().getString(R.string.noNet));

    }

    @Override
    public void onReposLoadedSuccess(List<T3Data> list) {
        simpleItemRecyclerViewAdapter.updateValues(list);
        gifImageView.setVisibility(View.GONE);
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

package com.kartikshah.reddit.mvp;

import android.util.Log;

import com.kartikshah.reddit.pojos.RedditMain;
import com.kartikshah.reddit.pojos.T3Data;
import com.kartikshah.reddit.pojos.T3KindData;
import com.kartikshah.reddit.utility.ApiCalls;
import com.kartikshah.reddit.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by kartikshah on 05/11/17.
 */

//todo not adding code for memory leaks here since thats already done before
public class ListPresenter implements IItemlListInteractor{

    IListUpdater iListUpdater;


    public ListPresenter(IListUpdater iListUpdater) {
        this.iListUpdater = iListUpdater;
    }


    @Override
    public void loadSubReddits() {

        ApiCalls apiCalls = Constants.getRetrofitInstance();
        apiCalls.getReddits().enqueue(new ReditCallback(this));
    }

    @Override
    public void onSubRedditSuccess(List<T3Data> t3DataList) {
        iListUpdater.onReposLoadedSuccess(t3DataList);
    }

    private static class ReditCallback implements Callback<RedditMain> {

        ListPresenter listPresenter ;
        public ReditCallback(ListPresenter listPresenter) {
            this.listPresenter = listPresenter;
        }

        @Override
        public void onResponse(Response<RedditMain> response, Retrofit retrofit) {

            List<T3Data> t3DataList=new ArrayList<>();
            RedditMain list=response.body();
            for (T3KindData t3KindData:list.getData().getChildren()) {
                t3DataList.add(t3KindData.getData());
            }
            listPresenter.onSubRedditSuccess(t3DataList);
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e("failure","failure");
        }
    }
}

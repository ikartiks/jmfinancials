package com.kartikshah.reddit.utility;

import com.kartikshah.reddit.pojos.RedditMain;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Url;

/**
 * Created by kartikshah on 25/12/15.
 */
public interface ApiCalls {

    @GET(".json")
    Call<RedditMain> getReddits();

    @GET
    Call<ResponseBody> getSubRedditsComments(@Url String url);
}

package com.kartikshah.reddit.mvp;

import com.kartikshah.reddit.pojos.T3Data;

import java.util.List;

/**
 * Created by kartikshah on 05/11/17.
 */

public interface IItemlListInteractor {

    void loadSubReddits();
    void onSubRedditSuccess(List<T3Data> t3DataList);
}

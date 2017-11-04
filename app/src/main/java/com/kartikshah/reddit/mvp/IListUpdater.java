package com.kartikshah.reddit.mvp;

import com.kartikshah.reddit.adapters.SimpleItemRecyclerViewAdapterMVP;
import com.kartikshah.reddit.pojos.T3Data;

import java.util.List;

/**
 * Created by kartikshah on 05/11/17.
 */

public interface IListUpdater {

    void onReposLoadedSuccess(List<T3Data> list);
}

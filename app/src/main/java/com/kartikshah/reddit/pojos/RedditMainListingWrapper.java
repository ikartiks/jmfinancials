package com.kartikshah.reddit.pojos;

import java.util.List;

/**
 * Created by kartikshah on 27/10/17.
 */


public class RedditMainListingWrapper {

    List<T3KindData> children;
    String after;
    String before;
    String modhash;
    String whitelist_status;


    public List<T3KindData> getChildren() {
        return children;
    }

    public void setChildren(List<T3KindData> children) {
        this.children = children;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public String getWhitelist_status() {
        return whitelist_status;
    }

    public void setWhitelist_status(String whitelist_status) {
        this.whitelist_status = whitelist_status;
    }

    @Override
    public String toString() {
        return after +  before + modhash +  whitelist_status +children;
    }
}

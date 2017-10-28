package com.kartikshah.reddit.pojos;

/**
 * Created by kartikshah on 27/10/17.
 */

public class RedditMain {

    String kind;
    RedditMainListingWrapper data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public RedditMainListingWrapper getData() {
        return data;
    }

    public void setData(RedditMainListingWrapper data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}

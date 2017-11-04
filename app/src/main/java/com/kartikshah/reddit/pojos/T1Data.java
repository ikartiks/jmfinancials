package com.kartikshah.reddit.pojos;

/**
 * Created by kartikshah on 27/10/17.
 */

public class T1Data {

    //todo things like id , body which are common in both T3 & T1Data objects can be added in base class for code reuse
    String subreddit_id;
    String body;
    long depth;
    long ups;
    String id;

    transient String parentId;
    transient boolean isOpened;
    transient long childSize;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public long getChildSize() {
        return childSize;
    }

    public void setChildSize(long childSize) {
        this.childSize = childSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public String getSubreddit_id() {
        return subreddit_id;
    }

    public void setSubreddit_id(String subreddit_id) {
        this.subreddit_id = subreddit_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getDepth() {
        return depth;
    }

    public void setDepth(long depth) {
        this.depth = depth;
    }

    public long getUps() {
        return ups;
    }

    public void setUps(long ups) {
        this.ups = ups;
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof T1Data))
            return false;
        T1Data object=(T1Data) obj;
        return (this.getId().equals(object.getId()));
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

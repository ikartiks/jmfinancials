package com.kartikshah.reddit.pojos;

/**
 * Created by kartikshah on 27/10/17.
 */

public class T3KindData {

    String kind;
    T3Data data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public T3Data getData() {
        return data;
    }

    public void setData(T3Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return kind+data;
    }
}

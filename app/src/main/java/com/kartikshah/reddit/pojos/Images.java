package com.kartikshah.reddit.pojos;

import java.util.List;

/**
 * Created by kartikshah on 27/10/17.
 */

public class Images {

    private String id;

    private Source source;

    private List<Resolutions> resolutions;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Source getSource ()
    {
        return source;
    }

    public void setSource (Source source)
    {
        this.source = source;
    }

    public List<Resolutions> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<Resolutions> resolutions) {
        this.resolutions = resolutions;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", source = "+source+", variants = "+", resolutions = "+resolutions+"]";
    }
}

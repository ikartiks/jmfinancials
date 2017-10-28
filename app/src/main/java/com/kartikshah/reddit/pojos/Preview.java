package com.kartikshah.reddit.pojos;

import java.util.List;

/**
 * Created by kartikshah on 27/10/17.
 */

public class Preview {

    private boolean enabled;

    private List<Images> images;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [enabled = "+enabled+", images = "+images+"]";
    }
}

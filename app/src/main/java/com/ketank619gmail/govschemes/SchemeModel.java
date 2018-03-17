package com.ketank619gmail.govschemes;

import java.io.Serializable;

/**
 * Created by Ketan-PC on 2/3/2018.
 */

public class SchemeModel implements Serializable {
    String Scheme,Description,url,id,CATEGORY;

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getScheme() {
        return Scheme;
    }

    public void setScheme(String scheme) {
        Scheme = scheme;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

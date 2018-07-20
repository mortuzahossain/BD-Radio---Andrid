package com.example.mortuza.radioplayer.model;

public class DataModel {
    private String name;
    private String image;
    private String streamUrl;

    public DataModel(String name, String image, String streamUrl) {
        this.name = name;
        this.image = image;
        this.streamUrl = streamUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }
}

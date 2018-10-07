package com.example.shreyesh.sarinstituteofmedicalscience;

public class Services {

    private String image, title;

    public Services(String image, String title) {
        this.image = image;
        this.title = title;
    }

    public Services() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package com.example.shreyesh.sarinstituteofmedicalscience;

public class SelectedPatient {
    private String name, image, userid;

    public SelectedPatient(String name, String image, String userid) {
        this.name = name;
        this.image = image;
        this.userid = userid;
    }

    public SelectedPatient() {
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

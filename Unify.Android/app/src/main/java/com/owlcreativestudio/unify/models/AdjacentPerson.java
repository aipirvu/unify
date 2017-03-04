package com.owlcreativestudio.unify.models;

public class AdjacentPerson {
    private String id;
    private String name;
    private UnifyLocation location;
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnifyLocation getLocation() {
        return location;
    }

    public void setLocation(UnifyLocation location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

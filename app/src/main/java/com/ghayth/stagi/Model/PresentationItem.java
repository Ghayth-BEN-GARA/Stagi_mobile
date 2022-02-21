package com.ghayth.stagi.Model;

public class PresentationItem {
    private String titre,description;
    private int image;

    public PresentationItem(String title, String description, int image) {
        this.titre = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return titre;
    }

    public void setTitle(String title) {
        this.titre = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

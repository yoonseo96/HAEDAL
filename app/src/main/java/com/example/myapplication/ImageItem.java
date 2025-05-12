
package com.example.myapplication;

public class ImageItem {
    private String ImageUrl;
    private long timestamp;
    public ImageItem(){

    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ImageItem(String ImageUrl, long timestamp){
        this.ImageUrl=ImageUrl;
        this.timestamp=timestamp;
    }
}

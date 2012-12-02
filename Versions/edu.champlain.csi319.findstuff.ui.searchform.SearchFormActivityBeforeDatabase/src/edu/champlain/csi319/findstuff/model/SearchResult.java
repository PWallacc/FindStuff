package edu.champlain.csi319.findstuff.model;

import android.graphics.Bitmap;


public class SearchResult
{
    private String heading = new String();
    private String location = new String();    
    private String imageURL = new String();  
    private Bitmap thumbnail; 
    private String id = new String();
    
    // is there a thumbnail?
    private boolean thumbnailSet = false;

    // constructor does nothing
    public SearchResult() { }

    public String getHeading()
    {
        return heading;
    }

    public void setHeading(String heading)
    {
        this.heading = heading;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Bitmap getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Bitmap thumbnail) {
		this.thumbnail = thumbnail;
	}

	public boolean isThumbnailSet() {
		return thumbnailSet;
	}

	public void setThumbnailSet(boolean thumbnailSet) {
		this.thumbnailSet = thumbnailSet;
	}


}

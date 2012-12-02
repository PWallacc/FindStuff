package edu.champlain.csi319.findstuff.model;


public class SearchResult
{
    private String heading = new String();
    private String location = new String();
    
    /**
     * Default Constructor - does nothing
     */
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
    

}

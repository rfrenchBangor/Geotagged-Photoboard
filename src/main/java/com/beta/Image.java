package com.beta;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Image {
    
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long imageID;
    private double lat, lon;
    private String description = "";
    private int imageAdded = 0;
    @Lob
    private byte[] byteArray;
    
    protected Image() {}
    
    Image(String description, double lat, double lon, byte[] byteArray)
    {
    	this.byteArray = byteArray;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        
    }
    Image(double lat, double lon,byte[] byteArray)
    {
    	this.byteArray = byteArray;
        this.lat = lat;
        this.lon = lon;
    }

	public long getID()
    {
        return imageID;
    }
	@Lob
	public byte[] getByteArray()
	{
		return byteArray;
	}
    public String getDesc()
    {
        return description;
    }
    public double getLat()
    {
        return lat;
    }
    public double getLon()
    {
        return lon;
    }

   
    @Override
    public String toString() {
        return String.format(
                "Image[id=%d, description='%s', lat='%f', lon='%f']",
                imageID, description,lat,lon);
    }
    
}

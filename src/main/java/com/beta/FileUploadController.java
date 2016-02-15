package com.beta;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;


@Controller
public class FileUploadController {

	GeoLocation geoLocation;
	double lat, lon;
	@Autowired
	ImageRepository repository;
	   
	@RequestMapping(value="/map", method=RequestMethod.GET)
    public String provideUploadInfo() {
        return "map";
    }

    @RequestMapping(value="/map", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("desc") String desc,
            @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty())
        {
            try {
                byte[] bytes = file.getBytes();
                Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(bytes));
                
                if (metadata.containsDirectoryOfType(GpsDirectory.class)) 
                {
                	Collection<GpsDirectory> gpsDirectories = metadata.getDirectoriesOfType(GpsDirectory.class);
                	for (GpsDirectory gpsDirectory : gpsDirectories) {
                		geoLocation = gpsDirectory.getGeoLocation();
                	}
                	lat = geoLocation.getLatitude();
                	lon = geoLocation.getLongitude();
                	if(desc.length() != 0)
                	{
                		repository.save(new Image(desc,lat,lon,bytes));
                	}
                	else
                	{
                		repository.save(new Image(lat,lon,bytes));
                	}
               
                	return "map";
                }
                else
                {
                	return "error";
                }
            } catch (Exception e) {
                return e.toString();
            }
        }
        else
        {
        	return "error";
        }
    }
}
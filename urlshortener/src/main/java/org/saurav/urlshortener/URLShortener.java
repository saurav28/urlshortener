package org.saurav.urlshortener;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class to shorten the URL
 * @author Saurav Sarkar
 *
 */
@Service
public class URLShortener {
	
	@Autowired
	URLRepository urlRepository;
	
	
	private HashMap<UUID,String> mapper = new HashMap<UUID,String>();
	
	public String getShortenedURL(String url) {
		
		UUID uuid = UUID.nameUUIDFromBytes(url.getBytes());
		
		URL urlObject = new URL();
		urlObject.setURL_UUID(uuid.toString());
		urlObject.setURL_NAME(url);
		
		urlRepository.save(urlObject);
		//mapper.put(uuid, url);
		return "http://localhost:8080/" + uuid;
		
	}
	
	public String getLongURL(String shorturl) {
		
		//String[] splitArray = shorturl.split("/");
		
		UUID uuid = UUID.fromString(shorturl);
		//String longURL = mapper.get(uuid);
		Optional<URL> longURL = urlRepository.findById(uuid.toString());
		if(longURL.isPresent()) {
		return longURL.get().getURL_NAME();
		}else {
			return "No URL mapped. Check the DB ";
		}
	}

}

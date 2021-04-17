package org.saurav.urlshortener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class URLShortenerViewController {
	
	@Autowired
	URLShortener shortenerService;
	
	 Logger logger = LoggerFactory.getLogger(URLShortenerViewController.class);
	 
	
	@RequestMapping("/index")
	public String index(Model model) {
		logger.info("Inside the index method");
		URL url = new URL();
		model.addAttribute("url", url);
		return "welcome";
	}
	
	
	@PostMapping("/geturl")
	public String getShortenedURL(URL url, Model model) {
		String urlName = url.getURL_NAME();
		model.addAttribute("shorturl",shortenerService.getShortenedURL(urlName));
		return "showurl";
		
	}
	
	@GetMapping("/{id}")
	public String getLongURL(@PathVariable("id") String id ,Model model) {
		String longURL = shortenerService.getLongURL(id);
		model.addAttribute("longurl",longURL);
		return "showurl";
	}

}

/**
 * 
 */
package com.example.tinryurlservice;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author aniket
 *
 */
@RestController
public class URLController {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLController.class);
    private final URLConvertorService urlConverterService;

    public URLController(URLConvertorService urlConverterService) {
        this.urlConverterService = urlConverterService;
    }

@RequestMapping(value = "/shortener", method=RequestMethod.POST, consumes = {"application/json"})
public String shortenUrl(@RequestBody @Valid final ShortenRequest shortenRequest, HttpServletRequest request) throws Exception {
    LOGGER.info("Received url to shorten: " + shortenRequest.getUrl());
    String longUrl = shortenRequest.getUrl();
   // if (URLValidator.INSTANCE.validateURL(longUrl)) {
        String localURL = request.getRequestURL().toString();
        String shortenedUrl = urlConverterService.shortenURL(localURL, shortenRequest.getUrl());
        LOGGER.info("Shortened url to: " + shortenedUrl);
        return shortenedUrl;
   // }
   // throw new Exception("Please enter a valid URL");
}

    @RequestMapping(value = "/{id}", method=RequestMethod.GET)
    public RedirectView redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {
        LOGGER.debug("Received shortened url to redirect: " + id);
        String redirectUrlString = urlConverterService.getLongURLFromID(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://" + redirectUrlString);
        return redirectView;
    }
}

class ShortenRequest{
    private String url;

    @JsonCreator
    public ShortenRequest() {

    }

    @JsonCreator
    public ShortenRequest(@JsonProperty("url") String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
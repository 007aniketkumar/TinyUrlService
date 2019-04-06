/**
 * 
 */
package com.example.tinryurlservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author aniket
 *
 */
@Service
public class URLConvertorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLConvertorService.class);
    private final URLRepository urlRepository;

    @Autowired
    public URLConvertorService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenURL(String localURL, String longUrl) {
        LOGGER.info("Shortening {}", longUrl);
        Long id = urlRepository.incrementID();
        String uniqueID = IDConvertor.INSTANCE.createUniqueID(id);
        urlRepository.saveUrl("url:"+uniqueID, longUrl);
        String baseString = formatLocalURLFromShortener(localURL);
        String shortenedURL = baseString + uniqueID;
        return shortenedURL;
    }

    public String getLongURLFromID(String uniqueID) throws Exception {
//        Long dictionaryKey = IDConvertor.INSTANCE.getUrl(uniqueID);
        String longUrl = urlRepository.getUrl(uniqueID);
        LOGGER.info("Converting shortened URL back to {}", longUrl);
        return longUrl;
    }

    private String formatLocalURLFromShortener(String localURL) {
        String[] addressComponents = localURL.split("/");
        // remove the endpoint (last index)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addressComponents.length - 1; ++i) {
            sb.append(addressComponents[i]);
        }
        sb.append('/');
        return sb.toString();
    }

}

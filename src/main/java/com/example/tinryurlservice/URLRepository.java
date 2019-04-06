/**
 * 
 */
package com.example.tinryurlservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

/**
 * @author aniket
 *
 */
@Repository
public class URLRepository {
    private final Jedis jedis;
    private final String idKey;
    private final String urlKey;
    private static final Logger LOGGER = LoggerFactory.getLogger(URLRepository.class);

    public URLRepository() {
        this.jedis = new Jedis();
        this.idKey = "id";
        this.urlKey = "url:";
    }

    public URLRepository(Jedis jedis, String idKey, String urlKey) {
        this.jedis = jedis;
        this.idKey = idKey;
        this.urlKey = urlKey;
    }

    public Long incrementID() {
        Long id = jedis.incr(idKey);
        LOGGER.info("Incrementing ID: {}", id-1);
        return id - 1;
    }

    public void saveUrl(String shortUrl, String longUrl) {
        LOGGER.info("Saving: {} at {}", longUrl, shortUrl);
        jedis.hset(urlKey, shortUrl, longUrl);
    }

    public String getUrl(Long id) throws Exception {
        LOGGER.info("Retrieving at {}", id);
        String url = jedis.hget(urlKey, "url:"+id);
        if (url == null) {
            throw new Exception("URL at key" + id + " does not exist");
        }
        return jedis.hget(urlKey, "url:"+id);
    }
    
    public String getUrl(String shortUrl) throws Exception {
    	
        LOGGER.info("Retrieving at {}", shortUrl);
        String url = jedis.hget(urlKey,"url:"+shortUrl);
        if (url == null) {
            throw new Exception("URL at key" + shortUrl + " does not exist");
        }
        return url;
    }
}
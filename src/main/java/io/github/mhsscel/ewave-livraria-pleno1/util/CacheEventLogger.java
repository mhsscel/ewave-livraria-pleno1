package io.github.mhsscel.bookjavaapi.util;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * Class that implements an event logger to show cache creation and expiration
 * in the API.
 *  
 * @author Murillo Henrique
 * @since 21/09/2020
 */
@Log4j2
@Component
public class CacheEventLogger implements CacheEventListener<Object, Object> {
 
	/**
	 * @see CacheEventListener#onEvent(CacheEvent)
	 */
    @Override
    public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
    	log.info("Cache event {} for item with key {}. Old value = {}, New value = {}", cacheEvent.getType(), 
    			cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }

}

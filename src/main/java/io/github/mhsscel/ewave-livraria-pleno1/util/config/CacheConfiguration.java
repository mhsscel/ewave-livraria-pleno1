package io.github.mhsscel.bookjavaapi.util.config;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class that implements the necessary settings for the cache in the API.
 *  
 * @author Murillo Henrique
 * @since 21/09/2020
 */
@EnableCaching
@Configuration
public class CacheConfiguration {

	/**
	 * Method that instantiates a cache manager based on ConcurrentMapCacheManager
	 * 
	 * @author Murillo Henrique
	 * @since 21/09/2020
	 * 
	 * @return <code>CacheManagerCustomizer<ConcurrentMapCacheManager></code> object
	 */
	@Bean
	public CacheManagerCustomizer<ConcurrentMapCacheManager> cacheManagerCustomizer() {
		return new CacheManagerCustomizer<ConcurrentMapCacheManager>() {
			@Override
			public void customize(ConcurrentMapCacheManager cacheManager) {
				cacheManager.setAllowNullValues(true);
			}
		};
	}
}

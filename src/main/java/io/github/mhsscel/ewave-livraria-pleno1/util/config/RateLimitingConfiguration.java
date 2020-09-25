package io.github.mhsscel.bookjavaapi.util.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.mhsscel.bookjavaapi.util.interceptor.RateLimitInterceptor;

/**
 * Class that implements the necessary settings for the rate limiting in the API
 *  
 * @author Murillo Henrique
 * @since 21/09/2020
 */
@Configuration
public class RateLimitingConfiguration implements WebMvcConfigurer {
	
	private RateLimitInterceptor interceptor;
	
	@Autowired
	public RateLimitingConfiguration(RateLimitInterceptor interceptor) {
		this.interceptor = interceptor;
	}
	
	/**
	 * Method that allow intercepting routes for rate limiting
	 * 
	 * @author Murillo Henrique
	 * @since 21/09/2020
	 */
	@Override 
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/book/v1/users/**",
        		"/book/v1/title/**");
    }

}

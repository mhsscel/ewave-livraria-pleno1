package io.github.mhsscel.bookjavaapi.util.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;

import com.zero_x_baadf00d.partialize.PartializeConverterManager;

import io.github.mhsscel.bookjavaapi.util.BigDecimalConverter;
import io.github.mhsscel.bookjavaapi.util.LocalDateTimeConverter;

/**
 * Class that implements the necessary settings for the API to works.
 *  
 * @author Murillo Henrique
 * @since 02/04/2020
 */
@Configuration
public class
BookApiConfiguration {
	
	/**
	 * Method that allow discovering links by relation type from some source.
	 * 
	 * @author Murillo Henrique
	 * @since 02/04/2020
	 * 
	 * @return <code>LinkDiscoverers</code> object
	 */
	@Bean(name = "linkDiscover")
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.of(plugins));
    }
	
	/**
	 * Method that allow the initialization of Partialize converters.
	 * 
	 * @author Murillo Henrique
	 * @since 21/09/2020
	 */
	@Bean
	public void initPartialize(){
		PartializeConverterManager.getInstance().registerConverter(new LocalDateTimeConverter());
		PartializeConverterManager.getInstance().registerConverter(new BigDecimalConverter());
	}
}

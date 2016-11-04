package com.chuckcaplan.libronuments.config;  
  
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
  
/**
 * Spring App Config Initialization Class
 * 
 * @author Chuck Caplan
 *
 */
@Configuration 
// where all the services are located
@ComponentScan("com.chuckcaplan.libronuments") 
@Import(DBConfig.class)
@EnableWebMvc   
public class AppConfig extends WebMvcConfigurerAdapter  {  
    @Bean  
    public InternalResourceViewResolver viewResolver() {  
	    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	    // allows the jsp files to be stored under WEB-INF and still display
        resolver.setPrefix("/WEB-INF/view/");  
        resolver.setSuffix(".jsp");
        return resolver;  
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	// additional layer of protection. Hides from the browser where the files are really stored on disk
    	registry.addResourceHandler("/app-resources/**").addResourceLocations("/resources/");
    }    
}  

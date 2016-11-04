package com.chuckcaplan.libronuments.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Spring Web App Initializer
 * @author Chuck Caplan
 *
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}
	
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}

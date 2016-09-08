package org.example.jenkins.service.list.route;

import org.example.jenkins.service.list.common.SystemProperties;
import org.example.jenkins.service.list.common.SystemConstants;
import org.example.jenkins.service.list.processor.ExceptionProcessor;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class BaseRoute extends RouteBuilder {
	private static final String JETTY_COMPONENT_ID = "jetty";
	private static final String RESTRICT_PROPERTY_KEY = "httpMethodRestrict";
	private static final String RESTRICT_PROPERTY_VALUE = "POST";
	
	private final Processor exceptionProcessor = new ExceptionProcessor();
	
	@Override
	public void configure() throws Exception {
		errorHandler(defaultErrorHandler());
		onException(Exception.class).process(exceptionProcessor).handled(true);
		
		restConfiguration()
			.component(JETTY_COMPONENT_ID)
			.componentProperty(RESTRICT_PROPERTY_KEY, RESTRICT_PROPERTY_VALUE)
			.host(SystemProperties.getProperty(SystemConstants.LIST_SERVICE_HOST_PROPERTY))
			.port(SystemProperties.getProperty(SystemConstants.LIST_SERVICE_PORT_PROPERTY))
			.bindingMode(RestBindingMode.auto);
	}
}

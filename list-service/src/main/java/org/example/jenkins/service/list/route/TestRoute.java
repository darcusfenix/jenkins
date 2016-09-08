package org.example.jenkins.service.list.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import org.example.jenkins.service.list.common.SystemProperties;
import org.example.jenkins.service.list.common.SystemConstants;

public class TestRoute extends BaseRoute {
	private static final String TEST_ROUTE_ID = "testRoute";
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		rest(SystemProperties.getProperty(SystemConstants.LIST_SERVICE_CONTEXT_PROPERTY)).enableCORS(true)
			.get(SystemConstants.LIST_TEST_PATH)
			.route().routeId(TEST_ROUTE_ID)
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						exchange.getOut().setBody("ON", String.class);
					}
				})
				.endRest();
	}
}

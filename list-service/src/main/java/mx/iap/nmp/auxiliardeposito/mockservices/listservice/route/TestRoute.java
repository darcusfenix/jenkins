package mx.iap.nmp.auxiliardeposito.mockservices.listservice.route;

import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.LIST_SERVICE_CONTEXT_PROPERTY;
import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.LIST_TEST_PATH;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemProperties;

public class TestRoute extends BaseRoute {
	private static final String TEST_ROUTE_ID = "testRoute";
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		rest(SystemProperties.getProperty(LIST_SERVICE_CONTEXT_PROPERTY)).enableCORS(true)
			.get(LIST_TEST_PATH)
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

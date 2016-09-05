package mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.util;

import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.LIST_SERVICE_CONTENT_TYPE;

import org.apache.camel.Exchange;

public final class ProcessorUtil {
	public static void setJsHeaders2Exchange(Exchange exchange, boolean in) {
		if (in) {
			exchange.getIn().setHeader(Exchange.CONTENT_TYPE,
					LIST_SERVICE_CONTENT_TYPE);
		} else {
			exchange.getOut().setHeader(Exchange.CONTENT_TYPE,
					LIST_SERVICE_CONTENT_TYPE);
		}
	}
}

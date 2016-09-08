package org.example.jenkins.service.list.common.util;

import org.apache.camel.Exchange;
import org.example.jenkins.service.list.common.SystemConstants;

public final class ProcessorUtil {
	public static void setJsHeaders2Exchange(Exchange exchange, boolean in) {
		if (in) {
			exchange.getIn().setHeader(Exchange.CONTENT_TYPE,
					SystemConstants.LIST_SERVICE_CONTENT_TYPE);
		} else {
			exchange.getOut().setHeader(Exchange.CONTENT_TYPE,
					SystemConstants.LIST_SERVICE_CONTENT_TYPE);
		}
	}
}

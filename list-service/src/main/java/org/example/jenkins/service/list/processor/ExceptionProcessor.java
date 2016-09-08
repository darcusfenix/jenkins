package org.example.jenkins.service.list.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import org.example.jenkins.service.list.common.datatransfer.SystemResponseDTO;
import org.example.jenkins.service.list.common.util.JSONUtil;
import org.example.jenkins.service.list.common.util.ProcessorUtil;
import org.example.jenkins.service.list.common.SystemConstants;

public class ExceptionProcessor implements Processor {
	@Override
	public void process(Exchange exchange)
	throws Exception {
		SystemResponseDTO response =
				new SystemResponseDTO(
						exchange.getProperty(Exchange.EXCEPTION_CAUGHT, String.class),
						SystemConstants.SYSTEM_BAD_RESPONSE_CODE);
		
		// Log the in and out
		System.out.println("Request: " + exchange.getIn().getBody(String.class) + "\nResponse: " + response);
		
		exchange.getOut().setBody(JSONUtil.parse(response));
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, response.getCode());
		
		ProcessorUtil.setJsHeaders2Exchange(exchange, false);
	}
}

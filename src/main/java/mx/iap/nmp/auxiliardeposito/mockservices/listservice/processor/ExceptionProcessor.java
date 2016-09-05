package mx.iap.nmp.auxiliardeposito.mockservices.listservice.processor;

import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.SYSTEM_BAD_RESPONSE_CODE;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.datatransfer.SystemResponseDTO;
import mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.util.JSONUtil;
import mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.util.ProcessorUtil;

public class ExceptionProcessor implements Processor {
	@Override
	public void process(Exchange exchange)
	throws Exception {
		SystemResponseDTO response =
				new SystemResponseDTO(
						exchange.getProperty(Exchange.EXCEPTION_CAUGHT, String.class),
						SYSTEM_BAD_RESPONSE_CODE);
		
		// Log the in and out
		System.out.println("Request: " + exchange.getIn().getBody(String.class) + "\nResponse: " + response);
		
		exchange.getOut().setBody(JSONUtil.parse(response));
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, response.getCode());
		
		ProcessorUtil.setJsHeaders2Exchange(exchange, false);
	}
}

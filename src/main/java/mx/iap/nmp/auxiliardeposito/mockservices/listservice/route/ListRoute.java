package mx.iap.nmp.auxiliardeposito.mockservices.listservice.route;

import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.LIST_SERVICE_CONTENT_TYPE;
import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.LIST_SERVICE_CONTEXT_PROPERTY;
import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.LIST_SERVICE_ERROR_MESSAGE;
import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.LIST_SERVICE_PATH;
import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.MONGO_DB_BEAN_NAME;
import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.MONGO_DB_COLLECTION_NAME;
import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.MONGO_DB_DATABASE_NAME;
import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.MONGO_DB_FILTER_FIELD;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.mongodb.converters.MongoDbBasicConverters;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemProperties;

public class ListRoute extends BaseRoute {
	private static final String LIST_ROUTE_ID = "listRoute";
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		rest(SystemProperties.getProperty(LIST_SERVICE_CONTEXT_PROPERTY)).enableCORS(true)
			.post(LIST_SERVICE_PATH)
			.consumes(LIST_SERVICE_CONTENT_TYPE)
			.produces(LIST_SERVICE_CONTENT_TYPE)
			.route().routeId(LIST_ROUTE_ID) 
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						if( exchange.getIn().getBody() != null){
							DBObject dbObject = MongoDbBasicConverters.fromMapToDBObject(
									exchange.getIn().getBody(HashMap.class));
							
							DBObject conditionField = new BasicDBObject("$in", dbObject.get(MONGO_DB_FILTER_FIELD));
							DBObject objectField    = new BasicDBObject(MONGO_DB_FILTER_FIELD, conditionField);
							
							exchange.getIn().setBody(objectField, DBObject.class);
						}
					}
				})
				.to("mongodb:" + MONGO_DB_BEAN_NAME + "?database="+MONGO_DB_DATABASE_NAME+"&collection="+MONGO_DB_COLLECTION_NAME+"&operation=findAll")
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						String dbResponse = exchange.getIn().getBody(String.class);
						
						if (dbResponse == null) {
							throw new Exception(LIST_SERVICE_ERROR_MESSAGE);
						}
					}
				})
				.endRest();
	}
}

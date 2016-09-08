package org.example.jenkins.service.list.route;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.mongodb.converters.MongoDbBasicConverters;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.example.jenkins.service.list.common.SystemProperties;
import org.example.jenkins.service.list.common.SystemConstants;

public class ListRoute extends BaseRoute {
	private static final String LIST_ROUTE_ID = "listRoute";
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		rest(SystemProperties.getProperty(SystemConstants.LIST_SERVICE_CONTEXT_PROPERTY)).enableCORS(true)
			.post(SystemConstants.LIST_SERVICE_PATH)
			.consumes(SystemConstants.LIST_SERVICE_CONTENT_TYPE)
			.produces(SystemConstants.LIST_SERVICE_CONTENT_TYPE)
			.route().routeId(LIST_ROUTE_ID) 
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						if( exchange.getIn().getBody() != null){
							DBObject dbObject = MongoDbBasicConverters.fromMapToDBObject(
									exchange.getIn().getBody(HashMap.class));
							
							DBObject conditionField = new BasicDBObject("$in", dbObject.get(SystemConstants.MONGO_DB_FILTER_FIELD));
							DBObject objectField    = new BasicDBObject(SystemConstants.MONGO_DB_FILTER_FIELD, conditionField);
							
							exchange.getIn().setBody(objectField, DBObject.class);
						}
					}
				})
				.to("mongodb:" + SystemConstants.MONGO_DB_BEAN_NAME + "?database="+ SystemConstants.MONGO_DB_DATABASE_NAME+"&collection="+ SystemConstants.MONGO_DB_COLLECTION_NAME+"&operation=findAll")
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						String dbResponse = exchange.getIn().getBody(String.class);
						
						if (dbResponse == null) {
							throw new Exception(SystemConstants.LIST_SERVICE_ERROR_MESSAGE);
						}
					}
				})
				.endRest();
	}
}

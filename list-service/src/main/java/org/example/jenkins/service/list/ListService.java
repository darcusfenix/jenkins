package org.example.jenkins.service.list;

import org.example.jenkins.service.list.common.SystemProperties;
import org.example.jenkins.service.list.common.SystemConstants;
import org.example.jenkins.service.list.route.ListRoute;
import org.example.jenkins.service.list.route.TestRoute;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.JndiRegistry;

import com.mongodb.Mongo;
import com.mongodb.MongoClientURI;

public class ListService {
	private final ListRoute listRoute = new ListRoute();
	private final TestRoute testRoute = new TestRoute();
	
	private static Mongo mongoDB;
	private static JndiRegistry registry;
	private static CamelContext context;
	
	private void init()
	throws Exception {
		// Create and register a Mongo Connection Bean
		mongoDB = Mongo.Holder.singleton().connect(
				new MongoClientURI(
						SystemProperties.getProperty(
								SystemConstants.LIST_SERVICE_DB_URI_PROPERTY)));
		
		registry = new JndiRegistry(true);
		registry.bind(SystemConstants.MONGO_DB_BEAN_NAME, mongoDB);
		
		context = new DefaultCamelContext(registry);
		context.addRoutes(listRoute);
		context.addRoutes(testRoute);
		context.start();
	}

	public static void main(String ... args) {
		ListService listService = new ListService(); 
		
		try {
			listService.init();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}

import mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemProperties;
import mx.iap.nmp.auxiliardeposito.mockservices.listservice.route.ListRoute;
import mx.iap.nmp.auxiliardeposito.mockservices.listservice.route.TestRoute;

import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.LIST_SERVICE_DB_URI_PROPERTY;
import static mx.iap.nmp.auxiliardeposito.mockservices.listservice.common.SystemConstants.MONGO_DB_BEAN_NAME;

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
								LIST_SERVICE_DB_URI_PROPERTY)));
		
		registry = new JndiRegistry(true);
		registry.bind(MONGO_DB_BEAN_NAME, mongoDB);
		
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

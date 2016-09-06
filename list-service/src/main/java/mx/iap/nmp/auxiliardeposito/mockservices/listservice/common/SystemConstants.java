package mx.iap.nmp.auxiliardeposito.mockservices.listservice.common;

public class SystemConstants {
	public static final String SYSTEM_PROPERTIES_FILE_NAME = "conf/system.properties";
	
	public static final String LIST_SERVICE_HOST_PROPERTY = "listservice.host";
	public static final String LIST_SERVICE_PORT_PROPERTY = "listservice.port";
	public static final String LIST_SERVICE_CONTEXT_PROPERTY = "listservice.context";
	public static final String LIST_SERVICE_DB_URI_PROPERTY = "listservice.db.uri";
	
	public static final String LIST_SERVICE_PATH = "/list";
	public static final String LIST_TEST_PATH = "/ching";
	public static final String LIST_SERVICE_CONTENT_TYPE = "application/json";
	public static final String LIST_SERVICE_ERROR_MESSAGE = "Data not found!";
	
	public static final String MONGO_DB_BEAN_NAME = "dbConnection";
	public static final String MONGO_DB_DATABASE_NAME = "auxDep";
	public static final String MONGO_DB_COLLECTION_NAME = "Pledges";
	public static final String MONGO_DB_FILTER_FIELD = "movementType";
	
	public static final Integer SYSTEM_OK_RESPONSE_CODE = 200;
	public static final Integer SYSTEM_BAD_RESPONSE_CODE = 400;
	
}

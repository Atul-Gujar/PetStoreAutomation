package api.endpoints;

// All URL's are maintained in this class for all modules


public class Routes {

	public static String base_url = "https://petstore3.swagger.io/api/v3";
	
	// user model
	public static String post_url=base_url+"/user";
	public static String get_url=base_url+"/user/{username}";
	public static String update_url=base_url+"/user/{username}";
	public static String delete_url=base_url+"/user/{username}";
}

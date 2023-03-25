package api;

import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiUtils {
	private static Response response;
	private static RequestSpecification request;
	private String endpoint;

	public ApiUtils(String baseURL) {
		RestAssured.baseURI = baseURL;
		request = RestAssured.given();
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public Response get() {
		response = request.get(endpoint);
		return response;
	}

	public int getStatusCode() {
		return response.getStatusCode();
	}

	public Map<String, String> getCookies(){
		return response.getCookies();
	}

	public String getCookie(String cookie) {
		return response.getCookie(cookie);
	}

	public List<String> getAPIData(String key) {
		return response.jsonPath().getList(key);
	}
}

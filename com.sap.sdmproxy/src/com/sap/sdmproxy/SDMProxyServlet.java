package com.sap.sdmproxy;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.mitre.dsmiley.httpproxy.ProxyServlet;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/** A proxy serlvet which communicates with SDM.
 * 
 *  Right now it has a hard coded SDM instance, client id and secret which is used to authenticate with the SDM instance.
 *  This should be improved with the credentials being read from the environment variables.
 *  Access token is also requested for each request. This should be improved to cache the token.
 * 
 * @author Saurav Sarkar
 *
 */
public class SDMProxyServlet  extends ProxyServlet{
	
	String tokenURL = "https://sdiconsumertest.authentication.sap.hana.ondemand.com/oauth/token";
	//Right now sdm-di service instance created in SDM-DI-Consumer-Qual account
	
	/**
	 * Adding the authorization header
	 */
	@Override
	protected void copyRequestHeaders(HttpServletRequest servletRequest, HttpRequest proxyRequest) {
		// TODO Auto-generated method stub
		super.copyRequestHeaders(servletRequest, proxyRequest);
		proxyRequest.addHeader("Authorization", "Bearer " + requestForToken());
	}
	
	//code inspired from https://github.wdf.sap.corp/ecm-cp/sdm-consumer-samples/blob/103ed330e424598ce8d4f6408a62790ba631e4aa/src/consumer/Consumer.java#L76
	private String requestForToken() {
		HttpResponse<JsonNode> jsonResponse;
		Object accessToken =null;
		try {
			jsonResponse = Unirest.post(tokenURL)
					.header("accept", "application/json")
					.field("client_id", "<client id>") // client id of the UAA clone of SDM service instance
					.field("client_secret", "<client secret>") //client secret of the UAA clone of SDM Service instance
					.field("grant_type", "client_credentials")
					.field("response_type", "token")
					//.field("authorities", this.additionalProperties)
					.asJson();
		
		if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
			log("Invalid response from UAA. Status code: " + String.valueOf(jsonResponse.getStatus()));
		}
		JSONObject response = jsonResponse.getBody().getObject();
		accessToken = response.get("access_token");
		if (accessToken == null) {
			log("No access token found. Response from UAA: " + response.toString());
		}
	}
		catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accessToken.toString();
	}

}

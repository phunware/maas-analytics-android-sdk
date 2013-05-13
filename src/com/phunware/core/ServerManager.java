package com.phunware.core;

class ServerManager {

	public static String getAnalyticsApi() {
		String baseUrl = "https://analytics-api.phunware.com/v1.0";
		String api = "reports";
		String applicationId = PwCoreSession.getInstance().getAccessKey();
		String reportType = "";
		String endpoint = baseUrl + "/" + api + "/" + applicationId + "/"
				+ reportType;
		
		return endpoint;
	}
}

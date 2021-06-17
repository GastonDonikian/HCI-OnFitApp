package com.example.hci_onfitapp.api.model;

public abstract class ApiService {
//    public static final String BASE_URL = "http://10.0.2.2:8080/api/";
    //Este lo uso yo (herni) que lo necesito para usar la api desde mi telefono localmente
    public static final String BASE_URL = "http://192.168.0.226:8080/api/";
    public static final int CONNECT_TIMEOUT = 10;
    public static final int READ_TIMEOUT = 60;
    public static final int WRITE_TIMEOUT = 60;
    private static String authToken = null;

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        ApiService.authToken = authToken;
    }

}

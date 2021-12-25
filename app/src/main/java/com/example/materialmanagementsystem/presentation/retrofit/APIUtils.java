package com.example.materialmanagementsystem.presentation.retrofit;

public class APIUtils {
    public static final String BASE_URL = "http://172.16.83.66/MMS/";
    //Get and sent data from server
    public static DataClient getData() {
        return RetrofitClient.getClient(BASE_URL).create(DataClient.class);
    }
}

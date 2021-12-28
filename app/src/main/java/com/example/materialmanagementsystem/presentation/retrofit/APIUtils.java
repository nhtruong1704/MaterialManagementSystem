package com.example.materialmanagementsystem.presentation.retrofit;

public class APIUtils {
    public static final String BASE_URL = "http://192.168.31.14/MMS/";
//   public static final String BASE_URL = "http://192.168.0.113/MMS/";
    //Get and sent data from server
    public static DataClient getData() {
        return RetrofitClient.getClient(BASE_URL).create(DataClient.class);
    }
}

package com.example.matthew.mvvmgoods.data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FixerService {

    @GET("/latest")
    Observable<FixerResponse> fetchLatest(@Query("access_key") String access_key);

    //with paid for API access you can use the convert end point which would be quicker than retrieving the exchange rate and converting manually
    @GET("/latest")
    Observable<FixerResponse> fetchSpecific(@Query("access_key") String access_key, @Query("symbols") String symbol);
}

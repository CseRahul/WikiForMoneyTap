package com.kumar.rahul.wikiformoneytap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
/**
 * @author Rahul Kumar on 08.07.2018.
 * @version 1.0
 *
 **/

public interface RequestInterface {

    //@GET("android/jsonandroid")
   // Call<JSONResponse> getJSON();
/*
    @GET("w/api.php")
    Call<JSONResponse> getJSON(
            @Query("action") String action,
            @Query("format") String format,
            @Query("prop") String prop,
            @Query("generator") String generator ,
            @Query("redirects") String redirects ,
            @Query("formatversion") String formatversion,
            @Query("piprop") String piprop,
            @Query("pithumbsize") String pithumbsize,
            @Query("pilimit") String pilimit,
            @Query("wbptterms") String wbptterms,
            @Query("gpssearch") String gpssearch
        );
*/
    @GET("w/api.php")
    Call<JSONWikiResponse> getJSON(
            @Query("action") String action,
            @Query("format") String format,
            @Query("prop") String prop,
            @Query("generator") String generator ,
            @Query("formatversion") String formatversion,
            @Query("wbptterms") String wbptterms,
            @Query("gpssearch") String gpssearch
    );
}
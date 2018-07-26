package org.newsapi.newsapidemo.api;

import org.newsapi.newsapidemo.model.ArticleDTO;
import org.newsapi.newsapidemo.model.SourceDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApiService {
    @GET("Sources")
    Call<SourceDTO> getSources();

    @GET("Everything")
    Call<ArticleDTO> getNewsEverything(
            @Query("sources") String sources,
            @Query("page") int page,
            @Query(value = "q",encoded = true) String query
    );

    @GET("Everything")
    Call<ArticleDTO> getNewsEverything(
            @Query("sources") String sources,
            @Query("page") int page
    );
}

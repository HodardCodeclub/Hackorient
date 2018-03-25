package rw.hackorient.dequeue;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by miller on 25/7/18.
 */

public interface HackOrientService {

    @FormUrlEncoded
    @GET("api/agencies")
    Observable<ResponseBody> getAgencies(@Query("id") String id, @Query("agencies") String agencies, @Query("point") String point, @Query("radius") Integer radius, @Query("bbox") String bbox, @Query("exclude") String exclude, @Query("limit") Integer limit, @Query("offset") Integer offset);

    @FormUrlEncoded
    @GET("api/agencies/{id}")
    Observable<ResponseBody> getAgence(@Path("id") String id);


    @FormUrlEncoded
    @GET("api/stops")
    Observable<ResponseBody> getStops(@Query("point") String point, @Query("radius") Integer radius, @Query("bbox") String bbox, @Query("modes") String modes, @Query("agencies") String agencies, @Query("servesLines") String servesLines, @Query("showChildren") Boolean showChildren, @Query("exclude") String exclude, @Query("limit") Integer limit, @Query("offset") Integer offset);

    @FormUrlEncoded
    @GET("api/stops/{id}")
    Observable<ResponseBody> getStop(@Path("id") String id);

    @FormUrlEncoded
    @GET("api/stops/{id}/stops")
    Observable<ResponseBody> getChildStop(@Path("id") String id);


    @FormUrlEncoded
    @GET("api/lines")
    Observable<ResponseBody> getLines(@Query("point") String point, @Query("radius") Integer radius, @Query("bbox") String bbox, @Query("modes") String modes, @Query("agencies") String agencies, @Query("servesStops") String servesStops, @Query("exclude") String exclude, @Query("limit") Integer limit, @Query("offset") Integer offset);

    @FormUrlEncoded
    @GET("api/lines/{id}")
    Observable<ResponseBody> getLine(@Path("id") String id);

    @FormUrlEncoded
    @GET("api/fareproducts")
    Observable<ResponseBody> getFareProducts(@Query("agencies") String agencies,@Query("exclude") String exclude, @Query("limit") Integer limit, @Query("offset") Integer offset);


    @FormUrlEncoded
    @GET("api/fareproducts/{id}")
    Observable<ResponseBody> getFareProduct(@Path("id") String id);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("https://identity.whereismytransport.com/connect/token")
    Observable<ResponseBody> getToken(@Field("client_id") String client_id, @Field("client_secret") String client_secret, @Field("grant_type") String grant_type, @Field("scope") String scope);


}

package rw.hackorient.dequeue;


import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by miller on 24/7/18.
 */

public class HackorientApi {

    public static  String mToken;
    private static final String TAG = "HackorientApi";
    private static final String SHURI_BUS_DRIVER_BASE_URL = "https://platform.whereismytransport.com/";

    private static HackorientApi instance;
    private HackOrientService hackOrientService;

    private HackorientApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .authenticator(new Authenticator() {
                    @Nullable
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        if (response.request().header("Authorization") != null) {
                            return null; // Give up, we've already attempted to authenticate.
                        }
                        Log.d(TAG,"Authenticating for response: " + response);
                        Log.d(TAG,"Challenges: " + response.challenges());
//                        String credential = Credentials.
                        if(mToken != null){
                            return response.request().newBuilder()
                                    .header("Authorization", "Bearer "+mToken)
                                    .build();
                        }
                        return  null;
                    }
                })
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SHURI_BUS_DRIVER_BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        hackOrientService = retrofit.create(HackOrientService.class);
    }

    public static HackorientApi getInstance(){
        if(instance == null){
            instance = new HackorientApi();
        }
        return  instance;
    }

    public Observable<ResponseBody> getAgencies(String id,String agencies,String point,Integer radius,String bbox,String exclude,Integer limit,Integer offset){

        return hackOrientService.getAgencies(id,agencies, point, radius, bbox, exclude, limit, offset);
    }

    public Observable<ResponseBody> getAgence(@NonNull String id){
        return  hackOrientService.getAgence(id);

    }

    public Observable<ResponseBody> getStops(String point, Integer radius, String bbox,String modes,String agencies,String servesLines, Boolean showChildren,String exclude, Integer limit,Integer offset){
        return  hackOrientService.getStops(point,  radius,  bbox, modes, agencies, servesLines,  showChildren,  exclude,  limit, offset);
    }

    public Observable<ResponseBody> getStop(@NonNull String id){
        return  hackOrientService.getAgence(id);

    }


    public  Observable<ResponseBody> getChildStop(@NonNull String id){
        return hackOrientService.getChildStop(id);
    }

    public   Observable<ResponseBody> getLines(String point, Integer radius,String bbox,String modes,String agencies, String servesStops, String exclude,Integer limit, Integer offset){
        return  hackOrientService.getLines( point, radius, bbox, modes, agencies, servesStops, exclude, limit, offset);
    }


    public  Observable<ResponseBody> getLine(@NonNull String id){
        return hackOrientService.getLine(id);
    }

    public Observable<ResponseBody> getFareProducts(String agencies,String exclude, Integer limit,Integer offset){
        return  hackOrientService.getFareProducts(agencies,exclude,limit,offset);
    }

    public  Observable<ResponseBody> getFareProduct(@NonNull String id){
        return hackOrientService.getFareProduct(id);
    }

    public Observable<ResponseBody> getToken(@NonNull  String client_id,@NonNull String client_secret,@NonNull  String grant_type,@Nullable String scope){
        return  hackOrientService.getToken(client_id, client_secret, grant_type, scope);
    }

}

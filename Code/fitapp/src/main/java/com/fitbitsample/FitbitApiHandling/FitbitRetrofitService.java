package com.fitbitsample.FitbitApiHandling;

import android.content.SharedPreferences;
import android.util.Log;


import com.fitbitsample.FitbitActivity.AppConstants;
import com.fitbitsample.FitbitActivity.FitbitDataFormat;
import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.BuildConfig;
import com.fitbitsample.FitbitSharedPref.AppPreference;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fitbitsample.FitbitActivity.PrefConstants.AUTHORIZATION;
import static com.fitbitsample.FitbitActivity.PrefConstants.CONTENT_TYPE;


/**
 * Handles the multi-connection networking for FitBit data.
 */
public class FitbitRetrofitService {
    public static final String BASE_URL = "https://api.fitbit.com/";
    private static final long CONNECT_TIMEOUT = 5000;
    private static final long WRITE_TIMEOUT = 5000;
    private static final long READ_TIMEOUT = 5000;
    private static SharedPreferences sharedPreferences;
    public static Retrofit getRestService(final boolean session, final String... accept) {

        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        b.readTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        b.writeTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        OkHttpClient okHttpClient = b.addInterceptor(logging).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                String fullAuthToken = AppPreference.getInstance().getString(PrefConstants.FULL_AUTHORIZATION, null);
                if (BuildConfig.DEBUG) {
                    if (session && FitbitDataFormat.isEmpty(fullAuthToken)) {
                        Log.i("OkHttp", "Error,not found");
                    } else if (session) {
                        Log.i("OkHttp", fullAuthToken);
                    }
                }
                Request.Builder builder = original.newBuilder()
                        .header("Accept", accept.length == 0 ? "application/json" : accept[0])
                        .header("User-Agent", "android");
                        /*.header("version-name", BuildConfig.VERSION_NAME)
                        .header("version-code", String.valueOf(BuildConfig.VERSION_CODE));*/
                if (fullAuthToken != null && session) {
                    builder.header(AUTHORIZATION, fullAuthToken);
                }else{
                    builder.header(CONTENT_TYPE, "application/x-www-form-urlencoded");
                    builder.header(AUTHORIZATION, "Basic "+ FitbitDataFormat.getEncodedValue(AppConstants.CLIENT_ID+":"+AppConstants.CLIENT_SECRET));
                }
                builder.method(original.method(), original.body());
                Request request = builder.build();
                return chain.proceed(request);
            }
        }).build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(Gsonbuilder.getGson()))
                .client(okHttpClient)
                .build();
    }
}

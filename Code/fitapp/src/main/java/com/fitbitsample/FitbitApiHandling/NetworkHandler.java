package com.fitbitsample.FitbitApiHandling;

import android.app.Activity;
import android.content.Context;

import com.fitbitsample.FitbitActivity.FitbitDataFormat;
import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.FitbitSharedPref.AppPreference;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * This class does the network handling
 * required for fitbit integration and API calling
 */
class NetworkHandler<T> {
    private Context context;
    private Call<T> call;
    private int retryCount;
    private boolean askUserForRetry;
    private NetworkListener<T> networkListener;
    private int currentRetryCount = 0;
    private int renewRetryCount = 0;
    private boolean progressBar = true;
    private Integer[] acceptCodes = {200, 201, 204}; // Server callback codes

    private NetworkHandler() {
    }

    NetworkHandler(Context context, Call<T> call) {
        this.context = context;
        this.call = call;
    }

    NetworkHandler setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    NetworkHandler askUserForRetry(boolean askUserForRetry) {
        this.askUserForRetry = askUserForRetry;
        return this;
    }

    NetworkHandler setProgressBar(boolean progressBar) {
        this.progressBar = progressBar;
        return this;
    }

    /**
     * Execute an asynchronous call with given listener.
     * @param networkListener Listener for callback
     */
    void execute(NetworkListener<T> networkListener) {
        currentRetryCount = 0;
        this.networkListener = networkListener;
        if (call != null) {
            enqueue();
        }
    }

    private void enqueue() {
        if (!isAlive()) // Make sure call is still good
        {
            return;
        }
        // Make call
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                for (String name : response.headers().names())
                {
                }
                if (isAlive() && networkListener != null)
                {
                    if (Arrays.asList(acceptCodes).contains(response.code()))
                    {
                        Map<String, String> headers = new HashMap<>();
                        if (response.headers() != null)
                        {
                            for (String name : response.headers().names()) // Save headers
                            {
                                headers.put(name, response.headers().get(name));
                            }
                        }
                        networkListener.success(response.body()); // Send response
                        networkListener.headers(headers); // Send headers
                    }
                    logResponse(response);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (askUserForRetry) {
                    if (context instanceof Activity) {
                        try {
                            if (isAlive() && networkListener != null) {
                                networkListener.failure();
                            }
                        } catch (Exception ignored) {
                        }
                    } else {
                        if (isAlive() && networkListener != null) {
                            networkListener.failure();
                        }
                    }
                } else {
                    if (retryCount > currentRetryCount) {
                        currentRetryCount++;
                        restart();
                    } else {
                        if (isAlive() && networkListener != null) {
                            networkListener.failure();
                        }
                    }
                }
            }
        });
    }

    private boolean isAlive() {
        return !(context instanceof Activity) || !(((Activity) context).isFinishing() || ((Activity) context).isDestroyed());
    }

    private void logResponse(final Response<T> response) {
        try {
            if (AppPreference.getInstance().getBoolean(PrefConstants.START_LOG)) {
                String url = response.raw().request().url().toString();
                String method = response.raw().request().method();
                String requestBody = null;
                String responseBody = null;
                if (response.raw().request().body() != null) {
                    final RequestBody copy = response.raw().request().body();
                    final Buffer buffer = new Buffer();
                    if (copy != null) {
                        copy.writeTo(buffer);
                        requestBody = buffer.readUtf8();
                    }
                }
                if (Arrays.asList(acceptCodes).contains(response.code())) {
                    if (response.body() != null) {
                        responseBody = response.body().toString();
                    }
                } else {
                    responseBody = "Failed";
                }
                StringBuilder builder = new StringBuilder();
                builder.append(String.format("Url: %s \n", url));
                builder.append(String.format("method: %s \n", method));
                if (!FitbitDataFormat.isEmpty(requestBody)) {
                    builder.append(String.format("Request Body --> %s \n", requestBody));
                }
                builder.append(String.format("Response code <-- %s \n", response.code()));
                if (!FitbitDataFormat.isEmpty(responseBody)) {
                    builder.append(String.format("Response <-- %s \n", responseBody));
                }
            }
        } catch (final IOException e) {
        }

    }

    private void restart() {
        if (call != null) {
            call = call.clone();
            enqueue();
        }
    }


    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }
}
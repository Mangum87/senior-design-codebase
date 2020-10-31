package com.fitbitsample.FitbitApiHandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Builds the gson file received from fitbit API and setting the date
 */
public class Gsonbuilder {
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create();

    public static Gson getGson() {
        return gson;
    }
}

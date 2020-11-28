package com.fitbitsample.FitbitApiHandling;

import java.util.List;
import java.util.Map;
/**
 * This interface serves as Listener for the Network Handler
 */
public interface NetworkListener<T> {

    void success(T t);

    void headers(Map<String, String> header);

    void failure();
}

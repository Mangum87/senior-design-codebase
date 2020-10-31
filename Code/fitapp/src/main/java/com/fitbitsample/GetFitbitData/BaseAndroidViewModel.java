package com.fitbitsample.GetFitbitData;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitApiHandling.FitbitAPIcalls;
import com.fitbitsample.FitbitApiHandling.FitbitRetrofitService;

/**
 * This class handles Fitbit API calls, sessions, and any generated error code.
 */
abstract class BaseAndroidViewModel<LiveData, Response, Request, Base> implements LifecycleObserver {
    RestCall<Response> restCall;
    FitbitAPIcalls fitbitAPIcalls;
    int errorCode;

    final MutableLiveData<LiveData> data = new MutableLiveData<>();

    abstract Base run(Context context, Request request);

    public BaseAndroidViewModel(boolean session, Lifecycle lifecycle) {
        fitbitAPIcalls = FitbitRetrofitService.getRestService(session).create(FitbitAPIcalls.class);
        lifecycle.addObserver(this);
    }

    BaseAndroidViewModel(boolean session, int errorCode) {
        this.errorCode = errorCode;
        fitbitAPIcalls = FitbitRetrofitService.getRestService(session).create(FitbitAPIcalls.class);
    }

    BaseAndroidViewModel(boolean session, String accept) {
        fitbitAPIcalls = FitbitRetrofitService.getRestService(session, accept).create(FitbitAPIcalls.class);
    }

    BaseAndroidViewModel(boolean session) {
        fitbitAPIcalls = FitbitRetrofitService.getRestService(session).create(FitbitAPIcalls.class);
    }

    public MutableLiveData<LiveData> getData() {
        return data;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        if (restCall != null) {
            restCall.cancel();
        }
    }

    void reset() {
        data.setValue(null);
    }
}

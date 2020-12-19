package com.fitbitsample.GetFitbitData;

import android.content.Context;

import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitDataType.Hourly.HourlyCalorie;
import com.fitbitsample.FitbitDataType.Hourly.HourlyStep;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FitbitSharedPref.FitbitPref;

import java.util.Map;

public class StepsModel extends BaseAndroidViewModel<Integer, HourlyStep, String, StepsModel>
{
    public StepsModel(int errorCode) {
        super(true, errorCode);
    }


    @Override
    public StepsModel run(final Context context, final String date)
    {
        restCall = new RestCall<>(context, true);

        restCall.execute(fitbitAPIcalls.getHourlyStep(AppPreference.getInstance().getString(PrefConstants.USER_ID), date), new NetworkListener<HourlyStep>() {
            @Override
            public void success(HourlyStep response) {
                if (response != null)
                {
                    // Save to SharedPreferences instance
                    FitbitPref.getInstance(context).setStepData(response);
                    data.postValue(0); // Send success code to observer
                } else {
                    data.postValue(errorCode);
                }
            }

            @Override
            public void headers(Map<String, String> header) {}

            @Override
            public void failure()
            {
                data.postValue(errorCode);
            }
        });
        return this;
    }
}

package com.fitbitsample.GetFitbitData;

import android.content.Context;

import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitDataType.Hourly.HourlyDistance;
import com.fitbitsample.FitbitDataType.Hourly.HourlyElevation;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FitbitSharedPref.FitbitPref;

import java.util.Map;

public class ElevationModel extends BaseAndroidViewModel<Integer, HourlyElevation, String, ElevationModel>
{
    public ElevationModel(int errorCode) {
        super(true, errorCode);
    }


    @Override
    public ElevationModel run(final Context context, final String date)
    {
        restCall = new RestCall<>(context, true);
        restCall.execute(fitbitAPIcalls.getHourlyElevation(AppPreference.getInstance().getString(PrefConstants.USER_ID), date), new NetworkListener<HourlyElevation>() {
            @Override
            public void success(HourlyElevation response) {
                if (response != null)
                {
                    // Save to SharedPreferences instance
                    FitbitPref.getInstance(context).saveElevationData(response);
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

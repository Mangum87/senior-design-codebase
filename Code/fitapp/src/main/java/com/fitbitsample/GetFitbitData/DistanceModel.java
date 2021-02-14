package com.fitbitsample.GetFitbitData;

import android.content.Context;
import android.os.StrictMode;

import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitDataType.Hourly.HourlyDistance;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FitbitSharedPref.FitbitPref;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class DistanceModel extends BaseAndroidViewModel<Integer, HourlyDistance, String, DistanceModel>
{
    public DistanceModel(int errorCode) {
        super(true, errorCode);
    }


    @Override
    public DistanceModel run(final Context context, final String date)
    {
        /*if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Call<HourlyDistance> call = fitbitAPIcalls.getHourlyDistance(AppPreference.getInstance().getString(PrefConstants.USER_ID), date);
        try
        {
            Response<HourlyDistance> res = call.execute();
            HourlyDistance dist = res.body();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        restCall = new RestCall<>(context, true);

        restCall.execute(fitbitAPIcalls.getHourlyDistance(AppPreference.getInstance().getString(PrefConstants.USER_ID), date), new NetworkListener<HourlyDistance>() {
            @Override
            public void success(HourlyDistance response) {
                if (response != null)
                {
                    // Save to SharedPreferences instance
                    FitbitPref.getInstance(context).saveDistanceData(response);
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

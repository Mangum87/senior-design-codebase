package com.fitbitsample.GetFitbitData;

import android.content.Context;
import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitDataType.Hourly.HourlyCalorie;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FitbitSharedPref.FitbitPref;

import java.util.Map;

public class CaloriesModel extends BaseAndroidViewModel<Integer, HourlyCalorie, String, CaloriesModel>
{
    public CaloriesModel(int errorCode) {
        super(true, errorCode);
    }


    @Override
    public CaloriesModel run(final Context context, final String date)
    {
        restCall = new RestCall<>(context, true);

        restCall.execute(fitbitAPIcalls.getHourlyCalorie(AppPreference.getInstance().getString(PrefConstants.USER_ID), date), new NetworkListener<HourlyCalorie>() {
            @Override
            public void success(HourlyCalorie response) {
                if (response != null)
                {
                    // Save to SharedPreferences instance
                    FitbitPref.getInstance(context).setCalorieData(response);
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

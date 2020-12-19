package com.fitbitsample.GetFitbitData;

import android.content.Context;

import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitDataType.Hourly.HourlyDistance;
import com.fitbitsample.FitbitDataType.Hourly.HourlyFloor;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FitbitSharedPref.FitbitPref;

import java.util.Map;

public class FloorModel extends BaseAndroidViewModel<Integer, HourlyFloor, String, FloorModel>
{
    public FloorModel(int errorCode) {
        super(true, errorCode);
    }


    @Override
    public FloorModel run(final Context context, final String date)
    {
        restCall = new RestCall<>(context, true);
        restCall.execute(fitbitAPIcalls.getHourlyFloor(AppPreference.getInstance().getString(PrefConstants.USER_ID), date), new NetworkListener<HourlyFloor>() {
            @Override
            public void success(HourlyFloor response) {
                if (response != null)
                {
                    // Save to SharedPreferences instance
                    FitbitPref.getInstance(context).saveFloorsData(response);
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

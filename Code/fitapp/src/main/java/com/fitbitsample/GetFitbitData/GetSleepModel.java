package com.fitbitsample.GetFitbitData;


import android.content.Context;

import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitDataType.SleepData.Sleep;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FitbitSharedPref.FitbitPref;
import com.fitbitsample.PaperConstants;
import com.fitbitsample.PaperDB;
import java.util.Map;


public class GetSleepModel extends BaseAndroidViewModel<Integer, Sleep, String, GetSleepModel>
{
    public GetSleepModel(int errorCode) {
        super(true, errorCode);
    }

    @Override
    public GetSleepModel run(final Context context, final String date)
    {
        restCall = new RestCall<>(context, true);

        restCall.execute(fitbitAPIcalls.getSleep(AppPreference.getInstance().getString(PrefConstants.USER_ID), date), new NetworkListener<Sleep>() {
            @Override
            public void success(Sleep response) {
                if (response != null)
                {
                    // Save to SharedPreferences instance
                    FitbitPref.getInstance(context).setSleepData(response);

                    // Save to local DB
                    PaperDB.getInstance().write(PaperConstants.SLEEP, response);
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

package com.fitbitsample.GetFitbitData;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.FitbitDataType.HeartRateZone;
import com.fitbitsample.PaperConstants;
import com.fitbitsample.PaperDB;
import com.fitbitsample.FitbitSharedPref.FitbitPref;
import com.fitbitsample.FitbitSharedPref.HeartRateInfo;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FitbitDataType.HeartRate;

import java.util.List;
import java.util.Map;


/**
 * This class makes the fitbit API call to get the heart data of the entire day.
 * The application saves the information in both PaperDB and Sharedpreference,
 * Shared preference can let you access the data any where in the application while
 * Paper DB can be utilized within the module.
 */
public class GetHrModel extends BaseAndroidViewModel<Integer, HeartRate, String[], GetHrModel> {
    private Integer averageheartrate;

    public GetHrModel(int errorCode) {
        super(true, errorCode);
    }

    @Override
    public GetHrModel run(final Context context, final String... params) {
        restCall = new RestCall<>(context, true);
        restCall.execute(fitbitAPIcalls.getHrByRange(AppPreference.getInstance().getString(PrefConstants.USER_ID), params[0], params[1]), new NetworkListener<HeartRate>() {
            @Override
            public void success(HeartRate heartRate) {
                if (heartRate != null) {
                    Log.i("Hr:", heartRate.toString());

                    // Save the heart rate info to new object
                    HeartRateInfo info = new HeartRateInfo(heartRate.getActivitiesHeart().get(0).getValue().getRestingHeartRate());
                    List<HeartRateZone> zones = heartRate.getActivitiesHeart().get(0).getValue().getHeartRateZones();
                    HeartRateZone z = zones.get(0);
                    info.setRange(z.getCaloriesOut(), z.getMin(), z.getMax(), z.getMinutes());

                    z = zones.get(1);
                    info.setFat(z.getCaloriesOut(), z.getMin(), z.getMax(), z.getMinutes());

                    z = zones.get(2);
                    info.setCardio(z.getCaloriesOut(), z.getMin(), z.getMax(), z.getMinutes());

                    z = zones.get(3);
                    info.setPeak(z.getCaloriesOut(), z.getMin(), z.getMax(), z.getMinutes());


                    // Save object to PaperDB
                    FitbitPref.getInstance(context).saveHeartData(info);
                    PaperDB.getInstance().write(PaperConstants.HEART_RATE, heartRate);
                    data.postValue(0);
                } else {
                    data.postValue(errorCode);
                }
            }

            @Override
            public void headers(Map<String, String> header) {

            }
            @Override
            public void failure() {
                data.postValue(errorCode);
            }
        });
        return this;
    }
}

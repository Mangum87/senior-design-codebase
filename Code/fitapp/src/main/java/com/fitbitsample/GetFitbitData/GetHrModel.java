package com.fitbitsample.GetFitbitData;

import android.content.Context;
import android.util.Log;

import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.PaperConstants;
import com.fitbitsample.PaperDB;
import com.fitbitsample.FitbitSharedPref.FitbitPref;
import com.fitbitsample.FitbitSharedPref.HeartRateInfo;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FitbitDataType.HeartRate;

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
                    //The shared preference is used to push the data in S3 bucket so, removing all the comma's, tab, and new line while saving the heart data in shared preference
                    FitbitPref.getInstance(context).saveHeartData(new HeartRateInfo(heartRate.toString().replaceAll("[\\n\\t ]", "").replace(",","|")));
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

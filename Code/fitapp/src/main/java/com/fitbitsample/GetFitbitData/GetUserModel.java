package com.fitbitsample.GetFitbitData;

import android.content.Context;
import android.util.Log;

import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.PaperConstants;
import com.fitbitsample.PaperDB;
import com.fitbitsample.FitbitSharedPref.FitbitPref;
import com.fitbitsample.FitbitSharedPref.FitbitUser;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FitbitDataType.UserInfo;

import java.util.Map;


/**
 * This class makes the fitbit API call to get the User information.
 * The application saves the information in both PaperDB and Sharedpreference,
 * Shared preference can let you access the data any where in the application while
 * Paper DB can be utilized within the module.
 */
public class GetUserModel extends BaseAndroidViewModel<Integer, UserInfo, Void, GetUserModel> {
    private String dateOfBirth, fullName, gender, height, weight, age;
    public GetUserModel(int errorCode) {
        super(true, errorCode);
    }

    @Override
    public GetUserModel run(final Context context, Void aVoid) {

        restCall = new RestCall<>(context, true);
        restCall.execute(fitbitAPIcalls.getUserProfile(AppPreference.getInstance().getString(PrefConstants.USER_ID)), new NetworkListener<UserInfo>() {
            @Override
            public void success(UserInfo userInfo) {
                if (userInfo != null) {
                    Log.i("User Profile:", userInfo.toString());

                    dateOfBirth = userInfo.getUser().getDateOfBirth();
                    fullName = userInfo.getUser().getFullName();
                    gender = userInfo.getUser().getGender();
                    height = userInfo.getUser().getHeight().toString();
                    weight = userInfo.getUser().getWeight().toString();
                    age = userInfo.getUser().getAge().toString();

                    FitbitUser fitbitUser = new FitbitUser(dateOfBirth,fullName,gender,height,weight,age);
                    FitbitPref.getInstance(context).savefitbitdata(fitbitUser);

                    PaperDB.getInstance().write(PaperConstants.PROFILE, userInfo);
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

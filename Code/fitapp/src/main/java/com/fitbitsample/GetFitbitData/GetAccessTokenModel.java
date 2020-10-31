package com.fitbitsample.GetFitbitData;

import android.content.Context;

import com.fitbitsample.FitbitActivity.AppConstants;
import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.PaperConstants;
import com.fitbitsample.PaperDB;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FragmentTraceManager.Trace;
import com.fitbitsample.FitbitDataType.OAuthResponse;

import java.util.Map;

import static com.fitbitsample.FitbitActivity.AppConstants.REDIRECT_URI;
import static com.fitbitsample.FitbitActivity.PrefConstants.CODE;
import static com.fitbitsample.FitbitActivity.PrefConstants.GRANT_TYPE;


/**
 * This class retrieves access token from fitbit which expires in 8 hours.
 */
public class GetAccessTokenModel extends BaseAndroidViewModel<Integer, OAuthResponse, Void, GetAccessTokenModel> {

    public GetAccessTokenModel(int errorCode) {
        super(false, errorCode);
    }

    @Override
    public GetAccessTokenModel run(final Context context, final Void aVoid) {
        String code = AppPreference.getInstance().getString(CODE);
        restCall = new RestCall<>(context, true);

        restCall.execute(fitbitAPIcalls.getAccessToken(AppConstants.CLIENT_ID, GRANT_TYPE, REDIRECT_URI, code), 3, new NetworkListener<OAuthResponse>() {
            @Override
            public void success(OAuthResponse response) {
                if (response != null)
                {
                    AppPreference app = AppPreference.getInstance();
                    app.putBoolean(PrefConstants.HAVE_AUTHORIZATION, true);
                    app.putString(PrefConstants.TOKEN_TYPE, response.getTokenType());
                    app.putString(PrefConstants.REFRESH_TOKEN, response.getRefreshToken());
                    app.putString(PrefConstants.FULL_AUTHORIZATION, response.getTokenType() + " " + response.getAccessToken());
                    app.putString(PrefConstants.USER_ID, response.getUserId());

                    response.setExpireTime(); // Set expiration time
                    app.putString(PrefConstants.EXPIRE_TIME, String.valueOf(response.getExpireTime())); // Set time from long

                    // Save to local DB
                    PaperDB.getInstance().write(PaperConstants.OAUTH_RESPONSE, response);
                    Trace.i("Response Access token:" + response.toString());
                    data.postValue(0);
                } else {
                    data.postValue(errorCode);
                }
            }

            @Override
            public void headers(Map<String, String> header) {

            }

            @Override
            public void failure()
            {
                data.postValue(errorCode);
            }
        });
        return this;
    }

}

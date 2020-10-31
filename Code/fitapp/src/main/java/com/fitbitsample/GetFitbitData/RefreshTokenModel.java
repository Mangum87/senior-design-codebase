package com.fitbitsample.GetFitbitData;

import android.content.Context;

import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitDataType.OAuthResponse;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.PaperConstants;
import com.fitbitsample.PaperDB;

import java.util.Map;


/**
 * A model that handles refreshing a token and saving the new OAuth to PaperDB
 * and SharedPreferences.
 */
public class RefreshTokenModel extends BaseAndroidViewModel<Integer, OAuthResponse, Void, RefreshTokenModel>
{
    public RefreshTokenModel(int errorCode)
    {
        super(false, errorCode);
    }

    @Override
    public RefreshTokenModel run(final Context context, final Void aVoid)
    {
        // Vars needed for call
        AppPreference pref = AppPreference.getInstance();
        String grant = "refresh_token"; // Grant type
        String refresh = pref.getString(PrefConstants.REFRESH_TOKEN);

        // Make the call
        restCall = new RestCall<>(context, true);
        restCall.execute(fitbitAPIcalls.refreshToken(grant, refresh), 3, new NetworkListener<OAuthResponse>()
        {
            @Override
            public void success(OAuthResponse response)
            {
                if(response != null) // Make sure not null
                {
                   // Rewrite token data in app
                    AppPreference app = AppPreference.getInstance();
                    app.putBoolean(PrefConstants.HAVE_AUTHORIZATION, true);
                    app.putString(PrefConstants.TOKEN_TYPE, response.getTokenType());
                    app.putString(PrefConstants.REFRESH_TOKEN, response.getRefreshToken());
                    app.putString(PrefConstants.FULL_AUTHORIZATION, response.getTokenType() + " " + response.getAccessToken());
                    app.putString(PrefConstants.USER_ID, response.getUserId());

                    response.setExpireTime(); // Set expiration time
                    app.putString(PrefConstants.EXPIRE_TIME, String.valueOf(response.getExpireTime())); // Set time from long
                    PaperDB.getInstance().write(PaperConstants.OAUTH_RESPONSE, response); // Rewrite token

                    data.postValue(0);
                }
                else { data.postValue(errorCode); }
            }


            @Override
            public void headers(Map<String, String> header)
            {
            }


            @Override
            public void failure() {
                data.postValue(errorCode);
            }
        });


        return this; // Return when done
    }
}

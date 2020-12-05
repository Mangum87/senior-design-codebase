package com.fitbitsample.GetFitbitData;

import android.content.Context;
import android.util.Log;

import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitDataType.Device;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FitbitSharedPref.FitbitPref;
import com.fitbitsample.PaperConstants;
import com.fitbitsample.PaperDB;
import java.util.Map;

public class GetDevicesModel extends BaseAndroidViewModel<Integer, Device, Void, GetDevicesModel>
{
    public GetDevicesModel(int errorCode) {
        super(true, errorCode);
    }

    @Override
    public GetDevicesModel run(final Context context, Void v)
    {
        restCall = new RestCall<>(context, true);

        restCall.execute(fitbitAPIcalls.getDevices(AppPreference.getInstance().getString(PrefConstants.USER_ID)), new NetworkListener<Device>() {
            @Override
            public void success(Device response) {
                if (response != null)
                {
                    // Save to SharedPreferences instance
                    FitbitPref.getInstance(context).setDeviceData(response);
                    Log.i("Device: ", response.toString());

                    // Save to local DB
                    PaperDB.getInstance().write(PaperConstants.DEVICE, response);
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

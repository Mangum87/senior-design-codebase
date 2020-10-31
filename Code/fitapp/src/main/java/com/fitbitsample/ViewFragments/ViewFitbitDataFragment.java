package com.fitbitsample.ViewFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.fitbitsample.FitbitDataType.OAuthResponse;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.GetFitbitData.RefreshTokenModel;
import com.fitbitsample.R;
import com.fitbitsample.FitbitActivity.FitbitDataFormat;
import com.fitbitsample.FitbitActivity.MainActivity;
import com.fitbitsample.databinding.FragmentDashboardBinding;
import com.fitbitsample.PaperConstants;
import com.fitbitsample.PaperDB;
import com.fitbitsample.FragmentTraceManager.Trace;
import com.fitbitsample.GetFitbitData.GetActivityModel;
import com.fitbitsample.GetFitbitData.GetHrModel;
import com.fitbitsample.GetFitbitData.GetUserModel;
import com.fitbitsample.FitbitDataType.HeartRate;
import com.fitbitsample.FitbitDataType.ActivityInfo;
import com.fitbitsample.FitbitDataType.UserInfo;

import java.util.Date;
/*
    This fragment is for temporary data viewing purpose only.
    The data can be seen in this fragment page while clicking on
    Sync with fitbit button on Settings Panel
 */

public class ViewFitbitDataFragment extends MainFragment {

    private FragmentDashboardBinding dashboardBinding;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setRetainInstance(true);
        resources = getResources();
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);

        return dashboardBinding.getRoot();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }


    public void resume()
    {
        if (getUserVisibleHint())
        {
            // Get saved OAuth response
            OAuthResponse response = PaperDB.getInstance().get().read(PaperConstants.OAUTH_RESPONSE);

            // Check if needs updating
            if(response != null && response.isTokenExpired())
            {
                RefreshTokenModel refresh = new RefreshTokenModel(2);
                refresh.run(context, null).getData().observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer i)
                    {
                        if(i != null && i == 0) // 0 posted on success
                            getData(); // Token refreshed
                    }
                });
            }
            else // No update needed
                getData();
        }
    }


    /**
     * Polls FitBit API for user data.
     */
    private void getData()
    {
        ((MainActivity) context).setTitle(getString(R.string.dashboard));
        getUserProfile();
        getActivityInfo();
        getHeartRate();
    }

    private void getUserProfile() {
        GetUserModel getUserModel = new GetUserModel(1);
        getUserModel.run(context, null).getData().observe(this, new Observer<Integer>(){
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null && integer > 0) {
                    Trace.i("get userInfo failed");
                } else {
                    Trace.i("userInfo fetching is done");
                    updateUi();
                }
            }
        });
    }

    private void getHeartRate() {
        GetHrModel hrModel = new GetHrModel(1);
        hrModel.run(context, FitbitDataFormat.convertDateFormat(new Date()), "1d").getData().observe(this, new Observer<Integer>(){
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null && integer > 0) {
                    Trace.i("get Hr failed");
                } else {
                    Trace.i("Hr fetching is done");
                    updateHr();
                }
            }
        });
    }

    private void getActivityInfo() {
        GetActivityModel activityModel = new GetActivityModel(1);
        activityModel.run(context,FitbitDataFormat.convertDateFormat(new Date())).getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null && integer > 0) {
                    Trace.i("get sleep failed");
                } else {
                    Trace.i("sleep fetching is done");
                    updateActivities();
                }
            }
        });
    }



    private void updateUi() {
        UserInfo userInfo = PaperDB.getInstance().get().read(PaperConstants.PROFILE, null);
        if (userInfo != null) {
            dashboardBinding.setUser(userInfo.toString());
        }
    }

    private void updateHr() {
        HeartRate heartRate = PaperDB.getInstance().get().read(PaperConstants.HEART_RATE, null);
        if (heartRate != null) {
            dashboardBinding.setHr(heartRate.toString());
        }
    }

    private void updateActivities() {
        ActivityInfo activityInfo = PaperDB.getInstance().get().read(PaperConstants.ACTIVITY_INFO, null);
        if (activityInfo != null) {
            dashboardBinding.setActivity(activityInfo.toString());
        }

    }

}

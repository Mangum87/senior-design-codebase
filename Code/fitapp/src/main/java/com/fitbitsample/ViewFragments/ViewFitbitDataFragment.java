package com.fitbitsample.ViewFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.fitbitsample.FitbitDataType.OAuthResponse;
import com.fitbitsample.FitbitDataType.SleepData.Sleep;
import com.fitbitsample.FitbitSharedPref.FitbitPref;
import com.fitbitsample.GetFitbitData.CaloriesModel;
import com.fitbitsample.GetFitbitData.DistanceModel;
import com.fitbitsample.GetFitbitData.ElevationModel;
import com.fitbitsample.GetFitbitData.FloorModel;
import com.fitbitsample.GetFitbitData.GetSleepModel;
import com.fitbitsample.GetFitbitData.HeartModel;
import com.fitbitsample.GetFitbitData.RefreshTokenModel;
import com.fitbitsample.GetFitbitData.StepsModel;
import com.fitbitsample.R;
import com.fitbitsample.FitbitActivity.FitbitDataFormat;
import com.fitbitsample.FitbitActivity.MainActivity;
import com.fitbitsample.databinding.FragmentDashboardBinding;
import com.fitbitsample.PaperConstants;
import com.fitbitsample.PaperDB;
import com.fitbitsample.FragmentTraceManager.Trace;
import com.fitbitsample.GetFitbitData.GetActivityModel;
import com.fitbitsample.GetFitbitData.GetUserModel;
import com.fitbitsample.FitbitDataType.HeartRate;
import com.fitbitsample.FitbitDataType.ActivityInfo;
import com.fitbitsample.FitbitDataType.UserInfo;

import java.util.Date;
/**
    This fragment is for temporary data viewing purpose only.
    The data can be seen in this fragment page while clicking on
    Sync with fitbit button on Settings Panel
 */
public class ViewFitbitDataFragment extends MainFragment {

    private FragmentDashboardBinding dashboardBinding;
    private Context context;


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
     * Sync the current day of FitBit data without
     * calling the fragment stack.
     * @param c Context for SharedPreferences
     */
    public void sync(Context c)
    {
        FitbitPref.getInstance(c); // Make sure the structure is initialized

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


    /**
     * Polls FitBit API for user data.
     */
    private void getData()
    {
        //((MainActivity) context).setTitle(getString(R.string.dashboard));
        getUserProfile();
        getActivityInfo();
        getSleep();
        getIntraday();
    }


    /**
     * Collect the various intraday data.
     */
    private void getIntraday()
    {
        getCalorie();
        getStep();
        getHeart();
        getDistance();
        getFloors();
        getElevation();
    }


    /**
     * Get the intraday elevation data.
     */
    private void getElevation()
    {
        ElevationModel model = new ElevationModel(1);
        String date = FitbitDataFormat.convertDateFormat(new Date());

        model.run(context, date).getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i)
            {
                if(i != null && i == 0)
                {
                    Trace.i("Elevation collected");
                }
                else
                {
                    Trace.i("Elevation failed");
                }
            }
        });
    }


    /**
     * Get the intraday floors data.
     */
    private void getFloors()
    {
        FloorModel model = new FloorModel(1);
        String date = FitbitDataFormat.convertDateFormat(new Date());

        model.run(context, date).getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i)
            {
                if(i != null && i == 0)
                {
                    Trace.i("Floors collected");
                }
                else
                {
                    Trace.i("Floors failed");
                }
            }
        });
    }


    /**
     * Collect the intraday distance.
     */
    private void getDistance()
    {
        DistanceModel model = new DistanceModel(1);
        String date = FitbitDataFormat.convertDateFormat(new Date());

        model.run(context, date).getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i)
            {
                if(i != null && i == 0)
                {
                    Log.i("Custom", "Distance collected");
                }
                else
                {
                    Log.i("Custom", "Distance failed");
                }
            }
        });
    }


    /**
     * Collect the intraday heart data.
     */
    private void getHeart()
    {
        HeartModel model = new HeartModel(1);
        String date = FitbitDataFormat.convertDateFormat(new Date());

        model.run(context, date).getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i)
            {
                if(i != null && i == 0)
                {
                    Trace.i("Heart collected");
                }
                else
                {
                    Trace.i("Heart failed");
                }
            }
        });
    }


    /**
     * Collect the step data.
     */
    private void getStep()
    {
        StepsModel model = new StepsModel(1);
        String date = FitbitDataFormat.convertDateFormat(new Date());

        model.run(context, date).getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i)
            {
                if(i != null && i == 0)
                {
                    Trace.i("Steps collected");
                }
                else
                {
                    Trace.i("Steps failed");
                }
            }
        });
    }


    /**
     * Collect the calorie data.
     */
    private void getCalorie()
    {
        CaloriesModel model = new CaloriesModel(1);
        String date = FitbitDataFormat.convertDateFormat(new Date());

        model.run(context, date).getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i)
            {
                if(i != null && i == 0)
                {
                    Trace.i("Calories collected");
                }
                else
                {
                    Trace.i("Calories failed");
                }
            }
        });
    }


    /**
     * Calls FitBit to get sleep data for current day.
     */
    private void getSleep()
    {
        GetSleepModel model = new GetSleepModel(1);
        String date = FitbitDataFormat.convertDateFormat(new Date());

        // Run the call and listen to code update
        model.run(context, date).getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i)
            {
                if(i != null && i == 0)
                {
                    Trace.i("Sleep data was successful");
                    updateSleep();
                }
                else
                {
                    Trace.i("Sleep data failed");
                }
            }
        });
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

    /*private void getHeartRate() {
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
    }*/

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


    /**
     * Read what was saved in PaperDB and update the dashboard.
     */
    private void updateSleep()
    {
        Sleep sleep = PaperDB.getInstance().get().read(PaperConstants.SLEEP, null);
        if (sleep != null && sleep.getSleep().size() > 0) {
            dashboardBinding.setSleep(sleep.toString());
        }
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

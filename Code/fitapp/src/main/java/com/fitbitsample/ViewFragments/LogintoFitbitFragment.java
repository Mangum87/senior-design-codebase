package com.fitbitsample.ViewFragments;


import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.fitbitsample.FitbitActivity.AppConstants;
import com.fitbitsample.R;
import com.fitbitsample.FitbitActivity.MainActivity;
import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FragmentTraceManager.Trace;
import com.fitbitsample.GetFitbitData.GetAccessTokenModel;

/*
    Login Page for fitbit Authorization in google custom tab
 */
public class LogintoFitbitFragment extends MainFragment {


    Boolean haveToken;
    public static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    private CustomTabsClient mClient;
    private CustomTabsSession mCustomTabsSession;
    private CustomTabsServiceConnection mCustomTabsServiceConnection;
    private CustomTabsIntent customTabsIntent;
    private Button redirecttofitbit;


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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        redirecttofitbit = view.findViewById(R.id.button);
        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        speedUpChromeTabs();
        CustomTabsClient.bindCustomTabsService(getActivity(), CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);
        customTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setToolbarColor(ContextCompat.getColor(getActivity(), R.color.primary))
                .setShowTitle(true)
                .build();
        haveToken = AppPreference.getInstance().getBoolean(PrefConstants.HAVE_AUTHORIZATION, false);

        /*
        Here use the client Id received after registration of the app.
        */
        redirecttofitbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!haveToken) {
                    String url = "https://www.fitbit.com/oauth2/authorize?" +
                            "response_type=code" +
                            "&client_id=" + AppConstants.CLIENT_ID +
                            "&expires_in=604800" +
                            "&scope=activity%20nutrition%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight" +
                            "&redirect_uri=fit://logincallback" +
                            "&prompt=login";
                    customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
                } else {
                    Toast.makeText(getActivity(),
                            "Already logged in. Please go Back", Toast.LENGTH_SHORT).show();
                }
            }
        });
        verifyAuthCode();
    }

    private void verifyAuthCode() {
        if (AppPreference.getInstance().getBoolean(PrefConstants.IS_CODE_RECEIVED) && !haveToken) {
            GetAccessTokenModel accessTokenModel = new GetAccessTokenModel(2);
            accessTokenModel.run(context, null).getData().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer integer) {
                    if (integer != null && integer > 0) {
                        Trace.i("AccessToken fetching failed");
                    } else {
                        Trace.i("Access Token fetching is done");
                        showDashboard();
                    }
                }
            });
        }
    }

    private void showDashboard() {
        ((MainActivity) context).showDashboard();
    }

    private void speedUpChromeTabs() {
        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                //Pre-warming
                mClient = customTabsClient;
                mClient.warmup(0L);
                mCustomTabsSession = mClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mClient = null;
            }
        };
    }


    public void resume() {
        if (getUserVisibleHint()) {
            ((MainActivity) context).setTitle(getString(R.string.login));

        }
        if (!haveToken) {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        getFragmentManager().popBackStack();
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}

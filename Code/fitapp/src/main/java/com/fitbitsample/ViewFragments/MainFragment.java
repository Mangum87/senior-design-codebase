package com.fitbitsample.ViewFragments;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.fitbitsample.FragmentTraceManager.Trace;
import java.util.Set;

/*
    This class manages the viewing fragments
 */
public class MainFragment extends Fragment {
    public Context context;
    public Resources resources;

    private static final int PERMISSION_REQUEST_CODE = 27 << 1; // Why not use 54 and no shift?

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setRetainInstance(true);
        resources = getResources();
        context = getActivity();
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            Set<String> keys = bundle.keySet();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IntentDump \n");
            stringBuilder.append("-----\n");

            for (String key : keys) {
                stringBuilder.append(key).append("=").append(bundle.get(key)).append("\n");
            }

            stringBuilder.append("------\n");
            Trace.i(stringBuilder.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    public void resume() {
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    public void pause() {
    }

    @Override
    public void onStart() {
        super.onStart();
        start();
    }

    public void start() {
    }

    @Override
    public void onStop() {
        super.onStop();
        stop();
    }

    public void stop() {
    }

    public void destroy() {
    }

    @Override
    public void onDestroy() {
        destroy();
        super.onDestroy();
        System.gc(); // Why force garbage collection?
        Runtime.getRuntime().gc();
    }

}
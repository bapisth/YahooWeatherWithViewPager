package com.esspl.hemendra.yahooweatherwithviewpager.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esspl.hemendra.yahooweatherwithviewpager.R;

/**
 * Created by BAPI1 on 06-12-2015.
 */
public class WeatherSmallDetailFragment extends Fragment {

    public static final String WEATHER_ICON = "icon";
    public static final String DESCRIPTION = "description";
    public static final String HIGH_TEMP = "high";
    public static final String LOW_TEMP = "low";
    public static final String DEGREE_TEMP = "temperature";
    private static final String TAG = "WeatherSmallDetailFragment";

    private ImageView v_weatherIcon;
    private TextView v_description;
    private TextView v_highTemp;
    private TextView v_lowTemp;
    private TextView v_temp;
    private LinearLayout firstLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Creating views ..................");
        View view = inflater.inflate(R.layout.fragment_weather_small_detail, container, false);

        v_weatherIcon = (ImageView) view.findViewById(R.id.v_weatherIcon);
        v_description = (TextView) view.findViewById(R.id.v_description);
        v_highTemp = (TextView) view.findViewById(R.id.v_highTemp);
        v_lowTemp = (TextView) view.findViewById(R.id.v_lowTemp);

        firstLinearLayout = (LinearLayout) view.findViewById(R.id.firstLinearLayout);
        v_temp = (TextView) firstLinearLayout.findViewById(R.id.v_temp);

        Bundle arguments = getArguments();
        if (arguments != null) {
            int code = arguments.getInt(WEATHER_ICON);

            int resourceId = getResources().getIdentifier("drawable/icon_" + code, null, getActivity().getPackageName());
            Drawable drawable = getResources().getDrawable(resourceId);
            v_weatherIcon.setImageDrawable(drawable);

            v_description.setText(arguments.getString(DESCRIPTION));
            int temp = arguments.getInt(DEGREE_TEMP);
            v_highTemp.setText("" + arguments.getInt(HIGH_TEMP) + "\u00B0");
            v_lowTemp.setText("" + arguments.getInt(LOW_TEMP) + "\u00B0");
            //temp=23;


            v_temp.setText("" + temp + "\u00B0");
        }

        return view;
    }


}

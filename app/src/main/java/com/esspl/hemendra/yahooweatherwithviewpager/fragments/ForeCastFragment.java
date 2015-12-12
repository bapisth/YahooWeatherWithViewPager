package com.esspl.hemendra.yahooweatherwithviewpager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.esspl.hemendra.yahooweatherwithviewpager.R;
import com.esspl.hemendra.yahooweatherwithviewpager.model.ForecastData;
import com.esspl.hemendra.yahooweatherwithviewpager.utility.ForecastListAdapter;

import java.util.List;

/**
 * Created by BAPI1 on 06-12-2015.
 */
public class ForeCastFragment extends Fragment {
    private static final String TAG = "ForeCastFragment";
    private ListView listView = null;
    private List<ForecastData> forecastDatas = null;
    private Activity activity = null;

    public ForeCastFragment() {
    }

    public ForeCastFragment(Activity activity,List<ForecastData> forecastDatas) {
        Log.d("MainActivity", "Forecast data in serviceSuccess method:555555->" + forecastDatas.size());
        this.activity = activity;
        this.forecastDatas = forecastDatas;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_layout, container, false);
        listView = (ListView) view.findViewById(R.id.forecast_list);
        //List<ForecastData> forecastDatas = getArguments().getParcelableArrayList("dfs");
        Log.d("MainActivity","Forecast data in serviceSuccess method:"+forecastDatas.size());
        ForecastListAdapter forecastListAdapter = new ForecastListAdapter(getActivity(), forecastDatas);
        listView.setAdapter(forecastListAdapter);
        return view;
    }

}

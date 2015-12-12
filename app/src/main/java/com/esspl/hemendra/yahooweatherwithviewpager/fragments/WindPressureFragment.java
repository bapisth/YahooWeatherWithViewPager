package com.esspl.hemendra.yahooweatherwithviewpager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esspl.hemendra.yahooweatherwithviewpager.R;

/**
 * Created by BAPI1 on 06-12-2015.
 */
public class WindPressureFragment extends Fragment {
    public WindPressureFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forecast_layout, container, false);
    }
}

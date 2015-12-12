package com.esspl.hemendra.yahooweatherwithviewpager.utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esspl.hemendra.yahooweatherwithviewpager.R;
import com.esspl.hemendra.yahooweatherwithviewpager.model.ForecastData;

import java.util.List;

/**
 * Created by BAPI1 on 03-12-2015.
 */
public class ForecastListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    ForecastData forecastData = null;
    private Activity context;
    private List<ForecastData> data;

    public ForecastListAdapter(Activity context, List<ForecastData> data) {
        //Log.d("ForecastListAdapter", "=============Inside =====forecast list Adapter constructor" + data.size());
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("ForecastListAdapter", "=============Inside =====forecast list Adapter" + getCount());

        View view = convertView;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = inflater.inflate(R.layout.forecast_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.tv_day = (TextView) view.findViewById(R.id.tv_day);
            viewHolder.tv_temp = (TextView) view.findViewById(R.id.tv_temp);
            viewHolder.v_description = (TextView) view.findViewById(R.id.v_description);
            viewHolder.v_highTemp = (TextView) view.findViewById(R.id.v_highTemp);
            viewHolder.v_lowTemp = (TextView) view.findViewById(R.id.v_lowTemp);
            viewHolder.icon_image = (ImageView) view.findViewById(R.id.icon_image);

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        if (data.size() > 0) {
            forecastData = data.get(position);
            viewHolder.tv_day.setText(forecastData.getDay().toString());
            viewHolder.tv_temp.setText(String.valueOf(forecastData.getCode()) + "\u00B0");
            viewHolder.v_description.setText(forecastData.getDescription() != null ? forecastData.getDescription().toString() : "");
            viewHolder.v_highTemp.setText(String.valueOf(forecastData.getHigh()) + "\u00B0");
            viewHolder.v_lowTemp.setText(String.valueOf(forecastData.getLow() + "\u00B0"));

            int resourceId = context.getResources().getIdentifier("drawable/icon_" + String.valueOf(forecastData.getCode()).trim(), null, context.getPackageName());
            Drawable drawable = context.getDrawable(resourceId);

            viewHolder.icon_image.setImageDrawable(drawable);
        }

        return view;
    }

    public static class ViewHolder {
        public TextView tv_day;
        public TextView tv_temp;
        public TextView v_description;
        public TextView v_highTemp;
        public TextView v_lowTemp;
        public ImageView icon_image;
    }
}

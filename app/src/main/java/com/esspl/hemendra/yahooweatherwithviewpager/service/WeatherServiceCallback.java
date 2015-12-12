package com.esspl.hemendra.yahooweatherwithviewpager.service;

import com.esspl.hemendra.yahooweatherwithviewpager.model.Channel;

/**
 * Created by BAPI1 on 01-12-2015.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception error);
}

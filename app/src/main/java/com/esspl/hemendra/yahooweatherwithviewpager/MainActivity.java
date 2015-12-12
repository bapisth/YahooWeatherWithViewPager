package com.esspl.hemendra.yahooweatherwithviewpager;

import android.accounts.NetworkErrorException;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.esspl.hemendra.yahooweatherwithviewpager.fragments.DetailsFragment;
import com.esspl.hemendra.yahooweatherwithviewpager.fragments.ForeCastFragment;
import com.esspl.hemendra.yahooweatherwithviewpager.fragments.PrecipitationFragment;
import com.esspl.hemendra.yahooweatherwithviewpager.fragments.WeatherSmallDetailFragment;
import com.esspl.hemendra.yahooweatherwithviewpager.fragments.WindPressureFragment;
import com.esspl.hemendra.yahooweatherwithviewpager.model.Channel;
import com.esspl.hemendra.yahooweatherwithviewpager.model.ForecastData;
import com.esspl.hemendra.yahooweatherwithviewpager.model.Item;
import com.esspl.hemendra.yahooweatherwithviewpager.service.DetectNetworkConnectivity;
import com.esspl.hemendra.yahooweatherwithviewpager.service.WeatherServiceCallback;
import com.esspl.hemendra.yahooweatherwithviewpager.service.YAHOOWeatherService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback,SearchView.OnQueryTextListener {

    private static final String TAG = "MainActivity";
    public Item item = null;
    public List<ForecastData> forecastDatas = null;
    DetectNetworkConnectivity networkConnectivity = null;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private YAHOOWeatherService service;
    private ProgressDialog dialog;
    private String searchCity;
    private WeatherSmallDetailFragment smallDetailFragment;
    private int resourceId;
    private SearchView searchView;
    private TextView v_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        int res = getResources().getIdentifier("drawable/icon_" + 20, null, getPackageName());
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(res));

        searchCity = "Bhubaneswar";


        service = new YAHOOWeatherService(MainActivity.this);
        searchWeather(searchCity);

        //populateSmallWeatherDeatilFragment();

    }

    private void searchWeather(String searchCity) {
        Log.d(TAG, "Inside Search Weather.........");
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.l_weatherLoading));
        dialog.show();
        networkConnectivity = new DetectNetworkConnectivity(this);
        if (networkConnectivity.isInternetConnected()) {
            service.refreshWeather(searchCity);
        } else {
            showViewTabs();
            serviceFailure(new NetworkErrorException(getString(R.string.l_noInternetConnection)));
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "setupViewPager..........");
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new WeatherSmallDetailFragment(), getString(R.string.l_tabWeather));
        viewPagerAdapter.addFragment(new ForeCastFragment(), getString(R.string.l_tabForecast));
        viewPagerAdapter.addFragment(new PrecipitationFragment(), getString(R.string.l_tabPrecipitation));
        viewPagerAdapter.addFragment(new DetailsFragment(), getString(R.string.l_tabDetails));
        viewPagerAdapter.addFragment(new WindPressureFragment(), getString(R.string.l_tabWindPressure));

        //setFragments to the view pager
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void serviceSuccess(Channel channel) {
        Log.d(TAG, "serviceSuccess method called...");

        dialog.hide();
        item = channel.getItem();
        forecastDatas = item.getForeCasts().getForeCasts();

        Log.d("MainActivity","Forecast data in serviceSuccess method:"+forecastDatas.size());
        showViewTabs();
    }

    private void showViewTabs() {
        v_location = (TextView) findViewById(R.id.v_location);
        v_location.setText(searchCity);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void serviceFailure(Exception error) {
        dialog.hide();
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "Inside onQueryTextSubmit");
        searchCity = query.toString();
        searchWeather(searchCity);
        viewPager.getAdapter().notifyDataSetChanged();// used to reload the views with the updated data.
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        //use FragmentStatePagerAdapter instead of FragmentPagerAdapter when you want your data to be dynamically updated

        private final ArrayList<Fragment> fragments = new ArrayList<>();
        private final ArrayList<String> fragmentTitles = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //When we call notifyDataSetChanged(), getItemPosition() is called and POSITION_NONE=-2 that means the viewpager recreats the view
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;//used to reload all views of the viewpager
        }

        @Override
        public Fragment getItem(int position) {
            if (networkConnectivity.isInternetConnected()) {
                switch (position) {
                    case 0:
                        Log.d(TAG, "Fragment 1................");
                        Fragment weatherSmallDetailFragment = new WeatherSmallDetailFragment();
                        Bundle args = new Bundle();
                        args.putInt(WeatherSmallDetailFragment.WEATHER_ICON, item.getCondition().getCode());
                        args.putString(WeatherSmallDetailFragment.DESCRIPTION, item.getCondition().getDescription());

                        args.putInt(WeatherSmallDetailFragment.LOW_TEMP, forecastDatas.get(0).getHigh());
                        args.putInt(WeatherSmallDetailFragment.HIGH_TEMP, forecastDatas.get(0).getHigh());
                        args.putInt(WeatherSmallDetailFragment.DEGREE_TEMP, item.getCondition().getTempaerature());
                        weatherSmallDetailFragment.setArguments(args);
                        return weatherSmallDetailFragment;
                    case 1:
                        Log.d(TAG, "Fragment 2................");
                        //Here I want to populate the listview of ForecastListFragment
                        Fragment forecastListFragment = new ForeCastFragment(MainActivity.this, forecastDatas);
                        return forecastListFragment;

                }
            }
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitles.add(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

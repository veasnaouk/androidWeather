package com.project.veasna.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Main extends AppCompatActivity{
    TabLayout tabLayout;
    ViewPager pager;
    PagerAdapter adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences pref=getSharedPreferences("city", Context.MODE_PRIVATE);
        if(pref.getString("city",null)==null){
            Intent intent=new Intent(this,CitySetter.class);
            startActivity(intent);
            finish();
        }else{

            pager=(ViewPager)findViewById(R.id.viewpager);
            tabLayout=(TabLayout)findViewById(R.id.sliding_tabs);

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            adp=new PagerAdapter(getSupportFragmentManager());
            tabLayout.setupWithViewPager(pager);
            pager.setAdapter(adp);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    pager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(this,CitySetter.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    class PagerAdapter extends FragmentStatePagerAdapter {

        //Constructor to the class
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    Weather weather = new Weather();
                    return weather;

                case 1:
                    Forecast forecast = new Forecast();
                    return forecast;
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return "Weather";

                case 1:

                    return "Forecast";
                default:
                    return null;
            }
        }

        //Overriden method getCount to get the number of tabs
        @Override
        public int getCount() {
            return 2;
        }
    }
}

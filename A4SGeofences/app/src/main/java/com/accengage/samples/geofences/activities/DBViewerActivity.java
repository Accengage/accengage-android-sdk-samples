package com.accengage.samples.geofences.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.accengage.samples.geofences.fragments.GeofencesFragment;
import com.accengage.samples.geofences.R;

public class DBViewerActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbviewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.vpager_db);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dbview_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_dbview_item_preferences) {
            Intent intent = new Intent(this, DBPreferencesActivity.class);
            intent.putExtra(DBPreferencesActivity.KEY_PREFERENCE_ID, mViewPager.getCurrentItem());
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.clearOnPageChangeListeners();
    }

    public class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new GeofencesFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Geofences";
                default:
                    break;

            }
            return "";
        }
    }
}

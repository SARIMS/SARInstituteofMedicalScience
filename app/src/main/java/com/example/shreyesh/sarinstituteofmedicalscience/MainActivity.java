package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar homeToolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SectionsPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise
        homeToolbar = (Toolbar) findViewById(R.id.homeToolbar);
        viewPager = (ViewPager) findViewById(R.id.tabPager);
        tabLayout = (TabLayout) findViewById(R.id.mainTabs);

        //Setup Toolbar
        setSupportActionBar(homeToolbar);
        getSupportActionBar().setTitle("Home");

        //setup tabs
        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);                    //Default page when app opens

    }


    //Setting Up Toolbar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);
        return true;
    }


    //Defining actions for the options in Toolbar Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adminRedirect:
                startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
                break;
            case R.id.aboutUs:
                startActivity(new Intent(MainActivity.this, PatientManagementActivity.class));
                break;
            default:
                Toast.makeText(MainActivity.this, "Invalid Option", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}

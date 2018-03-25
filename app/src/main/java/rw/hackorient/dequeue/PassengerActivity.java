package rw.hackorient.dequeue;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class PassengerActivity extends AppCompatActivity {
    public Toolbar toolbar;
    SectionsPagerAdapter adapter;
    ViewPager viewPager;
    MenuItem prevMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.student_fragment_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragemnt(new PassengerFragment());
        adapter.addFragemnt(new BusStopFragment());
        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);
        BottomNavigationView b_nav = findViewById(R.id.navigation);
        Helper.disableShiftMode(b_nav);
        b_nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.menu_student:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.menu_bus_stop:
                    viewPager.setCurrentItem(1);
            }
            return true;
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    b_nav.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                b_nav.getMenu().getItem(position).setChecked(true);
                prevMenuItem = b_nav.getMenu().getItem(position);
                switch (position){
                    case 0:
                        getSupportActionBar().setTitle(getString(R.string.student_fragment_title));
                        break;
                    case 1:
                        getSupportActionBar().setTitle(getString(R.string.bus_stop_fragment_title));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id){
            case R.id.home_about:
                Intent mAIntent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(mAIntent);
                return  true;
            case R.id.home_profile:
                Intent mOIntent = new Intent(getApplicationContext(),DriverProfileActivity.class);
                startActivity(mOIntent);
                return true;
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.stable,R.anim.slide_out);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

package rw.hackorient.dequeue;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;


public class Home extends AppCompatActivity {

    public Toolbar toolbar;
    public FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(getIntent() != null){
            mUser = getIntent().getParcelableExtra("user");
        }
        fragment_change(1);


        BottomNavigationView b_nav = findViewById(R.id.navigation);
        Helper.disableShiftMode(b_nav);
        b_nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.home_about:
                    fragment_change(1);
                    break;
                case R.id.menu_student:
                    fragment_change(2);
                    break;
                case R.id.menu_profile:
                    Intent mAIntent = new Intent(Home.this,PassengerProfileActivity.class);
                    startActivity(mAIntent);
                    break;
                default:
                    fragment_change(1);
            }
            return true;
        });
    }


    public void fragment_change(int id) {
        Fragment fragment = null;
        Fragment activeFragment = getSupportFragmentManager().findFragmentById(R.id.flContent);
        switch (id) {
            case 1:
                if (!(activeFragment instanceof HomeFragment)){
                    fragment = new HomeFragment();
                }
                break;
            case 2:
                if (!(activeFragment instanceof MapFragment)){
                    fragment = new MapFragment();
                }
                break;
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment, "Home").commit();
        }
    }
}

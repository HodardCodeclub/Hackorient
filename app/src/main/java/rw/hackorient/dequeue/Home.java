package rw.hackorient.dequeue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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
    }


    public void fragment_change(int id) {
        Fragment fragment = null;
        Fragment activeFragment = getSupportFragmentManager().findFragmentById(R.id.flContent);
        switch (id) {
            case 1:
                if (!(activeFragment instanceof MapFragment))
                    fragment = new MapFragment();
                    Bundle mBundle = new Bundle();
                    mBundle.putParcelable("user",mUser);
                    fragment.setArguments(mBundle);
                    break;
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment, "Home").commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_passenger:
                Intent mIntent = new Intent(this,PassengerActivity.class);
                startActivity(mIntent);
                overridePendingTransition(R.anim.slide_in,R.anim.stable);
                return  true;
            case R.id.home_profile:
                Intent mOIntent = new Intent(getApplicationContext(),DriverProfileActivity.class);
                startActivity(mOIntent);
                return  true;
            case R.id.home_about:
                Intent mAIntent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(mAIntent);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}

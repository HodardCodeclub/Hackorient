package rw.hackorient.dequeue;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.hawk.Hawk;


public class DriverProfileActivity extends AppCompatActivity {
    private static final String TAG = "DriverProfileActivity";
    HackorientApi mHackorientApi;
    ImageView driver_profile_image;
    TextView tvFirstname,tvLastname,tvDob,tvSex,tvPhone,tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        driver_profile_image = findViewById(R.id.driver_profile_image);
        tvFirstname = findViewById(R.id.tvFirstname);
        tvLastname = findViewById(R.id.tvLastname);
        tvDob = findViewById(R.id.tvDob);
        tvSex =  findViewById(R.id.tvSex);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);


        mHackorientApi = HackorientApi.getInstance();
        getSupportActionBar().setTitle("Title");
        Glide.with(this).setDefaultRequestOptions(new RequestOptions().fallback(R.drawable.ic_user_profile_circle)).load("url").apply(new RequestOptions().centerCrop()).into(driver_profile_image);
        tvFirstname.setText("Firstname");
        tvLastname.setText("Lastname");
        tvDob.setText("Lastname");
        tvSex.setText("Gender");
        tvPhone.setText("Phone");
        tvEmail.setText("Email");


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Ok", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnInfoClicked(View view) {
        switch (view.getId()){
            case R.id.tvPhone:
                TextView mNumberTextView = (TextView)view;
                String phone = mNumberTextView.getText().toString();
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", phone, null));
                startActivity(phoneIntent);
                break;
            case R.id.tvEmail:
                TextView mEmailTextView = (TextView)view;
                String email = mEmailTextView.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",email, null));
                startActivity(Intent.createChooser(intent, "Choose Email client :"));
                break;
        }
    }
}

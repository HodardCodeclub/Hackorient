package rw.hackorient.dequeue;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class PassengerProfileActivity extends AppCompatActivity {
    private static final String TAG = "PassengerProfileAct";
    HackorientApi mHackorientApi;
    ImageView passenger_profile_image;
    TextView tvNumberPassenger, tvPassengerEmail, tvPassengerLocation;
    TextView tvNumberLocation, tvEmailLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        passenger_profile_image = findViewById(R.id.passenger_profile_image);
        tvNumberPassenger = findViewById(R.id.tvNumberPassenger);
        tvPassengerEmail = findViewById(R.id.tvPassengerEmail);
        tvPassengerLocation = findViewById(R.id.tvPassengerLocation);
        tvNumberLocation =  findViewById(R.id.tvNumberLocation);
        tvEmailLocation = findViewById(R.id.tvEmailLocation);

        mHackorientApi = HackorientApi.getInstance();
        getSupportActionBar().setTitle("MUCYO Fred");
        Glide.with(this).setDefaultRequestOptions(new RequestOptions().fallback(R.drawable.ic_user_profile_circle)).load(R.drawable.miller).apply(new RequestOptions().centerCrop()).into(passenger_profile_image);
        tvNumberPassenger.setText("+250722222222");
        tvPassengerEmail.setText("info@email.com");
        tvPassengerLocation.setText("Nyamirambo,Nyarugenge");
        tvNumberLocation.setText("+250788888888");
        tvEmailLocation.setText("info@gmail.com");



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Ok", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.home_about:
                Intent mAIntent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(mAIntent);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnInfoClicked(View view) {
        switch (view.getId()){
            case R.id.tvNumberLocation:
            case R.id.tvNumberPassenger:
                TextView mNumberTextView = (TextView)view;
                String phone = mNumberTextView.getText().toString();
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", phone, null));
                startActivity(phoneIntent);
                break;
            case R.id.tvPassengerEmail:
            case R.id.tvEmailLocation:
                TextView mEmailTextView = (TextView)view;
                String email = mEmailTextView.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",email, null));
                startActivity(Intent.createChooser(intent, "Choose Email client :"));
                break;
        }
    }
}

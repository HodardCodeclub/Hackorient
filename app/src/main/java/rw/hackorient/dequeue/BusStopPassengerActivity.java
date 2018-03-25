package rw.hackorient.dequeue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rw.hackorient.dequeue.Model.Stop;


public class BusStopPassengerActivity extends AppCompatActivity {
    private static final String TAG = "BusStopStudent";
    public Toolbar toolbar;
    protected RecyclerView mRecyclerView;
    protected PassengerAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<Stop> mDataset;
    HackorientApi mHackorientApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop_passenger);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.bus_stop_student_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mHackorientApi = HackorientApi.getInstance();
        mDataset = new ArrayList<>();
        mRecyclerView =  findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PassengerAdapter(this,mDataset);
        mRecyclerView.setAdapter(mAdapter);
        if(getIntent() != null){
            initDataset(getIntent().getStringExtra("bus_stop_id"));
        }
    }


    private void initDataset(String stopId) {
//        mHackorientApi
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
}

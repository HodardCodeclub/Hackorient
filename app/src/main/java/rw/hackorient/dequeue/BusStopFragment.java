package rw.hackorient.dequeue;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rw.hackorient.dequeue.Model.Stop;

public class BusStopFragment extends Fragment {
    private static final String TAG = "BusStopFragment";
    protected RecyclerView mRecyclerView;
    protected BusStopAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<Stop> mDataset;
    HackorientApi mHackorientApi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHackorientApi = HackorientApi.getInstance();
        mDataset = new ArrayList<>();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_bus_stop, container, false);
        rootView.setTag(TAG);
        mRecyclerView =  rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BusStopAdapter(getContext(),mDataset);
        mRecyclerView.setAdapter(mAdapter);
        initDataset();
        return  rootView;
    }

    private void initDataset() {
        if(Hawk.contains("driver")){
//            mHackorientApi
        }
    }
}

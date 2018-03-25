package rw.hackorient.dequeue;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;
import rw.hackorient.dequeue.Model.Stop;


/**
 * Created by Mucyo Miller on 2/3/2017.
 * this class container data to bind from api server
 */

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.ViewHolder> {

    private Context context;
    private List<Stop> my_data;

    public BusStopAdapter(Context context, List<Stop> my_data) {
        this.context = context;
        this.my_data = my_data;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        //add status in future
        public TextView bus_stop_name,bus_stop_location;
        public ImageView imageView;
        public CardView card_view;
        public Button btn_bus_stop;
        public RelativeLayout relativeClickableItem;


        public ViewHolder(View itemView) {
            super(itemView);

            bus_stop_name = itemView.findViewById(R.id.bus_stop_name);
            bus_stop_location = itemView.findViewById(R.id.bus_stop_location);
            btn_bus_stop = itemView.findViewById(R.id.bus_stop_numbers);
            relativeClickableItem = itemView.findViewById(R.id.relativeClickableItem);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_stop_item,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.bus_stop_name.setText(my_data.get(position).getName());
        holder.bus_stop_location.setText("View on Map");
        holder.btn_bus_stop.setText(String.valueOf(my_data.get(position).getAgency().getName()));
        holder.relativeClickableItem.setOnClickListener(v ->{
            Intent mBIntent = new Intent(context,BusStopPassengerActivity.class);
            mBIntent.putExtra("bus_stop_id", my_data.get(position).getId());
            context.startActivity(mBIntent);
        });
        holder.bus_stop_location.setOnClickListener(v->{
            Toasty.success(context,"Not implemented yet!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }
}

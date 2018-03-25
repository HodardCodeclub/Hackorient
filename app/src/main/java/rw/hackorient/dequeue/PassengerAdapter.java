package rw.hackorient.dequeue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import rw.hackorient.dequeue.Model.Stop;

/**
 * Created by Mucyo Miller on 24/3/2017.
 * this class container data to bind from api server
 */

public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.ViewHolder> {

    private Context context;
    private List<Stop> my_data;

    public PassengerAdapter(Context context, List<Stop> my_data) {
        this.context = context;
        this.my_data = my_data;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        //add status in future
        public TextView passenger_name, location_name, level_name;
        public ImageView imageView;
        public CardView card_view;
        public RelativeLayout relativeClickableItem;


        public ViewHolder(View itemView) {
            super(itemView);

            passenger_name = itemView.findViewById(R.id.passenger_name);
            location_name = itemView.findViewById(R.id.location_name);
            level_name = itemView.findViewById(R.id.level_name);
            imageView = itemView.findViewById(R.id.image);
            relativeClickableItem = itemView.findViewById(R.id.relativeClickableItem);
            card_view = itemView.findViewById(R.id.card_view);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_item,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.passenger_name.setText(my_data.get(position).getName());
        holder.location_name.setText(my_data.get(position).getId());
        holder.level_name.setText(my_data.get(position).getAgency().getName());
        Glide.with(context).setDefaultRequestOptions(new RequestOptions().fallback(R.drawable.ic_user_profile_circle)).load(my_data.get(position).getName()).apply(new RequestOptions().centerCrop()).into(holder.imageView);
        holder.relativeClickableItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, PassengerProfileActivity.class);
            intent.putExtra("student", my_data.get(position));
            Pair pair = new Pair<View, String>(holder.imageView,"transProfileImage");
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,pair);
            context.startActivity(intent,optionsCompat.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }
}

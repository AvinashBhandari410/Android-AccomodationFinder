package c.ab.accommodationfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avina on 4/18/2018.
 */

public class BindGrid extends RecyclerView.Adapter<BindGrid.MyViewHolder> {
    private List<AccommodationAvailable> dataSet;
    private Context mContext;

    public BindGrid(Context context, List<AccommodationAvailable> data) {
        this.mContext = context;
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtAccomID.setText(Integer.toString(dataSet.get(position).getAccommodation_id()));
        holder.txtAccomTitle.setText(dataSet.get(position).getAcom_title());
        holder.txtCost.setText(dataSet.get(position).getAcom_cost());
        holder.txtContactDetail.setText(dataSet.get(position).getAcom_contactdetail());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void delete(int position) { //removes the row
        dataSet.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtAccomID;
        TextView txtAccomTitle;
        TextView txtCost;
        TextView txtContactDetail;
//        ImageView imgHouseIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtAccomID = itemView.findViewById(R.id.txtAccomID);
            this.txtAccomTitle = itemView.findViewById(R.id.txtAccomTitle);
            this.txtCost = itemView.findViewById(R.id.txtCost);
            this.txtContactDetail = itemView.findViewById(R.id.txtContactDetail);
            //  this.imgHouseIcon =itemView.findViewById(R.id.imgHouse);

            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Toast.makeText(v.getContext(), txtContactDetail.getText().toString(), Toast.LENGTH_SHORT).show();
                   // delete(getAdapterPosition());
                    return false;
                }
            });
        }
    }
}


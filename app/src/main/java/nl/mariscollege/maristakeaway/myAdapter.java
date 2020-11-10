package nl.mariscollege.maristakeaway;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

    String data1[], data2[];
    int images[];
    int flags[]= {R.drawable.belgisch_park, R.drawable.houtrust, R.drawable.bohemen, R.drawable.statenkwartier, R.drawable.waldeck, R.drawable.kijkduin};
    Context context;

    public myAdapter(Context ct, String s1[], String s2[], int img[]){
        context= ct;
        data1= s1;
        data2= s2;
        images= img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.location.setText(data1[position]);
        holder.address.setText(data2[position]);
        holder.location_image.setImageResource(images[position]);
        holder.menuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("data1", data1[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView location, address;
        ImageView location_image;
        ConstraintLayout menuLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            location= itemView.findViewById(R.id.location_text);
            address= itemView.findViewById(R.id.address_text);
            location_image= itemView.findViewById(R.id.location_image);
            menuLayout = itemView.findViewById(R.id.menuLayout);
        }
    }
}

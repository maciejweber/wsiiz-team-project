package com.example.myrzeszow_w;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.HolderPost> {

    private Context context;
    private ArrayList<ModelPost> postArrayList;

    public AdapterPost(Context context, ArrayList<ModelPost> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public HolderPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout inflater
        View view = LayoutInflater.from(context).inflate(R.layout.rv_row, parent, false);

        return new HolderPost(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPost holder, int position) {
        ModelPost model = postArrayList.get(position);

        //get data
        String authorName = model.getAuthorName();
        String id = model.getId();
        String title = model.getTitle();
        String estate = model.getEstate();
        String created_on = model.getCreated_on();
        String likes_count = model.getLikes_count();
        String category = model.getCategory();

        //formatting date

        String gmtDate = created_on;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //   2022-06-06T06:53:00-07:00
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy K:mm a"); // 06/06/2022 15:53
        String formattedDate = "";
        try {
            Date date = dateFormat.parse(gmtDate);
            formattedDate = dateFormat2.format(date);


        } catch (Exception e) {
            formattedDate = created_on;
            e.printStackTrace();
        }

        holder.postTitle.setText(title);
        holder.publishedTV.setText("By " + authorName + " " + formattedDate);
        holder.estateTV.setText("Dzielnica: "+estate);
        holder.categoryTV.setText("Kategoria: "+category);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostContentActivity.class);
//                intent.putExtra("Authorization", "Token 06f41b40338b2817554825a93bf630a773c15451");
                intent.putExtra("postId", id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        //returns size of array(number of records)
        return postArrayList.size();
    }

    //ViewHolder for rv_row view

    class HolderPost extends RecyclerView.ViewHolder {

        ImageButton btn_more;
        TextView postTitle, publishedTV, estateTV, categoryTV;

        public HolderPost(@NonNull View itemView) {
            super(itemView);

            //init views

            btn_more = itemView.findViewById(R.id.btn_more);
            postTitle = itemView.findViewById(R.id.postTitle);
            publishedTV = itemView.findViewById(R.id.publishedTV);
            estateTV = itemView.findViewById(R.id.estateTV);
            categoryTV = itemView.findViewById(R.id.categoryTV);


        }
    }
}

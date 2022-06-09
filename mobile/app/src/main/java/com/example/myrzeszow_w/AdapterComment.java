package com.example.myrzeszow_w;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.namespace.QName;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.HolderComment>{

    private Context context;
    private ArrayList<ModelComment> commentArrayList;

    public AdapterComment(Context context, ArrayList<ModelComment> commentArrayList) {
        this.context = context;
        this.commentArrayList = commentArrayList;
    }

    @NonNull
    @Override
    public HolderComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout inflate
        View view = LayoutInflater.from(context).inflate(R.layout.rv_comment, parent, false);

        return new HolderComment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderComment holder, int position) {

        //get data
        ModelComment modelComment = commentArrayList.get(position);
        String id = modelComment.getId();
        String text = modelComment.getText();
        String created_on = modelComment.getCreated_on();
        String author = modelComment.getAuthor();

        //date formatting

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

        //set data
        holder.nameTV.setText(author);
        holder.contentTV.setText(text);
        holder.dateTV.setText(formattedDate);

    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    class HolderComment extends RecyclerView.ViewHolder{

        ImageView profileIV;
        TextView nameTV, dateTV, contentTV;

        public HolderComment(@NonNull View itemView) {
            super(itemView);

            //init views
            profileIV = itemView.findViewById(R.id.profileIV);
            nameTV = itemView.findViewById(R.id.nameTV);
            dateTV = itemView.findViewById(R.id.dateTV);
            contentTV = itemView.findViewById(R.id.contentTV);
        }
    }
}

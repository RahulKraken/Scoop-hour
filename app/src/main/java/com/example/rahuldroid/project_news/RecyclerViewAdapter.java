package com.example.rahuldroid.project_news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rahuldroid.project_news.ContentRecievers.DataModel;

import java.util.ArrayList;
import java.util.List;

// This class is the Adapter for the recycler view to be displayed in every page of the view pager fragments.
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    // Needed to provide content to the viewHolder to populate it.
    private List<DataModel> data;
    private Context context;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(List<DataModel> data, Context context) {
        this.data = new ArrayList<>();
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(view);
    }

    // This method populates the views in a view holder with the relevant content.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_title.setText(data.get(position).getTitle());
        holder.tv_desc.setText(data.get(position).getDescription());

        // Inflating the main img with glide.
        Glide.with(context).load(data.get(position).getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.color.colorPrimaryLight)
                        .error(R.color.colorPrimaryLight))
                .into(holder.img_main);

    }

    // Returns the number of views in the viewHolder.
    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_title, tv_desc;
        private ImageView img_main, img_bookmark;

        ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            img_main = itemView.findViewById(R.id.img_main);
            img_bookmark = itemView.findViewById(R.id.bookmark_btn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Do this when an item in the recycler View is clicked.
            if (v.getId() != R.id.bookmark_btn) {
                // Anything in the layout but the bookmark button is clicked, it opens the article in a web view.
                Intent intent = new Intent(context, ArticleContent.class);
                intent.putExtra("URL", data.get(getAdapterPosition()).getArticleUrl());
                intent.putExtra("TITLE", data.get(getAdapterPosition()).getTitle());
                context.startActivity(intent);
            } else {
                // The bookmark button is clicked and it will change it's color.
                Toast.makeText(context, "I don't know what to do yet", Toast.LENGTH_LONG).show();
                img_bookmark.setImageResource(R.drawable.ic_bookmark_activated);
            }
        }
    }
}

package com.krakn.scoophour;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.krakn.scoophour.ContentRecievers.DataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// This class is the Adapter for the recycler view to be displayed in every page of the view pager fragments.
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
//    private FirebaseHelper firebaseHelper;

    // Needed to provide content to the viewHolder to populate it.
    private List<DataModel> data;
    private Context context;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(List<DataModel> data, Context context) {
        this.data = new ArrayList<>();
        this.data = data;
        this.context = context;
        if (context != null) {
            inflater = LayoutInflater.from(context);
//            firebaseHelper = new FirebaseHelper(context);
            Log.d(TAG, "RecyclerViewAdapter: " + Objects.requireNonNull(context).getClass().getSimpleName());
        }
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
                        .placeholder(R.color.grey)
                        .error(R.color.grey))
                .into(holder.img_main);
    }

    // Returns the number of views in the viewHolder.
    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView tv_title, tv_desc;
        private ImageView img_main, img_bookmark;

        ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            img_main = itemView.findViewById(R.id.img_main);
//            img_bookmark = itemView.findViewById(R.id.bookmark_btn);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
//            img_bookmark.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DataModel article = data.get(getAdapterPosition());
//                    firebaseHelper.addArticleReadLater(article);
//                }
//            });
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + context.getClass().getSimpleName());
            // Do this when an item in the recycler View is clicked.
            // Anything in the layout but the bookmark button is clicked, it opens the article in a web view.
            if (context.getClass().getSimpleName().equals(ForYouActivity.class.getSimpleName())) {
                // If activity is invoked from ForYouActivity
                Intent intent = new Intent(context, ArticleContent.class);
                intent.putExtra("URL", data.get(getAdapterPosition()).getArticleUrl());
                intent.putExtra("TITLE", data.get(getAdapterPosition()).getTitle());

                Log.d(TAG, "onClick: invoked article activity through forYouActivity context");

                context.startActivity(intent);
            } else if (context.getClass().getSimpleName().equals(SearchActivity.class.getSimpleName())) {
                // If activity is invoked from SearchActivity
                Intent intent = new Intent(context, ArticleContent.class);
                intent.putExtra("URL", data.get(getAdapterPosition()).getArticleUrl());
                intent.putExtra("TITLE", data.get(getAdapterPosition()).getTitle());

                Log.d(TAG, "onClick: invoked article activity through SearchActivity context");

                context.startActivity(intent);
            } else if (context.getClass().getSimpleName().equals(ReadLaterActivity.class.getSimpleName())) {
                // If activity is invoked from ReadLaterActivity
                Intent intent = new Intent(context, ArticleContent.class);
                intent.putExtra("URL", data.get(getAdapterPosition()).getArticleUrl());
                intent.putExtra("TITLE", data.get(getAdapterPosition()).getTitle());

                Log.d(TAG, "onClick: invoked article activity through ReadLaterActivity context");

                context.startActivity(intent);
            } else {
                Toast.makeText(context, "something wrong happened", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(View view) {
            Log.d(TAG, "onLongClick: " + context.getClass().getSimpleName());
            if (context.getClass().getSimpleName().equals(ReadLaterActivity.class.getSimpleName())) {
//                firebaseHelper.deleteArticle(data.get(getAdapterPosition()).getId());
            }
            return false;
        }
    }
}

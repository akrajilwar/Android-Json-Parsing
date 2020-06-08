package com.snowprojects.sample.androidjsonparsing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

/**
 * Created by Snow Corp Team on 06/06/20 at 6:05 PM.
 * support@snowcorp.org
 * www.snowcorp.org
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyHolder> {
    private Context mContext;
    private List<Movie> movieList;

    public MovieAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Movie item = movieList.get(position);

        holder.tvTitle.setText(item.getName());

        GlideApp.with(mContext)
                .load(item.getImage())
                .into(holder.ivThumb);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        MaterialTextView tvTitle;
        AppCompatImageView ivThumb;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            ivThumb = itemView.findViewById(R.id.iv_thumb);
        }
    }
}

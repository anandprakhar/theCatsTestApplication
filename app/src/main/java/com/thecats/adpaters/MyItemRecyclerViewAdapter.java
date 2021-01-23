package com.thecats.adpaters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.thecats.R;
import com.thecats.models.CatsImage;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<CatsImage> mValues;
    private Context mContext;

    public MyItemRecyclerViewAdapter(List<CatsImage> items, Context context) {
        mValues = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(mContext);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(circularProgressDrawable);
        requestOptions.error(R.drawable.the_cat_api_logo);
        requestOptions.skipMemoryCache(true);
        requestOptions.fitCenter();
        // setting the image in the imageView

        try {
            Glide.with(mContext)
                    .load(mValues.get(position).getUrl())
                    .apply(requestOptions) // here you have all options you need
                    .transition(DrawableTransitionOptions.withCrossFade()) // when image (url) will be loaded by glide then this face in animation help to replace url image in the place of placeHolder (default) image.
                    .into(holder.mImageView);

        } catch (Exception e) {
            Log.e("Exception", e.getLocalizedMessage());
        }

//        holder.mImageView.getLayoutParams().height = mValues.get(position).getHeight();
//        holder.mImageView.getLayoutParams().width = mValues.get(position).getWidth();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.imageViewMain);
        }
    }
}
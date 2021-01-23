package com.thecats.adpaters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.thecats.R;
import com.thecats.models.CatsImage;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<CatsImage> mCatsImages;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, List<CatsImage> catsImages) {
        this.context = applicationContext;
        this.mCatsImages = catsImages;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return mCatsImages.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_gridview, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.icon); // get the reference of ImageView
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(circularProgressDrawable);
        requestOptions.error(R.drawable.the_cat_api_logo);
        requestOptions.skipMemoryCache(true);
        requestOptions.fitCenter();
        // setting the image in the imageView
        try{
        Glide.with(context)
                .load(mCatsImages.get(i).getUrl())
                .apply(requestOptions) // here you have all options you need
                .transition(DrawableTransitionOptions.withCrossFade()) // when image (url) will be loaded by glide then this face in animation help to replace url image in the place of placeHolder (default) image.
                .into(icon);
    }
        catch (Exception e)
    {
        Log.e("Exception",e.getLocalizedMessage());
    }

//        icon.getLayoutParams().height = mCatsImages.get(i).getHeight();

        return view;
    }
}

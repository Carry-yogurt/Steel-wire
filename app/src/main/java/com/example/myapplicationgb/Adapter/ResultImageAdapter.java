package com.example.myapplicationgb.Adapter;

import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplicationgb.R;
import com.example.myapplicationgb.domain.ImageItem;
import com.example.myapplicationgb.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

public class ResultImageAdapter extends RecyclerView.Adapter<ResultImageAdapter.InnerHolder> {

    private List<ImageItem> mImageItems=new ArrayList<>();
    private int horizentalCount=1;


    @NonNull
    @Override
    public ResultImageAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        itemView.findViewById(R.id.image_check_box).setVisibility(View.GONE);

        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultImageAdapter.InnerHolder holder, int position) {
        View itemView = holder.itemView;
        Point point = SizeUtils.getScreenSize(itemView.getContext());
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(point.x/horizentalCount,point.x/horizentalCount);
        itemView.setLayoutParams(layoutParams);
        ImageView imageView =itemView.findViewById(R.id.image_iv);
        ImageItem imageItem = mImageItems.get(position);
        Glide.with(imageView.getContext()).load(imageItem.getPath()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mImageItems.size();
    }

    public void setData(List<ImageItem> result, int horizentalCount) {
        this.horizentalCount=horizentalCount;
        mImageItems.clear();
        mImageItems.addAll(result);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }



}

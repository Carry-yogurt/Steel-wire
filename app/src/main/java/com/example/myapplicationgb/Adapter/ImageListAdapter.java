package com.example.myapplicationgb.Adapter;

import android.graphics.Point;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplicationgb.R;
import com.example.myapplicationgb.domain.ImageItem;
import com.example.myapplicationgb.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.InnerHolder> {

    private List<ImageItem> mImageItems=new ArrayList<>();
    private List<ImageItem> mSelectedItems =new ArrayList<>();
    private OnItemSelectedChangeListener mOnItemSelectedChangeListener=null;
    private static final int MAX_SELECTED_COUNT = 9;
    private int mMaxSelectedCount= MAX_SELECTED_COUNT;

    public List<ImageItem> getmSelectedItems() {
        return mSelectedItems;
    }

    public void setmSelectedItems(List<ImageItem> mSelectedItems) {
        this.mSelectedItems = mSelectedItems;
    }

    public int getmMaxSelectedCount() {
        return mMaxSelectedCount;
    }

    public void setmMaxSelectedCount(int mMaxSelectedCount) {
        this.mMaxSelectedCount = mMaxSelectedCount;
    }

    @NonNull
    @Override
    public ImageListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载itemView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        Point point = SizeUtils.getScreenSize(itemView.getContext());
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(point.x/3,point.x/3);
        itemView.setLayoutParams(layoutParams);
        return new InnerHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ImageListAdapter.InnerHolder holder, int position) {
        //绑定数据
        final View itemView = holder.itemView;
        ImageView imageView = holder.itemView.findViewById(R.id.image_iv);
        final View imageCover=itemView.findViewById(R.id.image_cover);
        final CheckBox checkBox=itemView.findViewById(R.id.image_check_box);
        final ImageItem imageItem = mImageItems.get(position);
        Glide.with(imageView.getContext()).load(imageItem.getPath()).into(imageView);
        //根据状态显示数据
        //解决复用显示问题
        if(mSelectedItems.contains(imageItem)){
            //包含则设置成选上
            checkBox.setChecked(true);
            checkBox.setButtonDrawable(itemView.getContext().getDrawable(R.mipmap.checked));
            imageCover.setVisibility(View.VISIBLE);
        }else {
            //没有则设置成未选上
            checkBox.setChecked(false);
            checkBox.setButtonDrawable(itemView.getContext().getDrawable(R.mipmap.unchecked));
            imageCover.setVisibility(View.GONE);
        }


        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //是否选择上
                //如果选择上则取消
                //如果没有则选上
                if(mSelectedItems.contains(imageItem)){
                    //已经选择上了，则取消
                    mSelectedItems.remove(imageItem);
                    //修改UI
                    checkBox.setChecked(false);
                    checkBox.setButtonDrawable(itemView.getContext().getDrawable(R.mipmap.unchecked));
                    imageCover.setVisibility(View.GONE);
                }else {
                    //没有选择上，则选上,先判断是否超过最大值
                    if(mSelectedItems.size()>=mMaxSelectedCount){
                        Toast toast = Toast.makeText(checkBox.getContext(), null, Toast.LENGTH_SHORT);
                        toast.setText("最多可以选择"+mMaxSelectedCount+"张图片");
                        toast.show();
                        return;
                    }

                    mSelectedItems.add(imageItem);
                    //修改UI
                    checkBox.setChecked(true);
                    checkBox.setButtonDrawable(itemView.getContext().getDrawable(R.mipmap.checked));
                    imageCover.setVisibility(View.VISIBLE);
                }
                if (mOnItemSelectedChangeListener!=null){
                    mOnItemSelectedChangeListener.OnItemSelectedChange(mSelectedItems);
                }

            }
        });
    }


    public void setOnItemSelectedChangeListener(OnItemSelectedChangeListener listener){
        this.mOnItemSelectedChangeListener=listener;
    }


    //暴露一个接口给activity来判断选择
    public interface OnItemSelectedChangeListener{
        void OnItemSelectedChange(List<ImageItem> mSelectedItems);
    }


    @Override
    public int getItemCount() {
        return mImageItems.size();
    }

    public void setData(List<ImageItem> mImageItem) {
        mImageItems.clear();
        mImageItems.addAll(mImageItem);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}

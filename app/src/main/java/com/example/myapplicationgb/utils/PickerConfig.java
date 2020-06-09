package com.example.myapplicationgb.utils;

import com.example.myapplicationgb.domain.ImageItem;

import java.util.List;

public class PickerConfig {

    private PickerConfig(){};

    private static PickerConfig spickerConfig;

    public static PickerConfig getInstance(){
        if(spickerConfig==null){
            spickerConfig=new PickerConfig();
        }
        return spickerConfig;
    }



    private  OnImageSelectFinishListener mImageSelectedFinishListener=null;
    private int maxSelectedCount=1;

    public int getMaxSelectedCount() {
        return maxSelectedCount;
    }

    public void setMaxSelectedCount(int maxSelectedCount) {
        this.maxSelectedCount = maxSelectedCount;
    }

    public OnImageSelectFinishListener getmImageSelectedFinishListener() {
        return mImageSelectedFinishListener;
    }

    public void setOnImageSelectFinishListener(OnImageSelectFinishListener listener){
        this.mImageSelectedFinishListener=listener;
    }

    public interface OnImageSelectFinishListener{

        void onSelectedFinished(List<ImageItem> result);
    }


}

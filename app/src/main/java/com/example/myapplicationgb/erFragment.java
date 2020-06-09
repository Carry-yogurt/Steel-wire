package com.example.myapplicationgb;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplicationgb.domain.ImageItem;
import com.example.myapplicationgb.utils.PickerConfig;

import java.lang.ref.WeakReference;
import java.util.List;



public class erFragment extends Fragment implements PickerConfig.OnImageSelectFinishListener{
    private String context;
    private Button txt_zhichu;
    private Button txt_shouru;
    ImageView dreamImage;
    private final String TAG="erFragment";
    private List<CostBean> mCostBeanList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter mAdapter;
    public erFragment(String context) {
        this.context = context;
    }
    public erFragment() {}

    FragmentTransaction transaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jizhang, container, false);

        FragmentManager manager = getFragmentManager();
        transaction = manager.beginTransaction();



        txt_zhichu=view.findViewById(R.id.txt_zhichu);
        txt_zhichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                transaction = manager.beginTransaction();
                Fragment zhichufragmet = new zhichufragmet();
                transaction.replace(R.id.fragment_container, zhichufragmet);
                transaction.commit();
            }
        });


        txt_shouru=view.findViewById(R.id.txt_shouru);
        txt_shouru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                transaction = manager.beginTransaction();
                Fragment shourufragment = new shourufragment();
                transaction.replace(R.id.fragment_container, shourufragment);
                transaction.commit();
            }
        });


        dreamImage =view.findViewById(R.id.dream_image_iv);
        dreamImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),PickerAvtivity.class));
                dreamImage.setImageResource(R.drawable.after_change);
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        try {
//                            Thread.sleep(3000);//休眠3秒
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }.start();
//
            }
        });

        return view;
    }

        //褪色处理
    public void func(String path,int w,int h)
    {
        Log.d(TAG, "func: "+path);
        Bitmap bitmap = convertToBitmap(path,w,h);
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
        Bitmap base = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//        Bitmap B=toGrayscale(base);
        Canvas canvas = new Canvas(base);//以base为模板创建canvas对象
        canvas.drawBitmap(bitmap, new Matrix(),new Paint());
        for(int i = 0; i < base.getWidth(); i++)//遍历像素点
        {
            for(int j = 0; j < base.getHeight()*0.5; j++)
            {
                int color = bitmap.getPixel(i, j);

                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                int a = Color.alpha(color);

                int avg = (r+g+b)/3;//RGB均值

                r= (int) (r/3);
                g=(int)(g/3);
                b=(int)(b/3);
                base.setPixel(i, j,Color.argb(a, r, g, b));
//                if(avg >= 100)//100可以理解为经验值
//                {
//                    base.setPixel(i, j,Color.argb(a, 255, 255, 255));//设为白色
//                }
//                else
//                {
//                    base.setPixel(i, j,Color.argb(a, 0, 0, 0));//设为黑色
//                }
            }
        }
        bitmap.recycle();
        dreamImage.setImageBitmap(base);

    }
    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    @Override
    public void onSelectedFinished(List<ImageItem> result) {
        //返回来的数据显示出来
        for (ImageItem imageItem : result) {
            Log.d(TAG, " item----> "+imageItem);
        }
        ImageItem i=result.get(0);
        func(i.getPath(),i.getW(),i.getH());
        dreamImage.setImageResource(R.drawable.after_change);
    }
}

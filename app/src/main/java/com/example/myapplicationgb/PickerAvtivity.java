package com.example.myapplicationgb;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationgb.Adapter.ImageListAdapter;
import com.example.myapplicationgb.domain.ImageItem;
import com.example.myapplicationgb.utils.PickerConfig;

import java.util.ArrayList;
import java.util.List;

//D/PickerAvtivity: onCreate: ====================================
//D/PickerAvtivity: _id  ====  43
//D/PickerAvtivity: _data  ====  /storage/emulated/0/DCIM/Camera/IMG_20200420_064425.jpg
//D/PickerAvtivity: _size  ====  197214
//D/PickerAvtivity: _display_name  ====  IMG_20200420_064425.jpg
//D/PickerAvtivity: mime_type  ====  image/jpeg
//D/PickerAvtivity: title  ====  IMG_20200420_064425
//D/PickerAvtivity: date_added  ====  1587365066
//D/PickerAvtivity: date_modified  ====  1587365066
//D/PickerAvtivity: description  ====  null
//D/PickerAvtivity: picasa_id  ====  null
//D/PickerAvtivity: isprivate  ====  null
//D/PickerAvtivity: latitude  ====  37.422
//D/PickerAvtivity: longitude  ====  -122.084
//D/PickerAvtivity: datetaken  ====  1587365065974
//D/PickerAvtivity: orientation  ====  0
//D/PickerAvtivity: mini_thumb_magic  ====  null
//D/PickerAvtivity: bucket_id  ====  -1739773001
//D/PickerAvtivity: bucket_display_name  ====  Camera
//D/PickerAvtivity: width  ====  960
//D/PickerAvtivity: height  ====  1280
//D/PickerAvtivity: onCreate: ====================================

public class PickerAvtivity extends AppCompatActivity implements ImageListAdapter.OnItemSelectedChangeListener{

    private static final String TAG = "PickerAvtivity";
    public static final int LOADER_ID=1;

    private List<ImageItem> mImageItem=new ArrayList<>();
    ImageListAdapter mImageListAdapter;
    TextView finishView;
    PickerConfig picker;
    ImageView backView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

//        ContentResolver contentResolver=getContentResolver();
//        Uri imageUri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        Cursor query =contentResolver.query(imageUri,null,null,null,null);
//        String[] colunmNames=query.getColumnNames();
//        while (query.moveToNext()){
//            Log.d(TAG, "onCreate: ====================================");
//            for (String colunmName : colunmNames) {
//                Log.d(TAG, colunmName+"  ====  "+query.getString(query.getColumnIndex(colunmName)));
//            }
//            Log.d(TAG, "onCreate: ====================================");
//        }
//        query.close();

        initLoaderManager();
        initView();
        initEvent();
        initConfig();

    }

    private void initConfig() {
        picker = PickerConfig.getInstance();
        int maxSelectedCount= picker.getMaxSelectedCount();
        mImageListAdapter.setmMaxSelectedCount(maxSelectedCount);

    }

    private void initEvent() {
        mImageListAdapter.setOnItemSelectedChangeListener(this);
        finishView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取所选择的数据
                List<ImageItem> results = mImageListAdapter.getmSelectedItems();
                //通知其他地方
                PickerConfig.OnImageSelectFinishListener onImageSelectFinishListener = picker.getmImageSelectedFinishListener();
                if (onImageSelectFinishListener!=null){
                    onImageSelectFinishListener.onSelectedFinished(results);
                }
                //结束
                finish();
            }
        });
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        finishView = findViewById(R.id.finish_tv);
        RecyclerView listView=findViewById(R.id.image_list_view);
        backView = findViewById(R.id.back_press_iv);
        listView.setLayoutManager(new GridLayoutManager(this,3));
        //设置适配器
        mImageListAdapter = new ImageListAdapter();
        listView.setAdapter(mImageListAdapter);
    }

    private void initLoaderManager() {
        mImageItem.clear();
        LoaderManager loaderManager= LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
                if(id==LOADER_ID){
                    return new CursorLoader(PickerAvtivity.this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            new String[]{"_data","_display_name","date_added","_id","width","height"},
                            null,null,"date_added DESC");
                }
                return null;

            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
                if (cursor!=null){

                    while (cursor.moveToNext()){

                        String path=cursor.getString(0);
                        String title=cursor.getString(1);
                        long date=cursor.getLong(2);
                        int id=cursor.getInt(3);
                        int w=cursor.getInt(4);
                        int h=cursor.getInt(5);
                        ImageItem imageItem=new ImageItem(path,title,date,id,w,h);
//                        func(imageItem);
                        mImageItem.add(imageItem);
                    }
                    cursor.close();
//                    for (ImageItem imageItem : mImageItem) {
//                        Log.d(TAG, "imageItem  ==========> "+imageItem.toString());
//                    }

                    mImageListAdapter.setData(mImageItem);
                }

            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {

            }
        });



    }

    @Override
    public void OnItemSelectedChange(List<ImageItem> mSelectedItems) {
        //所选择的数据发生变化
        finishView.setText("("+mSelectedItems.size()+"/"+mImageListAdapter.getmMaxSelectedCount()+")完成");

    }

    public void func(ImageView view)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),43);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap base = Bitmap.createBitmap(width, height,bitmap.getConfig());
//        Bitmap B=toGrayscale(base);
        Canvas canvas = new Canvas(base);//以base为模板创建canvas对象
        canvas.drawBitmap(bitmap, new Matrix(),new Paint());

        for(int i = 0; i < width; i++)//遍历像素点
        {
            for(int j = 0; j < height*0.5; j++)
            {
                int color = bitmap.getPixel(i, j);

                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                int a = Color.alpha(color);

                int avg = (r+g+b)/3;//RGB均值

                if(avg >= 150)//100可以理解为经验值
                {
                    base.setPixel(i, j, Color.argb(a, 255, 255, 255));//设为白色
                }
                else
                {
                    base.setPixel(i, j, Color.argb(a, 0, 0, 0));//设为黑色
                }
            }
        }
        bitmap.recycle();
        view.setImageBitmap(base);
    }

}

package com.example.myapplicationgb;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class siFragment extends Fragment {
    private String context;
    private TextView mTextView;
    private Button daka;
    int i=0;
    private TextView day;
    int year1,month1,day1;
    private DatabaseHelper mDatabaseHelper ;
    private List<CostBean> mCostBeanList;

    public  int all1=0;

    TextView zhichu_money;
    TextView yue_money;
    EditText jine;
    String jin;
    public  int all_1;
    Button queding;
    PieChartView pieChart;
    PieChartData data;


    public siFragment(String context){
        this.context = context;
    }

    public siFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jin=jine.getText().toString();
                all_1=Integer.parseInt(jin);
                Log.e("11111", String.valueOf(all_1));
            }
        });
        //all1=all_1;
        //Log.e("222", String.valueOf(all1));
        //取本月
        Calendar calendar = Calendar.getInstance();
        year1 = calendar.get(Calendar.YEAR);
        month1 = calendar.get(Calendar.MONTH);
        day1 = calendar.get(Calendar.DAY_OF_MONTH);
        String i= siFragment.this.month1+"月";
        Log.e("22",i);
        Cursor cursor =mDatabaseHelper.selectlist(i);
        if (cursor != null){
            while (cursor.moveToNext()){
                CostBean costBean = new CostBean();
                costBean.costTitle = cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostBeanList.add(costBean);
                Log.e("data",costBean.costMoney );

            }
            cursor.close();
        }
        SumList();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.wode,container,false);

        mDatabaseHelper = new DatabaseHelper(getActivity());
        mCostBeanList = new ArrayList<>();

        daka=view.findViewById(R.id.daka);
        day=view.findViewById(R.id.day);
        daka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                int f=i;
                daka.setText("已打卡");
                day.setText(i+"天");
            }
        });

        zhichu_money=view.findViewById(R.id.zhichu_money);
        jine=view.findViewById(R.id.jine);
        yue_money=view.findViewById(R.id.yue_money);
        queding=view.findViewById(R.id.queding);
        pieChart = view.findViewById(R.id.pie_chart);
        pieChart.setOnValueTouchListener(new PieChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int i, SliceValue sliceValue) {
                Toast.makeText(view.getContext(), "选中值"+sliceValue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });
        initDatas();

        return view;
    }

    private void initDatas() {
        int numValues = 6;
        //初始化数据
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            values.add(sliceValue);
        }

//        SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
//        values.add(sliceValue);
//        sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
//        values.add(sliceValue);

        data = new PieChartData(values);
        data.setHasCenterCircle(true);//设置圆环
        //data.setHasLabels(true);//有标签
        data.setHasLabelsOnlyForSelected(true);//选中才显示标签
        //data.setHasLabelsOutside(true);//标签显示在外面
        data.setCenterText2("预算");//设置中心文字1
        pieChart.setPieChartData(data);
    }


    private void SumList() {
        int sumzhichu = 0;
        int sumshouru = 0;
        int lastyue = 0;
        int all=0;
        for (int i = 0; i < mCostBeanList.size(); i++) {
            CostBean costBean = mCostBeanList.get(i);
            int  costMoney = Integer.parseInt(costBean.getCostMoney());

            if(costMoney<=0){
                sumzhichu += costMoney;
            }else{
                sumshouru += costMoney;
            }
        }
        lastyue = sumzhichu+sumshouru;
        all=all_1+sumzhichu;
        //shouru.setText(String.valueOf(sumshouru));
        zhichu_money.setText(String.valueOf(sumzhichu));
        yue_money.setText(String.valueOf(all));

    }
}

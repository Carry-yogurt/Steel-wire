package com.example.myapplicationgb;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class sanFragment extends Fragment {
    private String context;
    private DatabaseHelper mDatabaseHelper ;
    private List<CostBean> mCostBeanList;
    private LineChartView mChart;
    private LineChartData mData;
    private Map<String,Integer> table = new TreeMap<>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private List<AxisValue> values = new ArrayList<>();
    TextView zhichu;
    TextView shouru;
    TextView yue;
    Button button;
    EditText editText;

    int all;


    public sanFragment(String context){
        this.context = context;
    }

    public sanFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i = editText.getText().toString();
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
                generateValues(mCostBeanList);
                generateData();
                SumList();
            }



        });
    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment,container,false);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        mCostBeanList = new ArrayList<>();
        button = view.findViewById(R.id.button);
        editText = view.findViewById(R.id.editText);
        mChart = view.findViewById(R.id.chart);
        zhichu = view.findViewById(R.id.zhichu);
        shouru = view.findViewById(R.id.shouru);
        yue = view.findViewById(R.id.yue);
        mData = new LineChartData();

        return view;
    }
    private void SumList() {
        int sumzhichu = 0;
        int sumshouru = 0;
        int lastyue = 0;
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
        shouru.setText(String.valueOf(sumshouru));
        zhichu.setText(String.valueOf(sumzhichu));
        yue.setText(String.valueOf(lastyue));


    }

    private void generateData(){
        List<Line> lines = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();
        int indexX = 0;
        for(Integer value : table.values()){
            values.add(new PointValue(indexX, value));
            indexX++;
        }
        Line line = new Line(values);
        line.setColor(ChartUtils.COLORS[0]);
        line.setShape(ValueShape.CIRCLE);
        line.setPointColor(ChartUtils.COLORS[1]);
        lines.add(line);
        mData.setLines(lines);
        setAxis();
        mChart.setLineChartData(mData);

    }
    private void generateValues(List<CostBean> mCostBeanList) {
        if(mCostBeanList != null){
            for (int i = 0; i < mCostBeanList.size(); i++) {
                CostBean costBean = mCostBeanList.get(i);
                String costDate = costBean.getCostDate();
                int  costMoney = Integer.parseInt(costBean.getCostMoney());
                mAxisXValues.add(new AxisValue(i).setLabel(costDate));
                if(!table.containsKey(costDate)){
                    table.put(costDate,costMoney);
                    //mAxisXValues.add(new AxisValue(i).setLabel(costDate));
                }else {
                    int originMoney = table.get(costDate);
                    table.put(costDate,originMoney + costMoney);
                }
            }
        }
    }

    private void setAxis() {
        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.GRAY);  //设置字体颜色
//                axisX.setName("日期");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(7); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        mData.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线


        Axis axisY = new Axis().setHasLines(true);
        axisY.setMaxLabelChars(6);//max label length, for example 60

        for(int i = 0; i < 100; i+=5 ){//Y轴的坐标范围  10 -15
            AxisValue value = new AxisValue(i);
            String label = i + "￥";
            value.setLabel(label);
            values.add(value);
        }
        axisY.setValues(values);
//                axisY.setName("钱");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        mData.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边
    }


}

package com.example.myapplicationgb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class shourufragment extends Fragment {


    private TextView queren;
    DatePicker datePicker;

    private TextView mTextView;
    private ImageButton gongzi;
    private ImageButton hongbao;
    private ImageButton jianzhi;
    private ImageButton lijin;
    private ImageButton qita;
    private TextView add_rili;

    private List<CostBean> mCostBeanList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter mAdapter;

    int year,month,day;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jizhang_shouru, container, false);


        //日历
        add_rili=view.findViewById(R.id.add_rili1);
        //获取页面上的日期选择器
        datePicker = view.findViewById(R.id.datePicker);
        //获取当前日期
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        add_rili.setText(shourufragment.this.year+"年"+(shourufragment.this.month+1)+"月"+shourufragment.this.day+"日");
        //初始化日期选择器并设置日期改变监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //获取选中的年月日
                shourufragment.this.year = year;
                //月份是从0开始的
                shourufragment.this.month = (monthOfYear+1);
                shourufragment.this.day = dayOfMonth;
                //弹窗显示
                add_rili.setText(shourufragment.this.year+"年"+shourufragment.this.month+"月"+shourufragment.this.day+"日");
                Toast.makeText(getActivity(),shourufragment.this.year+"年"+shourufragment.this.month+"月"+shourufragment.this.day+"日",Toast.LENGTH_SHORT).show();
            }
        });


        mDatabaseHelper = new DatabaseHelper(getActivity());
        mCostBeanList = new ArrayList<>();
        mAdapter = new CostListAdapter(getActivity(), mCostBeanList);
        // costList.setAdapter(mAdapter);
        final TextView title = view.findViewById(R.id.type_shouru);
        final TextView money = view.findViewById(R.id.tb_shouru_money);
        final TextView date = view.findViewById(R.id.add_rili1);
        queren = view.findViewById(R.id.queren1);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CostBean costBean = new CostBean();
                costBean.costTitle = title.getText().toString();
                costBean.costMoney = "+" + money.getText().toString();
                costBean.costDate = date.getText().toString();
                mDatabaseHelper.insertCost(costBean);
                mCostBeanList.add(costBean);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
            }
        });


        gongzi = (ImageButton) view.findViewById(R.id.gongzi);

        mTextView = (TextView) view.findViewById(R.id.type_shouru);
        gongzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("工资");
            }
        });

        hongbao=(ImageButton) view.findViewById(R.id.a_hongbao);
        hongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("红包");
            }
        });

        jianzhi=(ImageButton) view.findViewById(R.id.jianzhi);
        jianzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("兼职");
            }
        });


        lijin=(ImageButton) view.findViewById(R.id.lijin);
        lijin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("礼金");
            }
        });
        qita=(ImageButton) view.findViewById(R.id.qita);
        qita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("其他");
            }
        });
        return view;
    }

}

package com.example.myapplicationgb;

import android.os.Bundle;
import android.util.Log;
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

public class zhichufragmet extends Fragment {


    private TextView queren;
    DatePicker datePicker;

    private TextView mTextView;
    private ImageButton canyin;
    private ImageButton gouwu;
    private ImageButton chongzhi;
    private ImageButton hongbao;
    private ImageButton jiaotong;
    private ImageButton liwu;
    private ImageButton shuiguo;
    private ImageButton yiliao;
    private ImageButton yule;
    private TextView add_rili;

    private List<CostBean> mCostBeanList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter mAdapter;
    private String money;

    int year,month,day;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jizhang_zhichu, container, false);



        //日历
        add_rili=view.findViewById(R.id.add_rili);
        //setContentView(R.layout.activity_data_picker);
        //获取页面上的日期选择器
        datePicker = view.findViewById(R.id.datePicker);
        //获取当前日期
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        String i=zhichufragmet.this.month+"月";
        Log.e("22",i);
        add_rili.setText(zhichufragmet.this.year+"年"+(zhichufragmet.this.month+1)+"月"+zhichufragmet.this.day+"日");
        Log.e("data",zhichufragmet.this.year+"年"+zhichufragmet.this.month+"月"+zhichufragmet.this.day+"日" );
        //初始化日期选择器并设置日期改变监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //获取选中的年月日
                zhichufragmet.this.year = year;
                //月份是从0开始的
                zhichufragmet.this.month = (monthOfYear+1);
                zhichufragmet.this.day = dayOfMonth;
                //弹窗显示
                add_rili.setText(zhichufragmet.this.year+"年"+zhichufragmet.this.month+"月"+zhichufragmet.this.day+"日");
                Toast.makeText(getActivity(),zhichufragmet.this.year+"年"+zhichufragmet.this.month+"月"+zhichufragmet.this.day+"日",Toast.LENGTH_SHORT).show();
            }
        });










        mDatabaseHelper = new DatabaseHelper(getActivity());
        mCostBeanList = new ArrayList<>();
        mAdapter = new CostListAdapter(getActivity(), mCostBeanList);
        // costList.setAdapter(mAdapter);
        final TextView title = view.findViewById(R.id.type);
        final TextView money = view.findViewById(R.id.tb_note_money);
        final TextView date = view.findViewById(R.id.add_rili);
        queren = view.findViewById(R.id.queren);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CostBean costBean = new CostBean();
                costBean.costTitle = title.getText().toString();
                costBean.costMoney = "-" + money.getText().toString();
                costBean.costDate = date.getText().toString();
                mDatabaseHelper.insertCost(costBean);
                mCostBeanList.add(costBean);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
            }
        });






        canyin = (ImageButton) view.findViewById(R.id.canyin);

        mTextView = (TextView) view.findViewById(R.id.type);
        canyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("餐饮");
            }
        });
        gouwu=(ImageButton) view.findViewById(R.id.gouwu);
        gouwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("购物");
            }
        });

        chongzhi=(ImageButton) view.findViewById(R.id.tongxun);
        chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("充值");
            }
        });

        hongbao=(ImageButton) view.findViewById(R.id.hongbao);
        hongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("红包");
            }
        });
        jiaotong=(ImageButton) view.findViewById(R.id.jiaotong);
        jiaotong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("交通");
            }
        });
        liwu=(ImageButton) view.findViewById(R.id.liwu);
        liwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("礼物");
            }
        });
        shuiguo=(ImageButton) view.findViewById(R.id.shuiguo);
        shuiguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("水果");
            }
        });
        yiliao=(ImageButton) view.findViewById(R.id.yiliao);
        yiliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("医疗");
            }
        });
        yule=(ImageButton) view.findViewById(R.id.yule);
        yule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("娱乐");
            }
        });




        return view;
    }
}
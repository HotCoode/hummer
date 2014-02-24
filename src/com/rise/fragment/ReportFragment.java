package com.rise.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.common.DateUtils;
import com.base.orm.QueryHelper;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;
import com.rise.R;
import com.rise.db.SQL;
import com.rise.db.SqlConst;

/**
 * Created by kai.wang on 2/22/14.
 */
public class ReportFragment extends Fragment implements BaseFragment{
    private PieGraph pieGraph;
    private int perfectCount,upholdCount,quickCount,badCount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container = (ViewGroup) inflater.inflate(R.layout.fragment_report,null);
        injectViews(container);
        loadData(DateUtils.getYear(),DateUtils.getMonth());
        return container;
    }

    @Override
    public void injectViews(View parentView) {
        pieGraph = (PieGraph) parentView.findViewById(R.id.report_pie_graph);

        PieSlice slice = new PieSlice();
        slice.setColor(Color.parseColor("#99CC00"));
        slice.setValue(2);
        pieGraph.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#FFBB33"));
        slice.setValue(3);
        pieGraph.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#AA66CC"));
        slice.setValue(8);
        pieGraph.addSlice(slice);

    }

    private void loadData(int year,int month){
        QueryHelper.findCount(SQL.COUNT_NOTES_BY_TYPE_AND_MONTH,
                new String[]{SqlConst.NOTE_TYPE_HIGH_INCOME_LONG_HALF_LIFE, DateUtils.getStartOfMonth(year,month) + "",
                        DateUtils.getEndOfMonth(year,month) + "",
                        SqlConst.NOTE_STATUS_AVAILABLE},
                new QueryHelper.NumberCallBack() {
            @Override
            public void onFinish(Number num) {
                perfectCount = num.intValue();
                // todo 其他type的数据
            }
        });
    }
}
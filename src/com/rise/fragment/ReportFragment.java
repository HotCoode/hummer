package com.rise.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.base.common.DateUtils;
import com.base.orm.QueryHelper;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;
import com.rise.R;
import com.rise.adapter.ReportListAdapter;
import com.rise.bean.Report;
import com.rise.db.SQL;
import com.rise.db.SqlConst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai.wang on 2/22/14.
 */
public class ReportFragment extends Fragment implements BaseFragment,View.OnClickListener{
    private PieGraph pieGraph;
    private int perfectCount,upholdCount,quickCount,badCount;

	private final int REPORT_LOAD_FINISH = 100;

	private ListView reportListView;

    private ImageButton previousMonthBtn,nextMonthBtn;
    private TextView showMonthView;

	private ReportListAdapter adapter;
	private List<Report> reports = new ArrayList<Report>();

    private int monthOffset = 0;

	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if(msg.what == REPORT_LOAD_FINISH){
				updateReportViews();
			}
			return false;
		}
	});

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
        previousMonthBtn = (ImageButton) parentView.findViewById(R.id.report_previous_month);
        nextMonthBtn = (ImageButton) parentView.findViewById(R.id.report_next_month);
        showMonthView = (TextView) parentView.findViewById(R.id.report_month);

        showMonthView.setText(DateUtils.getNormalMonth(DateUtils.getMonth()));
        previousMonthBtn.setOnClickListener(this);
        nextMonthBtn.setOnClickListener(this);

        reportListView = (ListView) parentView.findViewById(R.id.report_list);
		adapter = new ReportListAdapter(getActivity(),reports);
	    reportListView.setAdapter(adapter);
    }

    private void loadData(int year,int month){
        previousMonthBtn.setClickable(false);
        nextMonthBtn.setClickable(false);
        reports.clear();

	    QueryHelper.findCount(SQL.COUNT_NOTES_BY_TYPE_AND_MONTH, new String[]{SqlConst.NOTE_TYPE_HIGH_INCOME_LONG_HALF_LIFE, DateUtils.getStartOfMonth(year, month) + "", DateUtils.getEndOfMonth(year, month) + "", SqlConst.NOTE_STATUS_AVAILABLE}, new QueryHelper.NumberCallBack() {
		    @Override
		    public void onFinish(Number num) {
                perfectCount = num.intValue();

                Report report = new Report();
                report.setType(SqlConst.NOTE_TYPE_HIGH_INCOME_LONG_HALF_LIFE);
                report.setCount(perfectCount);
                reports.add(report);
		    }
	    });
	    QueryHelper.findCount(SQL.COUNT_NOTES_BY_TYPE_AND_MONTH, new String[]{SqlConst.NOTE_TYPE_LOW_INCOME_LONG_HALF_LIFE, DateUtils.getStartOfMonth(year, month) + "", DateUtils.getEndOfMonth(year, month) + "", SqlConst.NOTE_STATUS_AVAILABLE}, new QueryHelper.NumberCallBack() {
		    @Override
		    public void onFinish(Number num) {
			    upholdCount = num.intValue();

                Report report = new Report();
                report.setType(SqlConst.NOTE_TYPE_LOW_INCOME_LONG_HALF_LIFE);
                report.setCount(upholdCount);
                reports.add(report);
		    }
	    });
	    QueryHelper.findCount(SQL.COUNT_NOTES_BY_TYPE_AND_MONTH, new String[]{SqlConst.NOTE_TYPE_HIGH_INCOME_SHORT_HALF_LIFE, DateUtils.getStartOfMonth(year, month) + "", DateUtils.getEndOfMonth(year, month) + "", SqlConst.NOTE_STATUS_AVAILABLE}, new QueryHelper.NumberCallBack() {
		    @Override
		    public void onFinish(Number num) {
			    quickCount = num.intValue();

                Report report = new Report();
                report.setType(SqlConst.NOTE_TYPE_HIGH_INCOME_SHORT_HALF_LIFE);
                report.setCount(quickCount);
                reports.add(report);
		    }
	    });
	    QueryHelper.findCount(SQL.COUNT_NOTES_BY_TYPE_AND_MONTH, new String[]{SqlConst.NOTE_TYPE_LOW_INCOME_SHORT_HALF_LIFE, DateUtils.getStartOfMonth(year, month) + "", DateUtils.getEndOfMonth(year, month) + "", SqlConst.NOTE_STATUS_AVAILABLE}, new QueryHelper.NumberCallBack() {
		    @Override
		    public void onFinish(Number num) {
			    badCount = num.intValue();

                Report report = new Report();
                report.setType(SqlConst.NOTE_TYPE_LOW_INCOME_SHORT_HALF_LIFE);
                report.setCount(badCount);
                reports.add(report);

                handler.sendEmptyMessage(REPORT_LOAD_FINISH);
		    }
	    });
    }

	private void updateReportViews(){
        adapter.notifyDataSetChanged();

        pieGraph.removeSlices();
		PieSlice slice = new PieSlice();
		slice.setColor(getResources().getColor(R.color.perfect));
		slice.setValue(perfectCount);
		pieGraph.addSlice(slice);

		slice = new PieSlice();
		slice.setColor(getResources().getColor(R.color.uphold));
		slice.setValue(upholdCount);
		pieGraph.addSlice(slice);

		slice = new PieSlice();
		slice.setColor(getResources().getColor(R.color.quick));
		slice.setValue(quickCount);
		pieGraph.addSlice(slice);

		slice = new PieSlice();
		slice.setColor(getResources().getColor(R.color.bad));
		slice.setValue(badCount);
		pieGraph.addSlice(slice);

        previousMonthBtn.setClickable(true);
        nextMonthBtn.setClickable(true);
	}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.report_previous_month:
                monthOffset -- ;
                break;
            case R.id.report_next_month:
                if(monthOffset == 0) return;
                monthOffset ++ ;
                break;
        }
        int time[] = DateUtils.computeMonth(monthOffset);
        loadData(time[0],time[1]);
        showMonthView.setText(DateUtils.getNormalMonth(time[1]));
    }
}
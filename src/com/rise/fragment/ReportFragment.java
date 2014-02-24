package com.rise.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
public class ReportFragment extends Fragment implements BaseFragment{
    private PieGraph pieGraph;
    private int perfectCount,upholdCount,quickCount,badCount;

	private final int REPORT_LOAD_FINISH = 100;

	private ListView reportListView;

	private ReportListAdapter adapter;
	private List<Report> reports = new ArrayList<Report>();

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

	    reportListView = (ListView) parentView.findViewById(R.id.report_list);
		adapter = new ReportListAdapter(getActivity(),reports);
	    reportListView.setAdapter(adapter);
    }

    private void loadData(int year,int month){
	    QueryHelper.findCount(SQL.COUNT_NOTES_BY_TYPE_AND_MONTH, new String[]{SqlConst.NOTE_TYPE_HIGH_INCOME_LONG_HALF_LIFE, DateUtils.getStartOfMonth(year, month) + "", DateUtils.getEndOfMonth(year, month) + "", SqlConst.NOTE_STATUS_AVAILABLE}, new QueryHelper.NumberCallBack() {
		    @Override
		    public void onFinish(Number num) {
			    perfectCount = num.intValue();
		    }
	    });
	    QueryHelper.findCount(SQL.COUNT_NOTES_BY_TYPE_AND_MONTH, new String[]{SqlConst.NOTE_TYPE_LOW_INCOME_LONG_HALF_LIFE, DateUtils.getStartOfMonth(year, month) + "", DateUtils.getEndOfMonth(year, month) + "", SqlConst.NOTE_STATUS_AVAILABLE}, new QueryHelper.NumberCallBack() {
		    @Override
		    public void onFinish(Number num) {
			    upholdCount = num.intValue();
		    }
	    });
	    QueryHelper.findCount(SQL.COUNT_NOTES_BY_TYPE_AND_MONTH, new String[]{SqlConst.NOTE_TYPE_HIGH_INCOME_SHORT_HALF_LIFE, DateUtils.getStartOfMonth(year, month) + "", DateUtils.getEndOfMonth(year, month) + "", SqlConst.NOTE_STATUS_AVAILABLE}, new QueryHelper.NumberCallBack() {
		    @Override
		    public void onFinish(Number num) {
			    quickCount = num.intValue();
		    }
	    });
	    QueryHelper.findCount(SQL.COUNT_NOTES_BY_TYPE_AND_MONTH, new String[]{SqlConst.NOTE_TYPE_LOW_INCOME_SHORT_HALF_LIFE, DateUtils.getStartOfMonth(year, month) + "", DateUtils.getEndOfMonth(year, month) + "", SqlConst.NOTE_STATUS_AVAILABLE}, new QueryHelper.NumberCallBack() {
		    @Override
		    public void onFinish(Number num) {
			    badCount = num.intValue();
		    }
	    });
    }

	private void updateReportViews(){
		// todo 删除已经存在的饼状图

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

		adapter.notifyDataSetChanged();
	}
}
package com.rise.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rise.R;
import com.rise.bean.Report;
import com.rise.common.RiseUtil;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by kai on 2/24/14.<br/>
 * Function :
 */
public class ReportListAdapter extends BaseAdapter {
    private List<Report> reports;

    private Context context;

    public ReportListAdapter(Context context, List<Report> reports) {
        this.context = context;
        this.reports = reports;
    }


    @Override
    public int getCount() {
        return reports.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        float countEvent = 0;
        for (Report report : reports) {
            countEvent += report.getCount();
        }
        if (countEvent != 0) {
            for (int i=0;i<reports.size();i++) {
                Report report = reports.get(i);
                int p = (int) (report.getCount() / countEvent * 100f);
                report.setPercent(p);
            }
        }
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.item_report, null);
        View percentContainer = convertView.findViewById(R.id.percent_container);
        TextView percent = (TextView) convertView.findViewById(R.id.percent);
        TextView countEvent = (TextView) convertView.findViewById(R.id.count_event);

        percentContainer.setBackgroundResource(RiseUtil.getColorByType(reports.get(position).getType()));
        percent.setText(reports.get(position).getPercent() + "");
        countEvent.setText(MessageFormat.format(context.getResources().getString(R.string.event_count), reports.get(position).getCount() + ""));
        return convertView;
    }
}

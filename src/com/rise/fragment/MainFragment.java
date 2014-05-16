package com.rise.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.base.orm.QueryHelper;
import com.rise.ItemsManageActivity;
import com.rise.R;
import com.rise.adapter.MainListAdapter;
import com.rise.bean.Item;
import com.rise.common.Const;
import com.rise.db.SQL;
import com.rise.db.SqlConst;
import com.rise.view.BoxView;

import java.util.List;

/**
 * Created by kai.wang on 2/11/14.
 * 主页面
 */
public class MainFragment extends Fragment implements BaseFragment, BoxView.BoxListener {
    private ListView mainListView;
    private BoxView perfectBox;
    private BoxView upholdBox;
    private BoxView quickBox;
    private BoxView badBox;

    private MainListAdapter mainListAdapter;
    private Activity activity;

    private ImageView putAnimView;
    private Animation animation;
    private Drawable circlePerfect, circleUphold, circleQuick, circleBad;

    private final int ITEM_LOAD_FINISH = 100;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == ITEM_LOAD_FINISH){
                mainListView.setAdapter(mainListAdapter);
            }
            return false;
        }
    });

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(Const.ACTION_ITEM_UPDATE.equals(action)){
                loadData();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        activity = getActivity();
        container = (ViewGroup) inflater.inflate(R.layout.fragment_main, null);
        injectViews(container);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Const.ACTION_ITEM_UPDATE);
        activity.registerReceiver(broadcastReceiver, intentFilter);

        return container;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void injectViews(View parentView) {
        perfectBox = (BoxView) parentView.findViewById(R.id.perfect_box);
        upholdBox = (BoxView) parentView.findViewById(R.id.uphold_box);
        quickBox = (BoxView) parentView.findViewById(R.id.quick_box);
        badBox = (BoxView) parentView.findViewById(R.id.bad_box);
        putAnimView = (ImageView) parentView.findViewById(R.id.put_anim);

        mainListView = (ListView) parentView.findViewById(R.id.main_list_view);

        perfectBox.setBoxListener(this);
        upholdBox.setBoxListener(this);
        quickBox.setBoxListener(this);
        badBox.setBoxListener(this);
        animation = AnimationUtils.loadAnimation(activity, R.anim.put);

        circlePerfect = activity.getResources().getDrawable(R.drawable.circle_perfect);
        circleUphold = activity.getResources().getDrawable(R.drawable.circle_uphold);
        circleQuick = activity.getResources().getDrawable(R.drawable.circle_quick);
        circleBad = activity.getResources().getDrawable(R.drawable.circle_bad);

    }

    @Override
    public void onEnter(BoxView view) {
        hover(view, true);
    }

    @Override
    public void onPut(BoxView view, ClipData data) {
        hover(view, false);
        if (data != null) {
            startPutAnim(view.getId());
            putNote(view.getId(),data.getItemAt(0).getText().toString());
        } else {
            Toast.makeText(activity, R.string.put_fail, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onExit(BoxView view) {
        hover(view, false);
    }

    /**
     * 添加新note
     * @param boxId
     * @param itemId
     */
    private void putNote(int boxId,String itemId){
        String type = "";
        switch (boxId){
            case R.id.perfect_box:
                type = SqlConst.NOTE_TYPE_HIGH_INCOME_LONG_HALF_LIFE;
                break;
            case R.id.uphold_box:
                type = SqlConst.NOTE_TYPE_LOW_INCOME_LONG_HALF_LIFE;
                break;
            case R.id.quick_box:
                type = SqlConst.NOTE_TYPE_HIGH_INCOME_SHORT_HALF_LIFE;
                break;
            case R.id.bad_box:
                type = SqlConst.NOTE_TYPE_LOW_INCOME_SHORT_HALF_LIFE;
                break;
        }
        QueryHelper.update(SQL.PUT_NEW_NOTE, new String[]{itemId, type, System.currentTimeMillis() + ""}, null);
    }

    /**
     * 设置hover画面响应
     *
     * @param view
     * @param isEnter
     */
    private void hover(BoxView view, boolean isEnter) {
        View answerView = ((ViewGroup) view.getParent()).getChildAt(0);
        int width;
        if (isEnter) {
            width = getResources().getDimensionPixelSize(R.dimen.hover_width);
        } else {
            width = getResources().getDimensionPixelSize(R.dimen.hover_show_width);
        }
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) answerView.getLayoutParams();
        params.width = width;
        answerView.setLayoutParams(params);
    }

    /**
     * 加載數據
     */
    private void loadData() {
        QueryHelper.findBeans(
                Item.class,
                SQL.FIND_ITEMS_BY_STATUS,
                new String[]{SqlConst.ITEM_STATUS_AVAILABLE},
                new QueryHelper.FindBeansCallBack<Item>() {
                    @Override
                    public void onFinish(List<Item> beans) {
                        mainListAdapter = new MainListAdapter(activity, beans);
                        handler.sendEmptyMessage(ITEM_LOAD_FINISH);
                    }
                });
    }

    private void startPutAnim(int id) {
        switch (id) {
            case R.id.perfect_box:
                putAnimView.setImageDrawable(circlePerfect);
                break;
            case R.id.uphold_box:
                putAnimView.setImageDrawable(circleUphold);
                break;
            case R.id.quick_box:
                putAnimView.setImageDrawable(circleQuick);
                break;
            case R.id.bad_box:
                putAnimView.setImageDrawable(circleBad);
                break;
            default:
                break;
        }
        putAnimView.startAnimation(animation);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_node, menu);
        menu.findItem(R.id.menu_more).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                startActivity(new Intent(activity, ItemsManageActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        activity.unregisterReceiver(broadcastReceiver);
        super.onDetach();
    }
}

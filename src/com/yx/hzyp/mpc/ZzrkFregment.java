package com.yx.hzyp.mpc;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.yx.hzyp.mpc.exlib.FoldingLayout;
import com.yx.hzyp.mpc.exlib.IContext.IContextImpl;
import com.yx.hzyp.mpc.exlib.OtherClass.Dir;
@SuppressWarnings("all")
public class ZzrkFregment extends Fragment {
	private TextView titleMessage;
	private Context context;
	private String k;// 库信息
	private String name;
	// ==================折叠效果_start===============//
	private String TAG_ARROW = "service_arrow";
	private String TAG_ITEM = "service_item";
	private final int FOLD_ANIMATION_DURATION = 1000;
	private ListView listView;
	// ==================折叠效果_end--===============//
	// ------------------三大控件--------------------
	private LinearLayout containerView;
	private RelativeLayout oTitle;
	private FoldingLayout oFold;

	// -------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bund = getArguments();
		if (IContextImpl.isNotEmpty(bund)) {
			String sfzh = bund.getString("sfzh");
			k = bund.getString("k");
			name = bund.getString("name");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.services_items2, null);
		final LoadUserInfo load = ((LoadUserInfo) getActivity());
		listView = (ListView) rootView.findViewById(R.id.thlist);// 初始化列表控件
		containerView = (LinearLayout) rootView.findViewById(R.id.traffic_layout);
		oTitle = (RelativeLayout) rootView.findViewById(R.id.traffic_bar_layout);// 得到标题
		oFold = (FoldingLayout) rootView.findViewWithTag(TAG_ITEM);// 得到正文Content
		titleMessage = (TextView) oTitle.findViewById(R.id.mm);// 设置标题
		LinearLayout linear = (LinearLayout) rootView;
		Dir dir = load.other.new Dir(linear, oTitle, oFold, null);
		load.other.add(k, dir);
		titleMessage.setText(name);// 设置标题
		oTitle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				load.other.handleAnimation(v, oFold, containerView, load.other.getDir(k).getNextDir().getMyContainer());
			}
		});
		return rootView;
	}
}

package com.yx.hzyp.mpc;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yx.hzyp.mpc.exlib.FoldingLayout;
import com.yx.hzyp.mpc.exlib.IContext.IContextImpl;
import com.yx.hzyp.mpc.exlib.OtherClass.Dir;
/**
 * 
 * 2015年2月5日下午8:38:29
 * 
 * @author Tips
 * 
 */
@SuppressWarnings("all")
public class THFregment extends Fragment {
	private TextView titleMessage;// 标题对象
	private String k;// 类名[包含包名]
	private String dataIn;// 数据来源
	private String name;// 标题的名字
	private ListView listView;// 列表对象
	private String sfzh;// 身份证号码
	// ------------------三大控件--------------------
	private LinearLayout containerView;
	private RelativeLayout oTitle;
	private FoldingLayout oFold;
	private String TAG_ITEM = "service_item";

	// -------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bund = getArguments();
		if (IContextImpl.isNotEmpty(bund)) {
			sfzh = bund.getString("sfzh");
			name = bund.getString("name");
			k = bund.getString("k");
			dataIn = bund.getString("dataIn");
		}
	}
	private LoadUserInfo load;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.services_items, null);
		titleMessage = (TextView) rootView.findViewById(R.id.mm);// 设置标题
		titleMessage.setText(name);// 设置标题
		try {
			load = (LoadUserInfo) getActivity();
			containerView = (LinearLayout) rootView;// 强制转换成LinearLayout对象
			listView = (ListView) rootView.findViewById(R.id.thlist);// 初始化列表对象
			oTitle = (RelativeLayout) rootView.findViewById(R.id.traffic_bar_layout);// 得到标题
			oFold = (FoldingLayout) rootView.findViewWithTag(TAG_ITEM);// 得到正文
			LinearLayout linear = (LinearLayout) rootView;
			Dir dir = load.other.new Dir(linear, oTitle, oFold, null);
			load.other.add(k, dir);
			oTitle.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					load.other.handleAnimation(v, oFold, containerView, load.other.getDir(k).getNextDir().getMyContainer());
				}
			});
		} catch (Exception e) {
			System.out.println("控件初始化失败" + e.getMessage());
		}
		return rootView;
	}
}

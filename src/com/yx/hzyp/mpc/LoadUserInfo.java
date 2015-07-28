package com.yx.hzyp.mpc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yx.hzyp.mpc.exlib.IContext;
import com.yx.hzyp.mpc.exlib.IContext.IContextImpl;
import com.yx.hzyp.mpc.exlib.OtherClass;
@SuppressWarnings("all")
public class LoadUserInfo extends Activity implements IContext {
	private String sfzh = STRING$EMPTY;
	private String kus = STRING$EMPTY;
	private Intent it;
	private LinearLayout containers;
	private StringBuffer strBuffer = new StringBuffer(STRING$EMPTY);// 初始化StringBuffer内存空间
	private List<Map<String, String>> fragments = new ArrayList<Map<String, String>>();// fragments集合
	private Map<String, View> linearList = new HashMap<String, View>();// linearList集合
	public OtherClass other = new OtherClass(this);
	public View view;
	// =======================控件区间=====================//
	private TextView idc, name, sex, age, birthday, address, ssly, whcd, pInfo_current_Address;

	// =======================控件区间=====================//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo);
		try {
			initialFragment(sfzh, kus);
		} catch (Exception e) {
			System.out.println("er:" + IContextImpl.getValue(e.getMessage(), "this Controll init lose"));
		}
	}

	/**
	 * 初始化Fragment控件
	 */
	private void initialFragment(String pSFZH, String dataIn) {
		int start = 0, last = 0;
		XmlResourceParser xml = getResources().getXml(R.xml.configlist);// 配置信息
		try {
			int eventType = xml.getEventType();
			while (eventType != XmlResourceParser.END_DOCUMENT) {
				if (eventType == XmlResourceParser.START_TAG) {// 开始标签
					String strName = xml.getName();
					if (strName.equals("tab")) {
						try {
							boolean lazy = xml.getAttributeBooleanValue(null, "lazy", false);
							boolean visiblity = xml.getAttributeBooleanValue(null, "vb", false);
							String className = xml.getAttributeValue(null, "class");
							String name = xml.nextText();
							if (visiblity) {
								if (IContextImpl.isNotEmpty$Null(className)) {
									Map<String, String> mapTemp = new HashMap<String, String>();
									mapTemp.put("className", className);
									mapTemp.put("pSHZH", pSFZH);
									mapTemp.put("name", name);
									mapTemp.put("dataIn", dataIn);
									fragments.add(mapTemp);
								}
							}
						} catch (Exception e) {
							System.out.println("初始化时失败:" + e);
						}
					}
				}
				eventType = xml.next();
			}
			xml.close();
			last = fragments.size() - 1;// 取得最后的fragment
			for (Map<String, String> ment : fragments) {
				// 创建Fragment
				try {
					createFragment(ment.get("className"), ment.get("pSHZH"), ment.get("name"), ment.get("dataIn"), last == start ? last : 0);
				} catch (Exception e) {
					System.out.println("异常处理:" + e);
				}
				start++;
			}
		} catch (Exception e) {
			System.out.println("Fragment初始化失败" + e.getMessage());
		}
	}

	/**
	 * 创建Fragment
	 * 
	 * 
	 * @param pClass
	 *            :需要创建的类
	 * @throws ClassNotFoundException
	 *             :类丢失异常
	 * @throws IllegalAccessException
	 *             :类型转换异常
	 * @throws InstantiationException
	 */
	private void createFragment(String pClass, String pSFZH, String name, String dataIn, int ls) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (IContextImpl.isNotEmpty$Null(pClass)) {
			Class<Fragment> iClass = (Class<Fragment>) Class.forName(pClass);
			FragmentManager iFragmentManager = this.getFragmentManager();
			FragmentTransaction iFTransaction = iFragmentManager.beginTransaction();
			Fragment iFragment = iClass.newInstance();// 得到Fragment的实例
			Bundle bund = new Bundle();
			bund.putString("sfzh", pSFZH);// 身份证
			bund.putString("name", name);// 当前选项卡的名称
			bund.putString("dataIn", dataIn);// 数据来源
			bund.putInt("callBack", ls);
			bund.putString("k", pClass + num++);// 类
			iFragment.setArguments(bund);
			iFTransaction.add(R.id.framList, iFragment, (pClass + num));// 将类名作为该fragment的标记
			iFTransaction.commit();
		}
	}
	private int num = 1;
	/**
	 * 姓名,性别,身份证,户籍,信息来源,文化程度
	 */
	public final String[] searchFields = { "XM_ns", "XB_ns", "SFZH_ns", "CSRQ_ns", "HKXZ_ntcn", "XXLY_is", "WHCD_ns", "GZDW_ntcn", "ZZXXDZ_ntcn" };

	// =========================================折叠方法==========================================
	public TextView getSex() {
		return sex;
	}

	public TextView getBirthday() {
		return birthday;
	}

	public TextView getIdc() {
		return idc;
	}

	public TextView getAddress() {
		return address;
	}

	public TextView getAge() {
		return age;
	}

	public TextView getSsly() {
		return ssly;
	}

	public TextView getName() {
		return name;
	}

	public TextView getWhcd() {
		return whcd;
	}

	public TextView getpInfo_current_Address() {
		return pInfo_current_Address;
	}
}

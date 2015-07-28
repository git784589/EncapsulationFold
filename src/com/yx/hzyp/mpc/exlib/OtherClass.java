package com.yx.hzyp.mpc.exlib;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yx.hzyp.mpc.R;
import com.yx.hzyp.mpc.exlib.IContext.IContextImpl;

/**
 * 第三方代理类
 * 
 * @author 菩提公子
 * 
 *         2015年3月13日->上午11:35:59
 * 
 */
public class OtherClass {
	private Context context;
	private String TAG_ARROW = "service_arrow";
	private String TAG_ITEM = "service_item";

	public OtherClass(Context context) {
		this.context = context;
	}

	public OtherClass() {
	}

	private final static Map<String, Dir> dirs = new LinkedHashMap<String, Dir>();

	/**
	 * 不为NULL值的键
	 * 
	 * @param key
	 * @param dir
	 */
	public void add(String key, Dir dir) {
		if (IContextImpl.isNotEmpty$Null(key) && IContextImpl.isNotEmpty(dir)) {
			dirs.put(key, dir);
			Dir myDir = getLinearFrouView(key);
			if (IContextImpl.isNotEmpty(myDir)) {
				myDir.setNextDir(dir);
			}
		}
	}

	public int Size() {
		return dirs.size();
	}

	/**
	 * 获取当前key的DIR对象
	 */
	public Dir getDir(String key) {
		Dir dir = null;
		if (IContextImpl.isNotEmpty(key) && dirs.containsKey(key)) {
			dir = dirs.get(key);
		}
		return dir;
	}

	public Dir getLinearNextView(String key) {
		if (IContextImpl.isNotEmpty(key) && IContextImpl.isNotEmpty(dirs)) {
			Set<Entry<String, Dir>> lin = dirs.entrySet();
			boolean isFlag = false;
			for (Entry<String, Dir> entry : lin) {
				if (isFlag) {
					return entry.getValue();
				}
				if (entry.getKey().equals(key)) {
					isFlag = true;
				}
			}
		}
		return null;
	}

	public Dir getLinearFrouView(String key) {
		Dir dir = null;
		if (IContextImpl.isNotEmpty(key) && IContextImpl.isNotEmpty(dirs)) {
			Set<Entry<String, Dir>> lin = dirs.entrySet();
			for (Entry<String, Dir> entry : lin) {

				if (entry.getKey().equals(key)) {
					return dir;
				} else {
					dir = entry.getValue();
				}
			}
		}
		return null;
	}
	
	public void clickClose(View v){
		
		
	}
	

	private void setMarginToTop(float foldFactor, View nextParent) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) nextParent.getLayoutParams();
		lp.topMargin = (int) (-dp2px(context, 135) * foldFactor) + dp2px(context, 10);
		nextParent.setLayoutParams(lp);
	}

	public void handleAnimation(final View bar, final FoldingLayout foldingLayout, View parent, final View nextParent) {
		final ImageView arrow = (ImageView) parent.findViewWithTag(TAG_ARROW);
		foldingLayout.setFoldListener(new OnFoldListener() {
			@Override
			public void onStartFold(float foldFactor) {
				bar.setClickable(true);
				arrow.setBackgroundResource(R.drawable.service_arrow_up);
				resetMarginToTop(foldingLayout, foldFactor, nextParent);
			}

			@Override
			public void onFoldingState(float foldFactor, float foldDrawHeight) {
				bar.setClickable(false);
				resetMarginToTop(foldingLayout, foldFactor, nextParent);
			}

			@Override
			public void onEndFold(float foldFactor) {
				bar.setClickable(true);
				arrow.setBackgroundResource(R.drawable.service_arrow_down);
				resetMarginToTop(foldingLayout, foldFactor, nextParent);
			}
		});

		animateFold(foldingLayout, FOLD_ANIMATION_DURATION);

	}

	private final int FOLD_ANIMATION_DURATION = 1000;

	private void resetMarginToTop(View view, float foldFactor, View nextParent) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) nextParent.getLayoutParams();
		lp.topMargin = (int) (-view.getMeasuredHeight() * foldFactor) + dp2px(context, 10);
		nextParent.setLayoutParams(lp);
	}

	@SuppressLint("NewApi")
	public void animateFold(FoldingLayout foldLayout, int duration) {
		float foldFactor = foldLayout.getFoldFactor();
		ObjectAnimator animator = ObjectAnimator.ofFloat(foldLayout, "foldFactor", foldFactor, foldFactor > 0 ? 0 : 1);
		animator.setRepeatMode(ValueAnimator.REVERSE);
		animator.setRepeatCount(0);
		animator.setDuration(duration);
		animator.setInterpolator(new AccelerateInterpolator());
		animator.start();
	}

	public final static int dp2px(Context context, float dpValue) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * density + 0.5f);
	}

	/**
	 * 负责封装控件
	 * 
	 * @author 菩提公子
	 * 
	 *         2015年3月13日->上午11:38:05
	 * 
	 */
	public class Dir {

		private LinearLayout myContainer;
		private RelativeLayout relative;
		private FoldingLayout fold;
		private Dir nextDir;

		public Dir(LinearLayout myContainer, RelativeLayout relative, FoldingLayout fold, Dir nextDir) {
			super();
			this.myContainer = myContainer;
			this.relative = relative;
			this.fold = fold;
			this.nextDir = nextDir;
		}

		public Dir() {
		}

		public RelativeLayout getRelative() {
			return relative;
		}

		public void setRelative(RelativeLayout relative) {
			this.relative = relative;
		}

		public FoldingLayout getFold() {
			return fold;
		}

		public void setFold(FoldingLayout fold) {
			this.fold = fold;
		}

		public LinearLayout getMyContainer() {

			return myContainer;
		}

		public void setMyContainer(LinearLayout myContainer) {
			this.myContainer = myContainer;
		}

		public Dir getNextDir() {
			if (IContextImpl.isEmpty(nextDir)) {
				nextDir = new Dir();
			}
			return nextDir;
		}

		public void setNextDir(Dir nextDir) {
			this.nextDir = nextDir;
		}

	}

}

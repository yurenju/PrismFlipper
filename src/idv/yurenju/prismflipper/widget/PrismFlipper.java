package idv.yurenju.prismflipper.widget;

import idv.yurenju.prismflipper.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class PrismFlipper extends ViewFlipper {
	private static final int DEFAULT_COLOR = 0xFFDDDDDD;
	private static final int DEFAULT_DURATION = 700;
	private static final String DEFAULT_TEXT = "PrismFlipper";
	
	private TextView mCurrentView = null;
	private TextView mNextView = null;
	private Drawable mFrontBackground;
	
	private TransitionDrawable mTopToFrontTransition;
	private TransitionDrawable mFrontToBottomTransition;
	private TransitionDrawable mFrontToTopTransition;
	private TransitionDrawable mBottomToFrontTransition;
	
	private Animation mAnimFlipperToFront = null;
	private Animation mAnimFlipperToBack = null;
	private Animation mAnimRollToFront = null;
	private Animation mAnimRollToBack = null;
	
	private int mDuration = DEFAULT_DURATION;
	private String mFrontText = DEFAULT_TEXT;

	public PrismFlipper(Context context) {
		super(context);
		initAttribute();
	}
	
	public PrismFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttribute();
	}
	
	public void setFrontText(String text) {
		mFrontText = text;
		mCurrentView.setText(mFrontText);
	}
	
	public void setTextColor(int color) {
		mNextView.setTextColor(color);
		mCurrentView.setTextColor(color);
	}
	
	public void setBackground(Drawable d) {
		mFrontBackground = d;
		ColorDrawable topBackground = new ColorDrawable(Color.WHITE);
		ColorDrawable bottomBackground = new ColorDrawable(Color.BLACK);
		
		mTopToFrontTransition = new TransitionDrawable(new Drawable[] {
				topBackground, mFrontBackground });
		mFrontToBottomTransition = new TransitionDrawable(new Drawable[] {
				mFrontBackground, bottomBackground });
		
		mFrontToTopTransition = new TransitionDrawable(new Drawable[] {
				mFrontBackground, topBackground });
		mBottomToFrontTransition = new TransitionDrawable(new Drawable[] {
				bottomBackground, mFrontBackground });
		
		mCurrentView.setBackgroundDrawable(mFrontToBottomTransition);
		mNextView.setBackgroundDrawable(mTopToFrontTransition);
	}
	
	public void setDuration(int duration) {
		mDuration = duration;
	}
	
	private void initAttribute() {
		mCurrentView = new TextView(getContext());
		mNextView = new TextView(getContext());
		mCurrentView.setGravity(Gravity.CENTER);
		mNextView.setGravity(Gravity.CENTER);
		
		mCurrentView.setText(mFrontText);
		
		mAnimFlipperToFront = AnimationUtils.loadAnimation(getContext(),
				R.anim.flipper_to_front);
		mAnimFlipperToFront.setDuration(mDuration);
		
		mAnimRollToFront = AnimationUtils.loadAnimation(getContext(),
				R.anim.roll_to_front);
		mAnimRollToFront.setDuration(mDuration);
		
		mAnimRollToBack = AnimationUtils.loadAnimation(getContext(),
				R.anim.roll_to_back);
		mAnimRollToBack.setDuration(mDuration);
		
		super.setInAnimation(mAnimFlipperToFront);
		
		mAnimFlipperToBack = AnimationUtils.loadAnimation(getContext(),
				R.anim.flipper_to_back);
		mAnimFlipperToBack.setDuration(mDuration);
		
		super.setOutAnimation(mAnimFlipperToBack);
		
		this.addView(mCurrentView);
		this.addView(mNextView);
		setBackground(new ColorDrawable(DEFAULT_COLOR));
	}

	public void showNext(String nextText, boolean reverse) {
		if (nextText.equals(mNextView.getText().toString())) {
			return;
		}		
		
		int viewPosition = this.getDisplayedChild();

		mCurrentView = (TextView)this.getChildAt(viewPosition);
		mNextView = (TextView) this.getChildAt((viewPosition + 1)
				% this.getChildCount());
		
		mNextView.setText(nextText);
		
		if (!reverse) {
			mTopToFrontTransition.resetTransition();
			mFrontToBottomTransition.resetTransition();
			mCurrentView.setBackgroundDrawable(mFrontToBottomTransition);
			mNextView.setBackgroundDrawable(mTopToFrontTransition);
			super.setInAnimation(mAnimFlipperToFront);
			super.setOutAnimation(mAnimFlipperToBack);
			mTopToFrontTransition.startTransition(mDuration);
			mFrontToBottomTransition.startTransition(mDuration);	
		}
		else {
			mBottomToFrontTransition.resetTransition();
			mFrontToTopTransition.resetTransition();
			mNextView.setBackgroundDrawable(mBottomToFrontTransition);
			mCurrentView.setBackgroundDrawable(mFrontToTopTransition);
			super.setInAnimation(mAnimRollToFront);
			super.setOutAnimation(mAnimRollToBack);
			mBottomToFrontTransition.startTransition(mDuration);
			mFrontToTopTransition.startTransition(mDuration);
		}
		super.showNext();
	}
}

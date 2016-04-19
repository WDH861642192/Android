package com.wdh.threes.view;


import com.wdh.threes.R;

import android.content.Context;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleView extends FrameLayout {
	private TextView mTitle;
	private RelativeLayout mLeftLayout;
	private ImageView mRightImg;
	private Button mLeftBtn;
	private TextView mRightBtn;

	public TitleView(Context context) {
		this(context, null);
	}

	public TitleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.title_view, this, true);
		mTitle = (TextView) findViewById(R.id.title_text);
		mLeftLayout = (RelativeLayout) findViewById(R.id.left_layout);
		mRightImg = (ImageView) findViewById(R.id.right_img);
		mLeftBtn = (Button) findViewById(R.id.left_btn);
		mRightBtn = (TextView) findViewById(R.id.right_btn);
		mLeftLayout.setVisibility(View.GONE);
		mRightBtn.setVisibility(View.INVISIBLE);
		mRightImg.setVisibility(View.INVISIBLE);
	}

	public void setTitle(String text) {
		mTitle.setText(text);
	}

	public void setTitle(int string) {
		mTitle.setText(string);
	}

	public void setTitileBg(int bg) {
		mTitle.setBackgroundResource(bg);
	}

	public String getTitle() {
		return mTitle.getText().toString();
	}

	public void setBackButton(OnClickListener listener) {
		mLeftLayout.setVisibility(View.VISIBLE);
		if (listener != null)
			mLeftLayout.setOnClickListener(listener);
	}

	public void setLeftButton(String text, int bgId, OnClickListener listener) {
		mLeftBtn.setVisibility(View.VISIBLE);
		mLeftBtn.setText(text);
		mLeftBtn.setBackgroundResource(bgId);
		if (listener != null)
			mLeftBtn.setOnClickListener(listener);
	}

	public void setRightButton(String text, int bgId, OnClickListener listener) {
		if (!TextUtils.isEmpty(text)) {
			mRightBtn.setVisibility(View.VISIBLE);
			mRightBtn.setText(text);
			// mRightBtn.setBackgroundResource(bgId);
			mRightBtn.setGravity(Gravity.CENTER_VERTICAL);
			mRightBtn.setOnClickListener(listener);
		} else
			mRightBtn.setVisibility(View.INVISIBLE);
		if (listener != null)
			mRightImg.setOnClickListener(listener);
		if (bgId != 0) {
			mRightImg.setVisibility(View.VISIBLE);
			mRightImg.setImageResource(bgId);
//			mRightBtn.setBackgroundResource(bgId);
//			if (listener != null)
//				mRightImg.setOnClickListener(listener);
		} else
			mRightImg.setVisibility(View.INVISIBLE);

	}

	public void setLeftButtonTextSize(int size) {
		mLeftBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
		mLeftBtn.setGravity(Gravity.CENTER);
	}

	public void setRightButtonTextSize(int size) {
		mRightBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
		mRightBtn.setGravity(Gravity.CENTER);
	}

	public void setLeftButtonTextColor(String color) {
		mLeftBtn.setTextColor(android.graphics.Color.parseColor(color));
		mLeftBtn.setGravity(Gravity.CENTER);
	}

	public void setRightButtonTextColor(String color) {
		mRightBtn.setTextColor(android.graphics.Color.parseColor(color));
		mRightBtn.setGravity(Gravity.CENTER);
	}

	public void setRightButtonTextColor(int color) {
		mRightBtn.setTextColor(color);
		mRightBtn.setGravity(Gravity.CENTER);
	}

	public boolean changeRightImg(int bgId) {
		if (bgId != 0) {
			mRightImg.setVisibility(View.VISIBLE);
			mRightImg.setImageResource(bgId);
//			mRightBtn.setVisibility(INVISIBLE);
//			mRightBtn.setBackgroundResource(bgId);
		}
		return true;
		
	}
}

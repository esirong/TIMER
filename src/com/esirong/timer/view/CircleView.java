package com.esirong.timer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.esirong.timer.R;

/**
 * Բ��imageView
 * 
 * @author esirong
 * 
 */
public class CircleView extends View {
	private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
	private int mBorderColor = DEFAULT_BORDER_COLOR;
	private boolean mReady;
	private boolean mSetupPending;
	private float mDrawableRadius;
	private Paint mBitmapPaint = new Paint();
	private float mBorderRadius;
	private Paint mBorderPaint = new Paint();

	public CircleView(Context context) {
		super(context);
		init(context, null);
	}

	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		if (null != attrs) {
			TypedArray ta = context.obtainStyledAttributes(attrs,
					R.styleable.CircleView);
			mBorderColor = ta.getColor(R.styleable.CircleView_text_color,
					DEFAULT_BORDER_COLOR);
			ta.recycle();

			mReady = true;

			if (mSetupPending) {
				setup();
				mSetupPending = false;
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// ����
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, mDrawableRadius,
				mBitmapPaint);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, mBorderRadius,
				mBorderPaint);
	}

	// ������
	private void setup() {
		mDrawableRadius = 10;
		mBitmapPaint.setColor(Color.BLUE);
		mBorderRadius = 15;
		mBorderPaint.setColor(Color.CYAN);
		
		//ͼƬ��Ӱ
		 new BitmapShader(null, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mBitmapPaint.setAntiAlias(true);
      
		invalidate();

	}
}

package xyz.toofun.diandian.widget.marker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import xyz.toofun.diandian.R;

/**
 * 代表用户当前位置的圆圈
 * Created by bear on 2017/3/14.
 */

public class MyCircleView extends View {
    private int mShadowColor; //阴影色
    private int mFrameColor; //边框色
    private int mPointColor; //圆点色

    private float mFrameWidth; //边框厚度

    private Paint mPaint; //画笔
    private float mCenterX, mCenterY; //中心的位置

    public MyCircleView(Context context) {
        this(context, null);
    }

    public MyCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mShadowColor = getResources().getColor(R.color.colorTranslateDark);
        mFrameColor = getResources().getColor(R.color.colorWhite);
        mPointColor = getResources().getColor(R.color.colorAccent);

        mFrameWidth = getResources().getDimension(R.dimen.margin_min);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCenterX == 0) {
            mCenterX = getWidth() / 2;
            mCenterY = getHeight() / 2;
        }

        mPaint.setColor(mShadowColor);
        canvas.drawCircle(mCenterX, mCenterY, mCenterX, mPaint);

        mPaint.setColor(mFrameColor);
        canvas.drawCircle(mCenterX, mCenterY, mCenterX - 2, mPaint);

        mPaint.setColor(mPointColor);
        canvas.drawCircle(mCenterX, mCenterY, mCenterX - mFrameWidth / 2, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (int) getResources().getDimension(R.dimen.icon_min);
        setMeasuredDimension(width, width);
    }
}

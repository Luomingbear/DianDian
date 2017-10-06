package xyz.toofun.diandian.widget.marker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

import xyz.toofun.diandian.R

/**
 * 代表用户当前位置的圆圈
 * Created by bear on 2017/3/14.
 */

class MyCircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var mShadowColor: Int = 0 //阴影色
    private var mFrameColor: Int = 0 //边框色
    private var mPointColor: Int = 0 //圆点色

    private var mFrameWidth: Float = 0.toFloat() //边框厚度

    private var mPaint: Paint? = null //画笔
    private var mCenterX: Float = 0.toFloat()
    private var mCenterY: Float = 0.toFloat() //中心的位置

    init {
        init()
    }

    private fun init() {
        mShadowColor = resources.getColor(R.color.colorTranslateDark)
        mFrameColor = resources.getColor(R.color.colorWhite)
        mPointColor = resources.getColor(R.color.colorAccent)

        mFrameWidth = resources.getDimension(R.dimen.margin_min)

        mPaint = Paint()
        mPaint!!.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        if (mCenterX == 0f) {
            mCenterX = (width / 2).toFloat()
            mCenterY = (height / 2).toFloat()
        }

        mPaint!!.color = mShadowColor
        canvas.drawCircle(mCenterX, mCenterY, mCenterX, mPaint!!)

        mPaint!!.color = mFrameColor
        canvas.drawCircle(mCenterX, mCenterY, mCenterX - 2, mPaint!!)

        mPaint!!.color = mPointColor
        canvas.drawCircle(mCenterX, mCenterY, mCenterX - mFrameWidth / 2, mPaint!!)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = resources.getDimension(R.dimen.icon_min).toInt()
        setMeasuredDimension(width, width)
    }
}

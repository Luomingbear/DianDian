package xyz.toofun.diandian.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import xyz.toofun.diandian.R
import xyz.toofun.diandian.uitl.DipPxConversion


/**
 * 圆角图片
 * Created by bear on 2017/9/30.
 */
class RoundImageView : ImageView {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mMatrix = Matrix()
        mBitmapPaint = Paint()
        mBitmapPaint.setAntiAlias(true)

        val a = context?.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
        if (a != null) {
            mBorderRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_BorderRadius, DipPxConversion.dip2px(context!!, 9f)).toFloat()// 默认为9dp
            type = a.getInt(R.styleable.RoundImageView_RoundType, TYPE_ROUND)
            scale = a.getFloat(R.styleable.RoundImageView_Scale, scale)
            a.recycle()
        }
    }


    /**
     * 图片的类型，圆形、圆角、指定角圆角
     */
    private var type: Int = 0
    val TYPE_CIRCLE = 0
    val TYPE_ROUND = 1

    /**
     * 指定某个角圆角
     */
    private val TYPE_TOP_LEFT_AND_TOP_RIGHT = TYPE_ROUND + 1 //左上角和右上角是圆角
    private val TYPE_BOTTOM_LEFT_AND_BOTTOM_RIGHT = TYPE_TOP_LEFT_AND_TOP_RIGHT + 1 //左下角和右下角是圆角

    /**
     * 圆角的大小
     */
    private var mBorderRadius: Float = 0f
    /**
     * 宽高比
     */
    private var scale = 0f
    /**
     * 绘图的Paint
     */
    private lateinit var mBitmapPaint: Paint
    /**
     * 圆角的半径
     */
    private var mRadius: Float = 0f
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private lateinit var mMatrix: Matrix
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private var mBitmapShader: BitmapShader? = null
    /**
     * view的宽度
     */
    private var mWidth: Int = 0
    private var mRoundRect: RectF? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        if (scale > 0) {
            setMeasuredDimension(View.getDefaultSize(0, widthMeasureSpec), View.getDefaultSize(0, heightMeasureSpec))
            // Children are just made to fill our space.
            val childWidthSize = measuredWidth
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(childWidthSize, View.MeasureSpec.EXACTLY)
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((childWidthSize / scale).toInt(), View.MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        /**
         * 如果类型是圆形，则强制改变view的宽高一致，以小值为准
         */
        if (type == TYPE_CIRCLE) {
            mWidth = Math.min(measuredWidth, measuredHeight)
            mRadius = mWidth / 2f
            setMeasuredDimension(mWidth, mWidth)
        }

    }

    fun setScale(scale: Float) {
        if (scale > 0)
            this.scale = scale
        requestLayout()
    }

    /**
     * 初始化BitmapShader
     */
    private fun setUpShader() {
        val drawable = drawable ?: return

        val bmp = drawableToBitmap(drawable)
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        mBitmapShader = BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        var scale = 1.0f
        if (type == TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值
            val bSize = Math.min(bmp.width, bmp.height)
            scale = mWidth * 1.0f / bSize

        } else {
            if (!(bmp.width == width && bmp.height == height)) {
                // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
                scale = Math.max(width * 1.0f / bmp.width, height * 1.0f / bmp.height)
            }
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale)
        // 设置变换矩阵
        mBitmapShader!!.setLocalMatrix(mMatrix)
        // 设置shader
        mBitmapPaint.setShader(mBitmapShader)
    }

    override fun onDraw(canvas: Canvas) {
        if (drawable == null) {
            return
        }
        setUpShader()

        when (type) {
            TYPE_CIRCLE -> canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint)
            TYPE_BOTTOM_LEFT_AND_BOTTOM_RIGHT -> drawSpecialCorner(canvas)
            TYPE_TOP_LEFT_AND_TOP_RIGHT -> drawSpecialCorner(canvas)
            TYPE_ROUND -> canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius, mBitmapPaint)
        }

    }

    /**
     * 绘制特定的角
     */
    private fun drawSpecialCorner(canvas: Canvas) {
        //原理：先绘制四个圆都是圆角的图片，然后把不需要圆角的地方补上
        canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius, mBitmapPaint)

        when (type) {
            TYPE_TOP_LEFT_AND_TOP_RIGHT -> {
                canvas.drawRect(0f, mRoundRect!!.bottom - mBorderRadius, mBorderRadius, mRoundRect!!.bottom, mBitmapPaint)
                canvas.drawRect(mRoundRect!!.right - mBorderRadius, mRoundRect!!.bottom - mBorderRadius, mRoundRect!!.right, mRoundRect!!.bottom, mBitmapPaint)
            }
            TYPE_BOTTOM_LEFT_AND_BOTTOM_RIGHT -> {
                canvas.drawRect(mRoundRect!!.right - mBorderRadius, 0f, mRoundRect!!.right, mBorderRadius, mBitmapPaint)
                canvas.drawRect(0f, 0f, mBorderRadius, mBorderRadius, mBitmapPaint)
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // 圆角图片的范围
        if (type != TYPE_CIRCLE)
            mRoundRect = RectF(0f, 0f, w.toFloat(), h.toFloat())
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)
        return bitmap
    }

    private val STATE_INSTANCE = "state_instance"
    private val STATE_TYPE = "state_type"
    private val STATE_BORDER_RADIUS = "state_border_radius"

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState())
        bundle.putInt(STATE_TYPE, type)
        bundle.putFloat(STATE_BORDER_RADIUS, mBorderRadius)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            super.onRestoreInstanceState(state.getParcelable(STATE_INSTANCE))
            this.type = state.getInt(STATE_TYPE)
            this.mBorderRadius = state.getFloat(STATE_BORDER_RADIUS)
        } else {
            super.onRestoreInstanceState(state)
        }

    }

    fun setBorderRadius(borderRadius: Float) {
        val pxVal = DipPxConversion.dip2px(context, borderRadius)
        if (this.mBorderRadius != pxVal.toFloat()) {
            this.mBorderRadius = pxVal.toFloat()
            invalidate()
        }
    }

    fun setType(type: Int) {
        if (this.type != type) {
            this.type = type
            if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE) {
                this.type = TYPE_CIRCLE
            }
            requestLayout()
        }

    }
}
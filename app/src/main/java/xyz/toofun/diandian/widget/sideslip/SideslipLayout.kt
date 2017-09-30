package xyz.toofun.diandian.widget.sideslip

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import xyz.toofun.diandian.R


/**
 * 侧滑菜单布局
 * Created by bear on 2017/9/30.
 */
class SideslipLayout : FrameLayout {
    private val TAG = "SideslipLayout"

    private var mLeftViewItem: SideslipViewItem? = null //左边需要显示的viewItem
    private var mTopViewItem: SideslipViewItem? = null //上面需要显示的viewItem
    private var mRightViewItem: SideslipViewItem? = null //右边需要显示的viewItem
    private var mBottomViewItem: SideslipViewItem? = null //下面需要显示的viewItem

    private var mHomeFragment: Fragment? = null //主界面的fragment
    private var mLeftFragment: Fragment? = null
    private var mTopFragment: Fragment? = null
    private var mRightFragment: Fragment? = null
    private var mBottomFragment: Fragment? = null

    private var mHomeView: View? = null //主界面
    private var mShadeView: View? = null //阴影view
    private var mWidth: Int = 0 //宽度
    private var mHeight: Int = 0 //高度
    private var isShowingSide = false //是否正在显示侧滑菜单
    private var isAnimateShowingSide = false //是否执行显示侧滑菜单的动画 true :显示策划菜单,false ：隐藏侧滑菜单
    private val mAnimateTime: Long = 280 //动画时间 ms

    private var mMoveSide = 0 //移动的侧边菜单是哪一面
    private var canHideSideView = true //是否可以通过手势滑动关闭侧滑菜单

    private var mFragmentManager: FragmentManager? = null

    private var isFirstLayout = true
    private var fragmentAddList = arrayListOf<Boolean>(false, false, false, false, false) //主，左，上，右，下

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context?.obtainStyledAttributes(attrs, R.styleable.SideslipLayout)
        var l1 = 0
        var l2 = 0
        var l3 = 0
        var l4 = 0
        var l5 = 0
        if (a != null) {
            l1 = a.getResourceId(R.styleable.SideslipLayout_homeLayout, 0)
            l2 = a.getResourceId(R.styleable.SideslipLayout_leftLayout, 0)
            l3 = a.getResourceId(R.styleable.SideslipLayout_topLayout, 0)
            l4 = a.getResourceId(R.styleable.SideslipLayout_rightLayout, 0)
            l5 = a.getResourceId(R.styleable.SideslipLayout_bottomLayout, 0)
            a.recycle()
        }

        if (l1 != 0)
            mHomeView = LayoutInflater.from(context).inflate(l1, null, false)
        if (l2 != 0)
            mLeftViewItem = SideslipViewItem(LayoutInflater.from(context).inflate(l2, null, false), 0.7f)
        if (l3 != 0)
            mTopViewItem = SideslipViewItem(LayoutInflater.from(context).inflate(l3, null, false), 0.7f)
        if (l4 != 0)
            mRightViewItem = SideslipViewItem(LayoutInflater.from(context).inflate(l4, null, false), 1f)
        if (l5 != 0)
            mBottomViewItem = SideslipViewItem(LayoutInflater.from(context).inflate(l5, null, false), 1f)
        addViews()
    }

    private fun addViews() {
        if (mHomeView != null)
            addView(mHomeView)

        //添加阴影view
        addShadeView()

        if (mLeftViewItem != null)
            addView(mLeftViewItem?.layout)

        if (mTopViewItem != null)
            addView(mTopViewItem?.layout)

        if (mRightViewItem != null)
            addView(mRightViewItem?.layout)

        if (mBottomViewItem != null)
            addView(mBottomViewItem?.layout)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        //得到布局的宽高
        this.mWidth = w
        this.mHeight = h
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        //加入主界面
        if (mHomeView != null) {
            mHomeView?.layout(0, 0, mWidth, mHeight)
        }

        //加入左边的界面
        layoutItemView(mLeftViewItem, Gravity.LEFT)

        //加入上面边的界面
        layoutItemView(mTopViewItem, Gravity.TOP)

        //加入右边的界面
        layoutItemView(mRightViewItem, Gravity.RIGHT)

        //加入下边的界面
        layoutItemView(mBottomViewItem, Gravity.BOTTOM)

        setFragments()

        Log.i(TAG, "onLayout: count:" + getChildCount())
    }

    /**
     * 加入阴影View
     */
    private fun addShadeView() {
        mShadeView = View(context)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mShadeView?.layoutParams = params
        //设置阴影颜色
        mShadeView?.setBackgroundColor(Color.parseColor("#b5353535"))
        addView(mShadeView)
        mShadeView?.layout(0, 0, mWidth, mHeight)
        setShadeViewAlpha(0f)
    }


    /**
     * 添加侧滑的view
     *
     * @param viewItem
     * @param position 位置
     */
    private fun layoutItemView(viewItem: SideslipViewItem?, position: Int) {
        if (viewItem == null || viewItem.layout == null)
            return
        //获取layoutParams
        var p: ViewGroup.LayoutParams? = viewItem.layout.layoutParams
        if (p == null) {
            p = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        when (position) {
            Gravity.LEFT -> {
                val x = (-mWidth * viewItem.scale).toInt()
                //设置宽度
                p.width = (mWidth * viewItem.scale).toInt()
                viewItem.layout.layoutParams = p
                //加入布局
                viewItem.layout.layout(x, 0, 0, mHeight)
            }
            Gravity.TOP -> {
                val t = (-mHeight * viewItem.scale).toInt()
                //
                p.height = (mHeight * viewItem.scale).toInt()
                viewItem.layout.layoutParams = p
                //
                viewItem.layout.layout(0, t, 0, 0)
            }
            Gravity.RIGHT -> {
                val r = (mWidth * (1 + viewItem.scale)).toInt()
                p.width = (mWidth * viewItem.scale).toInt()
                viewItem.layout.layoutParams = p
                //
                viewItem.layout.layout(mWidth, 0, r, mHeight)
            }
            Gravity.BOTTOM -> {
                val b = (mHeight * (1 + viewItem.scale)).toInt()
                p.height = (mHeight * viewItem.scale).toInt()
                viewItem.layout.layoutParams = p
                //
                viewItem.layout.layout(0, mHeight, mWidth, b)
            }
        }
    }

    /**
     * 设置fragment
     */
    fun setFragments() {
        if (mFragmentManager == null)
            return

        if (mHomeView != null && mHomeFragment != null) {
            mFragmentManager?.beginTransaction()?.replace(R.id.sideslip_home_layout, mHomeFragment)?.commit()
        }
        if (mLeftViewItem != null && mLeftFragment != null) {
            mFragmentManager?.beginTransaction()?.replace(R.id.sidelip_left_layout, mLeftFragment)?.commit()

        }

        if (mTopViewItem != null && mTopFragment != null) {
            mFragmentManager?.beginTransaction()?.replace(R.id.sideslip_top_layout, mTopFragment)?.commit()
        }


        if (mRightViewItem != null && mRightFragment != null) {
            mFragmentManager?.beginTransaction()?.replace(R.id.sideslip_right_layout, mRightFragment)?.commit()

        }

        if (mBottomViewItem != null && mBottomFragment != null) {
            mFragmentManager?.beginTransaction()?.replace(R.id.sideslip_bottom_layout, mBottomFragment)?.commit()

        }
    }


    private var mTouchStartX = 0f  //手指按下的x
    private var mTouchStartY = 0f //手指按下的y
    private var mTouchMoveX = 0f //移动的x
    private var mTouchMoveY = 0f //移动的y
    private var mInterceptMoveX = 0f //手指移动的x
    private var mInterceptMoveY = 0f //手指移动的y

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mTouchStartX = ev.x
                mTouchStartY = ev.y

                mInterceptMoveX = mTouchStartX
                mInterceptMoveY = mTouchStartY
                mTouchMoveX = mTouchStartX
                mTouchMoveY = mTouchStartY

                touchDownTime = System.currentTimeMillis() //保存当前时间
            }

            MotionEvent.ACTION_MOVE -> {
                val x = ev.x
                val y = ev.y
                mInterceptMoveX = x - mInterceptMoveX
                mInterceptMoveY = y - mInterceptMoveY

                /**
                 * 先判断手指按下时是否在边界
                 * 如果在边界，并且手指滑动的距离大于10； 则拦截触摸事件
                 */
                if (computeIsTouchInSide(mTouchStartX, mTouchStartY) && canHideSideView) {
                    if (Math.abs(mInterceptMoveX) > 10 || Math.abs(mInterceptMoveY) > 10) {
                        return true
                    }
                }
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
            }
        }
        return super.onInterceptTouchEvent(ev)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val y = event.y
                //变化的值
                val disX = x - mTouchMoveX
                val disY = y - mTouchMoveY
                mTouchMoveX = x
                mTouchMoveY = y

                //移动view
                touchMoveViews(disX, disY)
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                val x = event.x
                val y = event.y

                //移动距离不够则不进行动画
                if (Math.abs(x - mTouchStartX) < 50 && Math.abs(y - mTouchStartY) < 50)
                    return true

                /**
                 * 计算手指抬起的位置，判断是否应该显示或者隐藏侧滑菜单
                 */
                if (computeIsShowSide(event.x, event.y))
                    animateShowSideView(mMoveSide)
                else
                    animateHideSideView(mMoveSide)
            }
        }
        return true
    }


    /**
     * 计算手指按下的位置是否是在侧滑响应区域
     *
     * @param x 按下x
     * @param y 按下y
     * @return
     */
    private fun computeIsTouchInSide(x: Float, y: Float): Boolean {
        return if (x < mWidth / 5 || x > mWidth / 5f * 4 || y < mHeight / 5 || y > mHeight / 5f * 4) true else false
    }

    /**
     * 手指滑动的响应
     *
     * @param moveX
     * @param moveY
     */
    private fun touchMoveViews(moveX: Float, moveY: Float) {
        if (mTouchStartX < mWidth / 4) {
            if (Math.abs(moveX) > Math.abs(moveY)) {
                if (!isShowingSide) {
                    mMoveSide = Gravity.LEFT
                    moveLeftView(moveX)

                }
            }
        } else if (mTouchStartX > mWidth / 4.0f * 3) {
            if (Math.abs(moveX) > Math.abs(moveY)) {
                if (!isShowingSide) {
                    mMoveSide = Gravity.RIGHT
                    moveRightView(moveX)
                }
            }
        } else if (mTouchStartY < mHeight / 4) {
            if (Math.abs(moveY) > Math.abs(moveX)) {
                if (!isShowingSide) {
                    mMoveSide = Gravity.TOP
                    moveTopView(moveY)

                }
            }
        } else if (mTouchStartY > mHeight / 4f * 3) {
            if (Math.abs(moveY) > Math.abs(moveX)) {
                if (!isShowingSide) {
                    mMoveSide = Gravity.BOTTOM
                    moveBottomView(moveY)

                }
            }
        }
    }

    /**
     * 左边页面侧滑
     *
     * @param mx
     */
    private fun moveLeftView(mx: Float) {
        if (mLeftViewItem == null)
            return

        if (mLeftViewItem?.layout!!.getX() + mx <= 0) {
            mLeftViewItem?.layout?.setX(mLeftViewItem?.layout!!.getX() + mx)
            //计算移动的位置所占的比例
            //菜单完全不可见的x坐标 减去 当前x坐标就是变化值，再求百分比就可以了
            val change = Math.abs(-mWidth * mLeftViewItem!!.scale - mLeftViewItem?.layout!!.getX())
            val p = change / (mWidth * mLeftViewItem!!.scale)
            setShadeViewAlpha(p)
        }
    }

    /**
     * 顶部页面侧滑
     *
     * @param my
     */
    private fun moveTopView(my: Float) {
        if (mTopViewItem == null)
            return

        if (mTopViewItem?.layout!!.getY() + my <= 0) {
            mTopViewItem?.layout?.setY(mTopViewItem?.layout!!.getY() + my)
            //计算移动的位置所占的比例
            //菜单完全不可见的x坐标 减去 当前x坐标就是变化值，再求百分比就可以了
            val change = Math.abs(-mHeight * mTopViewItem!!.scale - mTopViewItem?.layout!!.getY())
            val p = change / (mHeight * mTopViewItem!!.scale)
            setShadeViewAlpha(p)
        }
    }

    /**
     * 右边页面侧滑
     *
     * @param mx
     */
    private fun moveRightView(mx: Float) {
        if (mRightViewItem == null)
            return
        if (mRightViewItem?.layout!!.getX() + mx >= (1 - mRightViewItem!!.scale) * mWidth) {
            mRightViewItem?.layout?.setX(mRightViewItem?.layout!!.getX() + mx)
            //计算移动的位置所占的比例
            //菜单完全不可见的x坐标 减去 当前x坐标就是变化值，再求百分比就可以了
            val change = Math.abs(mWidth - mRightViewItem?.layout!!.getX())
            val p = change / (mWidth * mRightViewItem!!.scale)
            setShadeViewAlpha(p)
        }
    }

    /**
     * 底部页面侧滑
     *
     * @param my
     */
    private fun moveBottomView(my: Float) {
        if (mBottomViewItem == null)
            return

        if (mBottomViewItem?.layout!!.getY() + my >= (1 - mBottomViewItem!!.scale) * mHeight) {
            mBottomViewItem?.layout?.setY(mBottomViewItem?.layout!!.getY() + my)
            //计算移动的位置所占的比例
            //菜单完全不可见的y坐标 减去 当前y坐标就是变化值，再求百分比就可以了
            val change = Math.abs(mHeight - mBottomViewItem?.layout!!.getY())
            val p = change / (mHeight * mBottomViewItem!!.scale)
            setShadeViewAlpha(p)
        }
    }

    private var touchDownTime: Long = 0 //手指按下时的时间

    /**
     * 计算是需要显示侧滑菜单还是隐藏
     *
     * @return true:显示侧滑菜单 false ：隐藏侧滑菜单
     */
    private fun computeIsShowSide(x: Float, y: Float): Boolean {
        //如果手指放下的时候没有在规定区域，则不进行动画
        if (!isShowingSide && !computeIsTouchInSide(mTouchStartX, mTouchStartY))
            return false

        val time = System.currentTimeMillis() - touchDownTime
        var speed = 0f
        when (mMoveSide) {
            Gravity.LEFT -> {
                speed = (x - mTouchStartX) / time
                if (speed > 1)
                    return true
            }
            Gravity.RIGHT -> {
                speed = (x - mTouchStartX) / time
                if (speed < -1)
                    return true
            }
            Gravity.TOP -> {
                speed = (y - mTouchStartY) / time
                if (speed > 1)
                    return true
            }
            Gravity.BOTTOM -> {
                speed = (y - mTouchStartY) / time
                if (speed < -1)
                    return true
            }
        }

        //手指滑动超过半个屏幕也可以启动动画
        return if (x - mTouchStartX > mWidth / 2 || y - mTouchStartY > mHeight / 2) true else false

    }


    /**
     * 执行view动画
     * 在手指抬起的时候执行
     *
     * @param gravity //执行动画的侧边
     */
    private fun animateShowSideView(gravity: Int) {
        var startVal = 0f
        var endVal = 0f
        when (gravity) {
            Gravity.LEFT -> {
                if (mLeftViewItem == null)
                    return
                startVal = mLeftViewItem?.layout!!.getX()
                endVal = 0f
            }
            Gravity.TOP -> {
                if (mTopViewItem == null)
                    return
                startVal = mTopViewItem?.layout!!.getY()
                endVal = 0f
            }
            Gravity.RIGHT -> {
                if (mTopViewItem == null)
                    return
                startVal = mRightViewItem?.layout!!.getX()
                endVal = (1 - mRightViewItem!!.scale) * mWidth
            }
            Gravity.BOTTOM -> {
                if (mBottomViewItem == null)
                    return
                startVal = mBottomViewItem?.layout!!.getY()
                endVal = (1 - mBottomViewItem!!.scale) * mHeight
            }
        }

        //清除之前的动画
        clearAnimation()

        /**
         * 主界面滑动
         */
        val interpolator = DecelerateInterpolator()  //插值器

        val animate = ObjectAnimator.ofFloat(this, "sideslip", startVal, endVal)
        animate.interpolator = interpolator
        animate.setDuration(mAnimateTime)
        animate.start()
        animate.addUpdateListener(updateListener)
        animate.addListener(animationListener)
        //正在显示侧滑菜单
        isShowingSide = true
    }

    /**
     * 执行动画隐藏侧滑菜单
     *
     * @param x
     * @param y
     * @param gravity
     */
    private fun animateHideSideView(gravity: Int) {
        var startVal = 0f
        var endVal = 0f
        when (gravity) {
            Gravity.LEFT -> {
                if (mLeftViewItem == null)
                    return
                startVal = mLeftViewItem?.layout!!.getX()
                endVal = -mLeftViewItem!!.scale * mWidth
            }
            Gravity.TOP -> {
                if (mTopViewItem == null)
                    return
                startVal = mTopViewItem?.layout!!.getY()
                endVal = -mTopViewItem!!.scale * mHeight
            }
            Gravity.RIGHT -> {
                if (mTopViewItem == null)
                    return
                startVal = mRightViewItem?.layout!!.getX()
                endVal = mWidth.toFloat()
            }
            Gravity.BOTTOM -> {
                if (mBottomViewItem == null)
                    return
                startVal = mBottomViewItem?.layout!!.getY()
                endVal = mHeight.toFloat()
            }
        }

        //清除之前的动画
        clearAnimation()

        /**
         * 主界面滑动
         */
        val interpolator = AccelerateInterpolator()  //弹簧效果

        val animate = ObjectAnimator.ofFloat(this, "sideslip", startVal, endVal)
        animate.interpolator = interpolator
        animate.setDuration(mAnimateTime)
        animate.start()
        animate.addUpdateListener(updateListener)
        animate.addListener(animationListener)

        //隐藏侧滑菜单
        isShowingSide = false
    }

    //动画更新监听
    private val updateListener = ValueAnimator.AnimatorUpdateListener { animation ->
        val cVal = animation.animatedValue as Float

        when (mMoveSide) {
            Gravity.LEFT -> {
                if (mLeftViewItem != null) {
                    moveLeftView(cVal - mLeftViewItem?.layout!!.getX())
                }
            }
            Gravity.TOP -> {
                if (mTopViewItem != null)
                    moveTopView(cVal - mTopViewItem?.layout!!.getY())
            }
            Gravity.RIGHT -> {
                if (mRightViewItem != null)
                    moveRightView(cVal - mRightViewItem?.layout!!.getX())
            }
            Gravity.BOTTOM -> {
                if (mBottomViewItem != null)
                    moveBottomView(cVal - mBottomViewItem?.layout!!.getY())
            }
        }
    }

    private val animationListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationEnd(animation: Animator) {
            if (mListener != null) {
                if (isShowingSide) {
                    mListener!!.onShow(mMoveSide)
                } else
                    mListener!!.onHide(mMoveSide)
            }
        }

        override fun onAnimationCancel(animation: Animator) {

        }

        override fun onAnimationRepeat(animation: Animator) {

        }
    }

    /**
     * 设置阴影VIew背景颜色
     *
     * @param p 透明度 0-1
     */
    private fun setShadeViewAlpha(p: Float) {
        var p = p
        if (p < 0.1)
            p = 0f
        if (p > 0.9)
            p = 1f

        mShadeView?.setAlpha(p)
    }

    fun getHomeView(): View? {
        return mHomeView
    }

    fun setHomeView(mHomeView: View) {
        this.mHomeView = mHomeView
        addView(mHomeView)
    }

    fun setHomeFragment(fragment: Fragment) {
        this.mHomeFragment = fragment
        val homelayout = FrameLayout(context)
        val p = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        homelayout.layoutParams = p
        homelayout.id = R.id.sideslip_home_layout

        this.mHomeView = homelayout
        addView(mHomeView)
    }

    fun getLeftViewItem(): SideslipViewItem? {
        return mLeftViewItem
    }

    fun setLeftViewItem(mLeftViewItem: SideslipViewItem) {
        this.mLeftViewItem = mLeftViewItem
        addView(mLeftViewItem.layout)
    }

    fun setLeftFragment(fragment: Fragment, scale: Float) {
        this.mLeftFragment = fragment
        val leftlayout = FrameLayout(context)
        val p = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        leftlayout.layoutParams = p
        leftlayout.id = R.id.sidelip_left_layout
        this.mLeftViewItem = SideslipViewItem(leftlayout, scale)

        addView(leftlayout)
    }

    fun getTopViewItem(): SideslipViewItem? {
        return mTopViewItem
    }

    fun setTopViewItem(mTopViewItem: SideslipViewItem) {
        this.mTopViewItem = mTopViewItem
        addView(mTopViewItem.layout)
    }

    fun setTopFragment(fragment: Fragment, scale: Float) {
        this.mTopFragment = fragment

        val topLayout = FrameLayout(context)
        val p = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        topLayout.layoutParams = p
        topLayout.id = R.id.sideslip_top_layout
        this.mTopViewItem = SideslipViewItem(topLayout, scale)
        addView(topLayout)
    }

    fun getRightViewItem(): SideslipViewItem? {
        return mRightViewItem
    }

    fun setRightViewItem(mRightViewItem: SideslipViewItem) {
        this.mRightViewItem = mRightViewItem
        addView(mRightViewItem.layout)
    }

    fun setRightFragment(fragment: Fragment, scale: Float) {
        val rightLayout = FrameLayout(context)
        val p = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        rightLayout.layoutParams = p
        rightLayout.id = R.id.sideslip_right_layout
        this.mRightViewItem = SideslipViewItem(rightLayout, scale)
        addView(rightLayout)

    }

    fun getBottomViewItem(): SideslipViewItem? {
        return mBottomViewItem
    }

    fun setBottomViewItem(mBottomViewItem: SideslipViewItem) {
        this.mBottomViewItem = mBottomViewItem
        addView(mBottomViewItem.layout)

    }

    fun setBottomFragment(fragment: Fragment, scale: Float) {
        this.mBottomFragment = fragment

        val bottomLayout = FrameLayout(context)
        val p = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        bottomLayout.layoutParams = p
        bottomLayout.id = R.id.sideslip_bottom_layout
        this.mBottomViewItem = SideslipViewItem(bottomLayout, scale)

        addView(bottomLayout)
    }

    fun setFragmentManager(fragmentManager: FragmentManager?) {
        this.mFragmentManager = fragmentManager
    }

    fun isCanHideSideView(): Boolean {
        return canHideSideView
    }

    fun setCanHideSideView(canHideSideView: Boolean) {
        this.canHideSideView = canHideSideView
    }

    private var mListener: OnSideslipListener? = null

    fun setOnSideslipListener(mListener: OnSideslipListener) {
        this.mListener = mListener
    }


    /**
     * 侧滑状态回掉函数
     */
    interface OnSideslipListener {
        //侧滑菜单显示
        fun onShow(gravity: Int)

        //侧滑菜单隐藏
        fun onHide(gravity: Int)
    }
}
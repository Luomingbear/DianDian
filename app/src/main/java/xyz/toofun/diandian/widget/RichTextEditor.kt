package xyz.toofun.diandian.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.util.Xml
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.View.*
import android.widget.*
import com.bumptech.glide.Glide
import org.xmlpull.v1.XmlPullParser
import xyz.toofun.diandian.R
import xyz.toofun.diandian.bean.EditLineData
import xyz.toofun.diandian.uitl.DipPxConversion
import xyz.toofun.diandian.uitl.KeyBordUtil
import xyz.toofun.diandian.uitl.SharePreferenceManager
import xyz.toofun.diandian.uitl.net.QiniuUploadManager
import java.io.ByteArrayInputStream

/**
 * 富文本编辑器
 * Created by bear on 2016/12/28.
 */

class RichTextEditor @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ScrollView(context, attrs, defStyleAttr) {

    private var viewTagIndex = 1 // 新生的view都会打一个tag，对每个view来说，这个tag是唯一的。
    private val allLayout: LinearLayout // 这个是所有子view的容器，scrollView内部的唯一一个ViewGroup
    private val keyListener: View.OnKeyListener // 所有EditText的软键盘监听器
    private val btnListener: View.OnClickListener // 图片右上角红叉按钮监听器
    private val focusListener: View.OnFocusChangeListener // 所有EditText的焦点监听listener
    private var lastFocusEdit: EditText? = null // 最近被聚焦的EditText
    private var editNormalPadding = 0 //
    private var disappearingImageIndex = 0

    private var mTmpImagePath: String? = null //临时存储图片地址

    private var onTextFoucsListener: OnTextFocusListener? = null

    interface OnTextFocusListener {
        fun onFocusChange(v: View, hasFocus: Boolean)
    }

    fun setOnTextFocusListener(onTextFoucsListener: OnTextFocusListener) {
        this.onTextFoucsListener = onTextFoucsListener
    }

    init {

        // 1. 初始化allLayout
        allLayout = LinearLayout(context)
        allLayout.orientation = LinearLayout.VERTICAL

        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT)
        addView(allLayout, layoutParams)

        // 2. 初始化键盘退格监听
        // 主要用来处理点击回删按钮时，view的一些列合并操作
        keyListener = OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DEL) {
                val edit = v as EditText
                onBackspacePress(edit)
            }
            false
        }

        // 3. 图片叉掉处理
        btnListener = OnClickListener { v ->
            val parentView = v.parent as RelativeLayout
            onImageCloseClick(parentView)
        }

        focusListener = OnFocusChangeListener { v, hasFocus ->
            if (onTextFoucsListener != null) {
                onTextFoucsListener!!.onFocusChange(v, hasFocus)
            }

            if (hasFocus) {
                lastFocusEdit = v as EditText
            }
        }

        val firstEditParam = LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        editNormalPadding = resources.getDimension(R.dimen.margin_normal).toInt()
        val firstEdit = createEditText(resources.getString(R.string.edit_content),
                dip2px(EDIT_FIRST_PADDING_TOP.toFloat()))
        allLayout.addView(firstEdit, firstEditParam)
        lastFocusEdit = firstEdit
    }

    /**
     * 处理软键盘backSpace回退事件
     *
     * @param editTxt 光标所在的文本输入框
     */
    private fun onBackspacePress(editTxt: EditText) {
        val startSelection = editTxt.selectionStart
        // 只有在光标已经顶到文本输入框的最前方，在判定是否删除之前的图片，或两个View合并
        if (startSelection == 0) {
            val editIndex = allLayout.indexOfChild(editTxt)
            val preView = allLayout.getChildAt(editIndex - 1) // 如果editIndex-1<0,
            // 则返回的是null
            if (null != preView) {
                if (preView is RelativeLayout) {
                    // 光标EditText的上一个view对应的是图片
                    onImageCloseClick(preView)
                } else if (preView is EditText) {
                    // 光标EditText的上一个view对应的还是文本框EditText
                    val str1 = editTxt.text.toString()
                    val str2 = preView.text.toString()

                    // 合并文本view时，不需要transition动画
                    allLayout.layoutTransition = null
                    allLayout.removeView(editTxt)

                    // 文本合并
                    preView.setText(str2 + str1)
                    preView.requestFocus()
                    preView.setSelection(str2.length, str2.length)
                    lastFocusEdit = preView
                }
            }
        }
    }

    /**
     * 处理图片叉掉的点击事件
     *
     * @param view 整个image对应的relativeLayout view
     * @type 删除类型 0代表backspace删除 1代表按红叉按钮删除
     */
    private fun onImageCloseClick(view: View) {
        disappearingImageIndex = allLayout.indexOfChild(view)
        allLayout.removeView(view)

    }

    /**
     * 生成文本输入框
     */
    private fun createEditText(hint: String, paddingTop: Int): EditText {
        //
        val editText = EditText(context)
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        editText.layoutParams = params
        editText.setPadding(editNormalPadding, paddingTop, editNormalPadding, 0)
        //
        editText.setOnKeyListener(keyListener)
        editText.tag = viewTagIndex++
        editText.hint = hint
        editText.onFocusChangeListener = focusListener
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.font_normal))
        editText.setTextColor(resources.getColor(R.color.colorBlackLight))
        editText.setBackgroundResource(0)
        editText.setLineSpacing(resources.getDimension(R.dimen.line_space_normal), 1f)
        editText.id = R.id.edit_content_paragraph
        return editText
    }

    private fun createTextView(content: String): TextView {
        //
        val textView = TextView(context)
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        textView.layoutParams = params
        textView.setPadding(editNormalPadding, resources.getDimensionPixelSize(R.dimen.font_small),
                editNormalPadding, 0)
        //
        textView.setOnKeyListener(keyListener)
        textView.tag = viewTagIndex++
        textView.text = content
        textView.onFocusChangeListener = focusListener
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.font_normal))
        textView.setTextColor(resources.getColor(R.color.colorBlackLight))
        textView.setBackgroundResource(0)
        textView.setLineSpacing(resources.getDimension(R.dimen.line_space_normal), 1f)
        textView.id = R.id.edit_content_paragraph
        return textView
    }

    /**
     * 生成图片View
     */
    private fun createImageLayout(): RelativeLayout {
        val layout = RelativeLayout(context)
        val p = RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layout.layoutParams = p
        val margin = resources.getDimension(R.dimen.margin_normal).toInt()
        layout.setPadding(0, margin, 0, margin)
        layout.tag = viewTagIndex++

        val imageView = DataImageView(context)
        imageView.layoutParams = p
        imageView.id = R.id.edit_image_view
        layout.addView(imageView)

        /**
         * 删除图片的按钮
         */
        val closeView = ImageView(context)
        val iconSize = resources.getDimension(R.dimen.icon_small).toInt()
        val params = RelativeLayout.LayoutParams(iconSize, iconSize)
        params.setMargins(0, DipPxConversion.dip2px(context, 2f), DipPxConversion.dip2px(context, 2f), 0)
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        closeView.layoutParams = params
        closeView.tag = layout.tag
        closeView.setOnClickListener(btnListener)
        closeView.id = R.id.image_close
        closeView.setBackgroundResource(R.drawable.close)
        layout.addView(closeView)

        /**
         * 底部的描述控件
         */
        val hint = EditText(context)
        val pp = RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        pp.addRule(RelativeLayout.BELOW, R.id.edit_image_view)
        hint.layoutParams = pp
        hint.setPadding(editNormalPadding, resources.getDimension(R.dimen.margin_small).toInt(), editNormalPadding, 0)
        //设置默认文本
        hint.setText(resources.getString(R.string.picture_hint,
                SharePreferenceManager.getNickName(context)))
        hint.gravity = Gravity.CENTER
        hint.tag = viewTagIndex++
        hint.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.font_small))
        hint.setTextColor(resources.getColor(R.color.colorGrayDeep))
        hint.setBackgroundResource(0)
        hint.id = R.id.edit_image_describe
        layout.addView(hint)

        return layout
    }

    /**
     * 根据绝对路径添加view
     *
     * @param imagePathList
     */
    //    public void insertImage(List<ImageItem> imagePathList) {
    //        if (imagePathList != null && imagePathList.size() > 0) {
    //            for (ImageItem item : imagePathList) {
    //                insertImage(item.path);
    //            }
    //        }
    //    }

    /**
     * 插入一张图片
     */
    private fun insertImage(imagePath: String) {
        val lastEditStr = lastFocusEdit!!.text.toString()
        val cursorIndex = lastFocusEdit!!.selectionStart
        val editStr1 = lastEditStr.substring(0, cursorIndex).trim { it <= ' ' }
        val lastEditIndex = allLayout.indexOfChild(lastFocusEdit)

        if (lastEditStr.length == 0 || editStr1.length == 0) {
            // 如果EditText为空，或者光标已经顶在了editText的最前面，则直接插入图片，并且EditText下移即可
            addImageViewAtIndex(lastEditIndex, imagePath)
        } else {
            // 如果EditText非空且光标不在最顶端，则需要添加新的imageView和EditText
            lastFocusEdit!!.setText(editStr1)
            val editStr2 = lastEditStr.substring(cursorIndex).trim { it <= ' ' }
            if (allLayout.childCount - 1 == lastEditIndex || editStr2.length > 0) {
                addEditTextAtIndex(lastEditIndex + 1, editStr2)
            }

            addImageViewAtIndex(lastEditIndex + 1, imagePath)
            lastFocusEdit!!.requestFocus()
            lastFocusEdit!!.setSelection(editStr1.length, editStr1.length)
        }

        //隐藏键盘
        KeyBordUtil.hideKeyboard(context, lastFocusEdit)

    }


    /**
     * 生成图片，仅限用于根据数据转化为view
     *
     * @param path     图片地址
     * @param describe 图片描述
     */
    private fun newImageLayout(path: String?, describe: String) {
        val layout = RelativeLayout(context)
        val p = RelativeLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layout.layoutParams = p
        val margin = resources.getDimension(R.dimen.margin_normal).toInt()
        layout.setPadding(0, margin, 0, margin)

        val imageView = DataImageView(context)
        imageView.layoutParams = p
        imageView.id = R.id.edit_image_view
        layout.addView(imageView)
        Glide.with(context).load(path).into(imageView)

        /**
         * 底部的描述控件
         */
        val hint = TextView(context)
        val pp = RelativeLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        pp.addRule(RelativeLayout.BELOW, R.id.edit_image_view)
        hint.layoutParams = pp
        hint.setPadding(editNormalPadding, resources.getDimension(R.dimen.margin_small).toInt(),
                editNormalPadding, 0)
        //设置默认文本
        hint.text = describe
        hint.gravity = Gravity.CENTER
        hint.tag = viewTagIndex++
        hint.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.font_small))
        hint.setTextColor(resources.getColor(R.color.colorGrayDeep))
        hint.setBackgroundResource(0)
        layout.addView(hint)

        allLayout.addView(layout)
    }


    /**
     * 在特定位置插入EditText
     *
     * @param index   位置
     * @param editStr EditText显示的文字
     */
    private fun addEditTextAtIndex(index: Int, editStr: String) {
        val editText2 = createEditText("", resources
                .getDimensionPixelSize(R.dimen.font_small))
        editText2.setText(editStr)

        // 请注意此处，EditText添加、或删除不触动Transition动画
        allLayout.layoutTransition = null
        allLayout.addView(editText2, index)
    }

    /**
     * 在特定位置添加ImageView
     */
    private fun addImageViewAtIndex(index: Int, imagePath: String) {
        val imageLayout = createImageLayout()
        val imageView = imageLayout
                .findViewById(R.id.edit_image_view) as DataImageView
        imageView.absolutePath = imagePath
        Glide.with(context).load(imagePath).into(imageView)

        val lp = RelativeLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        imageView.layoutParams = lp

        // onActivityResult无法触发动画，此处post处理
        allLayout.post { allLayout.addView(imageLayout, index) }

        //上传图片
        uploadImage(imageView)

    }


    private var qiniuUploadManager: QiniuUploadManager? = null  //七牛云上传管家

    /**
     * 上传图片
     */
    private fun uploadImage(dataImageView: DataImageView) {
        qiniuUploadManager = QiniuUploadManager(context)
        qiniuUploadManager!!.uploadFile(dataImageView.absolutePath?:"")
        qiniuUploadManager!!.setQiniuUploadInterface(object : QiniuUploadManager.QiniuUploadInterface {
            override fun onSucceed(pathList: List<String>) {
                dataImageView.absolutePath = pathList[0]
            }

            override fun onFailed(errorPathList: List<String>?) {

            }
        })
    }

    /**
     * 图片删除的时候，如果上下方都是EditText，则合并处理
     */
    private fun mergeEditText() {
        val preView = allLayout.getChildAt(disappearingImageIndex - 1)
        val nextView = allLayout.getChildAt(disappearingImageIndex)
        if (preView != null && preView is EditText && null != nextView
                && nextView is EditText) {
            Log.d("LeiTest", "合并EditText")
            val str1 = preView.text.toString()

            allLayout.layoutTransition = null
            allLayout.removeView(nextView)
            preView.requestFocus()
            preView.setSelection(str1.length, str1.length)
        }
    }

    /**
     * dp和pixel转换
     *
     * @param dipValue dp值
     * @return 像素值
     */
    fun dip2px(dipValue: Float): Int {
        val m = context.resources.displayMetrics.density
        return (dipValue * m + 0.5f).toInt()
    }

    /**
     * 对外提供的接口, 生成编辑数据上传
     */
    //        data.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");//xml格式／
    //图片
    //图片描述
    val editData: String
        get() {
            val data = StringBuffer()
            val num = allLayout.childCount
            for (index in 0..num - 1) {
                val itemView = allLayout.getChildAt(index)
                val itemData = EditLineData()
                if (itemView is EditText) {
                    if (itemView.getId() == R.id.edit_content_paragraph) {
                        data.append(TAG_P_START)
                        data.append(itemView.text)
                        data.append(TAG_P_END)
                    }
                    itemData.inputStr = itemView.text.toString()
                } else if (itemView is RelativeLayout) {
                    val image = itemView.findViewById(R.id.edit_image_view) as DataImageView
                    val describe = itemView.findViewById(R.id.edit_image_describe) as EditText
                    data.append(TAG_IMAGE_START)

                    data.append(TAG_IMAGE_PATH_START)
                    data.append(image.absolutePath)
                    data.append(TAG_IMAGE_PATH_END)

                    data.append(TAG_IMAGE_DESCRIBE_START)
                    data.append(describe.text)
                    data.append(TAG_IMAGE_DESCRIBE_END)

                    data.append(TAG_IMAGE_END)
                }
            }

            return data.toString()
        }

    /**
     * 获取摘要文件
     *
     * @return 摘要
     */
    val extract: String
        get() {
            val extract = ""
            for (index in 0..allLayout.childCount - 1) {
                val itemView = allLayout.getChildAt(index)
                val itemData = EditLineData()
                if (itemView is EditText) {
                    return itemView.text.toString()
                }
            }
            return extract
        }


    fun parseXml(xml: String) {
        allLayout.removeAllViews()

        //获取源码
        val parser = Xml.newPullParser()

        try {
            parser.setInput(ByteArrayInputStream(xml.toByteArray()), "UTF-8")

            var eventType = parser.eventType
            var path: String
            var describe: String
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_DOCUMENT//文档开始事件,可以进行数据初始化处理
                    -> Log.d(TAG, "parseXml: START_DOCUMENT")

                    XmlPullParser.START_TAG//开始元素事件
                    -> {
                        val name = parser.name
                        if (name == TAG_P) {
                            val s = parser.nextText()
                            Log.i(TAG, "parseXml: TAG_P+++++" + s)
                            allLayout.addView(createTextView(s))
                        } else if (name == TAG_IMAGE_PATH) {
                            path = parser.nextText()
                            mTmpImagePath = path
                            Log.i(TAG, "parseXml: TAG_IMAGE_PATH+++++" + path)
                        } else if (name == TAG_IMAGE_DESCRIBE) {
                            describe = parser.nextText()
                            newImageLayout(mTmpImagePath, describe)
                            Log.i(TAG, "parseXml: TAG_IMAGE_DESCRIBE+++++" + describe)
                        }
                    }

                    XmlPullParser.END_TAG//结束元素事件
                    -> Log.d(TAG, "parseXml: END_TAG")
                    else -> {
                    }
                }

                eventType = parser.next()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {
        private val TAG = "RichTextEditor"
        private val EDIT_PADDING = 10 // edittext常规padding是10dp
        private val EDIT_FIRST_PADDING_TOP = 10 // 第一个EditText的paddingTop值

        /**
         * 标记
         */
        var TAG_TITLE = "tit"
        var TAG_TITLE_START = "<tit>"
        var TAG_TITLE_END = "</tit>"
        var TAG_P = "p"
        var TAG_P_START = "<p>"
        var TAG_P_END = "</p>"
        var TAG_IMAGE = "img"
        var TAG_IMAGE_START = "<img>"
        var TAG_IMAGE_END = "</img>"
        var TAG_IMAGE_PATH = "path"
        var TAG_IMAGE_PATH_START = "<path>"
        var TAG_IMAGE_PATH_END = "</path>"
        var TAG_IMAGE_DESCRIBE = "des"
        var TAG_IMAGE_DESCRIBE_START = "<des>"
        var TAG_IMAGE_DESCRIBE_END = "</des>"
    }
}


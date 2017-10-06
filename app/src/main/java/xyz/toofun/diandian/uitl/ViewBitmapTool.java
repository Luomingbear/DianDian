package xyz.toofun.diandian.uitl;

import android.graphics.Bitmap;
import android.view.View;

/**
 * view转化为bitmap
 * Created by bear on 2016/12/2.
 */

public class ViewBitmapTool {
    //布局 转bitmap
    public static Bitmap convertLayoutToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        return view.getDrawingCache();
    }

}

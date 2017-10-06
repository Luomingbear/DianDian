package xyz.toofun.diandian.widget.marker;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.animation.LinearInterpolator;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;

import xyz.toofun.diandian.uitl.ViewBitmapTool;

/**
 * 用户个人位置的圆点图标
 * Created by bear on 2017/3/14.
 */

public class MyCircleMarker extends IMarker {
    private MyCircleView mMyCircleView;

    public MyCircleMarker(Context mContext, AMap mAMap, LatLng mLatLng) {
        super(mContext, mAMap, mLatLng);
        init();
    }

    private void init() {
        mMyCircleView = new MyCircleView(mContext);
        Bitmap bitmap = ViewBitmapTool.convertLayoutToBitmap(mMyCircleView);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mLatLng);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));

        mMarker = mAMap.addMarker(markerOptions);
    }

    /**
     * 动画地移动marker
     *
     * @param latLng
     */
    public void animate(LatLng latLng) {
        Animation animation = new TranslateAnimation(latLng);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(2000);

        mMarker.setClickable(false);
        mMarker.setInfoWindowEnable(false);
        mMarker.setAnimation(animation);
        mMarker.startAnimation();
    }

    /**
     * 移动marker
     *
     * @param latLng
     */
    public void move(LatLng latLng) {
        Animation animation = new TranslateAnimation(latLng);
        animation.setInterpolator(new LinearInterpolator());

        mMarker.setAnimation(animation);
        mMarker.startAnimation();
    }
}

package xyz.toofun.diandian.widget.marker;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;


/**
 * 地图上的标记图标的基础类
 * Created by bear on 2016/12/2.
 */

public class IMarker {
    public Marker mMarker; //标记对象
    public Context mContext;
    public AMap mAMap; //地图对象
    public LatLng mLatLng; //经纬度

    protected IMarker() {
    }

    public IMarker(Context mContext, AMap mAMap, LatLng mLatLng) {
        this.mContext = mContext;
        this.mAMap = mAMap;
        this.mLatLng = mLatLng;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng mLatLng) {
        this.mLatLng = mLatLng;
        this.mMarker.setPosition(mLatLng);
    }

    /**
     * 清除标记,会留下疤痕
     */
    public void destroy() {
        if (mMarker != null)
            mMarker.destroy();
    }

    public void remove() {
        if (mMarker != null)
            mMarker.remove();
    }
}

package com.example.xydt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    Stack<Point> stack=new Stack<>();
    MyGraph myGraph=new MyGraph();
    Overlay mPolyline=null;
    BaiduMap mBaidumap;
    LatLng cenpt = new LatLng(45.7144, 126.628135);
    private MapView mMapView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaidumap = mMapView.getMap();
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(19)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaidumap.setMapStatus(mMapStatusUpdate);
        //标记点;,,
        setPoint();
        //两点之间标记：
        List<LatLng> latLngs = new ArrayList<LatLng>();
//        latLngs.add(new LatLng(45.7134730000,126.6309890000));
//        latLngs.add(new LatLng(45.7127250000,126.6310520000));
        //设置折线的属性
        //获取点;
        stack=myGraph.Set();
        while (!stack.isEmpty()){
            Point pointtt=stack.pop();
            Log.d("wsx",pointtt.latitude+"");
            Log.d("wsx",pointtt.longitude+"");
            latLngs.add(new LatLng(pointtt.latitude,pointtt.longitude));
        }
        OverlayOptions mOverlayOptions = new PolylineOptions()
                .width(10)
                .color(0xAAFF0000)
                .points(latLngs);
        mPolyline = mBaidumap.addOverlay(mOverlayOptions);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    public void setPoint(){
        for(int i=0;i<19;i++){
            LatLng point = new LatLng(myGraph.points[i].latitude, myGraph.points[i].longitude);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.ding);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            mBaidumap.addOverlay(option);
        }
        //标记点

    }

}

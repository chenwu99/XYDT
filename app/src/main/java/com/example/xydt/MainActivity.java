package com.example.xydt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.service.autofill.Transformation;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.animation.Animation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton actionA,actionB;
    AlphaAnimation animation;
    Marker marker;
    MapStatusUpdate mMapStatusUpdate;
    MapStatus mMapStatus;
    InfoWindow minfoWindown;
    SavePoint savePoint;
    Transformation mTransforma;
    Button button1,button2;
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
        mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(17)
                .build();
        mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaidumap.setMapStatus(mMapStatusUpdate);
        //标记点
        setPoint();

        button1=findViewById(R.id.button_1);
        button1.setOnClickListener(this);
        button2=findViewById(R.id.button_2);
        button2.setOnClickListener(this);

        mBaidumap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            //marker被点击时回调的方法
            //若响应点击事件，返回true，否则返回false
            //默认返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intend=new Intent(MainActivity.this,Main3Activity.class);
                Bundle bundle = new Bundle();
//                bundle.putSerializable("data", "黑龙江大学"+MyGraph.points[finalI].name);
                intend.putExtras(bundle);
                startActivity(intend);
                return false;
            }
        });

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

    public void setPoint() {
        int i=0;
        for (i = 0; i < 19; i++) {
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
            marker = (Marker) mBaidumap.addOverlay(option);//将marker添加到地图，并获取该marker点对象
            Button button = new Button(getApplicationContext());
            button.setBackgroundResource(R.drawable.popup);
            button.setText(myGraph.points[i].name);
            //构造InfoWindow
            //point 描述的位置点
            //-100 InfoWindow相对于point在y轴的偏移量
            minfoWindown= new InfoWindow(button, point, -50);
            //使InfoWindow生效
            mBaidumap.showInfoWindow(minfoWindown);
            /**
             * 移动动画
             */
        }
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent date) {
        super.onActivityResult(requestCode, resultCode, date);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    savePoint = (SavePoint) date.getSerializableExtra("savePoint");
                    //获取点;
                    List<LatLng> latLngs = new ArrayList<LatLng>();
                    stack=myGraph.Set(savePoint.StartPoint, savePoint.EndPoint);
                    while (!stack.isEmpty()){
                        Point pointtt=stack.pop();
                        Log.d("wsx",pointtt.latitude+"");
                        Log.d("wsx",pointtt.longitude+"");
                        latLngs.add(new LatLng(pointtt.latitude,pointtt.longitude));
                    }
                    mBaidumap.clear();
                    OverlayOptions mOverlayOptions = new PolylineOptions()
                            .width(25)
                            .color(0xFF00BA6B)
                            .points(latLngs);
                    mPolyline = mBaidumap.addOverlay(mOverlayOptions);
                    MapStatus mMapStatus = new MapStatus.Builder()
                            .target(cenpt)
                            .zoom(17)
                            .build();
                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    //改变地图状态
                    mBaidumap.setMapStatus(mMapStatusUpdate);
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.ding);
                    //构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option_1 = new MarkerOptions()
                            .position(latLngs.get(0))
                            .icon(bitmap);
                    OverlayOptions option_2 = new MarkerOptions()
                            .position(latLngs.get(latLngs.size()-1))
                            .icon(bitmap);
                    //在地图上添加Marker，并显示
                    mBaidumap.addOverlay(option_1);
                    mBaidumap.addOverlay(option_2);
                    float rotate = 90.0f;
                    mMapStatus = new MapStatus.Builder(mBaidumap.getMapStatus()).rotate(rotate).build();
                    mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    mBaidumap.setMapStatus(mMapStatusUpdate);
                    float overlook = -30.0f;
                    mMapStatus = new MapStatus.Builder(mBaidumap.getMapStatus()).overlook(overlook).build();
                    mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    mBaidumap.setMapStatus(mMapStatusUpdate);
                    float zoom = 18f;
                    mMapStatusUpdate = MapStatusUpdateFactory.zoomTo(zoom);
                    mBaidumap.setMapStatus(mMapStatusUpdate);
//                    Toasty.success(MainActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
////                    new MyLog("qweqwe");
                }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.button_1:
                mBaidumap.clear();
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivityForResult(intent,0);
                mBaidumap.clear();
            case  R.id.button_2:
                mBaidumap.clear();
                float rotate = 90.0f;
                mMapStatus = new MapStatus.Builder(mBaidumap.getMapStatus()).rotate(rotate).build();
                mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaidumap.setMapStatus(mMapStatusUpdate);
                float overlook = -30.0f;
                mMapStatus = new MapStatus.Builder(mBaidumap.getMapStatus()).overlook(overlook).build();
                mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaidumap.setMapStatus(mMapStatusUpdate);
                float zoom = 17f;
                mMapStatusUpdate = MapStatusUpdateFactory.zoomTo(zoom);
                mBaidumap.setMapStatus(mMapStatusUpdate);
                setPoint();
        }

    }
}

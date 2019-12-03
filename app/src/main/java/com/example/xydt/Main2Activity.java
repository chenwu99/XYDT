package com.example.xydt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    NiceSpinner niceSpinner_1 ;
    NiceSpinner niceSpinner_2;
    List<String> dataset_1;
    List<String> dataset_2;
    String data;
    Button button;
    SavePoint savePoint=new SavePoint();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button=findViewById(R.id.button_2);
        button.setOnClickListener(this);
        niceSpinner_1 = (NiceSpinner) findViewById(R.id.text_nice_spinner_1);
        niceSpinner_2 = (NiceSpinner) findViewById(R.id.text_nice_spinner_2);
        niceSpinner_1.setTextColor(Color.BLACK);
        niceSpinner_2.setTextColor(Color.BLACK);

        dataset_1 = new LinkedList<>(Arrays.asList("C区篮球场","体育馆","二号楼","C区大门(南门)","A区游泳馆","老图书馆","体育场","主楼","C11","实验楼","一号楼","汇文楼", "三号楼", "四号楼", "艺术楼", "新图书馆","C区游泳馆","C区冰场","C区食堂"));
        niceSpinner_1.attachDataSource(dataset_1);
        dataset_2 = new LinkedList<>(Arrays.asList("C区篮球场","体育馆","二号楼","C区大门(南门)","A区游泳馆","老图书馆","体育场","主楼","C11","实验楼","一号楼","汇文楼", "三号楼", "四号楼", "艺术楼", "新图书馆","C区游泳馆","C区冰场","C区食堂"));
        niceSpinner_2.attachDataSource(dataset_2);

        niceSpinner_1.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                savePoint.StartPoint=dataset_1.get(i);
                Toast.makeText(Main2Activity.this, String.valueOf(data), Toast.LENGTH_SHORT).show();
                Log.e("什么数据",String.valueOf(dataset_1.get(i)));
            }
        });
        niceSpinner_2.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                savePoint.EndPoint=dataset_2.get(i);
                Toast.makeText(Main2Activity.this, String.valueOf(dataset_2.get(i)), Toast.LENGTH_SHORT).show();
                Log.e("什么数据",String.valueOf(dataset_2.get(i)));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_2:
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle .putSerializable("savePoint",  savePoint);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}

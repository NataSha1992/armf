package com.example.natali.journal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.util.Log;
import android.widget.EditText;

public class MainActivity3 extends Activity{
    TextView tw;
    private EditText et;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        String item = getIntent().getStringExtra("item");//
        int index = item.indexOf(",");//первое вхождение

        String name = item.substring(0,index);//подобрали 1ю строку

        String subValue = item.substring(index+1, item.length());
        int  index2 = subValue.indexOf(",");
        String value = subValue.substring(0, index2);
        int index3 = value.indexOf(":");
        String finalValue = value.substring(index3+2, value.length());

        Log.w("SUB_VALUE",subValue);
        Log.w("VALUE",value);

        tw = (TextView) findViewById(R.id.textView1);
        tw.setText(name);

        String norm = "норматив: " + getIntent().getStringExtra("norms");
        tw = (TextView) findViewById(R.id.textView2);
        tw.setText(norm);

        et = (EditText) findViewById(R.id.editText);
        et.setText(finalValue);


        //  EditText et = (EditText)findViewById(R.id.editText);
        // et.setText("Google is your friend.", TextView.BufferType.EDITABLE);
        //String item = getIntent().getStringExtra("item") + ", класс: " +
        //  		getIntent().getStringExtra("study");
    }

    public void cancelData(View b)
    {
        Log.w("TAG","1");
        finish();


    }
}
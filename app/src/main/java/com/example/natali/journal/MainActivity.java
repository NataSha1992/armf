package com.example.natali.journal;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import com.loopj.android.http.*;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {
    ARMFbase basa = new ARMFbase(this);
    SQLiteDatabase db;

    @Override
    protected void onDestroy()
    {
        db.close();
        basa.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = basa.getWritableDatabase();
        basa.onUpgradeNorms(db);
        basa.onUpgradeStudy(db);

        AsyncHttpClient client = new AsyncHttpClient();//э.к для отправки запросов к серверу
        client.get("http://192.168.0.102:1337/Norms", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    ContentValues cv = new ContentValues();
                    JSONArray json = response.getJSONArray("data");
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject norms = json.getJSONObject(i);//получаем текущий объект
                        cv.put(ARMFbase.NORMS_NAME, norms.getString("sports"));
                        try {
                            cv.put(ARMFbase.NORMS_UPPVALUE, norms.getInt("upperValue"));
                        } catch (JSONException e) {
                            // TODO
                        }
                        try {
                            cv.put(ARMFbase.NORMS_LOWVALUE, norms.getInt("lowerValue"));
                        } catch (JSONException e) {
                            // TODO
                        }
                        cv.put(ARMFbase.NORMS_MARK, norms.getInt("mark"));
                        cv.put(ARMFbase.NORMS_GENDER, norms.getBoolean("gender"));
                        cv.put(ARMFbase.NORMS_ID, norms.getString("id"));
                        db.insert(com.example.natali.journal.ARMFbase.TABLE_NORMS, null, cv);
                    }
                    Log.w("RESPONSE_TAG", json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject timeline) {
                Log.w("RESPONSE_ERROR_TAG", timeline.toString());
            }

        });
        client.get("http://192.168.0.102:1337/Classes", null, new JsonHttpResponseHandler() {

                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            ContentValues cv = new ContentValues();
                            JSONArray json = response.getJSONArray("data");
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject study = json.getJSONObject(i);//получаем текущий объект
                                cv.put(ARMFbase.STUDY_NAME, study.getInt("name"));
                                cv.put(ARMFbase.STUDY_ID, study.getString("id"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject timeline) {
                        Log.w("RESPONSE_ERROR_TAG", timeline.toString());
                    }

        });
        client.get("http://192.168.0.102:1337/Pupils", null, new JsonHttpResponseHandler(){

                public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                    try {
                        ContentValues cv = new ContentValues();
                        JSONArray json = response.getJSONArray("data");
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject pupils = json.getJSONObject(i);//получаем текущий объект
                            cv.put(ARMFbase.PUPILS_NAME, pupils.getString("fio"));
                            cv.put(ARMFbase.STUDYID_PUPILS, pupils.getInt("id"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject timeline) {
                    Log.w("RESPONSE_ERROR_TAG1", timeline.toString());
                }
            });
    }




    public void onClick(View v)
    {
        Intent intent =new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }




}

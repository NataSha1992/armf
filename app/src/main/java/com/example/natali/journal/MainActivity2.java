package com.example.natali.journal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;




public class MainActivity2 extends Activity
{
    final String NOT_CHOOSE = "Выберите класс";
    final String NOT_CHOOSE1 = "Выберите вид норматива";

    ARMFbase basa = new ARMFbase(this);
    SQLiteDatabase db;

    Spinner spinner;
    Spinner spinner2;
    ListView lv;

    @Override
    protected void onDestroy()
    {
        db.close();
        basa.close();
        super.onDestroy();
    }


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        db = basa.getWritableDatabase();
      //  basa.onUpgradeStudy(db);
//        basa.onUpgradeNorms(db);
        basa.onUpgradePupils(db);

     //   AddBase.CreateClass(db);
//        AddBase.CreateNorms(db);
        AddBase.CreatePupils(db);
        AddBase.CreatenormsReal(db);


        String query = "SELECT * FROM " + ARMFbase.TABLE_STUDY;
        Cursor cursor = db.rawQuery(query, null);

        String query1 = "SELECT DISTINCT name FROM " + ARMFbase.TABLE_NORMS;
        Cursor cursor1 = db.rawQuery(query1, null);

        String que = "SELECT * FROM " + ARMFbase.TABLE_NORMSREAL;
        Cursor cur = db.rawQuery(que, null);
        Log.w("COUNT", Integer.toString(cur.getCount()));

        String[] classes = new String[cursor.getCount()+1];
        classes[0] = NOT_CHOOSE;//
        int i = 1;
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ARMFbase.STUDY_NAME));
            if (name != null){
                classes[i] = name;
            }
            i++;
        }
        cursor.close();

        String[] norms = new String[cursor1.getCount()+1];
        norms[0] = NOT_CHOOSE1;//
        int n = 1;
        while (cursor1.moveToNext()){
            String name = cursor1.getString(cursor1.getColumnIndex(ARMFbase.NORMS_NAME));
            if (name != null){
                norms[n] = name;
            }
            n++;
        }
        cursor1.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classes);
        spinner.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, norms);
        spinner2.setAdapter(adapter1);

    }
    public void editNorms(View v)
    {
        Log.w("RESPONSE_ERROR_TAG", "1");
        if (spinner.getSelectedItem().toString()==NOT_CHOOSE ||spinner2.getSelectedItem().toString()==NOT_CHOOSE1){
            AlertDialog.Builder error = new AlertDialog.Builder(MainActivity2.this);
            error.setTitle("Выберите вид норматива");
            error.setNeutralButton("Ok", new OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    //ToDo
                }
            });
            error.show();
        }
        else
        {
            Log.w("RESPONSE_ERROR_TAG", "2");
            lv = (ListView) findViewById(R.id.listView1);
            String querys = "SELECT * FROM " + ARMFbase.TABLE_STUDY + " WHERE " +
                    ARMFbase.STUDY_NAME + "='" + spinner.getSelectedItem().toString() + "'";
            Cursor cursors = db.rawQuery(querys, null);
            if (!cursors.moveToFirst()) {
                Log.w("RESPONSE_ERROR_TAG", "cursors");
            }
            cursors.moveToLast();
            Log.w("RESPONSE_ERROR_TAG", "3");
            String studyid = cursors.getString(cursors.getColumnIndex(ARMFbase._ID));
            String queryn = "SELECT * FROM " + ARMFbase.TABLE_NORMS + " WHERE " +
                    ARMFbase.NORMS_NAME + "='" + spinner2.getSelectedItem().toString() + "'";
            Cursor cursorn = db.rawQuery(queryn, null);
            if (!cursorn.moveToFirst()) {
                Log.w("RESPONSE_ERROR_TAG", "cursorn");
            }
            cursorn.moveToLast();
            Log.w("RESPONSE_ERROR_TAG", "4");
            String normsid = cursorn.getString(cursorn.getColumnIndex(ARMFbase._ID));
            String queryp = "SELECT * FROM " + ARMFbase.TABLE_PUPILS + " WHERE " +
                    ARMFbase.STUDYID_PUPILS + "=" + studyid ;
            Cursor cursorp = db.rawQuery(queryp, null);
            if (!cursorp.moveToFirst()) {
                Log.w("RESPONSE_ERROR_TAG", "cursorp");
            }
            String[] lvString = new String[cursorp.getCount()];
            int n = 0;
            Log.w("RESPONSE_ERROR_TAG", "5");
            while (cursorp.moveToNext()){
                Log.w("RESPONSE_ERROR_TAG", "6");
                String querynr = "SELECT * FROM " + ARMFbase.TABLE_NORMSREAL + " WHERE " +
                        ARMFbase.NORMSID_NR + "=" + normsid + " AND " +
                        ARMFbase.PUPILSID_NR + "=" + cursorp.getInt(cursorp.getColumnIndex(ARMFbase._ID));
                Cursor cursornr = db.rawQuery(querynr, null);
                Cursor cur = db.rawQuery("SELECT * FROM normsReal", null);
                if (!cursornr.moveToFirst()) {
                    Log.w("RESPONSE_ERROR_TAG", "cursornr");
                    Log.w("RESPONSE_ERROR_TAG", querynr);
                    Log.w("COUNT", Integer.toString(cur.getCount()));
                }
                cursornr.moveToLast();
                lvString[n] =   cursorp.getString(cursorp.getColumnIndex(ARMFbase.PUPILS_NAME))
                        + ", значение: " + cursornr.getString(cursornr.getColumnIndex(ARMFbase.VALUE_NR))
                        + ", оценка: " + cursornr.getString(cursornr.getColumnIndex(ARMFbase.OCENKA_NR)) ;
                n++;
            }
            Log.w("RESPONSE_ERROR_TAG", "7");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lvString);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String item = (String) lv.getItemAtPosition(position);
                    Log.w("TEST", Long.toString(lv.getItemIdAtPosition(position)));
                    Toast.makeText(MainActivity2.this, "You selected : " + item, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("item", item);
                    intent.putExtra("study", spinner.getSelectedItem().toString());
                    intent.putExtra("norms", spinner2.getSelectedItem().toString());
                    startActivity(intent);
                }
            });

        }

    }
}


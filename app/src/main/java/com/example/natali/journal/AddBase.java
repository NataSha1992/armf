package com.example.natali.journal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AddBase {
  //  public static void CreateClass(SQLiteDatabase db){
    //    ContentValues cv = new ContentValues();
      //  for (int i=5; i<12; i++)
        //{
          //  cv.put(com.example.natali.journal.ARMFbase.STUDY_NAME, i+"A");
            //db.insert(com.example.natali.journal.ARMFbase.TABLE_STUDY, null, cv);

        //}

    //}

    public static void CreateNorms(SQLiteDatabase db){
        ContentValues cv = new ContentValues();




        String[] vid = { "Бег 30", "Бег 60", "Бег 100", "Бег 1000", "Бег 2000", "Челночный бег"};

        for (int i=0; i<vid.length; i++)
        {
            cv.put(com.example.natali.journal.ARMFbase.NORMS_NAME, vid[i] );
            db.insert(com.example.natali.journal.ARMFbase.TABLE_NORMS, null, cv);
        }
    }






    public static void CreatePupils(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        String[] vid = { "Карамзин О.", "Петрова Л.", "Говорун П.",
                "Лобов Д.", "Егоров А.", "Хорошеева Н."};

        for (int i=0; i<vid.length; i++)
        {

            for (int j=1; j<8; j++)
            {

                cv.put(com.example.natali.journal.ARMFbase.STUDYID_PUPILS , j);
                cv.put(com.example.natali.journal.ARMFbase.PUPILS_NAME, vid[i] );
                db.insert(com.example.natali.journal.ARMFbase.TABLE_PUPILS, null, cv);

            }
        }

    }

    public static void CreatenormsReal(SQLiteDatabase db) {

        String queryb = "SELECT * FROM " + ARMFbase.TABLE_NORMSREAL; //
        Cursor cursorb = db.rawQuery(queryb, null);
        Log.w("COUNT_NORMSREAL", Integer.toString(cursorb.getCount()));
        if (cursorb.getCount()<1 ){
            Log.w("CREATE_NORMSREAL", "CREATE");
            ContentValues cv = new ContentValues();

            String query1 = "SELECT * FROM " + ARMFbase.TABLE_NORMS; //
            Cursor cursor1 = db.rawQuery(query1, null);

            String query2 = "SELECT * FROM " + ARMFbase.TABLE_PUPILS; //
            Cursor cursor2 = db.rawQuery(query2, null);
            Log.w("COUNT_NORMS", Integer.toString(cursor1.getCount()));
            Log.w("COUNT_PUPILS", Integer.toString(cursor2.getCount()));
            while (cursor1.moveToNext()){

                while (cursor2.moveToNext()){
                    cv.put(com.example.natali.journal.ARMFbase.NORMSID_NR, cursor1.getInt(cursor1.getColumnIndex(ARMFbase._ID)));
                    cv.put(com.example.natali.journal.ARMFbase.PUPILSID_NR, cursor2.getInt(cursor2.getColumnIndex(ARMFbase._ID)));
                    cv.put(com.example.natali.journal.ARMFbase.VALUE_NR, 0);
                    cv.put(com.example.natali.journal.ARMFbase.OCENKA_NR, 0);
                    cv.put(com.example.natali.journal.ARMFbase.DATE_NR, "2014-05-23");
                    cv.put(com.example.natali.journal.ARMFbase.GROUP_NR, 0);
                    db.insert(com.example.natali.journal.ARMFbase.TABLE_NORMSREAL, null, cv);
                }
                cursor2.moveToFirst();
            }
            cursor1.close();
            cursor2.close();
        }
    }

}



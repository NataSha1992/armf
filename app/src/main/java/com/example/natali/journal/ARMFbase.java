package com.example.natali.journal;

import android.app.Activity;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;



public  class ARMFbase  extends SQLiteOpenHelper implements BaseColumns{

    private static final String DATABASE_NAME = "normativ.db";
    private static final int DATABASE_VERSION = 1;

    //////////////////////////////////////////////////////////
    public static final String TABLE_STUDY = "study";
    public static final String STUDY_NAME = "name";
    public static final String STUDY_ID = "studyId";

    public static final String TABLE_NORMS = "norms";
    public static final String NORMS_NAME = "name";
    public static final String NORMS_UPPVALUE = "upperValue";
    public static final String NORMS_LOWVALUE = "lowerValue";
    public static final String NORMS_MARK = "mark";
    public static final String NORMS_GENDER = "gender";
    public static final String NORMS_ID = "normsId";

    public static final String TABLE_PUPILS = "pupils";
    public static final String STUDYID_PUPILS = "pupilsStID";
    public static final String PUPILS_NAME = "fio";

    public static final String TABLE_NORMSREAL = "normsReal";
    public static final String NORMSID_NR = "normsID";
    public static final String PUPILSID_NR = "pupilsID";
    public static final String VALUE_NR = "value";
    public static final String OCENKA_NR = "ocenka";
    public static final String DATE_NR= "date";
    public static final String GROUP_NR = "gr";

    public static final String TABLE_NORMSOCENKA = "normsOcenka";
    public static final String NORMSID_NO = "normsID";
    public static final String OCENKA_NO = "ocenka";
    public static final String VALUETOP_NO = "valueTop";
    public static final String GROUP_NO = "gr";
    ///////////////////////////////////////////////////////////
    private static final String SQL_CREATE_STUDY = "CREATE TABLE "
            + TABLE_STUDY + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STUDY_NAME + " VARCHAR(3));";

    private static final String SQL_CREATE_NORMS = "CREATE TABLE "
            + TABLE_NORMS + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NORMS_NAME + " VARCHAR(45), "
            + NORMS_UPPVALUE + " INT, "
            + NORMS_LOWVALUE + " INT, "
            + NORMS_MARK + " INT, "
            + NORMS_GENDER + " BOOLEAN, "
            + NORMS_ID + " VARCHAR(45));";

    private static final String SQL_CREATE_PUPILS = "CREATE TABLE "
            + TABLE_PUPILS + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STUDYID_PUPILS + " INT,"
            + PUPILS_NAME + " VARCHAR(45));";

    private static final String SQL_CREATE_NORMSREAL = "CREATE TABLE "
            + TABLE_NORMSREAL + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NORMSID_NR  + " INT,"
            + PUPILSID_NR +" INT,"
            + VALUE_NR + " FLOAT,"
            + OCENKA_NR + " INT,"
            + DATE_NR + " VARCHAR(255),"
            + GROUP_NR + " VARCHAR(45));";

    private static final String SQL_CREATE_NORMSOCENKA = "CREATE TABLE "
            + TABLE_NORMSOCENKA + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NORMSID_NO + " INT,"
            + OCENKA_NO + " INT,"
            + VALUETOP_NO + " VARCHAR(45),"
            + GROUP_NO + " VARCHAR(45));";
    ////////////////////////////////////////////////////////////////////////////
    private static final String SQL_DELETE_STUDY = "DROP TABLE IF EXISTS " + TABLE_STUDY;
    private static final String SQL_DELETE_NORMS = "DROP TABLE IF EXISTS " + TABLE_NORMS;
    private static final String SQL_DELETE_PUPILS = "DROP TABLE IF EXISTS " + TABLE_PUPILS;
    private static final String SQL_DELETE_NORMSREAL = "DROP TABLE IF EXISTS " + TABLE_NORMSREAL;
    private static final String SQL_DELETE_NORMSOCENKA = "DROP TABLE IF EXISTS " + TABLE_NORMSOCENKA;

    public ARMFbase (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_STUDY);
        db.execSQL(SQL_CREATE_NORMS);
        db.execSQL(SQL_CREATE_PUPILS);
        db.execSQL(SQL_CREATE_NORMSREAL);
        db.execSQL(SQL_CREATE_NORMSOCENKA);
    }
    /////////////////////////////////////////////////////////////////////
    public void onUpgradeStudy(SQLiteDatabase db){
        db.execSQL(SQL_DELETE_STUDY);
        db.execSQL(SQL_CREATE_STUDY);
    }
    public void onUpgradeNorms(SQLiteDatabase db){
        db.execSQL(SQL_DELETE_NORMS);
        db.execSQL(SQL_CREATE_NORMS);
    }
    public void onUpgradePupils(SQLiteDatabase db){
        db.execSQL(SQL_DELETE_PUPILS);
        db.execSQL(SQL_CREATE_PUPILS);
    }
    public void onCreateNormsReal(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_NORMSREAL);
    }


    public void onUpgradeNormsocenka(SQLiteDatabase db){
        db.execSQL(SQL_DELETE_NORMSOCENKA);
        db.execSQL(SQL_CREATE_NORMSOCENKA);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_STUDY);
        db.execSQL(SQL_DELETE_NORMS);
        db.execSQL(SQL_DELETE_PUPILS);
        db.execSQL(SQL_DELETE_NORMSREAL);
        db.execSQL(SQL_DELETE_NORMSOCENKA);
        onCreate(db);
    }





}  




package com.incognysissolutions.currencyconverter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHARUL on 23-01-2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION =5 ;


    // Database Name
    private static final String DATABASE_NAME = "currencydata_storage";

    // Contacts table name
    private static final String TABLE_CURRENCY = "currency";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE="image";
    private static final String KEY_CURCODE = "currcode";
    private static final String KEY_SYMBOL = "symbol";
    private static final String KEY_CURRENCY_NAME = "currname";
    private static final String KEY_VALUE = "value";
    private static final String KEY_DEFAULT_CURRENCY = "defaultcurr";
    private static final String KEY_DATETIME = "time";
    SQLiteDatabase db;
    //private static final String KEY_DATE="created_date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHEQUE_TABLE = "CREATE TABLE " + TABLE_CURRENCY + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_CURCODE + " TEXT,"
                + KEY_SYMBOL + " TEXT,"
                + KEY_CURRENCY_NAME + " TEXT,"
                + KEY_VALUE + " TEXT,"
                + KEY_DEFAULT_CURRENCY + " TEXT,"
                + KEY_DATETIME + " TEXT "
                + ")";

        db.execSQL(CREATE_CHEQUE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void add_currency(Currency c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, c.getId());//1
        values.put(KEY_IMAGE,c.getImgurl());
        values.put(KEY_CURCODE, c.getCurrencycode());//2
        values.put(KEY_SYMBOL, c.getSymbol());//3
        values.put(KEY_CURRENCY_NAME, c.getCurrencyname());//4
        values.put(KEY_VALUE,c.getValue());//5
        values.put(KEY_DEFAULT_CURRENCY, c.getDefaultcurrencycode());//6
        values.put(KEY_DATETIME, c.getDatetime());//7
        db.insert(TABLE_CURRENCY, null, values);
        db.close();
    }

    public List<Currency> getallcurrency() {
        List<Currency> currencyList = new ArrayList<Currency>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CURRENCY;

        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_CURRENCY + "",null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Currency c = new Currency();

                c.setId(Integer.parseInt(cursor.getString(0)));
                c.setImgurl(cursor.getString(1));
                c.setCurrencycode(cursor.getString(2));
                c.setSymbol(cursor.getString(3));
                c.setCurrencyname(cursor.getString(4));
                c.setValue(cursor.getString(5));
                c.setDefaultcurrencycode(cursor.getString(6));
                c.setDatetime(cursor.getString(7));


                // Adding contact to list
                currencyList.add(c);
            } while (cursor.moveToNext());
        }

        // return note list
        return currencyList;
    }


    public int updatecurrency(Currency c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VALUE, c.getValue());
        values.put(KEY_DEFAULT_CURRENCY, c.getDefaultcurrencycode());
        values.put(KEY_DATETIME,c.getDatetime());


        return db.update(TABLE_CURRENCY, values, KEY_CURCODE + " = ?",
                new String[] { String.valueOf(c.getCurrencycode()) });
    }

    public String getCurrency(float s) {

        String query = "select * from currency where id='" + s + "'";
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        String curr = null;
        if (cursor != null && cursor.moveToFirst())
        {
            curr = cursor.getString(cursor.getColumnIndex("value"));
            //cursor.close();
        }
        //cursor.moveToFirst();
        // cursor.moveToNext();
        //String formula =cursor.getString( cursor.getColumnIndex("f_url"));
        cursor.close();
        return curr;

    }




}

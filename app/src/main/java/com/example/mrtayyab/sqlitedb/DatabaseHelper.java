package com.example.mrtayyab.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


   public final static  String DATABASE_NAME = "Student.db";
    public final static  String TABLE_NAME = "student_table";
    public final static  String COL_1 = "ID";
    public final static  String COL_2 = "NAME";
    public final static  String COL_3 = "PROFESSION";
    public final static  String COL_4 = "SALARY";


   public DatabaseHelper(Context context){
       super(context , DATABASE_NAME , null , 1);
   }
    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE IF NOT EXISTS " +TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME  TEXT , PROFESSION TEXT , SALARY INTEGER)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
       onCreate(db);

    }

    public Boolean intertData(String name , String profession , String salary){
       SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2 ,name  );
        cv.put(COL_3 , profession);
        cv.put(COL_4 , salary);
        long result = db.insert(TABLE_NAME , null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT * FROM " + TABLE_NAME + "WHERE ID = " +id ;
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=? ", new String[]{id},null);
    }

    public Boolean updateData(String id , String name , String profession , String salary){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1 ,id  );
        cv.put(COL_2 ,name  );
        cv.put(COL_3 , profession);
        cv.put(COL_4 , salary);
        db.update(TABLE_NAME , cv , "ID=?", new String[]{id});
        return true;
    }

    public Integer daleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME , "ID =? " , new String[]{id});
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME ,null);
        return cursor;
    }

}

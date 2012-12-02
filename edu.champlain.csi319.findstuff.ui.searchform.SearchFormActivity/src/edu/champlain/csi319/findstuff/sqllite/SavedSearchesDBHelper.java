package edu.champlain.csi319.findstuff.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SavedSearchesDBHelper extends SQLiteOpenHelper {

  public static final String TABLE_SEARCH_CRITERIA = "savedsearches";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_KEYWORD = "keyword";
  public static final String COLUMN_CATEGORY = "category";
  public static final String COLUMN_SUB_CATEGORY = "subCategory";
  public static final String COLUMN_COUNTRY = "country";
  public static final String COLUMN_STATE = "state";

  private static final String DATABASE_NAME = "savedSearches.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_SEARCH_CRITERIA + "(" 
	  + COLUMN_ID + " integer primary key autoincrement, " 
      + COLUMN_KEYWORD + " text not null, " 
      + COLUMN_CATEGORY + " text not null, "
      + COLUMN_SUB_CATEGORY + " text not null, "
      + COLUMN_COUNTRY + " text not null, "
  	  + COLUMN_STATE + " text not null);";

  public SavedSearchesDBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(SavedSearchesDBHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH_CRITERIA);
    onCreate(db);
  }

} 

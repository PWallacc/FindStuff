package edu.champlain.csi319.findstuff.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RecentSearchesDBHelper extends SQLiteOpenHelper {

	  public static final String TABLE_RECENT_SEARCHES = "recentsearches";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_KEYWORD = "keyword";

	  private static final String DATABASE_NAME = "recentSearches.db";
	  private static final int DATABASE_VERSION = 2;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_RECENT_SEARCHES + "(" 
		  + COLUMN_ID + " integer primary key autoincrement, " 
	      + COLUMN_KEYWORD + " text not null);";

	  public RecentSearchesDBHelper(Context context) {
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
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENT_SEARCHES);
	    onCreate(db);
	  }

	} 

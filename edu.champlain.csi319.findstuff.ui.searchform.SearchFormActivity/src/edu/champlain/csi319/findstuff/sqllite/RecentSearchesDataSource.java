package edu.champlain.csi319.findstuff.sqllite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import edu.champlain.csi319.findstuff.model.RecentSearch;
import edu.champlain.csi319.findstuff.model.SearchCriteria;

public class RecentSearchesDataSource {

	  // Database fields
	  private SQLiteDatabase database;
	  private RecentSearchesDBHelper dbHelper;
	  private String[] allColumns = { RecentSearchesDBHelper.COLUMN_ID, RecentSearchesDBHelper.COLUMN_KEYWORD};

	  public RecentSearchesDataSource(Context context) {
	    dbHelper = new RecentSearchesDBHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	  
	  //
	  // Create a saved search
	  //
	  public RecentSearch createRecentSearch(RecentSearch recentSearch){
		    ContentValues values = new ContentValues();
		    
		    values.put(RecentSearchesDBHelper.COLUMN_KEYWORD, recentSearch.getKeyword().toString());
		    long insertId = database.insert(RecentSearchesDBHelper.TABLE_RECENT_SEARCHES, null,
		        values);
		    
		    Cursor cursor = database.query(RecentSearchesDBHelper.TABLE_RECENT_SEARCHES,
		        allColumns, RecentSearchesDBHelper.COLUMN_ID + " = " + insertId, null,
		        null, null, null);
		    
		    cursor.moveToFirst();
		    RecentSearch newRecentSearch = cursorToRecentSearch(cursor);
		    cursor.close();
		    return newRecentSearch;
	  }
	  
	  public void deleteRecentSearch(SearchCriteria searchCriteria) {
	    long id = searchCriteria.getId();
	    database.delete(RecentSearchesDBHelper.TABLE_RECENT_SEARCHES, RecentSearchesDBHelper.COLUMN_ID
	        + " = " + id, null);
	    System.out.println("Record deleted with id: " + id);
	  }

	  public List<RecentSearch> getAllRecentSearches() {
	    List<RecentSearch> searchList = new ArrayList<RecentSearch>();
	    Cursor cursor = database.query(RecentSearchesDBHelper.TABLE_RECENT_SEARCHES,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	RecentSearch recentSearch = cursorToRecentSearch(cursor);
	    	searchList.add(recentSearch);
	    	cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return searchList;
	  }
	  
	  public String[] getRecentSearches(){
		    Cursor cursor = database.query(RecentSearchesDBHelper.TABLE_RECENT_SEARCHES, allColumns, null, null,
		    		null, null, RecentSearchesDBHelper.COLUMN_ID + " Desc", "50");
		    
		    cursor.moveToFirst();
		    String[] returnArray = new String[cursor.getCount()];
		    int i = 0;
		    while (!cursor.isAfterLast()) {
		    	returnArray[i] = cursor.getString(1);
		    	i++;
		    	cursor.moveToNext();
		    }
		  return returnArray;
	  }
	  
	  public boolean isSearchThere(String keyword){
		    Cursor cursor = database.query(RecentSearchesDBHelper.TABLE_RECENT_SEARCHES, allColumns, "keyword = '" + keyword + "'", null,
		    		null, null, RecentSearchesDBHelper.COLUMN_ID + " Desc", "50");
		    if(cursor.getCount() > 0)
		    	return true;
		    else
		    	return false;
	  }

	  private RecentSearch cursorToRecentSearch(Cursor cursor) {
		  RecentSearch recentSearch = new RecentSearch();	  
		  
		  recentSearch.setId(cursor.getInt(0));
		  recentSearch.setKeyword(cursor.getString(1));

		  return recentSearch;
	  }
	  
	} 
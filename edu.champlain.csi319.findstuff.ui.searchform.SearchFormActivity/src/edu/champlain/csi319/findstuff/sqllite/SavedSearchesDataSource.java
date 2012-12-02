package edu.champlain.csi319.findstuff.sqllite;

import java.util.ArrayList;
import java.util.List;

import edu.champlain.csi319.findstuff.model.SearchCriteria;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SavedSearchesDataSource {

  // Database fields
  private SQLiteDatabase database;
  private SavedSearchesDBHelper dbHelper;
  private String[] allColumns = { SavedSearchesDBHelper.COLUMN_ID,
	      SavedSearchesDBHelper.COLUMN_KEYWORD, SavedSearchesDBHelper.COLUMN_CATEGORY, 
	      SavedSearchesDBHelper.COLUMN_SUB_CATEGORY, SavedSearchesDBHelper.COLUMN_COUNTRY,
	      SavedSearchesDBHelper.COLUMN_STATE};

  public SavedSearchesDataSource(Context context) {
    dbHelper = new SavedSearchesDBHelper(context);
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
  public SearchCriteria createSavedSearch(SearchCriteria searchCriteria){
	    ContentValues values = new ContentValues();
	    
	    values.put(SavedSearchesDBHelper.COLUMN_KEYWORD, searchCriteria.getKeyword().toString());
	    values.put(SavedSearchesDBHelper.COLUMN_CATEGORY, searchCriteria.getCategory().toString());
	    values.put(SavedSearchesDBHelper.COLUMN_SUB_CATEGORY, searchCriteria.getSubCategory().toString());
	    values.put(SavedSearchesDBHelper.COLUMN_COUNTRY, searchCriteria.getCountry().toString());
	    values.put(SavedSearchesDBHelper.COLUMN_STATE, searchCriteria.getState().toString());
	    long insertId = database.insert(SavedSearchesDBHelper.TABLE_SEARCH_CRITERIA, null,
	        values);
	    Cursor cursor = database.query(SavedSearchesDBHelper.TABLE_SEARCH_CRITERIA,
	        allColumns, SavedSearchesDBHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    SearchCriteria newSearchCriteria = cursorToSearchCriteria(cursor);
	    cursor.close();
	    return newSearchCriteria;
  }
  
  public void deleteSavedSearch(SearchCriteria searchCriteria) {
    long id = searchCriteria.getId();
    database.delete(SavedSearchesDBHelper.TABLE_SEARCH_CRITERIA, SavedSearchesDBHelper.COLUMN_ID
        + " = " + id, null);
    System.out.println("Record deleted with id: " + id);
  }

  public List<SearchCriteria> getAllSavedSearches() {
  	  List<SearchCriteria> criteriaList = new ArrayList<SearchCriteria>();
 	  Cursor cursor = database.query(SavedSearchesDBHelper.TABLE_SEARCH_CRITERIA,
			  allColumns, null, null, null, null, null);
 	  
	  cursor.moveToFirst();
    
	  while (!cursor.isAfterLast()) {
 		  SearchCriteria searchCriteria = cursorToSearchCriteria(cursor);
  		  criteriaList.add(searchCriteria);
 		  cursor.moveToNext();
	  }
    
	  // Make sure to close the cursor
	  cursor.close();
	  return criteriaList;
  }

  private SearchCriteria cursorToSearchCriteria(Cursor cursor) {
	  SearchCriteria searchCriteria = new SearchCriteria();	  
	  
	  searchCriteria.setId(cursor.getInt(0));
	  searchCriteria.setKeyword(cursor.getString(1));
	  searchCriteria.setCategory(cursor.getString(2));
	  searchCriteria.setSubCategory(cursor.getString(3));
	  searchCriteria.setCountryString(cursor.getString(4));
	  searchCriteria.setStateString(cursor.getString(5));

	  return searchCriteria;
  }
} 
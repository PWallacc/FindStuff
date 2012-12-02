package edu.champlain.csi319.findstuff.ui.searchform;



import java.util.List;
import edu.champlain.csi319.findstuff.R;
import edu.champlain.csi319.findstuff.content.Categories;
import edu.champlain.csi319.findstuff.content.Locations;
import edu.champlain.csi319.findstuff.model.RecentSearch;
import edu.champlain.csi319.findstuff.model.SearchCriteria;
import edu.champlain.csi319.findstuff.sqllite.RecentSearchesDataSource;
import edu.champlain.csi319.findstuff.sqllite.SavedSearchesDataSource;
import edu.champlain.csi319.findstuff.ui.resultslist.ResultsListActivity;
import edu.champlain.csi319.findstuff.util.HashMapAdapter;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchFormFragment extends Fragment implements View.OnClickListener, OnItemSelectedListener, OnItemClickListener, OnItemLongClickListener
{
	//HashMap Adapters
	private static HashMapAdapter<String,String> categoryHashMapAdapter;
	private static HashMapAdapter<String,String> subCategoryHashMapAdapter;
	private static HashMapAdapter<String,String> countryHashMapAdapter;
	private static HashMapAdapter<String,String> stateHashMapAdapter;
	
	//Saved Searches Adapter
	private static SavedSearchAdapter savedSearchAdapter;
	
	//Recent Search Adapter
	private static  ArrayAdapter<String> adapter;

	
	// Get the spinners and editTexts
	private static Spinner categorySpinner;
	private static Spinner subCategorySpinner;
	private static Spinner countrySpinner;
	private static Spinner stateSpinner;
	private static AutoCompleteTextView searchEditText;

	//Saved search listView
	private ListView savedSearchListView;
	
	//Database variables
	private static SavedSearchesDataSource savedSearchesSQLLiteDatabase;
	private static RecentSearchesDataSource recentSearchesSQLLiteDatabase;
	
	public SearchFormFragment()
	{
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.searchform_fragment, container, false);
		
    	//Open our database connections
	   	if(savedSearchesSQLLiteDatabase == null){
    		savedSearchesSQLLiteDatabase = new SavedSearchesDataSource(view.getContext());
    		savedSearchesSQLLiteDatabase.open();
    	}
	   	
	   	if(recentSearchesSQLLiteDatabase == null){
	   		recentSearchesSQLLiteDatabase = new RecentSearchesDataSource(view.getContext());
	   		recentSearchesSQLLiteDatabase.open();
    	}
    	//Populate the spinners
		populateSpinners(view);
		
		// Get the spinners and editTexts
		categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
		subCategorySpinner = (Spinner) view.findViewById(R.id.subCategorySpinner);
		countrySpinner = (Spinner) view.findViewById(R.id.countrySpinner);
		stateSpinner = (Spinner) view.findViewById(R.id.stateSpinner);
		searchEditText = (AutoCompleteTextView) view.findViewById(R.id.searchEditText);
		
		//Set the listeners
		categorySpinner.setOnItemSelectedListener((OnItemSelectedListener) this);
		countrySpinner.setOnItemSelectedListener((OnItemSelectedListener) this);
		
		//set the button listener.
		Button search = (Button) view.findViewById(R.id.button_send);
		search.setOnClickListener((OnClickListener) this);
		
		Button saveAndSearch = (Button) view.findViewById(R.id.button_save_send);
		saveAndSearch.setOnClickListener((OnClickListener) this);
		
		//Get the list view and set the listeners
		savedSearchListView = (ListView) view.findViewById(R.id.saved_search_list);
		savedSearchListView.setOnItemClickListener((OnItemClickListener) this);
		savedSearchListView.setOnItemLongClickListener((OnItemLongClickListener) this);
		
        //Get all of the saved searches
	    List<SearchCriteria> savedSearches = savedSearchesSQLLiteDatabase.getAllSavedSearches();
	    if(savedSearches.size() > 0){
	    	savedSearchAdapter = new SavedSearchAdapter(view.getContext(), savedSearches);	
	    	savedSearchListView.setAdapter(savedSearchAdapter);
	    	savedSearchAdapter.notifyDataSetChanged();
	    }
	    
        //Get all of the recent searches
	    adapter = new ArrayAdapter<String>(this.getActivity(), 
	    		android.R.layout.simple_dropdown_item_1line,
	    		recentSearchesSQLLiteDatabase.getRecentSearches());
    	searchEditText.setAdapter(adapter);
    	adapter.notifyDataSetChanged();	
		
		return view;
	}
	
	public void populateSpinners(View view)
	{		
		// Instantiate my adapters
		categoryHashMapAdapter = new HashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Categories.CATEGORY);
		subCategoryHashMapAdapter = new HashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Categories.SUBCATEGORY_FORSALE);
		countryHashMapAdapter = new HashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Locations.COUNTRY);
		stateHashMapAdapter = new HashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Locations.STATES_USA);

		// Populate the category spinner;
		Spinner categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
		categorySpinner.setAdapter(categoryHashMapAdapter);
		
		// Populate the sub-category spinner;
		Spinner subCategorySpinner = (Spinner) view.findViewById(R.id.subCategorySpinner);
		subCategorySpinner.setAdapter(subCategoryHashMapAdapter);
		
		// Populate the Country spinner;
		Spinner countrySpinner = (Spinner) view.findViewById(R.id.countrySpinner);
		countrySpinner.setAdapter(countryHashMapAdapter);
		
		// Populate the State spinner;
		Spinner stateSpinner = (Spinner) view.findViewById(R.id.stateSpinner);
		stateSpinner.setAdapter(stateHashMapAdapter);
		
	}
	
	public void onClick(View view) 
	{
		//New SearchCriteria Object
		SearchCriteria searchCriteria = new SearchCriteria();		
	
		//Set the values 
		searchCriteria.setCategory(categoryHashMapAdapter.getValue(categorySpinner.getSelectedItem().toString()));
		searchCriteria.setSubCategory(subCategoryHashMapAdapter.getValue(subCategorySpinner.getSelectedItem().toString()));
		searchCriteria.setCountryString(countrySpinner.getSelectedItem().toString());
		searchCriteria.setStateString(stateHashMapAdapter.getValue(stateSpinner.getSelectedItem().toString()));
		//Get rid of line breaks.
		String keyword = searchEditText.getText().toString();
		keyword = keyword.replace("\n", "").replace("\r", "");
		searchCriteria.setKeyword(keyword);
		
		//New Recent Search
		RecentSearch recentSearch = new RecentSearch();
		recentSearch.setKeyword(searchEditText.getText().toString());

		Intent intent = new Intent(getActivity().getApplicationContext(), ResultsListActivity.class);
		
		//Clear the input
		searchEditText.setText("");

		switch(view.getId()){
			case R.id.button_send:	
				
				//Add the search to recent searches and update the adapter
				if(recentSearchesSQLLiteDatabase.isSearchThere(keyword) == false){
					recentSearchesSQLLiteDatabase.createRecentSearch(recentSearch);
				    adapter = new ArrayAdapter<String>(this.getActivity(), 
				    		android.R.layout.simple_dropdown_item_1line,
				    		recentSearchesSQLLiteDatabase.getRecentSearches());
			    	searchEditText.setAdapter(adapter);
			    	adapter.notifyDataSetChanged();		
				}

				// include the values from the screen.
		    	intent.putExtra("SearchCriteria", searchCriteria);
		    	
		    	//Start the new activity
		    	startActivityForResult(intent, 0);
				break;
			case R.id.button_save_send:		
				
				//Add a row to the saved search list view.
				savedSearchesSQLLiteDatabase.createSavedSearch(searchCriteria);
				savedSearchAdapter = new SavedSearchAdapter(this.getActivity(), savedSearchesSQLLiteDatabase.getAllSavedSearches());
				savedSearchListView.setAdapter(savedSearchAdapter);
				savedSearchAdapter.notifyDataSetChanged();
				
				//If this is a new search
				if(recentSearchesSQLLiteDatabase.isSearchThere(keyword) == false){
					//Then add the search to recent searches and update the adapter
					recentSearchesSQLLiteDatabase.createRecentSearch(recentSearch);
				    adapter = new ArrayAdapter<String>(this.getActivity(), 
				    		android.R.layout.simple_dropdown_item_1line,
				    		recentSearchesSQLLiteDatabase.getRecentSearches());
			    	searchEditText.setAdapter(adapter);
			    	adapter.notifyDataSetChanged();		
				}
				

				// include the values from the screen.
		    	intent.putExtra("SearchCriteria", searchCriteria);
		    	
		    	//Start the new activity
		    	startActivityForResult(intent, 0);
				break;
			default:
				break;
		}
   	
	}
	

	/*
	 *  android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)
	 *  
	 *  This function recognizes changes to the spinners
	 *  
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
	{
        int spinner = parent.getId();
        switch (spinner) 
        {
                case R.id.categorySpinner:
                	Spinner subCategorySpinner = (Spinner)getView().findViewById(R.id.subCategorySpinner);
            	    if(position == 0){
            	    	subCategoryHashMapAdapter = new HashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Categories.SUBCATEGORY_FORSALE);
            	    	subCategorySpinner.setAdapter(subCategoryHashMapAdapter);
            	    }
            	    if(position == 1){
            	    	subCategoryHashMapAdapter = new HashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Categories.SUBCATEGORY_REALESTATE);
            	    	subCategorySpinner.setAdapter(subCategoryHashMapAdapter);
            	    }
                break;
            case R.id.countrySpinner:
            	Spinner stateSpinner = (Spinner)getView().findViewById(R.id.stateSpinner);
        	    if(position == 0){
        	    	countryHashMapAdapter = new HashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Locations.STATES_USA);
        	    	stateSpinner.setAdapter(countryHashMapAdapter);
        	    }
        	    if(position == 1){
        	    	countryHashMapAdapter = new HashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Locations.PROVINCES_CAN);
        	    	stateSpinner.setAdapter(countryHashMapAdapter);
        	    }
            break;
        }			
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		//Do NOthing
	}

	/*
	 * This function recognizes NORMAL clicks on the saved search list view.
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
		//Get the search criteria from the list
		SearchCriteria search = (SearchCriteria) savedSearchListView.getItemAtPosition(position);
		
		//Start new intent
		Intent intent = new Intent(getActivity().getApplicationContext(), ResultsListActivity.class);
		
		// include the values from the saved search.
    	intent.putExtra("SearchCriteria", search);
    	
    	//Start the new activity
    	startActivityForResult(intent, 0);				
	}
	
	/*
	 * This function recognizes LONG clicks on the saved search list view.
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int position, long arg3) 
	{
		//New ALertDialog
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(adapterView.getContext());
 
		// set title
		alertDialogBuilder.setTitle("Delete Note?");
 
		// set dialog message
		alertDialogBuilder.setMessage("Are you sure you want to delete this saved search?")
			.setCancelable(false)
			.setPositiveButton("Yes, Delete it!",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				
				//Grab the seachCriteria that was LONG clicked and delete it from the database.
				SearchCriteria search = (SearchCriteria) savedSearchListView.getItemAtPosition(position);
				savedSearchesSQLLiteDatabase.deleteSavedSearch(search);

				//set the adapter and notify it of a change.
				savedSearchAdapter = new SavedSearchAdapter(getActivity(), savedSearchesSQLLiteDatabase.getAllSavedSearches());
				savedSearchListView.setAdapter(savedSearchAdapter);
				savedSearchAdapter.notifyDataSetChanged();
				
			}
		});
		alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();

		return true;
	}
}

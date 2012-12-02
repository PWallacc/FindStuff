package edu.champlain.csi319.findstuff.ui.searchform;


import edu.champlain.csi319.findstuff.R;
import edu.champlain.csi319.findstuff.content.Categories;
import edu.champlain.csi319.findstuff.content.Locations;
import edu.champlain.csi319.findstuff.model.SearchCriteria;
import edu.champlain.csi319.findstuff.ui.resultslist.ResultsListActivity;
import edu.champlain.csi319.findstuff.util.HashMapAdapter;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class SearchFormFragment extends Fragment implements View.OnClickListener, OnItemSelectedListener
{
	private static final String TAG = SearchFormFragment.class.getSimpleName();
	private HashMapAdapter<String,String> categoryHashMapAdapter;
	private HashMapAdapter<String,String> subCategoryHashMapAdapter;
	private HashMapAdapter<String,String> countryHashMapAdapter;
	private HashMapAdapter<String,String> stateHashMapAdapter;


	public SearchFormFragment()
	{
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.searchform_fragment, container, false);
		
		//Populate the spinners
		populateSpinners(view);
		
		//Get the spinners to have listeners
		Spinner categorySpinner = (Spinner)view.findViewById(R.id.categorySpinner);
		Spinner countrySpinner = (Spinner)view.findViewById(R.id.countrySpinner);
		
		//Set the listeners
		categorySpinner.setOnItemSelectedListener((OnItemSelectedListener) this);
		countrySpinner.setOnItemSelectedListener((OnItemSelectedListener) this);
		
		//set the button listener.
		Button button1 = (Button) view.findViewById(R.id.button_send);
		button1.setOnClickListener((OnClickListener) this);
		

		
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

		// Get the spinners and editTexts
		Spinner categorySpinner = (Spinner) getView().findViewById(R.id.categorySpinner);
		Spinner subCategorySpinner = (Spinner) getView().findViewById(R.id.subCategorySpinner);
		Spinner countrySpinner = (Spinner) getView().findViewById(R.id.countrySpinner);
		Spinner stateSpinner = (Spinner) getView().findViewById(R.id.stateSpinner);
		EditText searchEditText = (EditText) getView().findViewById(R.id.searchEditText);
	
		//Set the values 
		searchCriteria.setCategory(categoryHashMapAdapter.getValue(categorySpinner.getSelectedItem().toString()));
		searchCriteria.setSubCategory(subCategoryHashMapAdapter.getValue(subCategorySpinner.getSelectedItem().toString()));
		searchCriteria.setCountryString(countrySpinner.getSelectedItem().toString());
		searchCriteria.setStateString(stateHashMapAdapter.getValue(stateSpinner.getSelectedItem().toString()));
		searchCriteria.setKeyword(searchEditText.getText().toString());

		//
		// Start new activity
		//   					
		Intent intent = new Intent(getActivity().getApplicationContext(), ResultsListActivity.class);
		// include the values from the screen.
    	intent.putExtra("SearchCriteria", searchCriteria);
    	startActivityForResult(intent, 0);
   	
	}
	

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
	    //Toast.makeText(this.getActivity().getApplicationContext(), "Key: "+ view +" Value: "+ adapterView, Toast.LENGTH_LONG).show();
	
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}

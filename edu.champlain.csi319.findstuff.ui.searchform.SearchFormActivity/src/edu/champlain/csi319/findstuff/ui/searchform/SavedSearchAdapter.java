package edu.champlain.csi319.findstuff.ui.searchform;

import java.util.List;
import edu.champlain.csi319.findstuff.R;
import edu.champlain.csi319.findstuff.model.SearchCriteria;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SavedSearchAdapter extends ArrayAdapter<SearchCriteria>
{
	  private final Context context;
	  private final List<SearchCriteria> values;
		
	  public SavedSearchAdapter(Context context, List<SearchCriteria> values) {
	    super(context, R.layout.resultslist_activity, values);
	    this.context = context;
	    this.values = values;
	     
	  }
	  
	  static class RowHolder
	  {
		  protected TextView savedSearch;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		  RowHolder holder;
		  if (convertView == null) {
			    LayoutInflater inflater = (LayoutInflater) context
			            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    convertView = inflater.inflate(R.layout.saved_search_row_layout, null);
		      holder = new RowHolder();
		      holder.savedSearch = (TextView) convertView.findViewById(R.id.saved_search);
		      convertView.setTag(holder);
		  } else{
			  holder = (RowHolder) convertView.getTag();
		  }

		  String keyword = values.get(position).getKeyword();
		  String category = values.get(position).getCategory();
		  String subCategory = values.get(position).getSubCategory();
		  String country = values.get(position).getCountry();
		  String state = values.get(position).getState();
		  int id = values.get(position).getId();
		  
		  holder.savedSearch.setText(formatSavedSearch(id, keyword, category, subCategory, country, state));

		  
		  return convertView;
	  }  
	  
	  private String formatSavedSearch( int id, String keyword, String category, String subCategory, String country, String state)
	  {
		  if(keyword.isEmpty() == true){
			  keyword = "All";
		  }
		  
		  String savedSearchString = id + ": " + keyword + " - Cat: " + category + " Sub Cat: " +
				  subCategory + " Location: " + state + ", " + country;
		  
		  return savedSearchString;
	  }
} 

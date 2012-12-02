package edu.champlain.csi319.findstuff.ui.searchform;

import java.util.List;
import edu.champlain.csi319.findstuff.R;
import edu.champlain.csi319.findstuff.model.RecentSearch;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecentSearchAdapter extends ArrayAdapter<RecentSearch>
{
	  private final Context context;
	  private final List<RecentSearch> values;
		
	  public RecentSearchAdapter(Context context, List<RecentSearch> values) {
	    super(context, R.layout.resultslist_activity, values);
	    this.context = context;
	    this.values = values;
	     
	  }
	  
	  static class RowHolder
	  {
		  protected TextView recentSearch;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		  RowHolder holder;
		  if (convertView == null) {
			    LayoutInflater inflater = (LayoutInflater) context
			            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    convertView = inflater.inflate(R.layout.recent_search_row_layout, null);
		      holder = new RowHolder();
		      holder.recentSearch = (TextView) convertView.findViewById(R.id.keyword);
		      convertView.setTag(holder);
		  } else{
			  holder = (RowHolder) convertView.getTag();
		  }

		  String keyword = values.get(position).getKeyword();
//		  int id = values.get(position).getId();
		  
		  holder.recentSearch.setText(keyword);

		  
		  return convertView;
	  }  
}
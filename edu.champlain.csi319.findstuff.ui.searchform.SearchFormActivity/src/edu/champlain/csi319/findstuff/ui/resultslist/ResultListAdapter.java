package edu.champlain.csi319.findstuff.ui.resultslist;

import java.util.List;

import edu.champlain.csi319.findstuff.R;
import edu.champlain.csi319.findstuff.model.SearchResult;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultListAdapter extends ArrayAdapter<SearchResult>
{
  private final Context context;
  private final List<SearchResult> values;

  public ResultListAdapter(Context context, List<SearchResult> values) {
    super(context, R.layout.resultslist_activity, values);
    this.context = context;
    this.values = values;
  }
  
  static class RowHolder
  {
	  protected TextView heading;
	  protected ProgressBar progressBar;
	  protected TextView location;
	  protected ImageView icon;
	  protected TextView id;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
	  RowHolder holder;
	  if (convertView == null) {
		    LayoutInflater inflater = (LayoutInflater) context
		            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    convertView = inflater.inflate(R.layout.row_layout, null);
	      holder = new RowHolder();
	      holder.heading = (TextView) convertView.findViewById(R.id.heading);
	      holder.location = (TextView) convertView.findViewById(R.id.location);
	      holder.id = (TextView) convertView.findViewById(R.id.index);
	      holder.icon = (ImageView) convertView.findViewById(R.id.imageView1);
	      holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
	      convertView.setTag(holder);
	  } else{
		  holder = (RowHolder) convertView.getTag();
	  }

	  String heading = values.get(position).getHeading();
	  String location = values.get(position).getLocation();
	  String id = values.get(position).getId();
	  Bitmap thumbnail = values.get(position).getThumbnail();
	  
	  holder.heading.setText(heading);
	  holder.location.setText(location);
	  holder.id.setText("[" + id + "]");
	  if(values.get(position).isThumbnailSet() == true){
		  holder.progressBar.setVisibility(View.GONE);
		  holder.icon.setImageBitmap(thumbnail);
	  }else{
		  holder.progressBar.setVisibility(View.VISIBLE);
	  }
	  
    return convertView;
  }  
} 

package edu.champlain.csi319.findstuff.content;

import java.util.List;
import edu.champlain.csi319.findstuff.model.SearchResult;
import edu.champlain.csi319.findstuff.ui.resultslist.ResultListAdapter;
import edu.champlain.csi319.findstuff.ui.resultslist.ResultsListFragment;
import edu.champlain.csi319.findstuff.util.HttpImageLoader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


public class ImageLoaderAsync extends AsyncTask<List<SearchResult>, Void, SearchResult> {
	ResultListAdapter adapter;
	TaskCompleteListener callBack;
	int debug = 0;

	public interface TaskCompleteListener
	{
		public void onTaskComplete();
	}
	
	public ImageLoaderAsync(TaskCompleteListener myListener){
		this.callBack = myListener;
	}
	
	
    @Override
    protected SearchResult doInBackground(List<SearchResult>...searchResultList) {
    	Bitmap image;
    	SearchResult result = new SearchResult();
    	for(List<SearchResult> searchList : searchResultList){
	    	for(SearchResult tmpResult : searchList){  		        	
		        try{
		        	result = tmpResult;
		        	image = HttpImageLoader.decodeUrl(tmpResult.getImageURL(), 72, 72);
		        	if(image == null){
		        		//should set a no image found picture.
		        	} else{
		        		result.setThumbnail(image);
		        	}
	        		result.setThumbnailSet(true);
		        	publishProgress();
		        } catch (Exception e){	        	
		        	e.printStackTrace();
		        }        	
	        }
    	}
      return result;
    }
    
    @Override
    protected void onPreExecute(){ 
    	//Do Nothing
    }
    
    @Override 
    protected void onProgressUpdate(Void...result){
    	ResultsListFragment.getInstance().onTaskComplete();
    }
    
    @Override
    protected void onPostExecute(SearchResult result) {
    	//Update the list view.
    	callBack.onTaskComplete();
    }
} 
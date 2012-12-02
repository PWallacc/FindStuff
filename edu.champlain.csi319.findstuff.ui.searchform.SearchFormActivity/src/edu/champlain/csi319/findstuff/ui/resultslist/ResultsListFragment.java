package edu.champlain.csi319.findstuff.ui.resultslist;


import java.util.List;
import edu.champlain.csi319.findstuff.content.ImageLoaderAsync;
import edu.champlain.csi319.findstuff.content.ImageLoaderAsync.TaskCompleteListener;
import edu.champlain.csi319.findstuff.content.SearchTask;
import edu.champlain.csi319.findstuff.model.*;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.app.LoaderManager;
import android.content.Loader;



public class ResultsListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<SearchResult>>, TaskCompleteListener, OnItemClickListener
{
    private ResultListAdapter myAdapter;
    public static ResultsListFragment thisInstance;
    private static ListView myListView;
    private int currentPage =-1;
    private ImageLoaderAsync asyncImageLoader;

    
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {        
    	super.onCreate(savedInstanceState); 
    	thisInstance = this;

    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	//setListShown(true);
    	currentPage++;
   	
    	LoaderManager loaderManager = getLoaderManager();
    	loaderManager.initLoader(0, null, this);
    	 
    }

	@Override
	public Loader<List<SearchResult>> onCreateLoader(int id, Bundle bundle)
	{       
    	SearchCriteria searchCriteria = (SearchCriteria)getActivity().getIntent().getParcelableExtra("SearchCriteria");        
        
 		return new SearchTask(getActivity().getApplicationContext(), searchCriteria, 20, currentPage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onLoadFinished(Loader<List<SearchResult>> loader, List<SearchResult> resultsList) 
	{
		//Instantiate my adapter
		myAdapter = new ResultListAdapter(this.getActivity(), resultsList);

		//Send the results off to get the image urls formatted
		asyncImageLoader = new ImageLoaderAsync(this);
		asyncImageLoader.execute(resultsList);
		
		//Set the adapter
		setListAdapter(myAdapter);
		
		myListView = getListView();
	    myListView.setOnItemClickListener( (OnItemClickListener) this);
		
	}

	@Override
	public void onTaskComplete() {
		// notify data set change
		myAdapter.notifyDataSetChanged();		
	}
	
	public static ResultsListFragment getInstance(){
		return thisInstance;
	}

	@Override
	public void onLoaderReset(Loader<List<SearchResult>> arg0) {
		//Do Nothing
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
		// When someone clicks a row in the list grab the item they selected.
		SearchResult item = (SearchResult) myListView.getItemAtPosition(position);		
		Toast.makeText(view.getContext(), position + " " + item.getId() + " " + item.getHeading(), Toast.LENGTH_SHORT).show();
	}
	
}

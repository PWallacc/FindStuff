package edu.champlain.csi319.findstuff.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.champlain.csi319.findstuff.content.ImageLoaderAsync.TaskCompleteListener;
import edu.champlain.csi319.findstuff.model.SearchCriteria;
import edu.champlain.csi319.findstuff.model.SearchResult;
import edu.champlain.csi319.findstuff.ui.resultslist.ResultListAdapter;

import com.threetaps.client.SearchClient;
import com.threetaps.client.ThreetapsClient;
import com.threetaps.dto.search.SearchRequest;
import com.threetaps.dto.search.SearchResponse;
import com.threetaps.model.Posting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

/**
 * See http://docs.3taps.net/docs/search-api
 * 
 * Sample Query: (replace "KEY" for your AuthToken)
 * http://api.3taps.com/search?authToken=KEY&source=CRAIG&location=USA-VT&text=sofa
 *
 */

public class SearchTask extends AsyncTaskLoader<List<SearchResult>>
{
    private static final String TAG = SearchTask.class.getSimpleName();
    private static final String AUTH_ID = "cf547bab28f0410e9ef69c35fa321c5a";
    private SearchCriteria criteria;
    private SearchClient searchClient;
    private SearchRequest searchRequest;
    private final List<String> retVals;
    private int resultsPerPage;
    private int page;
    

    public SearchTask(Context context, SearchCriteria criteria, int resultsPerPage, int page)
    {
        super(context);
        this.criteria = criteria;
        this.resultsPerPage = resultsPerPage;
        this.page = page;
        this.retVals = new ArrayList<String>();
        this.retVals.add("images");
        this.retVals.add("heading");
        this.retVals.add("city");
        this.retVals.add("annotations");
        this.retVals.add("body");
        
        
        searchClient = ThreetapsClient.getInstance().setAuthID("75c8e3851e874fe692a8199022a06645").getSearchClient();
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }
    
    
    @Override
    public List<SearchResult> loadInBackground()
    { 
        searchRequest = new SearchRequest();
        searchRequest.setText(criteria.getKeyword());
        searchRequest.setSource("CRAIG");
        searchRequest.setCategory(criteria.getCategory() + criteria.getSubCategory());
        searchRequest.setLocation(criteria.getCountry() + "-" + criteria.getState());
        searchRequest.setRetvals(retVals);
        searchRequest.setRpp(resultsPerPage);
        searchRequest.setPage(page);
        try
        {
        	
            SearchResponse response = searchClient.search(searchRequest);  
            if(response.getResults().size() > 0)
	        {
	            List<SearchResult> results = new ArrayList<SearchResult>(response.getResults().size());
	            // Map whatever fields we want from Posting to SearchResult (a static mapper would be nicer)
	            int count = 0;
	            for(Posting posting : response.getResults())
	            {
	            	++count;
	                SearchResult result = new SearchResult();
	                result.setHeading(posting.getHeading());
	                result.setLocation((String)posting.getAnnotations().get("source_loc") + ", " + criteria.getState());
	                result.setId("" + count);
	                
	                if(posting.getImages().size() > 0)
	                	result.setImageURL(posting.getImages().get(0).getFull().toString());

	                
	                results.add(result);
	            }	            
	            return results;
            }
            else
            {
            	//No results Found!
	            List<SearchResult> results = new ArrayList<SearchResult>(1);
	            SearchResult result = new SearchResult();
	            result.setHeading("No Results found!");
	            result.setLocation("Please broaden your search criteria...");
	            result.setId("1");
	            results.add(result);   
	            
	            return results;
            }
         
        }
        catch(IOException e)
        {
            Log.e(TAG, e.getMessage());
            
            //Tell the user there was an error in the system.
            List<SearchResult> list = new ArrayList<SearchResult>(1);
            SearchResult result = new SearchResult();
            result.setHeading("Error in Search!");
            result.setLocation(e.getMessage());            	

            result.setId("1");
            list.add(result);
            
            return list;
        }
    }
}

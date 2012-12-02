package edu.champlain.csi319.findstuff.ui.resultslist;

import java.util.ArrayList;
import java.util.List;

import edu.champlain.csi319.findstuff.model.*;
import android.app.ListFragment;
import android.os.Bundle;


public class ResultsListFragment extends ListFragment
{
    private static final String TAG = ResultsListFragment.class.getSimpleName();
        
    private SearchCriteria criteria;
    private List<SearchResult> resultList;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        // TODO: Any initialization necessary
        resultList = initDummyData();

    }

    
    /**
     * Generate some dummy list data.
     * This will go away after Assignment 1
     */
    private static List<SearchResult> initDummyData()
    {
        List<SearchResult> list = new ArrayList<SearchResult>(50);
        
        for(int i = 0; i < 50; i++)
        {
            SearchResult result = new SearchResult();
            result.setHeading("Item " + String.valueOf(i));
            result.setLocation("BTV");
            
            list.add(result);
        }
        
        return list;
    }
}

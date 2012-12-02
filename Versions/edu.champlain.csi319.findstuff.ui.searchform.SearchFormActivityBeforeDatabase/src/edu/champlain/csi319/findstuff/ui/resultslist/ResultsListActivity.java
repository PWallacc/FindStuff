package edu.champlain.csi319.findstuff.ui.resultslist;

import edu.champlain.csi319.findstuff.R;
import edu.champlain.csi319.findstuff.model.SearchCriteria;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ResultsListActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.resultslist_activity);
    }
}

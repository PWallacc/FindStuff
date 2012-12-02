package edu.champlain.csi319.findstuff.ui.searchform;

import edu.champlain.csi319.findstuff.R;
import edu.champlain.csi319.findstuff.ui.resultslist.ResultsListActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


public class SearchFormActivity extends Activity
{
    private static final String TAG = SearchFormActivity.class.getSimpleName();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchform_activity);
	}


}

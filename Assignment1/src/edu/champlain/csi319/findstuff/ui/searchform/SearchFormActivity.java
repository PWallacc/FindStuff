package edu.champlain.csi319.findstuff.ui.searchform;

import edu.champlain.csi319.findstuff.R;
import android.app.Activity;
import android.os.Bundle;


public class SearchFormActivity extends Activity 
{
    private static final String TAG = SearchFormActivity.class.getSimpleName();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchform_activity);
	}
}

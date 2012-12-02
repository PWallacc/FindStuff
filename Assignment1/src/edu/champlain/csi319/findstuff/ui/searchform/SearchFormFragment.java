package edu.champlain.csi319.findstuff.ui.searchform;

import edu.champlain.csi319.findstuff.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SearchFormFragment extends Fragment 
{
	private static final String TAG = SearchFormFragment.class.getSimpleName();

	public SearchFormFragment()
	{
		super();
		
		// TODO: Any initialization necessary
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.searchform_fragment, container, false);
		
		// TODO: Bind to View Objects
		
		return view;
	}
}

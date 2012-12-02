package edu.champlain.csi319.findstuff.test;

import edu.champlain.csi319.findstuff.ui.searchform.SearchFormActivity;
import edu.champlain.csi319.findstuff.ui.searchform.SearchFormFragment;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import edu.champlain.csi319.findstuff.R;

public class SearchFormActivityTest extends ActivityInstrumentationTestCase2<SearchFormActivity>
{
	private SearchFormActivity activity;
	private SearchFormFragment fragment;
	
	private EditText keyword;
	
	private Spinner categorySpinner;
	private SpinnerAdapter categorySpinnerAdapter;
	
	public SearchFormActivityTest()
	{
		super(SearchFormActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception
	{
		activity = getActivity();
		fragment = (SearchFormFragment) activity.getFragmentManager().findFragmentById(R.id.searchFormFragment);
		
		keyword = (EditText) activity.findViewById(R.id.keyword);
		categorySpinner = (Spinner) activity.findViewById(R.id.categorySpinner);
		
		categorySpinnerAdapter = categorySpinner.getAdapter();
	}
	
	public void testPreCondition()
	{
		assertNotNull("", activity);
	}
	
	public void testCategorySpinnerUI()
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				categorySpinner.requestFocus();
				categorySpinner.setSelection(0);
			}
		});
		
		sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
		sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		
		int position = categorySpinner.getSelectedItemPosition();
		assertEquals(position, 1);
	}
	
}

package edu.champlain.csi319.findstuff.test;

import junit.framework.TestSuite;
import android.test.InstrumentationTestRunner;

public class SimpleTestSuite extends InstrumentationTestRunner
{
	@Override
	public TestSuite getAllTests()
	{
		TestSuite testSuite = new TestSuite("Simple Test Suite");
		
		testSuite.addTestSuite(SearchFormActivityTest.class);
		return testSuite;
	}

}

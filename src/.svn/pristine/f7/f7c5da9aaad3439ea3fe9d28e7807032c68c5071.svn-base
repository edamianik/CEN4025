package week03;

import test.AbstractTestHarness;

/**
 * File: TestHarness.java
 */
class TestHarness extends AbstractTestHarness
{
	public static void main(String[] args)
	{
		new TestHarness().runTests();

	}

	/**
	 * Implements the baseclass abstract method
	 */
	protected void runTests()
	{
		try
		{			
			boolean appTest = executeTest(week03.app.AppTest.class);
			boolean testData = executeTest(week03.data.DataAccessTest.class);
			boolean testJavadoc = executeTest(JUnitJavadocValidation.class);
			boolean result = appTest && testData && testJavadoc;

			trace(result ? "Tests Passed" : "Tests Failed");
		}
		catch(Exception ex)
		{
			trace(ex.getMessage());
		}
	}

}
package week03;

import static org.junit.Assert.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import test.TestResult;
import test.TestResultDetail;
import test.javadoc.ConstructorTestData;
import test.javadoc.FileTestData2;
import test.javadoc.JUnitJavadocValidationUtility2;
import test.javadoc.MethodTestData2;

/**
 * Tests the Javadoc in the source file.
 * 
 * @author Scott
 *
 */
public class JUnitJavadocValidation
{
	public JUnitJavadocValidation()
	{
		m_stream = System.out; // Standard out
		
		trace("########### Initializing JUnitJavadocValidation for week02 ###########");
		
		List<FileTestData2> testFiles = new ArrayList<FileTestData2>();
		List<MethodTestData2> methods = new ArrayList<MethodTestData2>();
		List<ConstructorTestData> constructors = new ArrayList<ConstructorTestData>();
				
		constructors.add(new ConstructorTestData("User", 0, "public"));
		constructors.add(new ConstructorTestData("User", 2, "public"));
		constructors.add(new ConstructorTestData("User",3, "public"));
		
		methods.add(new MethodTestData2("equals", "Object", "boolean","public"));
		methods.add(new MethodTestData2("getFirstName", "", "String","public"));
		methods.add(new MethodTestData2("getLastName", "", "String","public"));
		methods.add(new MethodTestData2("getUserId", "", "long","public"));
		methods.add(new MethodTestData2("setFirstName", "String", "void","public"));
		methods.add(new MethodTestData2("setLastName", "String", "void","public"));
		methods.add(new MethodTestData2("setUserId", "long", "void","public"));
		methods.add(new MethodTestData2("toString", "", "String","public"));
		methods.add(new MethodTestData2("getPin", "", "int","public"));
		methods.add(new MethodTestData2("setPin", "int", "void","public"));
		
		testFiles.add(new FileTestData2("week03.app", "User.java", methods,constructors));		
		
		List<MethodTestData2> methods2 = new ArrayList<MethodTestData2>();
		List<ConstructorTestData> constructors2 = new ArrayList<ConstructorTestData>();
				
		constructors2.add(new ConstructorTestData("DataAccess", 0, "public"));
		constructors2.add(new ConstructorTestData("DataAccess",2, "public"));
		
		methods2.add(new MethodTestData2("connect", "", "void","public"));
		methods2.add(new MethodTestData2("getConnection", "", "Connection","public"));
		methods2.add(new MethodTestData2("getUsers", "", "List<User>","public"));
		methods2.add(new MethodTestData2("saveUser", "User", "void","public"));
		
		testFiles.add(new FileTestData2("week03.data", "DataAccess.java", methods2,constructors2));
		
		m_validator = new JUnitJavadocValidationUtility2("Week03 Javadoc Test",
				testFiles);
		
		m_validator.suppressParserTrace(true);
	}

	@Test
	public void testJavadoc()
	{
		trace("** Validating Javadoc **");

		// Update these values for each assignment
		// m_packageName = "week02";
		TestResult result = m_validator.runTest();
		StringBuilder details = new StringBuilder();
		if(!result.passed())
		{			
			List<TestResultDetail> detailList = result.getTestResultDetails();
			for(TestResultDetail detail : detailList)
			{
				trace(detail.testDetails());
				details.append(detail.testDetails());
				details.append(CRLF);
			}
		}
		trace(String.format("** Test result: %s **", result.passed() ? "Passed" : "Failed"));
		assertTrue(details.toString(), result.passed());
	}
	
	/**
	 * Trace the msg to a PrintStream Provides the method for tests to report
	 * status
	 * 
	 * @param msg
	 */
	private void trace(String msg)
	{
		m_stream.println(msg);
	}
	
	private JUnitJavadocValidationUtility2 m_validator;
	protected PrintStream m_stream;
	private static String CRLF = System.getProperty("line.separator");
}

package test;

import java.io.PrintStream;

import test.javadoc.JUnitJavadocValidationUtility2;

public class AbstractJUnitBase
{
	/**
	 * Trace the msg to a PrintStream Provides the method for tests to report
	 * status
	 * 
	 * @param msg
	 */
	protected void trace(String msg)
	{
		if(m_stream == null)
		{
			System.out.println(msg);
		}
		else
		{
			m_stream.println(msg);
		}
	}

	protected PrintStream m_stream;
	protected JUnitJavadocValidationUtility2 m_validator;
	protected static String CRLF = System.getProperty("line.separator");
}

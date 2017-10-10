package week05.util;

import week05.core.AtmObject;

/**
 *
 * @author Eduardo Damianik
 *
 */

public class LoginResponse extends AtmObject
{
	private long m_sessionId;
	private Boolean m_loggedIn;

	/**
	 * Constructor
	 * 
	 * @param b
	 *            //Boolean
	 * @param l
	 *            //long
	 */
	public LoginResponse(Boolean b, long l)
	{
		m_loggedIn = b;
		m_sessionId = l;
	}

	/**
	 * Get logged in response
	 * 
	 * @return
	 */
	public Boolean getLoggedIn()
	{
		return m_loggedIn;
	}

	/**
	 * get session Id response
	 * 
	 * @return
	 */
	public long getSessionId()
	{
		return m_sessionId;
	}

	/**
	 * equals method
	 * 
	 * @return result //boolean
	 * @param obj
	 *            object
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		if(obj instanceof LoginResponse)
		{
			LoginResponse rhs = (LoginResponse)obj;
			if(this.getLoggedIn() == rhs.getLoggedIn()
					&& this.getSessionId() == rhs.getSessionId())
			{
				result = true;
			}
		}

		return result;
	}

}

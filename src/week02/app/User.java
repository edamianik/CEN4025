package week02.app;

/**
 * User.java file.
 * 
 * @author Eduardo Damianik
 *
 */

public class User
{
	/**
	 * Declare Variables
	 */
	private String m_firstName;
	private String m_lastName;
	private long m_userId;

	/**
	 * Default constructor
	 */
	public User()
	{
	}
	
	/**
	 * Constructor
	 * @param userId //receives user id long
	 * @param firstName //receives first name String
	 * @param lastName //receives last name String
	 */
	public User(long userId, String firstName, String lastName)
	{
		// TODO Auto-generated constructor stub
		m_userId = userId;
		m_firstName = firstName;
		m_lastName = lastName;
	}
	
	/**
	 * equals method
	 * @return boolean
	 * @param o //receives object o
	 */
	public boolean equals(Object o)
	{
		String string = o.toString();
		if(string == getFirstName()) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * get first name
	 * @return String
	 */
	public String getFirstName()
	{
		return m_firstName;
	}
	
	/**
	 * get last name
	 * @return String
	 */
	public String getLastName()
	{
		return m_lastName;
	}
	
	/**
	 * get user id
	 * @return long
	 */
	public long getUserId()
	{
		return m_userId;
	}

	/**
	 * set first name
	 * @param firstName //receives first name String
	 */
	public void setFirstName(String firstName)
	{
		m_firstName = firstName;
		
	}
	
	/**
	 * set last name
	 * @param lastName //receives last name String
	 */
	public void setLastName(String lastName)
	{
		m_lastName = lastName;
		
	}

	/**
	 * set user id
	 * @param userId //receives user id long
	 */
	public void setUserId(long userId)
	{
		m_userId = userId;
	}
	
	/**
	 * to string
	 * @return String
	 */
	public String toString()
	{
		return m_firstName;
	}

}

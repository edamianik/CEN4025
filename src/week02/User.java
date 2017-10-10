package week02;

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
	public void User()
	{
	}
	
	public User(long userId, String firstName, String lastName)
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return boolean
	 * @param o
	 */
	public boolean equals(Object o)
	{
		return true;
	}
	
	/**
	 * @return String
	 */
	public String getFirstName()
	{
		return m_firstName;
	}
	
	/**
	 * @return String
	 */
	public String getLastName()
	{
		return m_lastName;
	}
	
	/**
	 * @return long
	 */
	public long getUserId()
	{
		return m_userId;
	}

	/**
	 * @param firstName
	 */
	public void setFirstName(String firstName)
	{
		m_firstName = firstName;
		
	}
	
	/**
	 * @param lastName
	 */
	public void setLastName(String lastName)
	{
		m_lastName = lastName;
		
	}

	/**
	 * @param userId
	 */
	public void setUserId(long userId)
	{
		m_userId = userId;
	}
	
	/**
	 * @return String
	 */
	public String toString()
	{
		return "string";
	}

}

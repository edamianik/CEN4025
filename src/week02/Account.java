package week02;

public class Account
{
	
	long m_accountId;
	String m_accountName;
	double balance;
	User m_user;
	
	/**
	 * Default constructor
	 */
	public Account()
	{
	}
	
	/**
	 * 
	 * @param l
	 * @param u
	 * @param s
	 * @param d
	 */
	public Account(long l, User u, String s, double d)
	{
		
	}
	
	/**
	 * @return boolean
	 */
	public boolean equals(Object o)
	{
		return true;
	}
	
	/**
	 * @return long
	 */
	public long getAccountId()
	{
		return 21;
	}
	
	/**
	 * @return double
	 */
	public double getBalance()
	{
		return 1.2;
	}
	
	/**
	 * @return String
	 */
	public String getName()
	{
		return "name";
	}
	
	/**
	 * @return String
	 */
	public String getUser()
	{
		return "user";
	}
	
	/**
	 * @return long
	 */
	public long getUserId()
	{
		return 21;
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param newUser
	 */
	public void setUser(User newUser)
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return String
	 */
	public String toString()
	{
		return "string";
	}

}

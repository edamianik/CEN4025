package week02.app;

/**
 * Account.java file.
 * 
 * @author Eduardo Damianik
 *
 */

public class Account
{
	
	private long m_accountId;
	private String m_accountName;
	private double m_balance;
	private User m_user;
	
	/**
	 * Default constructor
	 */
	public Account()
	{
	}
	
	/**
	 * Constructor
	 * @param i //receives account id
	 * @param newUser //receives user User
	 * @param string //receives account name string
	 * @param d //receives balance double
	 */
	public Account(long i, User newUser, String string, double d)
	{
		this.m_accountId = i;
		this.m_accountName = string;
		this.m_balance = d;
		this.m_user = newUser;
	}

	/**
	 * Equals method
	 * @param o //receives objct o
	 * @return boolean
	 */
	public boolean equals(Object o)
	{
		String string = o.toString();
		if(string == getName()) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * get account id
	 * @return long
	 */
	public long getAccountId()
	{
		return m_accountId;
	}
	
	/**
	 * get balance
	 * @return double
	 */
	public double getBalance()
	{
		return m_balance;
	}
	
	/**
	 * get account name
	 * @return String
	 */
	public String getName()
	{
		return m_accountName;
	}
	
	/**
	 * set user
	 * @param u //receives new user parameter
	 */
	public void setUser(User u)
	{
		m_user = u;
	}
	
	/**
	 * get user
	 * @return String
	 */
	public User getUser()
	{
		return m_user;
	}
	
	/**
	 * get user id
	 * @return long
	 */
	public long getUserId()
	{
		return 0;
	}

	/**
	 * set account name
	 * @param string //receives name string
	 */
	public void setName(String string)
	{
		m_accountName = string;
		
	}
	
	/**
	 * to string
	 * @return String
	 */
	public String toString()
	{
		//return "string";
		return m_accountName;
	}

}

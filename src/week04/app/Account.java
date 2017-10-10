package week04.app;

import week04.app.Account;
import week04.app.User;

/**
 * @author Eric Willoughby
 * @since 2017-09-18
 * 
 * */

public class Account {
	
	public long m_accountID;
	public String m_accountName;
	public double m_balance;
	public User m_user;
	
	/**
	   * Default constructor
	   */
	public Account(){
		this(new User(), "Test Account");
	}

	/**
	 * two parameter constructor
	 * 
	 * @param user - user
	 * @param accountName - account name
	 */
	public Account(User user, String accountName){
		this(user, accountName, 0.0);
	}
	
	/**
	 * Three parameter constructor
	 * 
	 * @param user - user
	 * @param accountName - account name
	 * @param balance - balance
	 */
	public Account(User user, String accountName, double balance){
		this(-1, user, accountName, balance);
	}
	
	/**
	   * This is the main constructor method.
	   * @param accountID - account ID
	   * @param user - user
	   * @param accountName - account name
	   * @param balance - the balance
	   * @return none - nothing to see here.
	   */
	public Account(long accountID, User user, String accountName, double balance) {
		m_accountID = accountID;
		m_accountName = accountName;
		m_balance = balance;
		m_user = user;
	}
	
	/**
	   * This is the equals method.
	   * @param obj - Generic Object to compare
	   * @return boolean - if the object in question is equal.
	   */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (m_accountID != other.m_accountID)
			return false;
		if (m_accountName == null) {
			if (other.m_accountName != null)
				return false;
		} else if (!m_accountName.equals(other.m_accountName))
			return false;
		if (Double.doubleToLongBits(m_balance) != Double.doubleToLongBits(other.m_balance))
			return false;
		if (m_user == null) {
			if (other.m_user != null)
				return false;
		} else if (!m_user.equals(other.m_user))
			return false;
		return true;
	}

	/**
	   * This is the getAccountID method.
	   * @param none - nothing to see here.
	   * @return m_accountID - returns the value.
	   */
	public long getAccountId() {
		return m_accountID;
	}

	/**
	   * This is the setAccountID method.
	   * @param accountID - account ID.
	   * @return none - nothing to see here.
	   */
	public void setAccountId(long accountID) {
		m_accountID = accountID;
	}

	/**
	   * This is the getName method.
	   * @param none - nothing to see here.
	   * @return m_accountName - returns the value.
	   */
	public String getName() {
		return m_accountName;
	}

	/**
	   * This is the setName method.
	   * @param accountName - name of the account.
	   * @return none - nothing to see here.
	   */
	public void setName(String accountName) {
		m_accountName = accountName;
	}

	/**
	   * This is the getBalance method.
	   * @param none - nothing to see here.
	   * @return m_balance - returns the value.
	   */
	public double getBalance() {
		return m_balance;
	}

	/**
	   * This is the setBalance method.
	   * @param balance - the balance of the account.
	   * @return none - nothing to see here.
	   */
	public void setBalance(double balance) {
		m_balance = balance;
	}

	/**
	   * This is the getUser method.
	   * @param none - nothing to see here.
	   * @return m_user - returns the value.
	   */
	public User getUser() {
		return m_user;
	}

	/**
	   * This is the setUser method.
	   * @param user - the user object
	   * @return none - nothing to see here.
	   */
	public void setUser(User user) {
		m_user = user;
	}
	/**
	   * This is the getUserId method.
	   * @return m_userId - nothing to see here.
	   */
	public long getUserId(){
		return m_user.getUserId();
	}

	/**
	   * This is the toString method.
	   * @param none - nothing to see here.
	   * @return m_accountID - returns the value.
	   * @return m_accountName - returns the value.
	   * @return m_balance - returns the value.
	   * @return m_user - returns the value.
	   */
	@Override
	public String toString() {
		return "Account [m_accountID=" + m_accountID + ", m_accountName=" + m_accountName + ", m_balance=" + m_balance
				+ ", m_user=" + m_user + "]";
	}
}

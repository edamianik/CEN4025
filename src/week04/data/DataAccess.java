package week04.data;

/**
 * @author Eric Willoughby
 * @since 9/18/17
 * */

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.logging.*;

import week04.util.AtmLogger;
import week04.app.Account;
import week04.app.User;

public class DataAccess {

	private final static Logger logger = Logger.getLogger(AtmLogger.ATM_LOGGER + "." + DataAccess.class.getName());

	private String m_password = DEFAULT_PASS;
	private String m_userName = DEFAULT_USER;
	private Connection m_connect = null;

	private PreparedStatement m_selectAllUsersStatement;
	private PreparedStatement m_selectUserByIdStatement;
	private PreparedStatement m_updateUserByIdStatement;
	private PreparedStatement m_deleteUserByIdStatement;
	private PreparedStatement m_insertUserStatement;
	private PreparedStatement m_lastInsertStatement;

	private PreparedStatement m_selectAllAccountsStatement;
	private PreparedStatement m_selectAccountByIdStatement;
	private PreparedStatement m_updateAccountByIdStatement;
	private PreparedStatement m_insertAccountStatement;

	private static String INSERT_USER_SQL = "INSERT INTO atm.user(pin, first_name, last_name, last_update) values(?, ?, ?, ?)";
	private static String SELECT_USERS_SQL = "SELECT id, pin, first_name, last_name FROM atm.user";
	private static String SELECT_USER_BY_ID_SQL = "SELECT id, pin, first_name, last_name FROM atm.user WHERE id=?";
	private static String DELETE_USER_BY_ID_SQL = "DELETE FROM atm.user WHERE id=?";
	private static String UPDATE_USER_BY_ID_SQL = "UPDATE atm.user SET first_name=?, last_name=?, last_update=? WHERE id=?";
	private static String LAST_INSERT_ID = "SELECT LAST_INSERT_ID();";

	/**********************************************************************************/
	private static String SELECT_ALL_ACCOUNTS_SQL = "SELECT id, user_id, name, balance FROM atm.account";
	private static String SELECT_ACCOUNTS_BY_ID_SQL = "SELECT id, user_id, name, balance FROM atm.account WHERE id=?";
	private static String INSERT_ACCOUNT_SQL = "INSERT INTO  atm.account (user_id,name,balance,last_update) values (?, ?, ?, ?)";
	private static String UPDATE_ACCOUNT_SQL = "UPDATE atm.account SET user_id=?, name=?, balance=?,last_update=? WHERE  id=?";
	/**********************************************************************************/

	private static DataAccess m_dataAccess;
	private static String DEFAULT_PASS = "root";
	private static String DEFAULT_USER = "root";
	private static String CONNECTION_STRING = "jdbc:mysql://localhost/atm?user=%s&password=%s&useSSL=false";

	/**
	 * default public Constructor
	 */
	public DataAccess() {

	}

	/**
	 * Main constructor
	 * 
	 * @param userName
	 *            the name of user
	 * @param password
	 *            the password of user
	 */
	public DataAccess(String userName, String password) throws AtmDataException {
		m_password = password;
		m_userName = userName;

		AtmLogger.addAtmLoggerHandlers(logger);

		connect();

		logger.info("Successfully connected to the database " + m_connect.toString());
	}

	/**
	 * 
	 * @param user
	 *            the user object
	 * @param update
	 *            time update
	 * @return user
	 */
	private User updateUser(User user, java.sql.Date updateDate) throws AtmDataException {

		try {
			m_updateUserByIdStatement.setString(1, user.getFirstName());
			m_updateUserByIdStatement.setString(2, user.getLastName());
			m_updateUserByIdStatement.setDate(3, updateDate);
			m_updateUserByIdStatement.setLong(4, user.getUserId());
			m_updateUserByIdStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AtmDataException(e);
		}

		return user;
	}

	/**
	 * 
	 * @param user
	 *            the user object
	 * @param update
	 *            time update
	 * @return newUser
	 */
	private User insertUser(User user, java.sql.Date updateDate) throws AtmDataException {

		User newUser = null;

		try {
			m_insertUserStatement.setInt(1, user.getPin());
			m_insertUserStatement.setString(2, user.getFirstName());
			m_insertUserStatement.setString(3, user.getLastName());
			m_insertUserStatement.setDate(4, updateDate);

			int rowsInserted = m_insertUserStatement.executeUpdate();

			if (rowsInserted > 0) {
				ResultSet rs = m_lastInsertStatement.executeQuery();
				if (rs.next()) {
					int newId = rs.getInt(1);
					newUser = getUserById(newId);
					if (newUser == null) {
						String msg = String.format("Failed to find new inserted user %s %s...", user.getFirstName(),
								user.getLastName());
						throw new AtmDataException(msg);
					}
				}
			} else {
				String msg = String.format("Inserted user %s %s has failed...", user.getFirstName(),
						user.getLastName());
				throw new AtmDataException(msg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AtmDataException(e);
		}

		return newUser;
	}

	/**
	 * closes connection with database
	 */
	public void close() {

		try {
			m_connect.close();
		} catch (SQLException e) {
			trace(e.getMessage());
		}
	}

	/**
	 * connects to database
	 * 
	 * @throws AtmDataException
	 */
	public void connect() throws AtmDataException {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String connectionString = String.format(CONNECTION_STRING, m_userName, m_password);

			m_connect = DriverManager.getConnection(connectionString);

			m_insertUserStatement = m_connect.prepareStatement(INSERT_USER_SQL);
			m_selectAllUsersStatement = m_connect.prepareStatement(SELECT_USERS_SQL);
			m_selectUserByIdStatement = m_connect.prepareStatement(SELECT_USER_BY_ID_SQL);
			m_deleteUserByIdStatement = m_connect.prepareStatement(DELETE_USER_BY_ID_SQL);
			m_updateUserByIdStatement = m_connect.prepareStatement(UPDATE_USER_BY_ID_SQL);
			m_lastInsertStatement = m_connect.prepareStatement(LAST_INSERT_ID);
			
			m_insertAccountStatement = m_connect.prepareStatement(INSERT_ACCOUNT_SQL);
			m_selectAllAccountsStatement = m_connect.prepareStatement(SELECT_ALL_ACCOUNTS_SQL);
			m_selectAccountByIdStatement = m_connect.prepareStatement(SELECT_ACCOUNTS_BY_ID_SQL);
			m_updateAccountByIdStatement = m_connect.prepareStatement(UPDATE_ACCOUNT_SQL);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new AtmDataException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AtmDataException(e);
		}

	}

	/**
	 * deletes a user
	 * 
	 * @param id
	 *            id of a user
	 * @return result
	 * 
	 * @throws AtmDataException
	 */
	public boolean deleteUserById(long id) throws AtmDataException {

		boolean result = false;

		try {
			m_deleteUserByIdStatement.setLong(1, id);
			int count = m_deleteUserByIdStatement.executeUpdate();

			result = count == 1 ? true : false;
			trace("delete user row count " + count);

			if (!result) {

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AtmDataException(e);
		}

		return result;
	}

	/**
	 * gets the connection
	 * 
	 * @return m_connect the connection value
	 */
	public Connection getConnection() {
		return m_connect;
	}

	/**
	 * Singleton pattern implementation
	 * 
	 * @return DataAccess instance #throws AtmExeption on error
	 */
	public static DataAccess getInstance() throws AtmDataException {
		return DataAccess.getInstance("root", "root");
	}

	/**
	 * @param username
	 *            the user name
	 * @param password
	 *            the password
	 * @return m_dataAccess the new dataAccess object
	 */
	public static DataAccess getInstance(String username, String password) throws AtmDataException {
		if (m_dataAccess == null) {
			m_dataAccess = new DataAccess(username, password);
		}

		return m_dataAccess;
	}

	/**
	 * gets the user by ID
	 * 
	 * @param id
	 *            the user's id
	 * @throws AtmDataException
	 * 
	 * @return user
	 */
	public User getUserById(long id) throws AtmDataException {

		User user = null;
		ResultSet resultset = null;

		try {
			m_selectUserByIdStatement.setLong(1, id);
			resultset = m_selectUserByIdStatement.executeQuery();

			if (resultset.next()) {
				long userId = resultset.getLong("id");
				String first = resultset.getString("first_name");
				String last = resultset.getString("last_name");
				user = new User(userId, first, last);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AtmDataException(e);
		}

		return user;
	}

	/**
	 * @throws AtmDataException
	 * @return userList
	 */
	public List<User> getUsers() throws AtmDataException {
		List<User> userList = new ArrayList<User>();
		ResultSet resultset = null;

		try {
			resultset = m_selectAllUsersStatement.executeQuery();

			while (resultset.next()) {
				long userId = resultset.getLong("id");
				String first = resultset.getString("first_name");
				String last = resultset.getString("last_name");
				userList.add(new User(userId, first, last));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AtmDataException(e);
		}
		return userList;
	}

	/**
	 * Saves the user
	 * 
	 * @param user
	 *            the user object
	 * @throws AtmDataException
	 * @return updateUser
	 */
	public void saveUser(User user) throws AtmDataException {
		Calendar now = Calendar.getInstance();

		Date updateDate = new java.sql.Date(now.getTime().getTime());

		if (user.getUserId() == -1) {
			insertUser(user, updateDate);
		} else {
			updateUser(user, updateDate);
		}

	}

	/**
	 * makes it easier to do a System.out.println() command
	 */
	private void trace(String msg) {
		System.out.println(msg);
	}

	/**
	 * saves the account for the user
	 * 
	 * @param account
	 *            the user object
	 */
	public void saveAccount(Account account) throws AtmDataException {
		Calendar now = Calendar.getInstance();

		Date updateDate = new java.sql.Date(now.getTime().getTime());

		if (account.getAccountId() == -1) {
			insertAccount(account, updateDate);
		} else {
			updateAccount(account, updateDate);
		}
	}

	/**
	 * Gets Account on ID
	 * 
	 * @param id
	 *            gets the id of the account
	 * @return result result is the account being passed back
	 */
	public Account getAccount(int id) throws AtmDataException {
		Account account = null;
		ResultSet resultset = null;

		try {
			m_selectAccountByIdStatement.setLong(1, id);
			resultset = m_selectAccountByIdStatement.executeQuery();

			if (resultset.next()) {
				long accountId = resultset.getLong("id");
				User user = getUserById(resultset.getLong("user_id"));
				String name = resultset.getString("name");
				double balance = resultset.getDouble("balance");
				account = new Account(accountId, user, name, balance);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AtmDataException(e);
		}

		return account;
	}

	/**
	 * gets all the accounts
	 * 
	 * @return result result is the list of all accounts
	 */
	public List<Account> getAllAccounts() throws AtmDataException {
		List<Account> accountList = new ArrayList<Account>();
		ResultSet resultset = null;

		try {
			resultset = m_selectAllAccountsStatement.executeQuery();

			while (resultset.next()) {
				long accountId = resultset.getLong("id");
				User user = getUserById(resultset.getLong("user_id"));
				String name = resultset.getString("name");
				double balance = resultset.getDouble("balance");
				accountList.add(new Account(accountId, user, name, balance));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AtmDataException(e);
		}
		return accountList;
	}

	/**
	 * 
	 * @param user
	 *            the user object
	 * @param update
	 *            time update
	 * @return newUser
	 */
	private Account insertAccount(Account account, java.sql.Date updateDate) throws AtmDataException {

		Account newAccount = null;

		try {
			m_insertAccountStatement.setLong(1, account.getUserId());
			m_insertAccountStatement.setString(2, account.getName());
			m_insertAccountStatement.setDouble(3, account.getBalance());
			m_insertAccountStatement.setDate(4, updateDate);

			int rowsInserted = m_insertAccountStatement.executeUpdate();

			if (rowsInserted > 0) {
				ResultSet rs = m_lastInsertStatement.executeQuery();
				if (rs.next()) {
					int newId = rs.getInt(1);
					newAccount = getAccount(newId);
					if (newAccount == null) {
						String msg = String.format("Failed to find new inserted account %s with id: %s...",
								account.getName(), account.getAccountId());
						throw new AtmDataException(msg);
					}
				}
			} else {
				String msg = String.format("Inserted account %s with id: %s has failed...", account.getName(),
						account.getAccountId());
				throw new AtmDataException(msg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AtmDataException(e);
		}

		return newAccount;
	}
	
	private void updateAccount(Account account, java.sql.Date updateDate) throws AtmDataException {

		try {
			m_updateAccountByIdStatement.setLong(1, account.getUserId());
			m_updateAccountByIdStatement.setString(2, account.getName());
			m_updateAccountByIdStatement.setDouble(3, account.getBalance());
			m_updateAccountByIdStatement.setDate(4, updateDate);
			m_updateAccountByIdStatement.setLong(5, account.getAccountId());
			m_updateAccountByIdStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AtmDataException(e);
		}

	}
}

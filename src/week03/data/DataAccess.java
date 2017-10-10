package week03.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import week03.app.User;
//import com.mysql.jdbc.connection;

/**
 * DataAccess Java file
 * 
 * @author Eduardo Damianik
 *
 */
public class DataAccess
{

	/**
	 * Constructor
	 * 
	 * @param username //username
	 * @param password //password
	 */
	public DataAccess(String username, String password)
	{
		m_userName = username;
		m_password = password;
	}

	/**
	 * Public data access
	 */
	public DataAccess()
	{

	}

	/**
	 * Constructor
	 * 
	 * @return
	 * @throws AtmDataException
	 */
	public static DataAccess getInstance() throws AtmDataException
	{
		return getInstance(DEFAULT_USER, DEFAULT_PASS);
	}

	/**
	 * Constructor
	 * 
	 * @param username
	 *            //username
	 * @param password
	 *            //password
	 * @return
	 * @throws AtmDataException
	 */
	public static synchronized DataAccess getInstance(String username,
			String password) throws AtmDataException
	{
		if(m_dataAccess == null)
		{
			m_dataAccess = new DataAccess(username, password);
		}
		return m_dataAccess;
	}

	/**
	 * Save user method
	 * 
	 * @param user
	 *            //user information
	 * @return
	 * @throws AtmDataException
	 */
	public User saveUser(User user) throws AtmDataException
	{
		Calendar now = Calendar.getInstance();

		User updatedUser = null;
		Date updateDate = new java.sql.Date(now.getTime().getTime());

		if(user.getUserId() == -1)
		{
			updatedUser = insertUser(user, updateDate);
		}
		else
		{
			updatedUser = updatetUser(user, updateDate);
		}
		return updatedUser;
	}

	/**
	 * Get user method
	 * 
	 * @return
	 * @throws AtmDataException
	 */
	public List<User> getUsers() throws AtmDataException
	{
		List<User> userList = new ArrayList<User>();
		ResultSet resultSet = null;

		try
		{
			resultSet = m_selectAllUsersStatement.executeQuery();

			while(resultSet.next())
			{
				long userId = resultSet.getLong("id");
				String first = resultSet.getString("first_name");
				String last = resultSet.getString("last_name");

				userList.add(new User(userId, first, last));
			}
		}
		catch(SQLException ex)
		{

			ex.printStackTrace();
			throw new AtmDataException(ex);
		}
		return userList;

	}

	/**
	 * Connect method
	 * 
	 * @throws AtmDataException
	 */
	public void connect() throws AtmDataException
	{
		try
		{

			Class.forName("com.mysql.jdbc.Driver");

			String connectionString = String.format(CONN_FMT, m_userName,
					m_password);

			m_connect = DriverManager.getConnection(connectionString);
			m_insertUserStatement = m_connect.prepareStatement(INSERT_USER_SQL);
			m_selectAllUsersStatement = m_connect
					.prepareStatement(SELECT_ALL_USERS_SQL);
			m_selectUserByIdStatement = m_connect
					.prepareStatement(SELECT_USER_BY_ID_SQL);
			m_deleteUserByIdStatement = m_connect
					.prepareStatement(DELETE_USER_BY_ID_SQL);
			m_updateUserByIdStatement = m_connect
					.prepareStatement(UPDATE_USER_BY_ID_SQL);
			m_lastInsertStatement = m_connect.prepareStatement(LAST_INSERT_ID);

		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			throw new AtmDataException(ex);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new AtmDataException(ex);
		}

	}

	/**
	 * Get user by ID
	 * 
	 * @param id
	 *            //id
	 * @return
	 * @throws AtmDataException
	 */
	public User getUserById(long id) throws AtmDataException
	{
		User user = null;
		ResultSet resultSet = null;

		try
		{
			m_selectUserByIdStatement.setLong(1, id);
			resultSet = m_selectUserByIdStatement.executeQuery();

			if(resultSet.next())
			{
				long userId = resultSet.getLong("id");
				String first = resultSet.getString("first_name");
				String last = resultSet.getString("last_name");

				user = new User(userId, first, last);
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			throw new AtmDataException(ex);
		}
		return user;
	}

	/**
	 * Delete user by ID
	 * 
	 * @param id
	 *            //id
	 * @return
	 * @throws AtmDataException
	 */
	public boolean deleteUserById(long id) throws AtmDataException
	{
		boolean result = false;

		try
		{
			m_deleteUserByIdStatement.setLong(1, id);
			int count = m_deleteUserByIdStatement.executeUpdate();

			result = count == 1 ? true : false;
			trace("delete user row " + count);

			if(!result)
			{

			}
		}
		catch(SQLException ex)
		{

			ex.printStackTrace();
			throw new AtmDataException(ex);
		}

		return result;
	}

	/**
	 * Connection method
	 * 
	 * @return
	 */
	public Connection getConnection()
	{
		return m_connect;
	}

	public void close()
	{
		try
		{
			m_connect.close();
		}
		catch(SQLException ex)
		{
			trace(ex.getMessage());
		}
	}

	/**
	 * Insert User method
	 * 
	 * @param user
	 *            //user
	 * @param updateDate
	 *            //date
	 * @return
	 * @throws AtmDataException
	 */
	private User insertUser(User user, java.sql.Date updateDate)
			throws AtmDataException
	{
		User newUser = null;

		try
		{
			m_insertUserStatement.setInt(1, 1234);
			m_insertUserStatement.setString(2, user.getFirstName());
			m_insertUserStatement.setString(3, user.getLastName());
			m_insertUserStatement.setDate(4, updateDate);

			int rowsInserted = m_insertUserStatement.executeUpdate();

			if(rowsInserted > 0)
			{
				ResultSet rs = m_lastInsertStatement.executeQuery();
				if(rs.next())
				{
					int newId = rs.getInt(1);
					newUser = getUserById(newId);

					if(newUser == null)
					{
						String msg = String.format(
								"Failed to find new inserted user %s %s",
								user.getFirstName(), user.getLastName());

						throw new AtmDataException(msg);
					}
				}
			}
			else
			{
				String msg = String.format("Insert user %s %s Failed",
						user.getFirstName(), user.getLastName());

				throw new AtmDataException(msg);
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			throw new AtmDataException(ex);
		}
		return newUser;

	}

	/**
	 * Update User method
	 * 
	 * @param user
	 *            //user
	 * @param updateDate
	 *            //date
	 * @return
	 * @throws AtmDataException
	 */
	private User updatetUser(User user, java.sql.Date updateDate)
			throws AtmDataException
	{
		try
		{
			m_updateUserByIdStatement.setString(1, user.getFirstName());
			m_updateUserByIdStatement.setString(2, user.getLastName());
			m_updateUserByIdStatement.setDate(3, updateDate);
			m_updateUserByIdStatement.setLong(4, user.getUserId());
			m_updateUserByIdStatement.executeUpdate();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			throw new AtmDataException(ex);
		}

		return user;
	}

	/**
	 * Trace method
	 * 
	 * @param msg
	 *            //string message
	 */
	private void trace(String msg)
	{
		System.out.println(msg);
	}

	private Connection m_connect = null;
	private String m_userName = DEFAULT_USER;
	private String m_password = DEFAULT_PASS;

	private PreparedStatement m_insertUserStatement;
	private PreparedStatement m_selectAllUsersStatement;
	private PreparedStatement m_selectUserByIdStatement;
	private PreparedStatement m_deleteUserByIdStatement;
	private PreparedStatement m_updateUserByIdStatement;
	private PreparedStatement m_lastInsertStatement;

	private static String INSERT_USER_SQL = "insert into atm.user(pin,first_name,last_name,last_update) values (?,?,?,?)";
	private static String SELECT_ALL_USERS_SQL = "SELECT id, first_name, last_name from atm.user";
	private static String SELECT_USER_BY_ID_SQL = "SELECT id, first_name, last_name from atm.user where id=?";
	private static String DELETE_USER_BY_ID_SQL = "DELETE FROM atm.user where id=?";
	private static String UPDATE_USER_BY_ID_SQL = "UPDATE atm.user SET first_name=?, last_name=?, last_update=? WHERE id=?";
	private static String LAST_INSERT_ID = "SELECT LAST_INSERT_ID();";

	private static DataAccess m_dataAccess;
	private static String DEFAULT_USER = "root";
	private static String DEFAULT_PASS = "root";
	private static String CONN_FMT = "jdbc:mysql://localhost/atm?user=%s&password=%s";
}
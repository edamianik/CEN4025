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

public class DataAccessRef
{
	public DataAccessRef()
	{
		
	}
	
	/**
	 * Adds a user to the data store
	 * 
	 * @param user User object to save
	 * @throws AtmDataException on error
	 */
	public void saveUser(User user)
		throws AtmDataException
	{
		Calendar now = Calendar.getInstance();   // Gets the current date and time.
		Date updateDate = new java.sql.Date(now.getTime().getTime());
		
		try
		{
			m_saveUserStatement.setLong(1, user.getUserId());
			m_saveUserStatement.setString(2, user.getFirstName());
			m_saveUserStatement.setString(3, user.getLastName());
			m_saveUserStatement.setDate(4, updateDate);
			m_saveUserStatement.executeUpdate();
		}
		catch(SQLException ex)
		{
			// log error
			throw new AtmDataException(ex);
		}		
	}
	
	public List<User> getUsers()
			throws AtmDataException
	{
		List<User> usersList = new ArrayList<User>();
		ResultSet resultSet = null;
		try
		{
			resultSet = m_selectUserStatement.executeQuery();
			while(resultSet.next())
			{
				long userId = resultSet.getLong("id");
				String first = resultSet.getString("first_name");
				String last =  resultSet.getString("last_name");
				usersList.add(new User(userId, first, last));
			}
		}
		catch(SQLException ex)
		{
			// log error
			throw new AtmDataException(ex);
		}		
		
		return usersList;
	}

	/**
	 * Establishes the connection to the database
	 * 
	 * @throws AtmDataException on error
	 */
	public void connect()
		throws AtmDataException
	{
		try
		{
		  // this will load the MySQL driver, each DB has its own driver
		  Class.forName("com.mysql.jdbc.Driver");
		  
		  // setup the connection with the DB.
		  m_connect = DriverManager
			      .getConnection("jdbc:mysql://localhost/atm?"
			    		  + "user=root&password=mainroot");
		  // 
		  // precompile prepared statements
		  //
		  m_saveUserStatement = m_connect.prepareStatement(INSERT_USER_SQL);
		  m_selectUserStatement = m_connect.prepareStatement(SELECT_USER_SQL);
		}
		catch(SQLException ex)
		{
			// log exception
			System.out.println(ex.getMessage());
			throw new AtmDataException (ex);
		}
		catch(Exception ex)
		{
			// log exception
			System.out.println(ex.getMessage());
			throw new AtmDataException (ex);
		}
	}
	
	public Connection getConnection()
	{
		return m_connect;
	}
	
	private Connection m_connect = null;
	private PreparedStatement m_saveUserStatement;
	private PreparedStatement m_selectUserStatement;
	
	private String INSERT_USER_SQL = "insert into  atm.user values (?, ?, ?, ?)";
	private String SELECT_USER_SQL = "SELECT id, first_name, last_name from atm.user";
}

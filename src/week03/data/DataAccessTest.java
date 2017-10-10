package week03.data;

import static org.junit.Assert.*;

import org.junit.Test;
import test.AbstractJUnitBase;

import java.sql.Connection;
import java.util.List;

import week03.app.User;

public class DataAccessTest extends AbstractJUnitBase
{
	@Test
	public void testDBAccess()
	{
		try
		{
			DataAccess da = new DataAccess("root","root");
			da.connect();

			Connection conn = da.getConnection();
			conn.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			fail(ex.getMessage());			
		}
	}
	
	@Test
	public void testAddUser()
	{
		try
		{
			trace(" -- Test adding user ...");
			trace("  -- connecting to data access");
			DataAccess da = new DataAccess("root","root");
			da.connect();

			Connection conn = da.getConnection();

			trace("  -- connected to database: " + conn.toString());
						
			User user = new User("FirstTest", "LastTest");
			da.saveUser(user);

			trace("  -- User saved: " + user.toString());

			List<User> userList = da.getUsers();

			if(userList.size() > 0)
			{
				String msg = String.format("  -- successful save: Count=%d",
						userList.size());
				trace(msg);
				dumpList(userList);
			}
			else
			{
				fail(" failed to save user");
			}
		}
		catch(AtmDataException ex)
		{
			trace(" -- error: " + ex.getMessage());
			fail(ex.getMessage());
		}
	}

	private void dumpList(List<User> list)
	{
		for(User user : list)
		{
			trace(user.toString());
		}
	}
}

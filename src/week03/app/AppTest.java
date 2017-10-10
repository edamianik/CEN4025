package week03.app;


import static org.junit.Assert.*;

import org.junit.Test;
import test.AbstractJUnitBase;

public class AppTest extends AbstractJUnitBase
{	
	@Test
	public void testUserClassEquals()
	{
		trace("Testing User class and equals");
		boolean equalTest = true;
		
		// simple user creation
		User user = new User();						
		trace(user.toString());
		
		User user2 = new User();						
		trace(user2.toString());
		
		// expect to be same
		if( !user.equals(user2))
		{
			equalTest = false;
		}
		
		user2.setFirstName("Jim");
		user2.setLastName("Bo");
		user2.setUserId(1);					
		trace(user2.toString());

		// expect to be different
		if( user.equals(user2))
		{
			equalTest = false;
		}
		
		User user3 = new User(user2.getUserId(), user2.getFirstName(), user2.getLastName());		
		trace(user3.toString());
		
		// expect to be same
		if( !user3.equals(user2))
		{
			equalTest = false;
		}
		
		if(!equalTest)
		{
			fail("User.equals test failed");
		}
	}
}

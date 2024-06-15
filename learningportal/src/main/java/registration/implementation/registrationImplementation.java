package registration.implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


import registration.model.registration;

public class registrationImplementation {
	
	public int insertData(List<registration> lst)
	{
		int i=0;
		 Connection con = null;
		 try {
			 Class.forName("org.postgresql.Driver");  
			 con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Pass@2803");
			 registration robj = lst.get(0);
			 PreparedStatement pst = con.prepareStatement("insert into registration (name,email,password) values(?,?,?)");
			 pst.setString(1,robj.getName());
			 pst.setString(2, robj.getEmail());
			 pst.setString(3, robj.getPassword());
			 i = pst.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return i;
		
	}

}

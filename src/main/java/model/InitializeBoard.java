package model;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class InitializeBoard implements DBAccess {

	@Override
	public void execute(HttpServletRequest request) throws SQLException {
		DBct dbct = null;
		int r = 0;
		
		try {
			System.out.println("now its initialize board");
			dbct = new DBct();
			r = dbct.initialize();
			if (r > 0) System.out.println("delete success");
			else System.out.println("delete fail");
			
		}finally {
			dbct.close();
		}
	}

}

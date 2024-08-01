package model;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class DoGetFullBoard implements DBAccess{
	@Override
	public void execute(HttpServletRequest request) throws SQLException {
		ArrayList<ArrayList<Integer>> result = getFullBoard(request);
		if (result != null) request.setAttribute("board", result);
	}
}

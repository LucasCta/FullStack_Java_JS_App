package model;

import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLException;

public class AcessoBanco {

	public static Connection conectar() throws SQLException {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Error in driver");
		}
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/captchaDatabase", "lucas", "123@Teste");
		return con;
	}

	public static boolean desconectar(Connection con, PreparedStatement ps, ResultSet rs)
			throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
		if (con != null) {
			con.close();
		}
		return true;
	}

}

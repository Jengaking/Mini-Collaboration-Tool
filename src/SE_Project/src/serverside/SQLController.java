package serverside;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import basesesign.Notification;
import basesesign.Vote;

public class SQLController {
	private Connection conn = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public SQLController() {
		try {
			// 1. 드라이버 로딩
			// 드라이버 인터페이스를 구현한 클래스를 로딩
			// mysql, oracle 등 각 벤더사 마다 클래스 이름이 다르다.
			// mysql은 "com.mysql.jdbc.Driver"이며, 이는 외우는 것이 아니라 구글링하면 된다.
			// 참고로 이전에 연동했던 jar 파일을 보면 com.mysql.jdbc 패키지에 Driver 라는 클래스가 있다.
			Class.forName("com.mysql.jdbc.Driver");
			// 2. 연결하기
			// 드라이버 매니저에게 Connection 객체를 달라고 요청한다.
			// Connection을 얻기 위해 필요한 url 역시, 벤더사마다 다르다.
			// mysql은 "jdbc:mysql://localhost/사용할db이름" 이다.
			String url = "jdbc:mysql://localhost/se_project_database";

			// @param getConnection(url, userName, password);
			// @return Connection
			conn = DriverManager.getConnection(url, "root", "sql_818LWJ@persnal_#DEU");
			System.out.println("연결 성공");
			statement = conn.createStatement();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 사용할 sql을 미리 정해두고 전달하자.
	// 미리 만들어진 SQL 문자열을 전달하자.
	// 해당 SQL은 ServerController에 정의하자.

	// Select 는 접속시에만 사용하기로 한다.
	public Vector<Object> doSelect(String sql, String cate) {
		System.out.println(sql);
		Vector<Object> vector = new Vector<Object>();
		
		try {
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				switch(cate) {
				case "vote":
					//int vote_number, int worknote_number, String title, String date, int assent_number, int opposite_number, String owner,
					//boolean activation, boolean voted
					Vote v = new Vote(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(4), resultSet.getDate(3).toString(), resultSet.getInt(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getBoolean(8));
					vector.add(v);
					break;
				case "notification":
					//int notification_number, int worknote_number, String date, String contents, String owner
					Notification n = new Notification(resultSet.getInt(1), resultSet.getInt(2), resultSet.getDate(3).toString(), resultSet.getString(4), resultSet.getString(5));
					vector.add(n);
					break;
				}
			}
			return vector;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean doInsert(String sql) {
		try {
			statement.executeUpdate(sql);
			System.out.println("server: do Insert finished");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean doDelete(String sql) {
		try {
			statement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean doUpdate(String sql) {
		try {
			statement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}

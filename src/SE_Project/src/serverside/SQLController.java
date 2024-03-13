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
			// 1. ����̹� �ε�
			// ����̹� �������̽��� ������ Ŭ������ �ε�
			// mysql, oracle �� �� ������ ���� Ŭ���� �̸��� �ٸ���.
			// mysql�� "com.mysql.jdbc.Driver"�̸�, �̴� �ܿ�� ���� �ƴ϶� ���۸��ϸ� �ȴ�.
			// ����� ������ �����ߴ� jar ������ ���� com.mysql.jdbc ��Ű���� Driver ��� Ŭ������ �ִ�.
			Class.forName("com.mysql.jdbc.Driver");
			// 2. �����ϱ�
			// ����̹� �Ŵ������� Connection ��ü�� �޶�� ��û�Ѵ�.
			// Connection�� ��� ���� �ʿ��� url ����, �����縶�� �ٸ���.
			// mysql�� "jdbc:mysql://localhost/�����db�̸�" �̴�.
			String url = "jdbc:mysql://localhost/se_project_database";

			// @param getConnection(url, userName, password);
			// @return Connection
			conn = DriverManager.getConnection(url, "root", "sql_818LWJ@persnal_#DEU");
			System.out.println("���� ����");
			statement = conn.createStatement();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// ����� sql�� �̸� ���صΰ� ��������.
	// �̸� ������� SQL ���ڿ��� ��������.
	// �ش� SQL�� ServerController�� ��������.

	// Select �� ���ӽÿ��� ����ϱ�� �Ѵ�.
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

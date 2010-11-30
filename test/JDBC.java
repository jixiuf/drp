import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC {

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, Exception {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/drp", "root", "root");
		Statement stat = conn.createStatement();
		ResultSet rest = stat.executeQuery("select * from CLIENT");
		while (rest.next()) {
			System.out.println(rest.getObject(1) + ", " + rest.getObject(2)
					+ ", " + rest.getObject(3) + ", " + rest.getObject(4) 
				+" ,"+	rest.getObject(5)
					+" ,"+	rest.getObject(6)
						+" ,"+	rest.getObject(7)
					
			
			);
		}
		String sql ="insert into  t1 values ( 12,'中')";
	 stat.execute(sql);
		rest.close();
		stat.close();
		conn.close();

	}
}

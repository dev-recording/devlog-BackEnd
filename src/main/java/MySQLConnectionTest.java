import java.sql.*;

public class MySQLConnectionTest {

    // MySQL 서버 정보
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/study_db?useSSL=false&serverTimezone=Asia/Seoul";
    private static final String USER = "ysi";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {
        // JDBC 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // 데이터베이스 연결
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            System.out.println("Connected to the database");

            // 쿼리 실행
            try (Statement statement = connection.createStatement()) {
                String sql = "SELECT * FROM user";
                ResultSet resultSet = statement.executeQuery(sql);

                // 결과 출력
                while (resultSet.next()) {
                    // 결과 처리 예시 (실제로는 결과를 어떻게 처리할지에 따라 다름)
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    System.out.println("ID: " + id + ", Name: " + name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
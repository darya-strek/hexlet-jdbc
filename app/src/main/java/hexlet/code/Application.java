package hexlet.code;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {

        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            try (var statement = conn.createStatement()) {
                statement.execute(sql);
            }

            var sql2 = "INSERT INTO users (username, phone) VALUES (?, ?)";
            try (var statement2 = conn.prepareStatement(sql2)) {
                statement2.setString(1, "Sarah");
                statement2.setString(2, "931-999-99-99");
                statement2.executeUpdate();

                statement2.setString(1, "Piter");
                statement2.setString(2, "931-999-88-88");
                statement2.executeUpdate();

                statement2.setString(1, "Morgan");
                statement2.setString(2, "931-999-77-77");
                statement2.executeUpdate();
            }

            var sql3 = "DELETE FROM users WHERE username = ?";
            try (var statement3 = conn.prepareStatement(sql3)) {
                statement3.setString(1, "Morgan");
                statement3.execute();
            }

            var sql4 = "SELECT * FROM users";
            try (var statement4 = conn.createStatement()) {
                var resultSet = statement4.executeQuery(sql4);
                while (resultSet.next()) {
                    System.out.printf("%s %s\n",
                                        resultSet.getString("username"),
                                        resultSet.getString("phone"));
                }
            }
        }
    }
}

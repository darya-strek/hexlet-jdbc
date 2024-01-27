package hexlet.code;

import java.sql.DriverManager;
import java.sql.SQLException;

import hexlet.code.UserDAO;

public class Application {
    public static void main(String[] args) throws SQLException {

        try (var connection = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            try (var statement = connection.createStatement()) {
                statement.execute(sql);
            }

            var dao = new UserDAO(connection);

            var user1 = new User("Sarah", "931-999-99-99");
            var user2 = new User("Piter", "931-999-88-88");
            var user3 = new User("Morgan","931-999-77-77");

            System.out.println(user1.getId());
            dao.save(user1);
            System.out.println(user1.getId());

            dao.save(user2);
            dao.save(user3);
            dao.save(user1);

            dao.print();

            System.out.println(dao.find(user1.getId()).get());
            System.out.println(dao.find(user2.getId()).get());
            System.out.println(dao.find(user3.getId()).get());

            var user4 = dao.find(user1.getId()).get();

            System.out.println(user4);
            System.out.println(user1.getId() == user4.getId());

            dao.delete(user2.getId());

            dao.print();
        }
    }
}

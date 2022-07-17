package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getSQLConnection();
    public UserDaoJDBCImpl() throws SQLException {

    }
    public void createUsersTable() throws SQLException {
        String createTableSQL = "create table if not exists USERS " +
                "(id bigint not null AUTO_INCREMENT, name varchar(30) not null, " +
                "lastName varchar(50) not null, age tinyint, primary key(id))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
    public void dropUsersTable() throws SQLException {
        String dropTable = "drop table if exists USERS";
        try (PreparedStatement preparedStatement = connection.prepareStatement(dropTable)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String insertPerson = "insert into USERS(name, lastName, age) values (?, ?, ?)";
        try (PreparedStatement prep = connection.prepareStatement(insertPerson)) {
            prep.setString(1, name);
            prep.setString(2, lastName);
            prep.setByte(3, age);
            prep.executeUpdate();
            System.out.println("User " + " " + "'" + name + "'" + "added into data base");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
    public void removeUserById(long id) throws SQLException {
        String deletePerson = "delete from USERS where id = " + id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(deletePerson)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
    public List<User> getAllUsers() {
        String selectAll = "select * from USERS";
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAll);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                userList.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public void cleanUsersTable() throws SQLException {
        String clear = "truncate table USERS";
        try (PreparedStatement preparedStatement = connection.prepareStatement(clear)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
}
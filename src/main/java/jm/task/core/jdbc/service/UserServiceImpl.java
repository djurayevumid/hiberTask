package jm.task.core.jdbc.service;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import java.sql.SQLException;
import java.util.List;
public class UserServiceImpl implements UserService {
    UserDaoJDBCImpl userJDBC = new UserDaoJDBCImpl();
    public UserServiceImpl() throws SQLException{

    }
    public void createUsersTable() throws SQLException {
        userJDBC.createUsersTable();
    }
    public void dropUsersTable() throws SQLException {
        userJDBC.dropUsersTable();
    }
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        userJDBC.saveUser(name, lastName, age);
    }
    public void removeUserById(long id) throws SQLException {
        userJDBC.removeUserById(id);
    }
    public List<User> getAllUsers() {
        return userJDBC.getAllUsers();
    }
    public void cleanUsersTable() throws SQLException {
        userJDBC.cleanUsersTable();
    }
}

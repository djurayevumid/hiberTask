package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String toDo = "create table if not exists USERS " +
                    "(id int not null auto_increment primary key, " +
                    "name varchar(255) not null, lastName varchar(255) not null, age int not null)";
            session.createSQLQuery(toDo).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String toDo = "DROP TABLE IF EXISTS USERS";
            session.createSQLQuery(toDo).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String toDo = "INSERT INTO USERS (name, lastName, age) values (?, ?, ?)";
            session.createSQLQuery(toDo).addEntity(User.class).executeUpdate();
           transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.get(User.class, 1L);
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);

            Query query = session.createQuery(cq);
            List<User> userList = query.getResultList();
            return userList;
        }
    }
    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate table USERS")
                    .addEntity(User.class).executeUpdate();
            transaction.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
}

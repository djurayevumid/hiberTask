package jm.task.core.jdbc.util;

import jdk.jshell.EvalException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.internal.SessionFactoryRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static Connection connection;
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry serviceRegistry;
    public static Connection getSQLConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String userName = "djurayevumid";
            String password = "James4root!!";
            String connectionUrl = "jdbc:mysql://localhost:3306/smth";
            connection = DriverManager.getConnection(connectionUrl, userName, password);
            connection.setAutoCommit(false);

        } catch (ClassNotFoundException e) {
            System.out.println("ConnectionERROR: " + e.getMessage());
        }
        return connection;
    }

   static {
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
       Map<String, String> dbData = new HashMap<>();
       dbData.put(Environment.URL, "jdbc:mysql://localhost:3306/smth");
       dbData.put(Environment.USER, "djurayevumid");
       dbData.put(Environment.PASS, "James4root!!");
       dbData.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
       dbData.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
       dbData.put(Environment.AUTOCOMMIT, "false");
       dbData.put(Environment.SHOW_SQL, "true");
       dbData.put(Environment.FORMAT_SQL, "true");
       registryBuilder.applySettings(dbData);
       serviceRegistry = registryBuilder.build();
       MetadataSources sources = new MetadataSources(serviceRegistry);
       Metadata metadata = sources.getMetadataBuilder().build();
       sessionFactory = metadata.getSessionFactoryBuilder().build();
   }

   public static SessionFactory getSessionFactory() {
        return sessionFactory;
   }
}

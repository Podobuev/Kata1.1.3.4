package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Andrey", "Podobuev", (byte) 34);
        userService.saveUser("Dmitriy", "Bannikov", (byte) 33);
        userService.saveUser("Sergey", "Ivanov", (byte) 30);
        userService.saveUser("Igor", "Petrov", (byte) 22);

        List<User> list = userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();

        userService.close();

    }
}


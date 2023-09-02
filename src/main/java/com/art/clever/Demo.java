package com.art.clever;

import com.art.clever.model.entity.User;
import com.art.clever.model.pool.ConnectionPool;
import com.art.clever.service.UserService;
import com.art.clever.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        User user = new User();
        user.setPassportId("3220570A053BN5");
        user.setLastName("Андронов");
        user.setFirstName("Петр");
        user.setSurName("Васильевич");
        UserService userService = new UserServiceImpl();
        List<User> users = new ArrayList<>();
        try {
            ConnectionPool pool = ConnectionPool.INSTANCE;
            pool.initPool();
            userService.addUser(user);
            users = userService.findUsers();
            for (User u : users) {
                System.out.println(u);
            }
            System.out.println("--------------");
            System.out.println(userService.deleteUser("3220570A053BN5"));
            users = userService.findUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (User u : users) {
            System.out.println(u);
        }
    }
}

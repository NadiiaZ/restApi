package rest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import rest.config.Config;
import rest.model.User;

import java.io.Console;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

        Communication communication = applicationContext.getBean("communication", Communication.class);
        StringBuilder code = new StringBuilder();
        List<User> users = communication.showAllUsers();

        User newUser = new User(3L, "James", "Brown", (byte) 29);

        code.append(communication.saveUser(newUser));

        newUser.setName("Thomas");
        newUser.setLastName("Shelby");

        code.append(communication.updateUser(newUser));
        code.append(communication.deleteUser(newUser.getId()));

        System.out.println("Code: " + code);
    }
}

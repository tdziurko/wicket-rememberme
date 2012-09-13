package pl.tomaszdziurko.wicket.service;

import com.google.common.collect.Lists;
import pl.tomaszdziurko.wicket.model.User;

import java.util.List;

public class UserService {

    private static List<User> usersInDatabase = Lists.newArrayList(
            new User("John", "john"),
            new User("Lisa", "lisa"),
            new User("Tom", "tom"),
            new User("Steven", "steven"));


    public User findByLoginAndPassword(String login, String password) {

        for (User user : usersInDatabase) {
            if(user.getPassword().equals(password) & user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
}

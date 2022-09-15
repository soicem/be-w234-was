package persistence;

import model.User;

import java.util.ArrayList;

public class UserRepository {
    private ArrayList<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }
    public void add(User user) {
        users.add(user);
    }

    public ArrayList<User> findAll() {
        return users;
    }
}

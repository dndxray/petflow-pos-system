package repository;

import model.User;
import java.util.Optional;

public class UserRepository extends Repository<User> {

    @Override
    public Optional<User> findById(String id) {
        for (User u : data) {
            if (u.getId().equals(id)) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

    public Optional<User> login(String id, String password) {
        Optional<User> user = findById(id);
        if (user.isPresent() && user.get().checkPassword(password)) {
            return user;
        }
        return Optional.empty();
    }
}
package com.petflow.repository;

import java.util.Optional;

import com.petflow.model.User;

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
package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Repository<T> {
    protected List<T> data = new ArrayList<>();

    public void add(T item) {
        data.add(item);
    }

    public List<T> getAll() {
        return new ArrayList<>(data);
    }

    public void remove(T item) {
        data.remove(item);
    }

    public int count() {
        return data.size();
    }

    public abstract Optional<T> findById(String id);
}
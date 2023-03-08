package services;

import java.util.List;

public interface Services<T>{
    public void add(T p);
    public void delete(int id);
    public void update(T p);
    public List<T> getAll();
    public T getById(int id);
}

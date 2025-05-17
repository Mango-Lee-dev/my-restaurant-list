package com.example.restaurant.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract public class MemoryDBRepositoryAbstract<T extends MemoryDBEntity> implements MemoryDBRepositoryIfs<T> {
    private final List<T> db = new ArrayList<>();
    private int index = 0;

    @Override
    public Optional<T> findById(int index) {
        return db.stream().filter(it -> it.getIndex() == index).findFirst();
    }

    @Override
    public T save(T entity) {
        var optionalEntity = db.stream().filter(it -> it.getIndex() == entity.getIndex()).findFirst();
        if (optionalEntity.isEmpty()) {
            index++;
            entity.setIndex(index);
        } else {
            var preIndex = optionalEntity.get().getIndex();
            entity.setIndex(preIndex);

            deleteById(preIndex);
        }
        db.add(entity);
        return entity;
    }

    @Override
    public void deleteById(int index) {
        var optionalEntity = db.stream().filter(it -> it.getIndex() == index).findFirst();
        optionalEntity.ifPresent(db::remove);
    }

    @Override
    public List<T> listAll() {
        return db;
    }
}

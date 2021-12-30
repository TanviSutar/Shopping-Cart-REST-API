package com.thoughtworks.CartApp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
    boolean existsByName(String name);
    List<Item> findByNameContaining(String searchString);
    void deleteByName(String name);
}

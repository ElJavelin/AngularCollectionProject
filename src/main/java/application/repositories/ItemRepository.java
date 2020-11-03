package application.repositories;

import application.domains.Collection;
import application.domains.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
    List<Item> findByCollection(Collection collection);
}

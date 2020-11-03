package application.repositories;

import application.domains.Collection;
import application.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CollectionRepository extends CrudRepository<Collection, Long> {
    List<Collection> findByUser(User user);
}
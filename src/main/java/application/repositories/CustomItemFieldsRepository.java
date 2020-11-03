package application.repositories;

import application.domains.Collection;
import application.domains.CustomItemFields;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomItemFieldsRepository extends JpaRepository<CustomItemFields, Long> {
    CustomItemFields findByCollection(Collection collection);
}

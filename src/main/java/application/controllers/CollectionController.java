package application.controllers;

import application.domains.*;
import application.repositories.CollectionRepository;
import application.repositories.CustomItemFieldsRepository;
import application.repositories.ItemRepository;
import application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CollectionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CustomItemFieldsRepository customItemFieldsRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/addCollection/{userID}")
    public Collection addCollection(@PathVariable Long userID, @RequestBody Collection collection){
        System.out.println(collection.toString());
        if(userRepository.existsById(userID)){
            Optional<User> userOptional = userRepository.findById(userID);
            CustomItemFields customItemFields = new CustomItemFields(collection.getCustomItemFields());
            customItemFieldsRepository.save(customItemFields);
            Collection curCollection = new Collection(collection.getName(), collection.getDescription(), collection.getTheme(), collection.getColImg());
            curCollection.setDateOfCreation((new Timestamp(System.currentTimeMillis())).toString());
            curCollection.setCustomItemFields(customItemFields);
            curCollection.setUsingCustomFields(collection.getUsingCustomFields());
            curCollection.setUser(userOptional.get());
            collectionRepository.save(curCollection);
            userOptional.get().getCollections().add(curCollection);
            userRepository.save(userOptional.get());
            return curCollection;
        }
        return null;
    }

    @GetMapping("/getCurCollection/{collectionID}")
    public ResponseEntity<Collection> getCurCollection(@PathVariable Long collectionID){
        Optional<Collection> collections = collectionRepository.findById(collectionID);
        if(collections == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(collections.get());
    }

    @GetMapping("/getCollectionList/{userID}")
    public List<Collection> getCollectionList(@PathVariable Long userID){
        try {
            List<Collection> collections = collectionRepository.findByUser(userRepository.findById(userID).get());
            return collections;
        }catch (NoResultException | NoSuchElementException e){
            return null;
        }
    }

    @PostMapping("/deleteCollection")
    public boolean deleteCollection(@RequestBody Collection collection){
        try {
            collectionRepository.delete(collection);
            return true;
        }catch (NoResultException e){
            return false;
        }
    }

    @PostMapping("/saveNewCollectionValues")
    public Collection saveNewCollectionValues(@RequestBody Collection collection) {
        Optional<Collection> collectionOptional = collectionRepository.findById(collection.getCollectionID());
        collectionOptional.get().setName(collection.getName());
        collectionOptional.get().setDescription(collection.getDescription());
        collectionOptional.get().setTheme(collection.getTheme());
        collectionRepository.save(collectionOptional.get());
        return collectionOptional.get();
    }

    @GetMapping("/getColCustomFields/{collectionID}")
    public CustomItemFields getColCustomFields(@PathVariable Long collectionID){
        try {
            CustomItemFields customItemFields = customItemFieldsRepository.findByCollection(collectionRepository.findById(collectionID).get());
            return customItemFields;
        }catch (NoResultException | NoSuchElementException e){
            return null;
        }
    }
}

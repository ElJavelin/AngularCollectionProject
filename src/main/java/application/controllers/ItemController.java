package application.controllers;

import application.domains.Collection;
import application.domains.Item;
import application.repositories.CollectionRepository;
import application.repositories.CustomItemFieldsRepository;
import application.repositories.ItemRepository;
import application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ItemController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CustomItemFieldsRepository customItemFieldsRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/saveNewItem/{collectionID}")
    public boolean saveNewItem(@PathVariable Long collectionID, @RequestBody Item item){
        if(collectionRepository.existsById(collectionID)){
            Optional<Collection> collection = collectionRepository.findById(collectionID);
            item.setCollection(collection.get());
            itemRepository.save(item);
            collection.get().getItems().add(item);
            collectionRepository.save(collection.get());
            return true;
        }
        return false;
    }

    @GetMapping("/getItemList/{collectionID}")
    public List<Item> getItemList(@PathVariable Long collectionID){
        try {
            List<Item> items = itemRepository.findByCollection(collectionRepository.findById(collectionID).get());
            return items;
        }catch (NoResultException | NoSuchElementException e){
            return null;
        }
    }


    @PostMapping("/deleteItem")
    public boolean deleteItem(@RequestBody Item item){
        try {
            itemRepository.delete(item);
            return true;
        }catch (NoResultException e){
            return false;
        }
    }

    @PostMapping("/saveNewItemValues")
    public Item saveNewItemValues(@RequestBody Item item) {
        Optional<Item> itemOptional = itemRepository.findById(item.getItemID());
        itemOptional.get().setCustomFields(item);
        itemRepository.save(itemOptional.get());
        return itemOptional.get();
    }
}

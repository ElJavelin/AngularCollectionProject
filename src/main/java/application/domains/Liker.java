package application.domains;

import javax.persistence.*;
import java.util.List;

@Entity
public class Liker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long likerID;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LikerItemID")
    private Item item;

    /*
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "LikerUser",
            joinColumns = @JoinColumn(name = "LikerID"),
            inverseJoinColumns = @JoinColumn(name = "UserID"))
    private List<User> users;

     */

    public Liker() {}
}

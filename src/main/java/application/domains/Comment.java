package application.domains;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commentID;
    private String text;
    private java.sql.Timestamp postTimestamp;

    /*
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CommentUserID")
    private User user;

     */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CommentCollectionID")
    private Collection collection;

    public Comment() {}

    public Comment(String text) {
        this.text = text;
        postTimestamp = new Timestamp(System.currentTimeMillis());
    }

    public long getCommentID() {
        return commentID;
    }

    public void setCommentID(long commentID) {
        this.commentID = commentID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getPostTimestamp() {
        return postTimestamp;
    }

    public void setPostTimestamp(Timestamp postTimestamp) {
        this.postTimestamp = postTimestamp;
    }


}

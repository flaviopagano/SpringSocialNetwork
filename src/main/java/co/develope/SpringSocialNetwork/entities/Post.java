package co.develope.SpringSocialNetwork.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
  private String text;

  private String image;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userWhoPosts;

  @OneToMany(mappedBy = "postToComment", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Comment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "postToReact", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Reaction> reactions = new ArrayList<>();

  public Post(){}

  public Post(String text, User userWhoPosts) {
      super();
      this.text = text;
      this.userWhoPosts = userWhoPosts;
  }
  public Post(String text, User userWhoPosts, String images) {
      super();
      this.text = text;
      this.userWhoPosts = userWhoPosts;
      this.image = images;
  }
  public int getId() {
      return id;
  }

  public String getText() {
      return text;
  }

  public void setText(String text) {
      this.text = text;
  }

  public User getUserWhoPosts() {
      return userWhoPosts;
  }

  public void setUserWhoPosts(User userWhoPosts) {
      this.userWhoPosts = userWhoPosts;
  }

  public List<Comment> getComments() {
      return comments;
  }

  public void setComments(List<Comment> comments) {
      this.comments = comments;
  }

  public List<Reaction> getReactions() {
      return reactions;
  }

  public void setReactions(List<Reaction> reactions) {
      this.reactions = reactions;
  }

    public String getImages() {
        return image;
    }

    public void setImages(String images) {
        this.image = images;
    }
}

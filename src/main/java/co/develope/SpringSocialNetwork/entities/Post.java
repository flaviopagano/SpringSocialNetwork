package co.develope.SpringSocialNetwork.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
  private String text;

  private String image;


  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userWhoPosts;

  @OneToMany
  @JsonIgnore
  private List<Comment> comments = new ArrayList<>();

  @OneToMany
  @JsonIgnore
  private List<Reaction> reactions = new ArrayList<>();

  public Post(){}

  public Post(String text, User userWhoPosts) {
      super();
      this.text = text;
      this.userWhoPosts = userWhoPosts;
  }
    public Post(String text, User userWhoPosts, String image) {
        super();
        this.text = text;
        this.userWhoPosts = userWhoPosts;
        this.image = image;
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

}

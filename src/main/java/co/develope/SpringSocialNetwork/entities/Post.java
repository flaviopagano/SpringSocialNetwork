package co.develope.SpringSocialNetwork.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Post {

  @Id
  @GeneratedValue
  private int id;
  private String text;
  //come contare i post di un user?
  //private static int postCount; //come funziona con spring?


    public Post() {
    }

    public Post(int id, String text) {
        this.id = id;
        this.text = text;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}

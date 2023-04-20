package co.develope.SpringSocialNetwork.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{

    private String title;
    private String caption;
    private String fileName;
    //private Set<String> tags;
    @OneToOne
    private User userP;
    @ManyToOne(fetch = FetchType.LAZY)
    private Post postP;

    public Picture(String fileName){
        this.fileName = fileName;
    }

    public Picture(String title, String fileName){
        this.title = title;
        this.fileName = fileName;
    }

    public Picture(String title, String caption, String fileName) {
        this.title = title;
        this.caption = caption;
        this.fileName = fileName;
    }

    /*
    public Picture(String title, String caption, String fileName, Set<String> tags) {
        this.title = title;
        this.caption = caption;
        this.fileName = fileName;
        this.tags = tags;
    }
    */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /*
    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
    */

    public User getUserP() {
        return userP;
    }

    public void setUserP(User userP) {
        this.userP = userP;
    }

    public Post getPostP() {
        return postP;
    }

    public void setPostP(Post postP) {
        this.postP = postP;
    }

}

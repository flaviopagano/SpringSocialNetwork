package co.develope.SpringSocialNetwork.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(unique = true, nullable = false)
    private String username;

    /** decommenta i due nullable = false quando avrai aggiunto i due campi alla creazione dell'user, ora come ora
     * bloccano tutto perche creando un user non si possono assegnre quei due campi ma possiedono allo stesso
     * tempo il constraint
     * Vedi anche se conviene usare Date o LocalDateTime **/

    @Column//(nullable = false)
    // ho cambiato "Date" in "LocalDateTime" perchè pare sia una
    // libreria di Java più recente e più versatile
    private LocalDateTime dateOfBirth;

    @Column//(nullable = false)
    private String placeOfBirth;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne
    private Picture profilePicture;

    @OneToMany
    @JsonIgnore
    List<Post> posts = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    List<Reaction> reactions = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    Set<User> friendList = new HashSet<>();

    public User(){}

    public User(String name, String surname, String username, String email, String password,
                LocalDateTime dateOfBirth, String placeOfBirth) {
        super();
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;

    }

    /*public User(String id, String name, String surname) {
        super.setId(id);
        this.name = name;
        this.surname = surname;
    }

    public User(String nickname, String email, String password) {
        this.username = nickname;
        this.email = email;
        this.password = password;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String nickname) {
        this.username = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Picture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePictureFilename(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
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

    public Set<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(Set<User> friendList) {
        this.friendList = friendList;
    }

}

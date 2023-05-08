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

    @Column
    private LocalDateTime dateOfBirth;

    @Column
    private String placeOfBirth;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profilePicture;

    private boolean isActive;

    private String activationCode;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;

    @OneToMany(mappedBy = "userWhoPosts", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "userWhoComments", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "userWhoReacts", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Reaction> reactions = new ArrayList<>();

    /*@OneToMany
    @JsonIgnore
    Set<User> friendList = new HashSet<>();*/

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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /*public Set<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(Set<User> friendList) {
        this.friendList = friendList;
    }*/

}

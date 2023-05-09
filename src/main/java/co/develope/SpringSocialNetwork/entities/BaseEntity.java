package co.develope.SpringSocialNetwork.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @Column(nullable = false)
    private LocalDateTime publicationDate;

    private LocalDateTime updateDate;

    public BaseEntity(){
        this.publicationDate = LocalDateTime.now();
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

}

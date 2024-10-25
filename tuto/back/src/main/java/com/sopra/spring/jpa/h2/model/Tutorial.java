package com.sopra.spring.jpa.h2.model;

import com.sopra.spring.jpa.h2.model.listener.AuditTutorialListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(exclude = "histories")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners({AuditingEntityListener.class, AuditTutorialListener.class})
@Table(name = "tutorials")
public class Tutorial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "operation")
    private String operation;

    @Version
    @Column(name = "version")
    private int version;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column(name = "published_date")
    private LocalDateTime lastPublishedDate;

    @OneToMany(mappedBy = "tutorial", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<History> histories;

    public Tutorial(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    @PrePersist
    public void onPrePersist() {
        this.setOperation("INSERT");
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setOperation("UPDATE");
        this.updateLastPublishedDate();
    }

    void updateLastPublishedDate() {
        if (this.published) {
            this.lastPublishedDate = LocalDateTime.now();
        }
    }
}

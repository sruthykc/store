package com.diviso.graeshoppe.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Review.
 */
@Entity
@Table(name = "review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "review")
    private String review;

    @Column(name = "reviewed_date")
    private ZonedDateTime reviewedDate;

    @ManyToOne
    @JsonIgnoreProperties("reviews")
    private Store store;

    @OneToMany(mappedBy = "review")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reply> replies = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public Review userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReview() {
        return review;
    }

    public Review review(String review) {
        this.review = review;
        return this;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public ZonedDateTime getReviewedDate() {
        return reviewedDate;
    }

    public Review reviewedDate(ZonedDateTime reviewedDate) {
        this.reviewedDate = reviewedDate;
        return this;
    }

    public void setReviewedDate(ZonedDateTime reviewedDate) {
        this.reviewedDate = reviewedDate;
    }

    public Store getStore() {
        return store;
    }

    public Review store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Set<Reply> getReplies() {
        return replies;
    }

    public Review replies(Set<Reply> replies) {
        this.replies = replies;
        return this;
    }

    public Review addReply(Reply reply) {
        this.replies.add(reply);
        reply.setReview(this);
        return this;
    }

    public Review removeReply(Reply reply) {
        this.replies.remove(reply);
        reply.setReview(null);
        return this;
    }

    public void setReplies(Set<Reply> replies) {
        this.replies = replies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Review review = (Review) o;
        if (review.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), review.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Review{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", review='" + getReview() + "'" +
            ", reviewedDate='" + getReviewedDate() + "'" +
            "}";
    }
}

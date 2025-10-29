package ch.zli.m223.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
public class Entry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(readOnly = true)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime checkIn;

  @Column(nullable = false)
  private LocalDateTime checkOut;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "category")
  private Category category;

  @ManyToMany
  @JoinTable(
    name = "tag_entry",
    joinColumns = @JoinColumn(name = "entry_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private Set<Tag> tags;

  public Entry (Long id, LocalDateTime checkIn, LocalDateTime checkOut, Set<Tag> tags) {
    this.id = id;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.tags = tags;
  }

  public Entry (LocalDateTime checkIn, LocalDateTime checkOut, Set<Tag> tags, Category category) {
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.tags = tags;
    this.category = category;
  }

  public Entry () {
  }

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public LocalDateTime getCheckIn () {
    return checkIn;
  }

  public void setCheckIn (LocalDateTime checkIn) {
    this.checkIn = checkIn;
  }

  public LocalDateTime getCheckOut () {
    return checkOut;
  }

  public void setCheckOut (LocalDateTime checkOut) {
    this.checkOut = checkOut;
  }

  @Override
  public boolean equals (Object obj) {
    return Objects.equals(this.id, ((Entry) obj).id) &&
      this.checkIn == ((Entry) obj).checkIn &&
      this.checkOut == ((Entry) obj).checkOut;
  }

  public Category getCategory () {
    return category;
  }

  public void setCategory (Category category) {
    this.category = category;
  }

  public Set<Tag> getTags () {
    return tags;
  }

  public void setTags (Set<Tag> tags) {
    this.tags = tags;
  }
}

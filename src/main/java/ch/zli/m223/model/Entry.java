package ch.zli.m223.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "category")
  private Category category;

  public Entry (Long id, LocalDateTime checkIn, LocalDateTime checkOut) {
    this.checkIn = checkIn;
    this.checkOut = checkOut;
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

  public void setCategory (Category category) {
    this.category = category;
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
}

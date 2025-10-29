package ch.zli.m223.dto;

import java.time.LocalDateTime;
import java.util.Set;

import ch.zli.m223.model.Tag;

public class EntryDTO {
  private final Long id;
  private final LocalDateTime checkIn;
  private final LocalDateTime checkOut;
  private final Long categoryId;
  private Set<Tag> tags;

  public EntryDTO (Long id, LocalDateTime checkIn, LocalDateTime checkOut, Long categoryId, Set<Tag> tags) {
    this.id = id;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.categoryId = categoryId;
    this.tags = tags;
  }

  public Long getCategoryId () {
    return categoryId;
  }

  public Long getId () {
    return id;
  }

  public LocalDateTime getCheckIn () {
    return checkIn;
  }

  public LocalDateTime getCheckOut () {
    return checkOut;
  }

  public Set<Tag> getTags () {
    return tags;
  }

  public void setTags (Set<Tag> tags) {
    this.tags = tags;
  }
}

package ch.zli.m223.dto;

import java.time.LocalDateTime;

public class EntryDTO {
  private final Long id;
  private final LocalDateTime checkIn;
  private final LocalDateTime checkOut;
  private final Long categoryId;

  public EntryDTO (Long id, LocalDateTime checkIn, LocalDateTime checkOut, Long categoryId) {
    this.id = id;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.categoryId = categoryId;
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getCheckIn () {
    return checkIn;
  }

  public LocalDateTime getCheckOut () {
    return checkOut;
  }

  public Long getCategoryId () {
    return categoryId;
  }
}

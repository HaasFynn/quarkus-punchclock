package ch.zli.m223.dto;

import java.time.LocalDateTime;

public class EntryDTO {
  private final Long id;
  private final LocalDateTime checkIn;
  private final LocalDateTime checkOut;

  public EntryDTO (Long id, LocalDateTime checkIn, LocalDateTime checkOut) {
    this.id = id;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
  }

  public Long  getId() {
    return id;
  }

  public LocalDateTime getCheckIn () {
    return checkIn;
  }

  public LocalDateTime getCheckOut () {
    return checkOut;
  }
}

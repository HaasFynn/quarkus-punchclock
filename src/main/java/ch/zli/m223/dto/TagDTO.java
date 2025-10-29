package ch.zli.m223.dto;

public class TagDTO {
  private final Long id;
  private final String name;

  public TagDTO (Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId () {
    return id;
  }

  public String getName () {
    return name;
  }
}

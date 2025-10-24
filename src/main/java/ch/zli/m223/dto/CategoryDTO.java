package ch.zli.m223.dto;

public class CategoryDTO {

  private final Long id;
  private final String name;

  public CategoryDTO (Long id, String name) {
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

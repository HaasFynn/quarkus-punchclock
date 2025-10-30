package ch.zli.m223.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(readOnly = true)
  private Long id;

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  private List<Entry> entries;

  public Category() {

  }

  public Category (Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Category (String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals (Object obj) {
    Objects.requireNonNull(obj);

    return Objects.equals(this.id, ((Category) obj).id) &&
      Objects.equals(this.name, ((Category) obj).name);
  }

}

package ch.zli.m223.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(readOnly = true)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
  private Set<Entry> entries;

  public Tag () {
  }

  public Tag (Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Tag (String name) {
    this.name = name;
  }

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public String getName () {
    return name;
  }

  public void setName (String name) {
    this.name = name;
  }

  @Override
  public boolean equals (Object obj) {
    Objects.requireNonNull(obj);

    return Objects.equals(this.id, ((Tag) obj).id) &&
      Objects.equals(this.name, ((Tag) obj).name);
  }
}

package ch.zli.m223.start;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TestDataLoader {

  private EntityManager em;

  @Inject
  public TestDataLoader (EntityManager entityManager) {
    this.em = entityManager;
  }

  @Transactional
  public void onStart (@Observes StartupEvent ev) {
    if (!io.quarkus.runtime.LaunchMode.current().isDevOrTest()) return;
    addTags();
    addCategories();
    addEntries();
  }

  private void addTags () {
    em.persist(new Tag("tag1"));
    em.persist(new Tag("tag2"));
    em.persist(new Tag("tag3"));
    em.persist(new Tag("tag4"));
    em.persist(new Tag("tag5"));
  }

  private void addCategories () {
    em.persist(new Category("category1"));
    em.persist(new Category("category2"));
    em.persist(new Category("category3"));
    em.persist(new Category("category4"));
    em.persist(new Category("category5"));
  }

  private void addEntries () {
    Set<Tag> tags = getTags();
    var firstTags = tags.stream().limit(2).collect(Collectors.toCollection(HashSet::new));
    var lastTags = tags.stream().limit(3).collect(Collectors.toCollection(HashSet::new));
    ArrayList<Category> categories = getCategories();
    var rand = new Random();
    var category = categories.get(rand.nextInt(categories.size()));
    var anotherCategory = categories.get(rand.nextInt(categories.size()));

    LocalDateTime randomDay = LocalDateTime.of(2023, 5, 15, 0, 0);
    LocalDateTime veryEarly = randomDay.withHour(6).withMinute(0);
    LocalDateTime veryLate = randomDay.withHour(12).withMinute(59);

    em.persist(new Entry(veryEarly, veryLate, firstTags, category));
    em.persist(new Entry(veryEarly, veryLate, lastTags, anotherCategory));
  }

  private Set<Tag> getTags () {
    return new HashSet<>(
      em.createQuery("FROM Tag", Tag.class).getResultList()
    );
  }

  private ArrayList<Category> getCategories () {
    return new ArrayList<>(
      em.createQuery("FROM Category", Category.class).getResultList()
    );
  }
}

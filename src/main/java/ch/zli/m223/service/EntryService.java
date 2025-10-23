package ch.zli.m223.service;

import java.util.List;

import ch.zli.m223.model.Entry;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class EntryService {
  @Inject
  private EntityManager entityManager;

  @Transactional
  public Entry createEntry (Entry entry) {
    entityManager.persist(entry);
    return entry;
  }

  @Transactional
  public boolean updateEntry (Entry entry) {
    return entityManager.merge(entry).equals(entry);
  }

  @Transactional
  public boolean deleteEntry (Long id) {
    Entry entry = entityManager.find(Entry.class, id);
    if (entry != null) {
      entityManager.remove(entry);
      return true;
    }
    return false;
  }

  public List<Entry> findAll () {
    var query = entityManager.createQuery("FROM Entry", Entry.class);
    return query.getResultList();
  }
}

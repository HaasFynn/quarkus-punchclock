package ch.zli.m223.service;

import java.util.ArrayList;
import java.util.List;

import ch.zli.m223.dto.EntryDTO;
import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class EntryService {

  private EntityManager entityManager;

  @Inject
  public EntryService (EntityManager entityManager, TagService tagService) {
    this.entityManager = entityManager;
  }

  @Transactional
  public EntryDTO createEntry (EntryDTO entryDTO) {
    Entry entry = toEntry(entryDTO);
    if (entryDTO.getCategoryId() != null) {
      Category category = entityManager.find(Category.class, entryDTO.getCategoryId());
      entry.setCategory(category);
    }
    entityManager.persist(entry);
    return toEntryDTO(entry);
  }

  @Transactional
  public boolean updateEntry (EntryDTO entryDTO) {
    Entry entry = toEntry(entryDTO);
    return convertedEquals(entityManager.merge(entry), entry);
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

  public List<EntryDTO> findAll () {
    var query = entityManager.createQuery("FROM Entry", Entry.class);
    return listToEntryDTOs(query.getResultList());
  }

  private boolean convertedEquals (Entry merged, Entry entry) {
    return merged.getCheckIn().equals(entry.getCheckIn()) &&
      merged.getCheckOut().equals(entry.getCheckOut());
  }

  public Entry toEntry (EntryDTO entryDTO) {
    return new Entry(
      entryDTO.getId(),
      entryDTO.getCheckIn(),
      entryDTO.getCheckOut(),
      entryDTO.getTags()
      );
  }

  public EntryDTO toEntryDTO (Entry entry) {
    return new EntryDTO(
      entry.getId(),
      entry.getCheckIn(),
      entry.getCheckOut(),
      entry.getCategory().getId(),
      entry.getTags()
    );
  }

  public List<Entry> listToEntries (List<EntryDTO> entryDTOs) {
    List<Entry> entryList = new ArrayList<>();
    for (EntryDTO entryDTO : entryDTOs) {
      Entry entry = new Entry(
        entryDTO.getId(),
        entryDTO.getCheckIn(),
        entryDTO.getCheckOut(),
        entryDTO.getTags()
      );

      entryList.add(entry);
    }
    return entryList;
  }

  public List<EntryDTO> listToEntryDTOs (List<Entry> entries) {
    List<EntryDTO> entryDTOList = new ArrayList<>();
    for (Entry entry : entries) {
      Long categoryId = entry.getCategory() != null ? entry.getCategory().getId() : -1;
      EntryDTO entryDTO = new EntryDTO(
        entry.getId(),
        entry.getCheckIn(),
        entry.getCheckOut(),
        categoryId,
        entry.getTags()
      );
      entryDTOList.add(entryDTO);
    }
    return entryDTOList;
  }
}

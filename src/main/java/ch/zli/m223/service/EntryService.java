package ch.zli.m223.service;

import java.util.ArrayList;
import java.util.List;

import ch.zli.m223.dto.EntryDTO;
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
  public EntryDTO createEntry (EntryDTO entryDTO) {
    Entry entry = toEntry(entryDTO);
    entityManager.persist(entry);
    return toEntryDTO(entry);
  }

  @Transactional
  public boolean updateEntry (EntryDTO entryDTO) {
    Entry entry = toEntry(entryDTO);
    return convertedEquals(entityManager.merge(entry), entry);
  }

  private boolean convertedEquals (Entry merged, Entry entry) {
    return merged.getCheckIn().equals(entry.getCheckIn()) &&
      merged.getCheckOut().equals(entry.getCheckOut());
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

  public Entry toEntry (EntryDTO entryDTO) {
    return new Entry(entryDTO.getId(), entryDTO.getCheckIn(), entryDTO.getCheckOut());
  }

  public EntryDTO toEntryDTO (Entry entry) {
    return new EntryDTO(entry.getId(), entry.getCheckIn(), entry.getCheckOut());
  }

  public List<Entry> listToEntries (List<EntryDTO> entryDTOs) {
    List<Entry> entryList = new ArrayList<>();
    for (EntryDTO entryDTO : entryDTOs) {
      Entry entry = new Entry(entryDTO.getId(), entryDTO.getCheckIn(), entryDTO.getCheckOut());
      entryList.add(entry);
    }
    return entryList;
  }

  public List<EntryDTO> listToEntryDTOs (List<Entry> entries) {
    List<EntryDTO> entryDTOList = new ArrayList<>();
    for (Entry entry : entries) {
      EntryDTO entryDTO = new EntryDTO(entry.getId(), entry.getCheckIn(), entry.getCheckOut());
      entryDTOList.add(entryDTO);
    }
    return entryDTOList;
  }
}

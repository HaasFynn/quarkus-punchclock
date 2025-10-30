package ch.zli.m223.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ch.zli.m223.dto.EntryDTO;
import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EntryService {

  private final EntityManager entityManager;
  private final TimeSummaryService timeSummaryService;

  @Inject
  public EntryService (EntityManager entityManager, TimeSummaryService timeSummaryService) {
    this.entityManager = entityManager;
    this.timeSummaryService = timeSummaryService;
  }

  @Transactional
  public EntryDTO createEntry (EntryDTO entryDTO) {
    Entry entry = toEntry(entryDTO);
    if (entryDTO.getCategory() != null) {
      Category category = entityManager.find(Category.class, entryDTO.getCategory().getId());
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

  @Transactional
  public String getTimeSummaries (Date date) {
    LocalDate targetDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    List<Entry> entries = entityManager.createQuery("FROM Entry", Entry.class).getResultList();
    List<Entry> filteredEntries = entries.stream().filter(
      entry -> entry.getCheckIn().toLocalDate().isEqual(targetDate)
    ).collect(Collectors.toList());

    return timeSummaryService.calculateSummaryPerDay(filteredEntries);
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
      entry.getCategory(),
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
      EntryDTO entryDTO = new EntryDTO(
        entry.getId(),
        entry.getCheckIn(),
        entry.getCheckOut(),
        entry.getCategory(),
        entry.getTags()
      );
      entryDTOList.add(entryDTO);
    }
    return entryDTOList;
  }
}

package ch.zli.m223.service;

import java.util.ArrayList;
import java.util.List;

import ch.zli.m223.dto.TagDTO;
import ch.zli.m223.model.Tag;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TagService {
  @Inject
  private EntityManager entityManager;

  @Transactional
  public TagDTO createEntry (TagDTO tagDTO) {
    Tag tag = toTag(tagDTO);
    entityManager.persist(tag);
    return toTagDTO(tag);
  }

  @Transactional
  public boolean updateEntry (TagDTO tagDTO) {
    Tag tag = toTag(tagDTO);
    return convertedEquals(entityManager.merge(tag), tag);
  }

  @Transactional
  public boolean deleteEntry (Long id) {
    Tag tag = entityManager.find(Tag.class, id);
    if (tag != null) {
      entityManager.remove(tag);
      return true;
    }
    return false;
  }

  public List<TagDTO> findAll () {
    var query = entityManager.createQuery("FROM Tag ", Tag.class);
    return listToTagDTOs(query.getResultList());
  }

  private boolean convertedEquals (Tag merged, Tag tag) {
    return merged.getName().equals(tag.getName());
  }

  public Tag toTag (TagDTO tagDTO) {
    return new Tag(tagDTO.getId(), tagDTO.getName());
  }

  public TagDTO toTagDTO (Tag tag) {
    return new TagDTO(tag.getId(), tag.getName());
  }

  public List<Tag> listToTags (List<TagDTO> tagDTOs) {
    List<Tag> tagList = new ArrayList<>();
    for (TagDTO tagDTO : tagDTOs) {
      Tag tag = new Tag(tagDTO.getId(), tagDTO.getName());
      tagList.add(tag);
    }
    return tagList;
  }

  public List<TagDTO> listToTagDTOs (List<Tag> categories) {
    List<TagDTO> tagDTOList = new ArrayList<>();
    for (Tag tag : categories) {
      TagDTO tagDTO = new TagDTO(tag.getId(), tag.getName());
      tagDTOList.add(tagDTO);
    }
    return tagDTOList;
  }
}

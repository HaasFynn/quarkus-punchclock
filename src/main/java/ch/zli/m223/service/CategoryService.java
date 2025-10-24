package ch.zli.m223.service;

import java.util.ArrayList;
import java.util.List;

import ch.zli.m223.dto.CategoryDTO;
import ch.zli.m223.model.Category;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class CategoryService {

  @Inject
  private EntityManager entityManager;

  @Transactional
  public CategoryDTO createEntry (CategoryDTO categoryDTO) {
    Category category = toCategory(categoryDTO);
    entityManager.persist(category);
    return toCategoryDTO(category);
  }

  @Transactional
  public boolean updateEntry (CategoryDTO categoryDTO) {
    Category category = toCategory(categoryDTO);
    return convertedEquals(entityManager.merge(category), category);
  }

  @Transactional
  public boolean deleteEntry (Long id) {
    Category category = entityManager.find(Category.class, id);
    if (category != null) {
      entityManager.remove(category);
      return true;
    }
    return false;
  }

  public List<CategoryDTO> findAll () {
    var query = entityManager.createQuery("FROM Category ", Category.class);
    return listToCategoryDTOs(query.getResultList());
  }

  private boolean convertedEquals (Category merged, Category category) {
    return merged.getName().equals(category.getName());
  }

  public Category toCategory (CategoryDTO categoryDTO) {
    return new Category(categoryDTO.getId(), categoryDTO.getName());
  }

  public CategoryDTO toCategoryDTO (Category category) {
    return new CategoryDTO(category.getId(), category.getName());
  }

  public List<Category> listToCategories (List<CategoryDTO> categoryDTOs) {
    List<Category> categoryList = new ArrayList<>();
    for (CategoryDTO categoryDTO : categoryDTOs) {
      Category category = new Category(categoryDTO.getId(), categoryDTO.getName());
      categoryList.add(category);
    }
    return categoryList;
  }

  public List<CategoryDTO> listToCategoryDTOs (List<Category> categories) {
    List<CategoryDTO> categoryDTOList = new ArrayList<>();
    for (Category category : categories) {
      CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getName());
      categoryDTOList.add(categoryDTO);
    }
    return categoryDTOList;
  }
}

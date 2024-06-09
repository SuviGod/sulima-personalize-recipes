package we.juicy.juicyrecipes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import we.juicy.juicyrecipes.domain.IngredientIngredientCategory;
import we.juicy.juicyrecipes.repository.IngredientIngredientCategoryRepository;

@Service
@RequiredArgsConstructor
public class IngredientIngredientCategoryServiceImpl implements IngredientIngredientCategoryService {

    private final IngredientIngredientCategoryRepository ingredientIngredientCategoryRepository;

    @Override
    public void delete(IngredientIngredientCategory ingredientIngredientCategoryToDelete) {
        ingredientIngredientCategoryRepository.delete(ingredientIngredientCategoryToDelete);
    }

    @Override
    @Transactional
    public void deleteByCategoryIdAndIngredientId(Integer categoryId, Integer ingredientId) {
        ingredientIngredientCategoryRepository.deleteByCategoryIdAndIngredientId(categoryId, ingredientId);
    }
}

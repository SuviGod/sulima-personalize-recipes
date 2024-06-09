package we.juicy.juicyrecipes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import we.juicy.juicyrecipes.domain.Contents;
import we.juicy.juicyrecipes.domain.Recipe;
import we.juicy.juicyrecipes.domain.RecipeUser;
import we.juicy.juicyrecipes.dto.IngredientContentsDifference;
import we.juicy.juicyrecipes.repository.ContentsRepository;
import we.juicy.juicyrecipes.repository.RecipeRepository;
import we.juicy.juicyrecipes.repository.UserRepository;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final ContentsRepository contentsRepository;
    private final UserRepository userRepository;

    @Override
    public Set<Recipe> findAll() {
        Set<Recipe> recipes = new HashSet<>();
        Pageable firstPageWithTenElements = PageRequest.of(0, 15);
        recipeRepository.findAll(firstPageWithTenElements).forEach(recipes::add);
        return recipes;
    }

    @Override
    public Optional<Recipe> findById(Integer id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Recipe findOneByName(String name) {
        return recipeRepository.findOneByName(name);
    }

    @Transactional
    @Override
    public Recipe addIngredient(Integer recipeId, Contents contents) {
        Optional<Recipe> maybeRecipe = recipeRepository.findById(recipeId);
        if (maybeRecipe.isEmpty())
            throw new RuntimeException("Recipe with id is not found");

        Recipe recipe = maybeRecipe.get();
        Optional<Contents> ingredientContents = recipe.getNecessaryAmount()
                .stream()
                .filter(it -> it.getIngredient().equals(contents.getIngredient()))
                .findFirst();

        if (ingredientContents.isEmpty()) {
            contents.setRecipe(recipe);
            Contents savedContents = contentsRepository.save(contents);
            recipe.addContents(savedContents);
        } else {
            Contents updatedIngredientContents = ingredientContents.get();
            updatedIngredientContents.setAmount(updatedIngredientContents.getAmount() + contents.getAmount());
            contentsRepository.save(updatedIngredientContents);
        }

        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public List<IngredientContentsDifference> findMissingIngredientAndAmount(Integer recipeId) {
        Optional<Recipe> maybeRecipe = recipeRepository.findById(recipeId);
        if (maybeRecipe.isEmpty()){
            throw new RuntimeException("Recipe is not found");
        }
        Optional<RecipeUser> maybeUser = userRepository.findById(1);
        if (maybeUser.isEmpty())
            return Collections.emptyList();

        List<Contents> recipeContents = maybeRecipe.get().getNecessaryAmount();
        List<Contents> userContents = maybeUser.get().getAmountPresent();

        return recipeContents.stream()
                .map(it -> countDifference(it, userContents))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<IngredientContentsDifference> countDifference(Contents recipeContents, List<Contents> userContents) {
        Optional<Contents> maybeIngredientContents = userContents.stream().filter(userIngredient -> userIngredient.getIngredient().equals(recipeContents.getIngredient())).findFirst();
        if (maybeIngredientContents.isEmpty()) {
            return Optional.of(new IngredientContentsDifference(recipeContents.getIngredient(), recipeContents.getAmount()));
        }

        Contents userIngredientContents = maybeIngredientContents.get();
        Double diffAmount = recipeContents.getAmount() - userIngredientContents.getAmount();

        if (diffAmount > 0) {
            return Optional.of(new IngredientContentsDifference(recipeContents.getIngredient(), diffAmount));
        }

        return Optional.empty();
    }

    @Override
    public Set<Recipe> findByOrderByDifficulty(){
        return recipeRepository.findByOrderByDifficulty();
    }

    @Override
    public List<Recipe> findManyById(List<Integer> ids) {
        List<Recipe> recipes = new ArrayList<>();
        Integer recipeAmount = (int)recipeRepository.count();
        for(Integer id : ids) {
            Optional<Recipe> maybeRecipe = recipeRepository.findById( id % recipeAmount);
            maybeRecipe.ifPresent(recipes::add);
        }
        return recipes;
    }


}

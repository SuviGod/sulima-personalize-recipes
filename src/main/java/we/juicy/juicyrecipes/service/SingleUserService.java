package we.juicy.juicyrecipes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import we.juicy.juicyrecipes.domain.Contents;
import we.juicy.juicyrecipes.domain.Recipe;
import we.juicy.juicyrecipes.domain.RecipeUser;
import we.juicy.juicyrecipes.repository.ContentsRepository;
import we.juicy.juicyrecipes.repository.UserRepository;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SingleUserService {

    private static final Integer SINGLE_USER_ID = 1;

    public Integer CURRENT_USER = 0;

    private final UserRepository userRepository;
    private final ContentsRepository contentsRepository;
    private final RecipeService recipeService;

    private Map<Integer, Integer> userIdMap = new HashMap<>();

    public RecipeUser getCurrentUser() {
        log.info("Current user is {}" ,CURRENT_USER);
        if (CURRENT_USER == 0)
            throw new RuntimeException("User is not found");

        Optional<RecipeUser> maybeSingleUser = userRepository.findById(userIdMap.get(CURRENT_USER));
        if (maybeSingleUser.isEmpty())
            throw new RuntimeException("User is not found");

        return maybeSingleUser.get();
    }

    public RecipeUser saveUser(RecipeUser user) {
        CURRENT_USER = user.getId();
        RecipeUser savedUser = userRepository.save(user);
        userIdMap.put(CURRENT_USER, savedUser.getId());
        return savedUser;
    }

    public Optional<RecipeUser> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    public RecipeUser addIngredient(Integer id, Contents contents) {
        Optional<RecipeUser> maybeUser = userRepository.findById(id);
        if (maybeUser.isEmpty())
            throw new RuntimeException("User with id is not found");

        RecipeUser user = maybeUser.get();
        Optional<Contents> ingredientContents = user.getAmountPresent()
                        .stream()
                        .filter(it -> it.getIngredient().equals(contents.getIngredient()))
                        .findFirst();
        if (ingredientContents.isEmpty()) {
            contents.setRecipeUser(user);
            Contents savedContents = contentsRepository.save(contents);
            user.addContents(savedContents);
        } else {
            Contents updatedIngredientContents = ingredientContents.get();
            updatedIngredientContents.setAmount(updatedIngredientContents.getAmount() + contents.getAmount());
            contentsRepository.save(updatedIngredientContents);
        }

        return userRepository.save(user);
    }

    public List<Recipe> findRecipesForUserIngredientContents() {
        Set<Recipe> allRecipes = recipeService.findAll();
        List<Contents> userIngredientContents = contentsRepository.findByRecipeUserId(SINGLE_USER_ID);

        return allRecipes.stream()
                .filter(it -> isAllMatchingIngredientContents(it, userIngredientContents))
                .toList();
    }

    private boolean isAllMatchingIngredientContents(Recipe recipe, List<Contents> userIngredientContents) {
        return recipe.getNecessaryAmount()
                .stream()
                .allMatch(it -> isMatchIngredientAndAmount(it, userIngredientContents));
    }

    private boolean isMatchIngredientAndAmount(Contents ingredientContents, List<Contents> userIngredientContents) {
        return userIngredientContents
                .stream()
                .anyMatch(it -> it.getIngredient().equals(ingredientContents.getIngredient())
                        && it.getAmount() >= ingredientContents.getAmount());
    }
}

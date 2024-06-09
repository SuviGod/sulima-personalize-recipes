package we.juicy.juicyrecipes.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import we.juicy.juicyrecipes.domain.Contents;
import we.juicy.juicyrecipes.domain.Recipe;
import we.juicy.juicyrecipes.domain.RecipeUser;
import we.juicy.juicyrecipes.service.IngredientService;
import we.juicy.juicyrecipes.service.RecipeService;
import we.juicy.juicyrecipes.service.RecommendationService;
import we.juicy.juicyrecipes.service.SingleUserService;

import java.util.List;
import java.util.Optional;

@Slf4j @RequiredArgsConstructor
@Controller @RequestMapping("/me")
public class UserController {

    private final SingleUserService userService;
    private final IngredientService ingredientService;
    private final RecommendationService recommendationService;
    private final RecipeService recipeService;

    @GetMapping
    public String getUser(Model model) {
        try {
            RecipeUser currentUser = userService.getCurrentUser();
            List<Integer> recommendedRecipes= recommendationService.getRecommendedItems(userService.CURRENT_USER);

            List<Recipe> matchedRecipes = recipeService.findManyById(recommendedRecipes);
            log.info("recipes is -> {}", matchedRecipes);

            model.addAttribute("user", currentUser);
            model.addAttribute("matchedRecipes", matchedRecipes);
        } catch (RuntimeException ex) {
            log.info("User is not found. Trying to create new.");
            log.info(ex.getMessage());
            return "redirect:/me/register";
        }

        return "user/profile";
    }

    @GetMapping("/register")
    public String tryCreatingUser(Model model) {
        log.info("Trying to create new user");
        model.addAttribute("user", new RecipeUser());
        return "user/registration";
    }

    @PostMapping("/register")
    public String saveProfile(@ModelAttribute("user") RecipeUser user) {
        log.info("Saving user -> {}", user);
        userService.saveUser(user);
        return "redirect:/me";
    }

    @GetMapping("/{userId}/ingredients/new")
    public String addNewIngredient(@PathVariable("userId") Integer userId, Model model ){
        model.addAttribute("ingredients", ingredientService.findAll());
        Optional<RecipeUser> maybeRecipeUser = getUserById(userId);

        model.addAttribute("content", new Contents());
        model.addAttribute("user", maybeRecipeUser.get());
        return "user/ingredient_contents_form";
    }

    private Optional<RecipeUser> getUserById(Integer userId) {
        Optional<RecipeUser> maybeRecipeUser = userService.findById(userId);
        if (maybeRecipeUser.isEmpty())
            throw new RuntimeException("User is not found");

        return maybeRecipeUser;
    }

    @PostMapping("/{userId}/ingredients/new")
    public String saveAddingNewIngredient(@ModelAttribute Contents contents , @PathVariable("userId") Integer userId, Model model){
        if (contents.getAmount() <= 0) {
            return addNewIngredient(userId, model);
        }
        userService.addIngredient(userId, contents);

        return "redirect:/me";
    }

    @GetMapping("{id}/show")
    public String getById(@PathVariable("id") Integer userId, Model model){
        Optional<RecipeUser> maybeRecipe = userService.findById(userId);
        if (maybeRecipe.isPresent()) {
            RecipeUser user = maybeRecipe.get();
            model.addAttribute("user", user);
            return "user/profile";
        }
        return "error";
    }
}

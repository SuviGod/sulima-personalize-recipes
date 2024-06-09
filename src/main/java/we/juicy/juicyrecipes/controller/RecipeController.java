package we.juicy.juicyrecipes.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import we.juicy.juicyrecipes.domain.Contents;
import we.juicy.juicyrecipes.domain.Recipe;
import we.juicy.juicyrecipes.service.IngredientService;
import we.juicy.juicyrecipes.service.RecipeService;

import java.util.Optional;
import java.util.Set;

@Slf4j @RequiredArgsConstructor
@Controller @RequestMapping(value = "/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @GetMapping("/all")
    public String getRecipe(Model model) {
        log.info("Requesting for all recipes");
        Set<Recipe> allRecipes = recipeService.findAll();
        model.addAttribute("recipes", allRecipes);
        return "recipe/all";
    }

    @GetMapping("/{id}/show")
    public String getRecipeById(@PathVariable("id") Integer id, Model model) {
        log.info("Requesting for recipe with id -> {}", id);
        Optional<Recipe> maybeRecipe = recipeService.findById(id);
        if (maybeRecipe.isPresent()) {
            Recipe recipe = maybeRecipe.get();
            model.addAttribute("recipe", recipe);
            model.addAttribute("contentsDifference", recipeService.findMissingIngredientAndAmount(id));
            return "recipe/show";
        }
        return "error";
    }

    @GetMapping(value = "/byName/{name}")
    public Recipe getRecipeByName(@PathVariable("name") String name){
        return recipeService.findOneByName(name);
    }

    @GetMapping(value = "/new")
    public String saveRecipe(Model model){
        model.addAttribute("recipe", new Recipe());
        log.info("Requesting to create new recipe");
        return "recipe/recipeform";
    }

    @PostMapping(value = "/")
    public String saveOrUpdate(@ModelAttribute Recipe recipeToSave){
        log.info("In post mapping method - creating recipe -> {}", recipeToSave.getName());
        Recipe savedRecipe = recipeService.save(recipeToSave);
        return "redirect:/recipe/" + savedRecipe.getId() + "/show";
    }

    @GetMapping("/{recipeId}/ingredients/new")
    public String addNewIngredient(@PathVariable("recipeId") Integer recipeId, Model model) {
        model.addAttribute("ingredients", ingredientService.findAll());
        Optional<Recipe> maybeRecipe = recipeService.findById(recipeId);
        if (maybeRecipe.isEmpty())
            return "error";

        model.addAttribute("content", new Contents());
        model.addAttribute("recipe", maybeRecipe.get());
        return "recipe/ingredient_contents_form";
    }

    @PostMapping("/{recipeId}/ingredients/new")
    public String saveAddingNewIngredient(@ModelAttribute Contents contents, @PathVariable("recipeId") Integer recipeId) {
        log.info("Saving ingredient contents for recipe -> {}, id -> {}", contents, recipeId);
        recipeService.addIngredient(recipeId, contents);

        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping("/all/ordered")
    public String getRecipesOrderedByDifficulty(Model model){
        //log.info("Requesting for all recipes ordered by Difficulty");
        Set<Recipe> allRecipesOrderedByDifficulty = recipeService.findByOrderByDifficulty();
        model.addAttribute("recipes", allRecipesOrderedByDifficulty);
        return "recipe/ordered";
    }
}

package we.juicy.juicyrecipes.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import we.juicy.juicyrecipes.domain.Ingredient;
import we.juicy.juicyrecipes.domain.IngredientCategory;
import we.juicy.juicyrecipes.service.IngredientCategoryService;
import we.juicy.juicyrecipes.service.IngredientService;

import java.util.Optional;
import java.util.Set;

@Slf4j @RequiredArgsConstructor
@Controller @RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;
    private final IngredientCategoryService ingredientCategoryService;

    @GetMapping(value ="/all")
    public String showAllIngredients(@RequestParam(required = false) String category, Model model) {
        log.info("Requesting for all ingredient categories");
        Set <IngredientCategory> ingredientCategories = ingredientCategoryService.findAll();
        model.addAttribute("categories", ingredientCategories);

        if (StringUtils.isEmptyOrWhitespace(category)) {
            log.info("Requesting for all ingredients");
            Set<Ingredient> allIngredients = ingredientService.findAll();
            model.addAttribute("ingredients", allIngredients);
            return "ingredient/all";
        }

        Set<Ingredient> allIngredients = ingredientService.findAllByCategory(category);
        model.addAttribute("ingredients", allIngredients);
        return "ingredient/all";
    }

    @GetMapping(value = "/{id}/show")
    public String showById(@PathVariable("id") Integer id, Model model ) {
        log.info("Requesting for ingredient with id -> {}", id);
        Optional<Ingredient> maybeIngredient = ingredientService.findById(id);
        if (maybeIngredient.isPresent()) {
            Ingredient ingredient = maybeIngredient.get();
            model.addAttribute("ingredient", ingredient);
            return "ingredient/show";
        }

        return "error";
    }

    @GetMapping(value = "/new")
    public String createIngredientForm(Model model) {
        log.info("Requesting for all ingredient categories");
        Set<IngredientCategory> allIngredientCategories = ingredientCategoryService.findAll();
        model.addAttribute("allCategories", allIngredientCategories);

        model.addAttribute("ingredient", new Ingredient());
        log.info("Going to ingredient/creation_form view");
        return "ingredient/creation_form";
    }

    @PostMapping(value = "/")
    public String updateIngredient(@ModelAttribute Ingredient ingredientToUpdate) {
        log.info("In ingredient post mapping method - creating new ingredient");
        Ingredient updatedIngredient = ingredientService.updateIngredient(ingredientToUpdate);
        return "redirect:/ingredient/" + updatedIngredient.getId() + "/show";
    }
}

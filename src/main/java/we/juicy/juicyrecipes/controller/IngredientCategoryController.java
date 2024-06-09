package we.juicy.juicyrecipes.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import we.juicy.juicyrecipes.domain.Ingredient;
import we.juicy.juicyrecipes.domain.IngredientCategory;
import we.juicy.juicyrecipes.service.IngredientCategoryService;
import we.juicy.juicyrecipes.service.IngredientService;

import java.util.Set;

@Slf4j @RequiredArgsConstructor
@Controller @RequestMapping("/ingredient-category")
public class IngredientCategoryController {

    private final IngredientCategoryService ingredientCategoryService;
    private final IngredientService ingredientService;

    @GetMapping("/new")
    public String createIngredientCategoryForm(Model model){
        log.info("Requesting for all ingredients");
        Set<Ingredient> allIngredients = ingredientService.findAll();
        model.addAttribute("allIngredients", allIngredients);
        model.addAttribute("category", new IngredientCategory());

        return "ingredientCategory/creation_form";
    }

    @PostMapping(value = "/")
    public String updateIngredientCategory(@ModelAttribute IngredientCategory ingredientCategoryToUpdate){
        log.info("In ingredient category post mapping method - creating new ingredient");
        ingredientCategoryService.updateIngredientCategory(ingredientCategoryToUpdate);
        return "redirect:/ingredient/all";
    }
}

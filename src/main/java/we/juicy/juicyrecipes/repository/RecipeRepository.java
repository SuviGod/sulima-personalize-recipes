package we.juicy.juicyrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import we.juicy.juicyrecipes.domain.Recipe;

import java.util.List;
import java.util.Set;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Recipe findOneByName(String name);
    Set<Recipe> findByOrderByDifficulty();

}

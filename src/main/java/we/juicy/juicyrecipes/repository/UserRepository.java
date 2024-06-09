package we.juicy.juicyrecipes.repository;

import org.springframework.data.repository.CrudRepository;
import we.juicy.juicyrecipes.domain.RecipeUser;


public interface UserRepository extends CrudRepository<RecipeUser, Integer> {
}

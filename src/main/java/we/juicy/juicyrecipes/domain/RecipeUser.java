package we.juicy.juicyrecipes.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RecipeUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "recipeUser")
    private List<Contents> amountPresent = new ArrayList<>();

    public void addContents(Contents contents){
        amountPresent.add(contents);
        contents.setRecipeUser(this);
    }

    @Override
    public String toString() {
        return "RecipeUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

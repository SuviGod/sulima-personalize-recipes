package we.juicy.juicyrecipes.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Lob
    private String description;

    @Enumerated(value = EnumType.ORDINAL)
    private Difficulty difficulty;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "recipe")
    private List<Contents> necessaryAmount = new ArrayList<>();

    public void addContents(Contents contents) {
        necessaryAmount.add(contents);
        contents.setRecipe(this);
    }
    @Override
    public String toString(){
        return "RecipeUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}

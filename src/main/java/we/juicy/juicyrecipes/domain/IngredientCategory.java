package we.juicy.juicyrecipes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class IngredientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "IngredientIngredientCategory",
            joinColumns = @JoinColumn(name = "categoryId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredientId", referencedColumnName = "id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    @Override
    public String toString() {
        return "IngredientCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

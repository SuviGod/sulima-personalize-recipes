package we.juicy.juicyrecipes.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TypeOfMeasure type;

    @ManyToMany(mappedBy = "ingredients")
    private List<IngredientCategory> categories = new ArrayList<>();

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", categories=" + categories +
                '}';
    }

    public void setCategoryRelation(){
        for(IngredientCategory category : this.getCategories()){
            category.getIngredients().add(this);
        }
    }
}

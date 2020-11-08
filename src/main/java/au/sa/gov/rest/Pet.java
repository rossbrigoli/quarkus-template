package au.sa.gov.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

//The entity class is also the repository to simplify things with panache
@Entity
@Cacheable
public class Pet extends PanacheEntity {

    //Using field access rewrite.
    //When your users read person.name they will actually call your getName() accessor, and similarly for field writes and the setter.
    //This allows for proper encapsulation at runtime as all fields calls will be replaced by the corresponding getter/setter calls.
    @Column(length = 20)
    public String species;
    public String breed;
    public String name;

    @Transient
    private final String speciesFormat = "%s species";

    public Pet() {
    }

    //You may use constructor as well. But not required as you may just set the fields public
    public Pet(String species, String breed, String name) {
        this.species = species;
        this.breed = breed;
        this.name = name;
    }

    //You can still create accessors, at runtime the calls to pet.breed is implicitly mapped to this getter
    public String getSpecies() {
        return String.format(speciesFormat, this.species);
    }

    public static List<Pet> findByBreed(String breed) {
        return list("breed", breed);
    }

    public static Pet findByName(String name) {
        return find("name", name).firstResult();
    }

    public static List<String> getListOfBreeds() {
        return streamAll().map(p -> ((Pet) p).breed).collect(Collectors.toList());
    }
}

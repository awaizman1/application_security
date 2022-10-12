package resource_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import resource_server.generated.api.PetsApi;
import resource_server.generated.model.Pet;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PetsApiController implements PetsApi {

    private final List<Pet> pets;

    public PetsApiController() {
        pets = new ArrayList<>();
        pets.add(new Pet()
                .id(1)
                .name("snoopy")
                .type(Pet.TypeEnum.DOG));
        pets.add(new Pet()
                .id(2)
                .name("kitty")
                .type(Pet.TypeEnum.CAT));
    }

    @Override
    public ResponseEntity<List<Pet>> petsGet() {

        return ResponseEntity.ok(pets);
    }

    @Override
    public ResponseEntity<Void> petsPost(Pet pet) {

        pets.add(pet);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
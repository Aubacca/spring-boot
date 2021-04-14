package ch.rohner.demo.springboot.api;

import ch.rohner.demo.springboot.models.Gender;
import ch.rohner.demo.springboot.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    public UserController() {
        users.add(new User(1, "rohnerp", "Peter", "Rohner",
                LocalDate.of(1967, 9, 12), Gender.MALE));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long userId) {
        User foundUser = users.stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElse(new User());
        return ResponseEntity.ok(foundUser);
    }
}

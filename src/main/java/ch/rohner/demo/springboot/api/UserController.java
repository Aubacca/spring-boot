package ch.rohner.demo.springboot.api;

import ch.rohner.demo.springboot.models.Gender;
import ch.rohner.demo.springboot.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {
    private List<User> users = new ArrayList<>();

    public UserController() {
        users.add(new User(1, "user1", "Peter", "Brown", LocalDate.of(1967, 9, 12), Gender.MALE));
        users.add(new User(2, "user2", "Claudia", "Smith", LocalDate.of(1965, 11, 5), Gender.FEMALE));
        users.add(new User(3, "guest5", "Tom", "Jones", LocalDate.of(1958, 4, 1), Gender.MALE));
    }

    private User findUserById(long id) {
        log.info("findUserById>id: {}", id);
        User foundUser = users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(new User());
        log.info("findUserById>foundUser: {}", foundUser);
        return foundUser;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long userId) {
        User foundUser = findUserById(userId);
        return ResponseEntity.ok(foundUser);
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user) {
        user.setId(users.size() + 1);
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> update(@PathVariable("id") long id, @RequestBody User user) {
        log.info("update>id: {}, user: {}", id, user);
        User oldUser = findUserById(id);
        if (oldUser != null) {
            int oldUserPosition = users.indexOf(oldUser);
            users.remove(oldUserPosition);
            log.info("update>users: {}", users);
            //
            user.setId(id);
            users.add(oldUserPosition, user);
            log.info("update>users: {}", users);
        }
        log.info("update>users: {}", users);
        log.info("update<user: {}", user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") long id) {
        log.info("delete>id: {}", id);
        User oldUser = findUserById(id);
        if (oldUser != null) {
            int oldUserPosition = users.indexOf(oldUser);
            users.remove(oldUserPosition);
        }
        log.info("delete<users: {}", users);
        return ResponseEntity.ok(oldUser);
    }
}

package io.github.mhsscel.bookjavaapi.controller.v1.user;

import io.github.mhsscel.bookjavaapi.dto.model.user.UserDTO;
import io.github.mhsscel.bookjavaapi.model.UserModel;
import io.github.mhsscel.bookjavaapi.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    private UserController(UserServiceImpl userService) {
        this.userService = userService;

    }

    //WORKS
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        Optional<UserModel> user = userService.findUserByUsername(userDTO.getUsername());
        if (!user.isPresent()) {
            userService.save(new UserModel().convertDTOToEntity(userDTO));
            return ResponseEntity.ok("User Created");
        }
        return ResponseEntity.badRequest().body("Username taken");
    }

    /**
     * @param userId
     * @param
     * @return Deletes the user from the DB
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        Optional<UserModel> user = userService.findUserById(userId);
        if (user.isPresent()) {
            userService.delete(userId);
            return ResponseEntity.ok("User deleted");
        }
        return ResponseEntity.badRequest().body("User not found"); // change to user not found
    }


    /**
     * @param isEnabled
     * @return set the user to active/inactive
     * WORKS
     */

    @PatchMapping("/{userId}/enable")
    public ResponseEntity<?> enableUser(@PathVariable(value = "userId") Long userId,
                                        @RequestParam(value = "enable") Boolean isEnabled) {

        Optional<UserModel> user = userService.findUserById(userId);
        if (user.isPresent()) {
            userService.enableUser(userId, isEnabled);
            if (isEnabled.equals(true)) {
                return ResponseEntity.ok("User is active");
            } else if (isEnabled.equals(false)) {
                return ResponseEntity.ok("User is inactive");
            }
        }
        return ResponseEntity.badRequest().body("User not found!");
    }


    /**
     * @return all DTO USERS
     */

    @GetMapping
    public ResponseEntity<?> getUsers(Principal p) {
        Optional<UserModel> currentUser = userService.findUserByUsername(p.getName());
        if (currentUser.isPresent() && currentUser.get().getEnabled() == true) {
            List<UserDTO> allUsers = userService.findAllUsers();
            if (!allUsers.isEmpty()) {
                return ResponseEntity.ok(allUsers);
            }
            return ResponseEntity.badRequest().body("No users found!");
        }
        return ResponseEntity.badRequest().body("Your account is temporary disabled!");
    }
}

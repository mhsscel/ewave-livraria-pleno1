package io.github.mhsscel.bookjavaapi.service.user;

import io.github.mhsscel.bookjavaapi.dto.model.user.UserDTO;
import io.github.mhsscel.bookjavaapi.model.UserModel;

import java.util.List;
import java.util.Optional;


public interface UserService {

  List<UserDTO> findAllUsers();

  void save(UserModel user);

  void delete(Long userId);

  Optional<UserModel> findUserById(Long id);

  Optional<UserModel> findUserByUsername(String Username);

  void enableUser(Long userId, Boolean isEnabled);

}

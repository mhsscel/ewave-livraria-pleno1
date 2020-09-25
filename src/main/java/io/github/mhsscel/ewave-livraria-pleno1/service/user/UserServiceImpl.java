package io.github.mhsscel.bookjavaapi.service.user;

import io.github.mhsscel.bookjavaapi.dto.model.user.UserDTO;
import io.github.mhsscel.bookjavaapi.model.UserModel;
import io.github.mhsscel.bookjavaapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements io.github.mhsscel.bookjavaapi.service.user.UserService {
    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;
    private final RatingRepository ratingRepository;
    private final RecentRepository recentRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, LibraryRepository libraryRepository, RatingRepository ratingRepository, RecentRepository recentRepository) {
        this.userRepository = userRepository;
        this.libraryRepository = libraryRepository;
        this.ratingRepository = ratingRepository;
        this.recentRepository = recentRepository;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserModel> users = userRepository.findAll();

        List<UserDTO> dtoWithStatusUsers = new ArrayList<>();
        if (!users.isEmpty()) {
            for (UserModel u : users) {
                dtoWithStatusUsers.add(u.convertEntityToDTO());
            }
        }
        return dtoWithStatusUsers;
    }

    @Override
    public void save(UserModel user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long userId) {
        /*if (libraryRepository.containsUser(userId)) {
            libraryRepository.deleteUser(userId);
        }
        if (ratingRepository.containsUser(userId)) {
            ratingRepository.deleteUser(userId);
        }
        if (recentRepository.containsUser(userId)) {
            recentRepository.deleteUser(userId);
        }
        if (userRepository.containsUser(userId)) {
            userRepository.deleteUser(userId);
        }*/
    }

    @Override
    public Optional<UserModel> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<UserModel> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void enableUser(Long userId, Boolean isEnabled) {
       // userRepository.enableUser(userId, isEnabled);
    }


    // used for test only
    public void deleteByUsername(String username) {
        //userRepository.deleteByUsername(username);
    }

}

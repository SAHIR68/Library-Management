package user.service;

import user.dto.UserDTO;

import java.util.List;

public interface UserService {
    void addUser(UserDTO userDTO);

    void readUser(Integer userId);

    UserDTO updateUser(Integer userId);

    void deleteUser(Integer userId);
    List<UserDTO> getAllUser();
    void printAllUser();
}

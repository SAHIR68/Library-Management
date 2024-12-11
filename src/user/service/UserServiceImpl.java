package user.service;

import user.dao.UserDAO;
import user.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserServiceImpl implements UserService {

    @Override
    public void addUser(UserDTO userDTO) {
        UserDAO userDAO = new UserDAO();
        userDAO.addUserToDatabase(userDTO);
    }

    @Override
    public void readUser(Integer userId) {
        UserDTO userDTO = new UserDTO();
        userDTO = searchUserDTO(userId);
        if (userDTO.getId() == null) {
            System.out.println("There is not any user with this ID in this library!!");
            userDTO = null;
        } else
            System.out.println(userDTO);
    }

    @Override
    public UserDTO updateUser(Integer userId) {
        UserDTO userDTO = new UserDTO();
        userDTO = searchUserDTO(userId);
        return userDTO;
    }

    @Override
    public void deleteUser(Integer userId) {
        UserDTO userDTO = new UserDTO();
        userDTO = searchUserDTO(userId);
        userDTO.setId(0);
        userDTO.setSsn(null);
        userDTO.setName(null);
        userDTO.setAge(null);
    }

    @Override
    public List<UserDTO> getAllUser() {
        UserDAO userDAO = new UserDAO();
        return userDAO.getUserDTOSFromDatabase();
    }

    @Override
    public void printAllUser() {
        for (UserDTO userDTO : getAllUser()) {
            System.out.println(userDTO);
        }
    }

    public UserDTO searchUserDTO(Integer userId) {
        UserDTO userDTO = new UserDTO();
        UserDAO userDAO = new UserDAO();
        userDTO = userDAO.findUserById(userId);
        userDTO.setId(userId);
        return userDTO;
    }

    public Integer searchUserName(String name) {
        Integer Id = 0;
        UserDAO userDAO = new UserDAO();
        List<UserDTO> allUserDTO = new ArrayList<>();
        allUserDTO = userDAO.getUserDTOSFromDatabase();
        for (UserDTO userDTO : allUserDTO) {
            if (Objects.equals(userDTO.getName(), name)) {
                Id = userDTO.getId();
                break;
            } else
                Id = 0;
        }
        return Id;
    }

}

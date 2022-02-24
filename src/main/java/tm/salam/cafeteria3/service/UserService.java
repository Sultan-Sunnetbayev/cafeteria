package tm.salam.cafeteria3.service;

import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dto.LoginDTO;
import tm.salam.cafeteria3.dto.UserDTO;
import tm.salam.cafeteria3.models.User;

public interface UserService {

    ResponseTransfer UpdateProfileUser(UserDTO userDTO, int id);

    ResponseTransfer CreateNewAdmin(UserDTO userDTO);

    ResponseTransfer CreateNewSeller(UserDTO sellerDTO);

    boolean RemoveUserById(int id);

    boolean findUserByUsername(String username);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    UserDTO getUserDTOById(int id);

    LoginDTO getLoginDTOById(int id);

    User getUserById(int id);
}

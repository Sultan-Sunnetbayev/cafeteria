package tm.salam.cafeteria3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dao.UserRepository;
import tm.salam.cafeteria3.dao.RoleRepository;
import tm.salam.cafeteria3.dto.LoginDTO;
import tm.salam.cafeteria3.dto.UserDTO;
import tm.salam.cafeteria3.models.Role;
import tm.salam.cafeteria3.models.Status;
import tm.salam.cafeteria3.models.User;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public ResponseTransfer CreateNewAdmin(UserDTO adminDTO) {

        User helper1 = userRepository.findFirstByUsername(adminDTO.getUsername());
        User helper2 = userRepository.findFirstByEmail(adminDTO.getEmail());

        if (helper1 != null || helper2 != null) {

            return new ResponseTransfer("username or email already present", false);
        }
        Role role = roleRepository.findByName("ROLE_ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        User admin = User.builder()
                .imagePath(adminDTO.getImagePath())
                .username(adminDTO.getUsername())
                .password(passwordEncoder.encode(adminDTO.getPassword()))
                .email(adminDTO.getEmail())
                .status(Status.ACTIVE)
                .roles(roles)
                .build();

        userRepository.save(admin);

        if (userRepository.findFirstByUsername(admin.getUsername()) == null) {

            return new ResponseTransfer("admin don't created", false);
        } else {

            return new ResponseTransfer("success", true);
        }
    }

    @Override
    @Transactional
    public ResponseTransfer UpdateProfileUser(UserDTO userDTO, int id) {


        User savedUser = userRepository.findById(id);

        if (savedUser == null) {

            return new ResponseTransfer("user not found", false);
        }

        if (!Objects.equals(userDTO.getUsername(), savedUser.getUsername())) {

            if (userRepository.findFirstByUsername(userDTO.getUsername()) != null) {

                return new ResponseTransfer("this username already present", false);
            }
        }
        if (!Objects.equals(userDTO.getEmail(), savedUser.getEmail())) {

            if (userRepository.findFirstByEmail(userDTO.getEmail()) != null) {

                return new ResponseTransfer("this email already present", false);
            }
        }
        savedUser.setUsername(userDTO.getUsername());
        savedUser.setEmail(userDTO.getEmail());
        savedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (!Objects.equals(userDTO.getImagePath(), savedUser.getImagePath())) {
            savedUser.setImagePath(userDTO.getImagePath());
        }

        userRepository.save(savedUser);

        return new ResponseTransfer("success", true);
    }

    @Override
    @Transactional
    public ResponseTransfer CreateNewSeller(UserDTO sellerDTO) {

        User helper1 = userRepository.findFirstByUsername(sellerDTO.getUsername());
        User helper2 = userRepository.findFirstByEmail(sellerDTO.getEmail());

        if (helper1 != null || helper2 != null) {

            return new ResponseTransfer("username or email already present", false);
        }
        Role role = roleRepository.findByName("ROLE_SELLER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User seller = User.builder()
                .username(sellerDTO.getUsername())
                .password(sellerDTO.getPassword())
                .imagePath(sellerDTO.getImagePath())
                .email(sellerDTO.getEmail())
                .status(Status.ACTIVE)
                .roles(roles)
                .build();

        seller.setPassword(passwordEncoder.encode(sellerDTO.getPassword()));
        userRepository.save(seller);

        if (userRepository.findFirstByUsername(seller.getUsername()) == null) {

            return new ResponseTransfer("seller don't created", false);
        } else {

            return new ResponseTransfer("success", true);
        }
    }

    @Override
    @Transactional
    public boolean RemoveUserById(int id) {

        User user = userRepository.findById(id);
        System.out.println(user.getRoles().get(0).getName());
        if (user == null || Objects.equals(user.getRoles().get(0).getName(), "ROLE_ADMIN")) {

            return false;
        }
        userRepository.deleteById(id);

        return true;

    }

    @Override
    public boolean findUserByUsername(String username) {

        User user = userRepository.findFirstByUsername(username);

        if (user == null) {

            return false;
        } else {

            return true;
        }
    }

    @Override
    public User getUserByUsername(String username) {

        if (this.findUserByUsername(username)) {

            return userRepository.findFirstByUsername(username);
        } else {

            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {

        User user = userRepository.findFirstByEmail(email);

        if (user == null) {

            return null;
        } else {

            return user;
        }
    }


    @Override
    public UserDTO getUserDTOById(int id) {

        User user = userRepository.findById(id);

        if (user == null) {

            return null;
        }

        return UserDTO.builder()
                .imagePath(user.getImagePath())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }

    @Override
    public LoginDTO getLoginDTOById(int id) {

        User user = userRepository.findById(id);
        if (user == null) {

            return null;
        } else {
            List<String> roles = new ArrayList<>();

            for (Role role : user.getRoles()) {
                roles.add(role.getName());
            }

            return LoginDTO.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .roles(roles)
                    .build();
        }
    }

    @Override
    public User getUserById(int id) {

        User user = userRepository.findFirstById(id);

        if (user == null) {

            return null;
        } else {

            return user;
        }
    }

}
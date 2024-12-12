package homework4.UserService;

import homework4.dao.UserDao;
import homework4.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(String username) {
        User user = new User();
        user.setUsername(username);
        userDao.createUser(user);
    }

    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    public Long getUserIdByUsername(String username) {
        return userDao.getUserIdByUsername(username);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void updateUser(Long id, String username) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        userDao.updateUser(user);
    }

    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }
}

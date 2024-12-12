package homework4;

import homework4.UserService.UserService;
import homework4.config.DataSourceConfig;
import homework4.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfig.class);

        UserService userService = context.getBean(UserService.class);

        // CRUD операции
        userService.createUser("Игорь Николаев");

        Long id = userService.getUserIdByUsername("Игорь Николаев");
        User user = userService.getUserById(id);
        System.out.println("Наш юзер: " + user.getUsername());

        userService.updateUser(id, "Николаев Игорь");
        user = userService.getUserById(id);
        System.out.println("Наш юзер: " + user.getUsername());

        userService.deleteUser(id);

        context.close();
    }
}


import model.User;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ApiService service = ApiService.getInstance();
        User user = new User("Alex", 30);
        System.out.println(service.create(user));
        System.out.println(service.read(0));
        System.out.println(service.update(user));
        System.out.println(service.delete(0));
        for (int i = 0; i < 10; i++) {
            User tmp = new User("Name", 0);
            service.create(tmp);
        }
        System.out.println(service.getAllUsers());
    }
}

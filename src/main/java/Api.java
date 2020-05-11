import model.User;

import java.io.IOException;
import java.util.ArrayList;

public interface Api {

    String create(User user) throws IOException;

    User read(int targetUserId) throws IOException;

    String update(User user) throws IOException;

    String delete(int targetUserId) throws IOException;

    ArrayList<User> getAllUsers() throws IOException;
}

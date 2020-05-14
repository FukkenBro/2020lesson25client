import model.Image;

import java.io.IOException;
import java.util.Set;

public interface Api {

    String create(Image image) throws IOException;

    Image read(int targetImageId) throws IOException;

    String update(Image image) throws IOException;

    String delete(int targetImageId) throws IOException;

    Set<Integer> getAllImages() throws IOException;
}

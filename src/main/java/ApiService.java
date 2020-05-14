import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Image;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ApiService implements Api {

    private static ApiService instance;

    public static ApiService getInstance() {
        if (ApiService.instance == null) {
            if (ApiService.instance == null) {
                ApiService.instance = new ApiService();
            }
        }
        return ApiService.instance;
    }

    private String ip = "127.0.0.1";
    private String port = "";
    private String servletName = "my-api";
    private String imageModuleName = "image";

    public String create(Image image) throws IOException {
        String apiName = "newImage";
        String url = urlGen(apiName);
        String body = serializeImage(image);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        MediaType type = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(type, body);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody)
                .build();
        String result = Objects.requireNonNull(client.newCall(request).execute().body()).string();
        return result;
    }

    public String createFromFile() throws IOException {
        String b64 = loadFromFile();
        Image image = new Image(b64);
        String apiName = "newImage";
        String url = urlGen(apiName);
        String body = serializeImage(image);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        MediaType type = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(type, body);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody)
                .build();
        String result = Objects.requireNonNull(client.newCall(request).execute().body()).string();
        return result;
    }

    public Image read(int targetImageId) throws IOException {
        String apiName = "";
        String url = urlGen(apiName);
        StringBuilder sb = new StringBuilder(url);
        sb.append(targetImageId);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(sb.toString())
                .method("GET", null)
                .build();
        String result = Objects.requireNonNull(client.newCall(request).execute().body()).string();
        Image image = deserializeImage(result);
        return image;
    }

    public String update(Image image) throws IOException {
        String apiName = "updateImage";
        String url = urlGen(apiName);
        String body = serializeImage(image);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        MediaType type = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(type, body);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody)
                .build();
        String result = Objects.requireNonNull(client.newCall(request).execute().body()).string();
        return result;
    }

    public String delete(int targetImageId) throws IOException {
        String apiName = "delete";
        String url = urlGen(apiName);
        StringBuilder sb = new StringBuilder(url);
        sb.append(targetImageId);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(sb.toString())
                .method("GET", null)
                .build();
        String result = Objects.requireNonNull(client.newCall(request).execute().body()).string();
        return result;
    }

    public String deleteAll() throws IOException {
        String apiName = "deleteAll";
        String url = urlGen(apiName);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url.toString())
                .method("GET", null)
                .build();
        String result = Objects.requireNonNull(client.newCall(request).execute().body()).string();
        return result;
    }

    public Set<Integer> getAllImages() throws IOException {
        String apiName = "getAllImages";
        String url = urlGen(apiName);
        Type imagesType = new TypeToken<Set<Integer>>() {
        }.getType();
        Set<Integer> images = new Gson().fromJson(getJson(url.toString()), imagesType);
        return images;
    }

    private String urlGen(String apiName) {
        StringBuilder url = new StringBuilder("http://" + ip + ":" + port);
        url.append("/" + servletName + "/" + imageModuleName + "/" + apiName);
        return url.toString();
    }

    private String serializeImage(Image image) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(image);
    }

    private Image deserializeImage(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, Image.class);
    }

    private String getJson(String url) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .callTimeout(120, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        String result = Objects.requireNonNull(client.newCall(request).execute().body()).string();
        return result;
    }

    String loadFromFile() throws IOException {
        FileDialog dialog = new FileDialog((Frame) null, "Select Save File to Load");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String path = dialog.getDirectory() + dialog.getFile();
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String encodedFile = Base64.getEncoder().encodeToString(bytes);
        return encodedFile;
    }

}

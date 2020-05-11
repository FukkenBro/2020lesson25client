import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private String userModuleName = "user";

    public String create(User user) throws IOException {
        String apiName = "newUser";
        String url = urlGen(apiName);
        String body = serializeUser(user);
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

    public User read(int targetUserId) throws IOException {
        String apiName = "";
        String url = urlGen(apiName);
        StringBuilder sb = new StringBuilder(url);
        sb.append(targetUserId);
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
        User user = deserializeUser(result);
        return user;
    }

    public String update(User user) throws IOException {
        String apiName = "updateUser";
        String url = urlGen(apiName);
        String body = serializeUser(user);
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

    public String delete(int targetUserId) throws IOException {
        String apiName = "delete";
        String url = urlGen(apiName);
        StringBuilder sb = new StringBuilder(url);
        sb.append(targetUserId);
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

    public ArrayList<User> getAllUsers() throws IOException {
        String apiName = "getAllUsers";
        String url = urlGen(apiName);
        Type usersType = new TypeToken<List<User>>() {
        }.getType();
        ArrayList<User> users = new Gson().fromJson(getJson(url.toString()), usersType);
        return users;
    }

    private String urlGen(String apiName) {
        StringBuilder url = new StringBuilder("http://" + ip + ":" + port);
        url.append("/" + servletName + "/" + userModuleName + "/" + apiName);
        return url.toString();
    }

    private String serializeUser(User user) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(user);
    }

    private User deserializeUser(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, User.class);
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

}

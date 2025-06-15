import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private static final UserService instance = new UserService();

    private final Map<String, String> userMap = new HashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final File file = new File("users.json");

    private UserService() {
        loadFromFile();
    }
    
    public static UserService getInstance() {
        return instance;
    }

    public boolean register(String id, String password) {
        if (userMap.containsKey(id)) return false;
        userMap.put(id, password);
        saveToFile();
        return true;
    }

    public boolean login(String id, String pw) {
        return userMap.containsKey(id) && userMap.get(id).equals(pw);
    }

    private void saveToFile() {
        try {
            objectMapper.writeValue(file, userMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        if (!file.exists()) return;
        try {
            Map<String, String> loaded = objectMapper.readValue(file, new TypeReference<>() {});
            userMap.putAll(loaded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

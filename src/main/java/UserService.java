import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    // 싱글톤 인스턴스 생성 (객체 1개만 유지)
    private static final UserService instance = new UserService();

    // 사용자 정보를 저장할 맵 (id -> password)
    private final Map<String, String> userMap = new HashMap<>();

    // JSON 직렬화/역직렬화를 위한 ObjectMapper
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 사용자 정보를 저장할 JSON 파일
    private final File file = new File("users.json");

    // 생성자: 프로그램 시작 시 파일에서 사용자 정보 불러오기
    private UserService() {
        loadFromFile();
    }

    // 외부에서 인스턴스를 가져오는 메서드
    public static UserService getInstance() {
        return instance;
    }

    // 회원가입: id가 중복되지 않으면 저장하고 true 반환, 중복이면 false 반환
    public boolean register(String id, String password) {
        if (userMap.containsKey(id)) return false;
        userMap.put(id, password);
        saveToFile(); // JSON 파일로 저장
        return true;
    }

    // 로그인: id가 존재하고, 비밀번호가 일치하면 true 반환
    public boolean login(String id, String pw) {
        return userMap.containsKey(id) && userMap.get(id).equals(pw);
    }

    // userMap을 JSON 파일로 저장
    private void saveToFile() {
        try {
            objectMapper.writeValue(file, userMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // JSON 파일이 존재하면 사용자 정보 불러오기
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

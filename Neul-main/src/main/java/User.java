// 사용자 정보 클래스 (Jackson 직렬화용 POJO)
public class User {
    private String username;
    private String password; // 해시된 비밀번호 저장

    public User() {} // Jackson 역직렬화를 위한 기본 생성자

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // 입력받은 비밀번호가 저장된 해시와 일치하는지 확인
    public boolean checkPassword(String inputPassword) {
        String hashedInput = PasswordUtil.hashPassword(inputPassword);
        return this.password.equals(hashedInput);
    }
}

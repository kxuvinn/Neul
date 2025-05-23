// 현재 로그인된 사용자 상태를 관리하는 클래스

public class AuthManager {
    private static User currentUser;

    // 로그인된 사용자 저장
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    // 로그아웃 처리
    public static void logout() {
        currentUser = null;
    }

    // 로그인 여부 확인
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    // 현재 로그인된 사용자 반환
    public static User getCurrentUser() {
        return currentUser;
    }
}

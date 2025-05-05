package API_Tests.helpers;

public class ApiToken {
    private static String token;

    public static String getToken() {
        if (token == null || isTokenExpired()) {
            refreshToken();
        }
        return token;
    }

    public static void refreshToken() {
        AuthHelper authHelper = new AuthHelper();
        token = authHelper.authAndGetToken("leonardo", "leads"); // пользватель
    }

    private static boolean isTokenExpired() {
       return true;
    }
}

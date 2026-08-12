package uet.ltnc.arkanoidgame;

public class ThemeManager {

    private static String currentTheme = "Theme1";

    public static String getCurrentTheme() {
        return currentTheme;
    }

    public static void setCurrentTheme(String themeName) {
        if (themeName == null || themeName.isEmpty()) {
            return;
        }

        currentTheme = themeName;
    }

    public static String getImagePath(String assetPath) {
        return "/Images/" + currentTheme + "/" + assetPath;
    }
}
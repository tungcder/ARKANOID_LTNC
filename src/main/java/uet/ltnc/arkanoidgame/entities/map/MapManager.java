package uet.ltnc.arkanoidgame.entities.map;

public class MapManager {

    private final String[] levelPaths = {
            "/Levels/Map1.csv",
            "/Levels/Map2.csv",
            "/Levels/Map3.csv"
    };

    private int currentLevel;

    public MapManager() {
        currentLevel = 0;
    }

    public int[][] loadCurrentMap() {
        return MapLoader.loadMap(levelPaths[currentLevel]);
    }

    public boolean hasNextLevel() {
        return currentLevel < levelPaths.length - 1;
    }

    public boolean nextLevel() {
        if (!hasNextLevel()) {
            return false;
        }

        currentLevel++;
        return true;
    }

    public void reset() {
        currentLevel = 0;
    }

    public int getCurrentLevelNumber() {
        return currentLevel + 1;
    }

    public int getTotalLevels() {
        return levelPaths.length;
    }

    public String getCurrentMapPath() {
        return levelPaths[currentLevel];
    }
}
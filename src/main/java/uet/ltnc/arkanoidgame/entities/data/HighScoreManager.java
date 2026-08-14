package uet.ltnc.arkanoidgame.entities.data;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreManager {
    private static final String HIGH_SCORE_FILE = "highscores.txt";
    private static final int MAX_HIGH_SCORES = 10;

    /**
     * Lưu high score (không trả về hạng).
     * Giữ lại cho tương thích ngược với các nơi đang gọi hàm này.
     */
    public static void saveHighScore(String playerName, int score, int timeInSeconds, int levelsCompleted, boolean gameCompleted) {
        saveHighScoreAndGetRank(playerName, score, timeInSeconds, levelsCompleted, gameCompleted);
    }

    public static void saveHighScore(int score, int timeInSeconds, int levelsCompleted, boolean gameCompleted) {
        saveHighScoreAndGetRank("Player", score, timeInSeconds, levelsCompleted, gameCompleted);
    }

    /**
     * Lưu high score và trả về hạng THẬT SỰ sau khi đã thêm vào danh sách và sắp xếp.
     * Ưu tiên dùng hàm này thay vì gọi getRank() rồi saveHighScore() riêng lẻ,
     * vì gọi 2 lần có thể cho ra hạng không khớp khi có điểm bằng nhau (tie),
     * và tránh đọc file 2 lần không cần thiết.
     *
     * @return hạng (rank) của điểm vừa lưu, tính từ 1
     */
    public static int saveHighScoreAndGetRank(String playerName, int score, int timeInSeconds, int levelsCompleted, boolean gameCompleted) {
        int rank = -1;
        try {
            List<HighScore> scores = loadHighScores();

            HighScore newScore = new HighScore(playerName, score, timeInSeconds, levelsCompleted, gameCompleted);
            scores.add(newScore);
            Collections.sort(scores);

            rank = scores.indexOf(newScore) + 1;

            if (scores.size() > MAX_HIGH_SCORES) {
                scores = scores.subList(0, MAX_HIGH_SCORES);
            }

            saveToFile(scores);

            System.out.println("✅ Đã lưu high score (" + newScore.getPlayerName() + "): " + score + " điểm, hạng #" + rank);
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi lưu high score: " + e.getMessage());
            e.printStackTrace();
        }
        return rank;
    }

    public static List<HighScore> loadHighScores() {
        List<HighScore> scores = new ArrayList<>();

        File file = new File(HIGH_SCORE_FILE);
        if (!file.exists()) {
            return scores;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                HighScore score = HighScore.fromFileLine(line);
                if (score != null) {
                    scores.add(score);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi tải high scores: " + e.getMessage());
        }

        return scores;
    }

    private static void saveToFile(List<HighScore> scores) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {
            for (HighScore score : scores) {
                writer.write(score.toFileLine());
                writer.newLine();
            }
        }
    }

    public static void clearHighScores() {
        File file = new File(HIGH_SCORE_FILE);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("✅ Đã xóa tất cả high scores");
            } else {
                System.err.println("❌ Không thể xóa file high scores");
            }
        }
    }

    public static boolean isHighScore(int score) {
        List<HighScore> scores = loadHighScores();
        if (scores.size() < MAX_HIGH_SCORES) {
            return true;
        }
        return score > scores.get(scores.size() - 1).getScore();
    }

    public static int getRank(int score) {
        List<HighScore> scores = loadHighScores();
        for (int i = 0; i < scores.size(); i++) {
            if (score > scores.get(i).getScore()) {
                return i + 1;
            }
        }
        return scores.size() + 1;
    }
}
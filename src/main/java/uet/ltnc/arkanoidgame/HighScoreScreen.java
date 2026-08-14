package uet.ltnc.arkanoidgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import uet.ltnc.arkanoidgame.entities.data.HighScore;
import uet.ltnc.arkanoidgame.entities.data.HighScoreManager;

import java.util.List;

public class HighScoreScreen extends StackPane {
    private static final String GOLD_COLOR = "#FFD700";
    private static final String CYAN_COLOR = "#00FFFF";

    private final Runnable onBack;

    public HighScoreScreen(Runnable backAction) {
        this.onBack = backAction;
        setPrefSize(800, 600);
        setupUI();
    }

    private void setupUI() {
        setStyle("-fx-background-color: linear-gradient(to bottom, #0a0a1e, #1e0a32);");

        VBox mainBox = new VBox(20);
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.setPadding(new Insets(30, 50, 30, 50));

        Label titleLabel = createTitle();
        VBox scoresList = createScoresList();

        ScrollPane scrollPane = new ScrollPane(scoresList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPrefHeight(420);

        Button backButton = createBackButton();

        mainBox.getChildren().addAll(titleLabel, scrollPane, backButton);
        getChildren().add(mainBox);
    }

    private Label createTitle() {
        Label label = new Label("🏆 HIGH SCORES 🏆");
        label.setFont(Font.font("System", FontWeight.BOLD, 48));
        label.setTextFill(Color.web(GOLD_COLOR));

        DropShadow glow = new DropShadow(20, Color.web(GOLD_COLOR, 0.8));
        label.setEffect(glow);

        return label;
    }

    private VBox createScoresList() {
        VBox list = new VBox(10);
        list.setAlignment(Pos.TOP_CENTER);
        list.setPadding(new Insets(10));

        List<HighScore> scores = HighScoreManager.loadHighScores();

        if (scores.isEmpty()) {
            Label emptyLabel = new Label("Chưa có điểm số nào");
            emptyLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
            emptyLabel.setTextFill(Color.web("#888888"));
            list.getChildren().add(emptyLabel);
        } else {
            HBox header = createHeaderRow();
            list.getChildren().add(header);

            for (int i = 0; i < scores.size(); i++) {
                HBox scoreRow = createScoreRow(i + 1, scores.get(i));
                list.getChildren().add(scoreRow);
            }
        }

        return list;
    }

    private HBox createHeaderRow() {
        HBox header = new HBox(8);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));
        header.setStyle(
                "-fx-background-color: rgba(0, 255, 255, 0.1);" +
                        "-fx-border-color: rgba(0, 255, 255, 0.3);" +
                        "-fx-border-width: 0 0 2 0;"
        );

        Label rank = createHeaderLabel("#", 35);
        Label name = createHeaderLabel("TÊN", 120);
        Label score = createHeaderLabel("ĐIỂM", 90);
        Label time = createHeaderLabel("THỜI GIAN", 90);
        Label level = createHeaderLabel("MÀN", 55);
        Label status = createHeaderLabel("TRẠNG THÁI", 110);
        Label date = createHeaderLabel("NGÀY", 150);

        header.getChildren().addAll(rank, name, score, time, level, status, date);
        return header;
    }

    private Label createHeaderLabel(String text, double width) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 14));
        label.setTextFill(Color.web(CYAN_COLOR));
        label.setPrefWidth(width);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private HBox createScoreRow(int rank, HighScore highScore) {
        HBox row = new HBox(8);
        row.setAlignment(Pos.CENTER);
        row.setPadding(new Insets(10));

        String bgColor;
        String textColor;
        if (rank == 1) {
            bgColor = "rgba(255, 215, 0, 0.2)";
            textColor = GOLD_COLOR;
        } else if (rank == 2) {
            bgColor = "rgba(192, 192, 192, 0.2)";
            textColor = "#C0C0C0";
        } else if (rank == 3) {
            bgColor = "rgba(205, 127, 50, 0.2)";
            textColor = "#CD7F32";
        } else {
            bgColor = "rgba(255, 255, 255, 0.05)";
            textColor = "#FFFFFF";
        }

        row.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.1);" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 8;"
        );

        Label rankLabel = createDataLabel(String.valueOf(rank), 35, textColor);
        rankLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        Label nameLabel = createDataLabel(highScore.getPlayerName(), 120, textColor);
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 15));

        Label scoreLabel = createDataLabel(String.valueOf(highScore.getScore()), 90, textColor);
        scoreLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

        Label timeLabel = createDataLabel(highScore.getFormattedTime(), 90, "#FFFFFF");
        Label levelLabel = createDataLabel(String.valueOf(highScore.getLevelsCompleted()), 55, "#FFFFFF");

        String statusText = highScore.isGameCompleted() ? "✓ Hoàn thành" : "✗ Chưa xong";
        String statusColor = highScore.isGameCompleted() ? "#00FF00" : "#FF6666";
        Label statusLabel = createDataLabel(statusText, 110, statusColor);

        Label dateLabel = createDataLabel(highScore.getFormattedDate(), 150, "#AAAAAA");
        dateLabel.setFont(Font.font("System", 11));

        row.getChildren().addAll(rankLabel, nameLabel, scoreLabel, timeLabel, levelLabel, statusLabel, dateLabel);

        return row;
    }

    private Label createDataLabel(String text, double width, String color) {
        Label label = new Label(text);
        label.setFont(Font.font("System", 14));
        label.setTextFill(Color.web(color));
        label.setPrefWidth(width);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private Button createBackButton() {
        Button button = new Button("⬅ QUAY LẠI");
        button.setFont(Font.font("System", FontWeight.BOLD, 18));
        button.setPrefSize(200, 50);
        button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #FF6B6B, #C92A2A);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #8B0000;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 10;"
        );

        button.setOnMouseEntered(e -> {
            button.setScaleX(1.05);
            button.setScaleY(1.05);
        });
        button.setOnMouseExited(e -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });

        button.setOnAction(e -> {
            if (onBack != null) onBack.run();
        });

        return button;
    }
}
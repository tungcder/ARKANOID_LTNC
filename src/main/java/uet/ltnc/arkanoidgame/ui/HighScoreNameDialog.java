package uet.ltnc.arkanoidgame.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import uet.ltnc.arkanoidgame.entities.data.HighScoreManager;

import java.util.function.Consumer;

/**
 * Dialog nhập tên người chơi khi đạt High Score Kỷ Lục Mới
 */
public class HighScoreNameDialog extends StackPane {

    public HighScoreNameDialog(int score, int timeInSeconds, int levelReached, boolean gameCompleted, Consumer<String> onSubmitted) {
        setPrefSize(800, 600);

        // Dark dim overlay
        Rectangle overlay = new Rectangle(800, 600);
        overlay.setFill(Color.rgb(0, 0, 0, 0.75));

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.setMaxSize(480, 360);
        box.setPadding(new Insets(30));
        box.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1a0933, #0a0a1e);" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-color: #FFD700;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-radius: 20;"
        );

        DropShadow glow = new DropShadow(25, Color.web("#FFD700", 0.6));
        box.setEffect(glow);

        Label title = new Label("🏆 KỶ LỤC MỚI! 🏆");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#FFD700"));

        // Hạng ước lượng, hiển thị TRƯỚC khi lưu (chỉ mang tính tham khảo,
        // hạng chính xác sẽ được tính lại khi người chơi bấm nút LƯU KỶ LỤC)
        int estimatedRank = HighScoreManager.getRank(score);
        Label subTitle = new Label("Xếp hạng #" + estimatedRank + " | Điểm số: " + score);
        subTitle.setFont(Font.font("System", FontWeight.BOLD, 18));
        subTitle.setTextFill(Color.web("#00FFFF"));

        Label promptLabel = new Label("Nhập tên người chơi của bạn:");
        promptLabel.setFont(Font.font("System", FontWeight.SEMI_BOLD, 14));
        promptLabel.setTextFill(Color.WHITE);

        TextField nameInput = new TextField("Player");
        nameInput.setMaxWidth(300);
        nameInput.setPrefHeight(45);
        nameInput.setAlignment(Pos.CENTER);
        nameInput.setFont(Font.font("System", FontWeight.BOLD, 18));
        nameInput.setStyle(
                "-fx-background-color: #2a2a4a;" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #00FFFF;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 10;"
        );

        // Limit length to 15 characters
        nameInput.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > 15) {
                nameInput.setText(oldText);
            }
        });

        Button submitBtn = new Button("LƯU KỶ LỤC");
        submitBtn.setFont(Font.font("System", FontWeight.BOLD, 18));
        submitBtn.setPrefSize(200, 50);
        submitBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #00FFCC, #009999);" +
                        "-fx-text-fill: #0a0a1e;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"
        );

        submitBtn.setOnMouseEntered(e -> submitBtn.setScaleX(1.05));
        submitBtn.setOnMouseExited(e -> submitBtn.setScaleX(1.0));

        // Chặn bấm 2 lần liên tiếp -> tránh lưu trùng 1 điểm số 2 lần vào file highscore
        submitBtn.setOnAction(e -> {
            submitBtn.setDisable(true);

            String name = nameInput.getText().trim();
            if (name.isEmpty()) name = "Player";

            // Lưu + lấy hạng CHÍNH XÁC trong 1 lần đọc/ghi file (không đọc file 2 lần)
            int finalRank = HighScoreManager.saveHighScoreAndGetRank(
                    name, score, timeInSeconds, levelReached, gameCompleted
            );

            if (finalRank > 0) {
                subTitle.setText("Xếp hạng #" + finalRank + " | Điểm số: " + score);
            }

            if (onSubmitted != null) {
                onSubmitted.accept(name);
            }
        });

        box.getChildren().addAll(title, subTitle, promptLabel, nameInput, submitBtn);
        getChildren().addAll(overlay, box);
    }
}
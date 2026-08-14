package uet.ltnc.arkanoidgame.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.Consumer;

/**
 * Dialog nhập tên người chơi TRƯỚC khi bắt đầu ván chơi mới.
 * Tên này sẽ được dùng xuyên suốt ván chơi: hiển thị HUD (nếu cần),
 * lưu vào file save để Continue giữ nguyên tên, và dùng khi lưu high score.
 */
public class PlayerNameDialog extends StackPane {

    private static final int MAX_NAME_LENGTH = 15;

    public PlayerNameDialog(Consumer<String> onConfirm) {
        setPrefSize(800, 600);

        Rectangle overlay = new Rectangle(800, 600);
        overlay.setFill(Color.rgb(0, 0, 0, 0.75));

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.setMaxSize(480, 320);
        box.setPadding(new Insets(30));
        box.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1a0933, #0a0a1e);" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-color: #00FFFF;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-radius: 20;"
        );

        DropShadow glow = new DropShadow(25, Color.web("#00FFFF", 0.6));
        box.setEffect(glow);

        Label title = new Label("🎮 NHẬP TÊN NGƯỜI CHƠI");
        title.setFont(Font.font("System", FontWeight.BOLD, 26));
        title.setTextFill(Color.web("#00FFFF"));

        Label promptLabel = new Label("Tên này sẽ dùng cho ván chơi và bảng xếp hạng:");
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
        // Bôi đen sẵn để người chơi gõ đè luôn, không cần xóa "Player" trước
        nameInput.selectAll();

        // Giới hạn độ dài tên
        nameInput.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > MAX_NAME_LENGTH) {
                nameInput.setText(oldText);
            }
        });

        Button startBtn = new Button("BẮT ĐẦU CHƠI");
        startBtn.setFont(Font.font("System", FontWeight.BOLD, 18));
        startBtn.setPrefSize(220, 50);
        startBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #00FFCC, #009999);" +
                        "-fx-text-fill: #0a0a1e;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"
        );
        startBtn.setOnMouseEntered(e -> startBtn.setScaleX(1.05));
        startBtn.setOnMouseExited(e -> startBtn.setScaleX(1.0));

        Runnable confirm = () -> {
            startBtn.setDisable(true); // chặn bấm 2 lần
            String name = nameInput.getText().trim();
            if (name.isEmpty()) name = "Player";
            if (onConfirm != null) {
                onConfirm.accept(name);
            }
        };

        startBtn.setOnAction(e -> confirm.run());

        // Cho phép nhấn Enter để bắt đầu luôn, khỏi phải bấm chuột
        nameInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                confirm.run();
            }
        });

        box.getChildren().addAll(title, promptLabel, nameInput, startBtn);
        getChildren().addAll(overlay, box);

        // Tự động focus vào ô nhập tên khi dialog hiện ra
        this.setOnMouseEntered(e -> nameInput.requestFocus());
        javafx.application.Platform.runLater(nameInput::requestFocus);
    }
}
package uet.ltnc.arkanoidgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import uet.ltnc.arkanoidgame.entities.menu.MainMenu;

public class ArkanoidGame extends Application {
    private Stage primaryStage;
    private StackPane root;
    private MainMenu mainMenu;
    private GamePanel gamePanel;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        root = new StackPane();

        mainMenu = new MainMenu(this);

        root.getChildren().add(mainMenu);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Arkanoid JavaFX");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        mainMenu.requestFocus();
    }

    public void showMenu() {
        root.getChildren().clear();
        mainMenu = new MainMenu(this);
        root.getChildren().add(mainMenu);
        mainMenu.requestFocus();
        mainMenu.render();
    }

    public void startGame() {
        root.getChildren().clear();
        gamePanel = new GamePanel(this);
        root.getChildren().add(gamePanel);
        gamePanel.requestFocus();
        gamePanel.startNewGame();
    }

    public static void main(String[] args) {
        launch();
    }
}
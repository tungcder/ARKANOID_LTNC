package uet.ltnc.arkanoidgame.entities.menu;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import uet.ltnc.arkanoidgame.ArkanoidGame;

public class MainMenu extends Canvas {
    private ArkanoidGame mainApp;
    private int selectedOption = 0;
    private String[] menuOptions = {"START GAME", "HELP", "EXIT"};

    public MainMenu(ArkanoidGame mainApp) {
        super(800, 600);
        this.mainApp = mainApp;
        setFocusTraversable(true);

        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                selectedOption--;
                if (selectedOption < 0) selectedOption = menuOptions.length - 1;
                render();
            } else if (e.getCode() == KeyCode.DOWN) {
                selectedOption++;
                if (selectedOption >= menuOptions.length) selectedOption = 0;
                render();
            } else if (e.getCode() == KeyCode.ENTER) {
                handleSelection();
            }
        });
        render();
    }

    private void handleSelection() {
        switch (selectedOption) {
            case 0: mainApp.startGame(); break;
            case 1: showHelp(); break;
            case 2: System.exit(0); break;
        }
    }

    private void showHelp() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.rgb(0, 0, 0, 0.95));
        gc.fillRect(0, 0, 800, 600);

        gc.setFill(Color.CYAN);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("ARKANOID CONTROLS & HELP", 400, 70);

        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        String[] helpLines = {
            "LEFT/RIGHT ARROWS or A/D - Move paddle",
            "SPACE - Launch ball / Fire laser cannon",
            "ESC - Return to main menu",
            "",
            "POWERUPS (Drop from destroyed bricks):",
            "E - Enlarge paddle | L - Laser cannon | C - Catch ball",
            "S - Slow ball | D - Duplicate balls | B - Break bottom row",
            "P - Extra life",
            "",
            "BRICK TYPES:",
            "Colored bricks (1 hit) | Silver bricks (3 hits)",
            "Gold bricks (Unbreakable) | Moving bricks (Horizontal motion)",
            "",
            "Destroy all breakable bricks to advance through 18 levels!",
            "",
            "Press any key to return..."
        };

        for (int i = 0; i < helpLines.length; i++) {
            if (helpLines[i].startsWith("POWERUPS") || helpLines[i].startsWith("BRICK TYPES")) {
                gc.setFill(Color.YELLOW);
                gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            } else {
                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
            }
            gc.fillText(helpLines[i], 400, 120 + i * 26);
        }

        setOnKeyPressed(e -> {
            setOnKeyPressed(ev -> {
                if (ev.getCode() == KeyCode.UP) { selectedOption--; if (selectedOption < 0) selectedOption = menuOptions.length - 1; render(); }
                else if (ev.getCode() == KeyCode.DOWN) { selectedOption++; if (selectedOption >= menuOptions.length) selectedOption = 0; render(); }
                else if (ev.getCode() == KeyCode.ENTER) handleSelection();
            });
            render();
        });
    }

    public void render() {
        GraphicsContext gc = getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 600);

        gc.setFill(Color.CYAN);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("ARKANOID", 400, 150);

        gc.setFill(Color.GRAY);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        gc.fillText("JavaFX Edition", 400, 185);

        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        int startY = 280;
        int spacing = 50;
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedOption) {
                gc.setFill(Color.YELLOW);
                gc.setTextAlign(TextAlignment.LEFT);
                gc.fillText(">", 260, startY + i * spacing);
            } else {
                gc.setFill(Color.WHITE);
            }
            gc.setTextAlign(TextAlignment.LEFT);
            gc.fillText(menuOptions[i], 300, startY + i * spacing);
        }

        gc.setFill(Color.GRAY);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("USE ARROW KEYS TO SELECT - PRESS ENTER TO CONFIRM", 400, 530);
        gc.setFill(Color.DARKGRAY);
        gc.fillText("© 1986 TAITO CORP.", 400, 560);
    }
}

package uet.ltnc.arkanoidgame.entities.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class MapLoader {

    private MapLoader() {
    }

    public static int[][] loadMap(String resourcePath) {
        InputStream input =
                MapLoader.class.getResourceAsStream(resourcePath);

        if (input == null) {
            throw new RuntimeException(
                    "Không tìm thấy file map: " + resourcePath
            );
        }

        List<int[]> rows = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(input))) {

            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] values = line.split(",");
                int[] row = new int[values.length];

                for (int col = 0; col < values.length; col++) {
                    row[col] =
                            Integer.parseInt(values[col].trim());
                }

                rows.add(row);
            }

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(
                    "File map không hợp lệ: " + resourcePath,
                    e
            );
        }

        if (rows.isEmpty()) {
            throw new RuntimeException(
                    "File map đang trống: " + resourcePath
            );
        }

        int[][] map = new int[rows.size()][];

        for (int row = 0; row < rows.size(); row++) {
            map[row] = rows.get(row);
        }

        return map;
    }
}
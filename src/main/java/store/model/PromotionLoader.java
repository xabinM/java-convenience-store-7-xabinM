package store.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PromotionLoader {
    private final String filePath;

    public PromotionLoader() {
        this.filePath = "src/main/resources/promotions.md";
    }

    public List<Promotion> loadPromotionFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            br.readLine();

            return br.lines().map(this::parsePromotion).toList();
        } catch (IOException e) {
            throw new IllegalArgumentException("파일을 읽는 도중 에러가 발생했습니다.");
        }
    }

    private Promotion parsePromotion(String line) {
        String[] parts = line.split(",");

        String name = parts[0];
        int buy = Integer.parseInt(parts[1]);
        int get = Integer.parseInt(parts[2]);
        LocalDate start = LocalDate.parse(parts[3]);
        LocalDate end = LocalDate.parse(parts[4]);

        return new Promotion(name, buy, get, start, end);
    }
}

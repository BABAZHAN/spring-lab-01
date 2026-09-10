package kz.iitu.spring_lab_01.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting("Hello, " + name + "!", owner, LocalDateTime.now());
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(owner,
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors());
    }

    // --- Индивидуальное задание (Вариант 6) ---
    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(required = false) String numbers) {
        if (numbers == null || numbers.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Ошибка: перелейте список чисел через запятую (например, ?numbers=1,5,10)");
        }

        try {
            List<Double> numList = Arrays.stream(numbers.split(","))
                    .map(String::trim)
                    .map(Double::parseDouble)
                    .toList();

            DoubleSummaryStatistics stats = numList.stream()
                    .mapToDouble(Double::doubleValue)
                    .summaryStatistics();

            return ResponseEntity.ok(new StatsResult(stats.getMin(), stats.getMax(), stats.getAverage()));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Ошибка: список должен содержать только числа!");
        }
    }

    public record Greeting(String message, String owner, LocalDateTime timestamp) { }

    public record Info(String owner, String javaVersion, int cpuCores) { }

    public record StatsResult(double min, double max, double average) { }
}
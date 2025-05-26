package Backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardEntry {

    private String name;
    private double time;

    // Constructor
    public LeaderboardEntry() {}

    public LeaderboardEntry(String name, double time) {
        this.name = name;
        this.time = time;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public double getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(double time) {
        this.time = time;
    }

    // 🔥 Static method to add new entry to leaderboard.json
    public static void addEntry(String name, double time, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        List<LeaderboardEntry> entries;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                entries = mapper.readValue(file, new TypeReference<List<LeaderboardEntry>>() {});
            } else {
                entries = new ArrayList<>();
            }

            entries.add(new LeaderboardEntry(name, time));
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, entries);

            System.out.println("✅ Leaderboard entry saved: " + name + " - " + time + "ms");

        } catch (IOException e) {
            System.out.println("⚠️ Error saving leaderboard entry: " + e.getMessage());
        }
    }
}

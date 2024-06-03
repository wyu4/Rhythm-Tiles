package Content;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Map {
    private String name, description, audioPath;
    private double spawnToGoal;
    private List<List<Double>> tilesDown, tilesUp;

    public Map() {
        this("unknown", "empty", "", 1);
    }

    public Map(String name, String description, String audioPath, double spawnToGoal) {
        tilesDown = new ArrayList<>();
        tilesUp = new ArrayList<>();

        setName(name);
        setDescription(description);
        setAudioPath(audioPath);
        setSpawnToGoal(spawnToGoal);
        resetTiles();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAudioPath(String audioPath) {
        File audioFile = new File(audioPath);
        if (audioFile.exists()) {
            this.audioPath = audioFile.getPath();
        }
    }

    public void setSpawnToGoal(double spawnToGoal) {
        this.spawnToGoal = spawnToGoal;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public double getSpawnToGoal() {
        return spawnToGoal;
    }

    public void resetTiles() {
        tilesDown = new ArrayList<>();
        tilesUp = new ArrayList<>();

        tilesDown.add(new ArrayList<>());
        tilesDown.add(new ArrayList<>());
        tilesDown.add(new ArrayList<>());
        tilesDown.add(new ArrayList<>());
        tilesUp.add(new ArrayList<>());
        tilesUp.add(new ArrayList<>());
        tilesUp.add(new ArrayList<>());
        tilesUp.add(new ArrayList<>());
    }

    private List<Double> copyList(List<Double> list) {
        List<Double> deepCopy = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            deepCopy.add(i, list.get(i));
        }
        return deepCopy;
    }

    public void addTileDown(int column, double timestamp) {
        tilesDown.get(column).add(timestamp);
    }

    public void addTileUp(int column, double timestamp) {
        tilesUp.get(column).add(timestamp);
    }

    public List<List<Double>> getTilesDown() {
        List<List<Double>> deepCopy = new ArrayList<>();

        deepCopy.add(0, copyList(tilesDown.getFirst()));
        deepCopy.add(1, copyList(tilesDown.get(1)));
        deepCopy.add(2, copyList(tilesDown.get(2)));
        deepCopy.add(3, copyList(tilesDown.get(3)));

        return deepCopy;
    }

    public List<List<Double>> getTilesUp() {
        List<List<Double>> deepCopy = new ArrayList<>();

        deepCopy.add(0, copyList(tilesUp.getFirst()));
        deepCopy.add(1, copyList(tilesUp.get(1)));
        deepCopy.add(2, copyList(tilesUp.get(2)));
        deepCopy.add(3, copyList(tilesUp.get(3)));

        return deepCopy;
    }

    public static Map openJSON(File file) {
        if (file.exists()) {
            Gson converter = new Gson();
            try {
                String data = Files.readString(file.toPath());
                converter.fromJson(data, Map.class);
            } catch (IOException e) {
                System.out.println("Could not open file: " + e);
                return new Map();
            }
        }

        return new Map();
    }

    public String exportJSON() {
        Gson converter = new Gson();
        return converter.toJson(this);
    }

    @Override
    public String toString() {
        return exportJSON();
    }
}

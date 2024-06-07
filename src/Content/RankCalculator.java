package Content;

public class RankCalculator {
    private int miss, bad, good, great, perfect, points, combo;

    /**
     * Creates a new RankCalculator object.
     */
    public RankCalculator() {
        reset();
    }

    /**
     * Reset all stats.
     */
    public void reset() {
        // Set all instance variables to 0
        this.combo = 0;
        this.points = 0;
        this.miss = 0;
        this.bad = 0;
        this.good = 0;
        this.great = 0;
        this.perfect = 0;
    }

    /**
     * Add earned rank to counter.
     * @param rank Rank selection
     */
    public void addRankCount(Tile.Rank rank) {
        combo++;
        switch (rank) { // Switch through selections
            case BAD:
                bad++;
                break;
            case GOOD:
                good++;
                break;
            case GREAT:
                great++;
                break;
            case PERFECT:
                perfect++;
                break;
            case MISS:
                miss++;
                combo = 0;
                break;
        }
    }

    /**
     * Get the amount earned of a registered rank.
     * @param rank Rank selection
     * @return (int)The number of this rank earned.
     */
    public int getRankCount(Tile.Rank rank) {
        return switch (rank) { // Switch through selections and return the corresponding variable
            case BAD -> bad;
            case GOOD -> good;
            case GREAT -> great;
            case PERFECT -> perfect;
            case MISS -> miss;
        };
    }

    /**
     * Calculate the amount of points earned by a rank.
     * @param rank Rank selection
     * @return (int)Points
     */
    public int calculatePoints(Tile.Rank rank) {
        return switch (rank) { // Switch through selections and return corresponding points
            case BAD -> 1;
            case GOOD -> 2;
            case GREAT -> 3;
            case PERFECT -> 4;
            default -> 0;
        };
    }

    /**
     * Add points to the points counter based on earned rank. Will also be awared a combo.
     * @param rank Rank selection
     */
    public void addPoints(Tile.Rank rank) {
        addRankCount(rank);

        int newPoints = calculatePoints(rank); // Calculate points owed
        points += newPoints; // Add points to variable
    }

    /**
     * Get the total amount of points earned to date
     * @return (int)Points
     */
    public int getPoints() {
        return points; // Return points var
    }

    /**
     * Get the current combo
     * @return (int) Combo
     */
    public int getCombo() {
        return combo; // Return combo var
    }

    @Override
    public String toString() {
        return "RankCalculator{" +
                "points:" + points + "," +
                "bad:" + bad + "," +
                "good:" + good + "," +
                "great:" + great + "," +
                "perfect:" + perfect + "," +
                "miss:" + miss + "," +
                "combo:" + combo + "}";
    }
}

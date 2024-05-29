package Content;

import Content.RTComponents.RTPanel;

import java.awt.Color;

public class Tile extends RTPanel {
    private final RTPanel goal;
    private double hitboxMult;

    public enum Rank {
            PERFECT, GREAT, GOOD, BAD, MISS
    }

    public Tile(RTPanel goal) {
        this(goal, 1);
    }

    public Tile(RTPanel goal, double hitboxMult) {
        this.goal = goal;
        this.hitboxMult = hitboxMult;
    }

    public void init() {
        if (getParent() != null) {
            setSize(getParent().getWidth(), getParent().getWidth());
        }
        setName("Tile");
        setBackground(new Color(137, 196, 197));
        setAlpha(1);
    }

    public Rank calculateRank() {
        double dist = Math.abs(goal.getAccurateCenterY() - getAccurateCenterY());
        if (dist <= (getAccurateHeight()/2) * 0.25 * hitboxMult) {
            return Rank.PERFECT;
        } else if (dist <= (getAccurateHeight()/2) * 0.5 * hitboxMult) {
            return Rank.GREAT;
        } else if (dist <= (getAccurateHeight()/2) * 0.75 * hitboxMult) {
            return Rank.GOOD;
        }  else if (dist <= (getAccurateHeight()/2) * hitboxMult) {
            return Rank.BAD;
        } else {
            return Rank.MISS;
        }
    }

    public boolean isOutOfReach() {
        return ((getAccurateY() > goal.getAccurateY()) && (calculateRank() == Rank.MISS));
    }
}

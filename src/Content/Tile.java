package Content;

import Content.RTComponents.RTPanel;

import java.awt.Color;

public class Tile extends RTPanel {
    private final RTPanel goal;
    private double hitboxMult;
    private boolean triggered;

    public enum Rank {
            PERFECT, GREAT, GOOD, BAD, MISS
    }

    public Tile(RTPanel goal) {
        this(goal, 1);
    }

    public Tile(RTPanel goal, double hitboxMult) {
        this.goal = goal;
        this.hitboxMult = hitboxMult;
        triggered = false;
        init();
    }

    public void init() {
        setSize(goal.getSize());
        setName("Tile");
        setBackground(new Color(167, 254, 255));
        setAlpha(0.8f);
    }

    public Rank calculateRank() {
        double dist = Math.abs(goal.getAccurateCenterY() - getAccurateCenterY());
        if (dist <= (getAccurateHeight()) * 0.25 * hitboxMult) {
            return Rank.PERFECT;
        } else if (dist <= (getAccurateHeight()) * 0.5 * hitboxMult) {
            return Rank.GREAT;
        } else if (dist <= (getAccurateHeight()) * 0.75 * hitboxMult) {
            return Rank.GOOD;
        }  else if (dist <= (getAccurateHeight()) * hitboxMult) {
            return Rank.BAD;
        } else {
            return Rank.MISS;
        }
    }

    public boolean isOutOfReach() {
        return ((getAccurateY() > goal.getAccurateY()) && (calculateRank() == Rank.MISS));
    }

    public void setTriggered(boolean flag) {
        triggered = flag;
    }

    public boolean getTriggered() {
        return triggered;
    }
}

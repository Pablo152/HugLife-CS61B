package creatures;

import huglife.*;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {

    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates plip with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    public Clorus() {
        this(1);
    }


    public Color color() {
        r = 34;
        b = 231;
        g = 0;
        return color(r, g, b);
    }


    public void attack(Creature c) {
        this.energy += c.energy();
    }

    public void move() {
        this.energy -= 0.03;
        if (this.energy < 0)
            this.energy = 0;
    }


    public void stay() {
        this.energy -= 0.01;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();

        for (Direction dir : neighbors.keySet()) {
            if (neighbors.get(dir).name().equals("empty")) {
                emptyNeighbors.add(dir);
            }
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        for (Direction dir : neighbors.keySet()) {
            if (neighbors.get(dir).name().equals("plip") && Math.random() < 0.5) {
                plipNeighbors.add(dir);
                return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plipNeighbors));
            }
        }

        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
        }

        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));

    }


    public Clorus replicate() {
        Clorus clorusClone = new Clorus(this.energy / 2);
        this.energy = this.energy / 2;
        return clorusClone;
    }
}

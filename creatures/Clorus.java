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
     * creates Clorus with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }
    
    /**
    * creates Clorus with energy 1
    */
    
    public Clorus() {
        this(1);
    }

    
    /**
    * Sets clorus color
    */
    
    public Color color() {
        r = 34;
        b = 231;
        g = 0;
        return color(r, g, b);
    }
    
    /**
    * Consumes the energy of creature it attacks.
    */

    public void attack(Creature c) {
        this.energy += c.energy();
    }

    /**
    * looses 0.03 energy for every move it makes.
    */
    
    public void move() {
        this.energy -= 0.03;
    }

    /**
    * looses 0.01 energy if it stays.
    */
    
    public void stay() {
        this.energy -= 0.01;
    }
    
    /**
    * Creates a new clorus and gives his offspring half his energy.
    */

    public Clorus replicate() {
        Clorus clorusClone = new Clorus(this.energy / 2);
        this.energy = this.energy / 2;
        return clorusClone;
    }
    
    /**
    * Chooses an action based on different rules
    * Rule 1: if neighbour cells are empty, the Clorus stays.
    * Rule 2: if neighbour cells have plips, attack a random cell which has a plip.
    * Rule 3: if energy is greater than or equal to 1, the Clorus replicates itself.
    * Rule 4: Otherwise, move to a random empty cell.
    */

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
}

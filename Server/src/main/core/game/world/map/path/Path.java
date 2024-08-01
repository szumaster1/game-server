package core.game.world.map.path;

import core.game.node.entity.Entity;
import core.game.world.map.Point;

import java.util.ArrayDeque;
import java.util.Deque;

public class Path {

    private boolean succesful;

    private boolean moveNear;

    private Deque<Point> points = new ArrayDeque<Point>();

    public Path() {
        /*
         * empty.
         */
    }

    public void walk(Entity entity) {
        if (entity.getLocks().isMovementLocked()) {
            return;
        }
        entity.getWalkingQueue().reset();
        for (Point step : points) {
            entity.getWalkingQueue().addPath(step.getX(), step.getY());
        }
    }

    public boolean isSuccessful() {
        return succesful;
    }

    public void setSuccesful(boolean succesful) {
        this.succesful = succesful;
    }

    public Deque<Point> getPoints() {
        return points;
    }

    public void setPoints(Deque<Point> points) {
        this.points = points;
    }

    public boolean isMoveNear() {
        return moveNear;
    }

    public void setMoveNear(boolean moveNear) {
        this.moveNear = moveNear;
    }
}
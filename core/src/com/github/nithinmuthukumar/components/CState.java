package com.github.nithinmuthukumar.components;

import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.badlogic.ashley.core.Component;

public class CState implements Component {
    private Action action;
    public Direction direction;
    private Action previousAction;
    public CState(){
        action = Action.IDLE;
        previousAction = action;
        direction = Direction.LEFT;
    }
    public CState(Action action,Direction direction){
        this.action = action;
        previousAction = action;
        this.direction = direction;
    }

    public void setAction(Action action) {
        previousAction = this.action;
        this.action = action;
    }

    public Action getPreviousAction() {
        return previousAction;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "CState{" +
                "action=" + action +
                ", direction=" + direction +
                '}';
    }

    public void setPreviousAction(Action action) {
        previousAction = action;
    }
}

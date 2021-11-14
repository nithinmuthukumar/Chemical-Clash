package com.github.nithinmuthukumar.components;

import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.badlogic.ashley.core.Component;

public class CState implements Component {
    public Action action;
    public Direction direction;
    public CState(){
        action = Action.IDLE;
        direction = Direction.LEFT;
    }
    public CState(Action action,Direction direction){
        this.action = action;
        this.direction = direction;
    }

}

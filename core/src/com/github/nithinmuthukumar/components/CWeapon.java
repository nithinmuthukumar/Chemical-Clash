package com.github.nithinmuthukumar.components;

import com.badlogic.ashley.core.Component;

public class CWeapon implements Component {
    public boolean isEnemy;
    public CWeapon(boolean isEnemy){
        this.isEnemy = isEnemy;
    }
}

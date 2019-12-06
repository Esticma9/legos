/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maze;

/**
 *
 * @author uidk2372
 */
public class BotMovement {
    private BOT_DIRECTION direction;
    private BOT_ACTIONS [] actions;
    
    public BotMovement(BOT_DIRECTION direction, BOT_ACTIONS [] actions){
        this.direction = direction;
        this.actions = actions;
    }

    /**
     * @return the direction
     */
    public BOT_DIRECTION getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(BOT_DIRECTION direction) {
        this.direction = direction;
    }

    /**
     * @return the actions
     */
    public BOT_ACTIONS[] getActions() {
        return actions;
    }

    /**
     * @param actions the actions to set
     */
    public void setActions(BOT_ACTIONS[] actions) {
        this.actions = actions;
    }
    
    public enum BOT_DIRECTION{
        up,
        down,
        left,
        right
    }
        
    public enum BOT_ACTIONS{
        forward,
        backward,
        stop,
        left,
        right
    }
}

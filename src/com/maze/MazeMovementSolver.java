/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.maze.BotMovement.BOT_ACTIONS;
import com.maze.BotMovement.BOT_DIRECTION;

/**
 *
 * @author uidk2372
 */
public class MazeMovementSolver {
    public static final String[] TEST_SOLUTION = new String[] {"forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "forward", "right", "right", "right", "right", "right", "right", "right", "right", "forward", "forward", "forward", "forward"};
    private static final BOT_DIRECTION INITIAL_BOT_DIRECTION = BOT_DIRECTION.up;
    private static BOT_DIRECTION CURRENT_BOT_DIRECTION = BOT_DIRECTION.up;
    
    public MazeMovementSolver(){
        //Nothing to initialize
    }
    
    public List<BOT_ACTIONS> getActionsFromMovementList(List<BotMovement> movements){
        List<BOT_ACTIONS> actions = new ArrayList<>();
        movements.forEach((movement) -> {
            actions.addAll(Arrays.asList(movement.getActions()));
        });
        return actions;
    }
    
    public List<BotMovement> getAllMovements(List<BOT_ACTIONS> solutionArray, BOT_DIRECTION currentDirection){
        if(currentDirection == null){
            CURRENT_BOT_DIRECTION = INITIAL_BOT_DIRECTION;
        }
        else{
            CURRENT_BOT_DIRECTION = currentDirection;
        }
        
        List<BotMovement> solutionList = new ArrayList<>();
        
        for (BOT_ACTIONS movement : solutionArray) {
            BotMovement  actualMovement = getNextMovement(CURRENT_BOT_DIRECTION, movement);
            solutionList.add(actualMovement);
            CURRENT_BOT_DIRECTION = actualMovement.getDirection();
        }
        
        return solutionList;
    }
    
    public BotMovement getNextMovement(BOT_DIRECTION previousDirection, BOT_ACTIONS currentMovement){
        //When bot is in UP direction
        if(previousDirection == BOT_DIRECTION.up && currentMovement == BOT_ACTIONS.forward){
            return new BotMovement(BOT_DIRECTION.up, new BOT_ACTIONS[] {BOT_ACTIONS.forward});
        }
        else if(previousDirection == BOT_DIRECTION.up && currentMovement == BOT_ACTIONS.right){
            return new BotMovement(BOT_DIRECTION.right, new BOT_ACTIONS[] {BOT_ACTIONS.right, BOT_ACTIONS.forward});
        }
        else if(previousDirection == BOT_DIRECTION.up && currentMovement == BOT_ACTIONS.left){
            return new BotMovement(BOT_DIRECTION.left, new BOT_ACTIONS[] {BOT_ACTIONS.left, BOT_ACTIONS.forward});
        }
        else if(previousDirection == BOT_DIRECTION.up && currentMovement == BOT_ACTIONS.backward){
            return new BotMovement(BOT_DIRECTION.down, new BOT_ACTIONS[] {BOT_ACTIONS.right, BOT_ACTIONS.right, BOT_ACTIONS.forward});
        }
        //When bot is in DOWN direction
        else if(previousDirection == BOT_DIRECTION.down && currentMovement == BOT_ACTIONS.forward){
            return new BotMovement(BOT_DIRECTION.up, new BOT_ACTIONS[] {BOT_ACTIONS.right, BOT_ACTIONS.right, BOT_ACTIONS.forward});
        }
        else if(previousDirection == BOT_DIRECTION.down && currentMovement == BOT_ACTIONS.right){
            return new BotMovement(BOT_DIRECTION.right,new BOT_ACTIONS[] {BOT_ACTIONS.left, BOT_ACTIONS.forward});
        }
        else if(previousDirection == BOT_DIRECTION.down && currentMovement == BOT_ACTIONS.left){
            return new BotMovement(BOT_DIRECTION.left, new BOT_ACTIONS[] {BOT_ACTIONS.right, BOT_ACTIONS.forward});
        }
        else if(previousDirection == BOT_DIRECTION.down && currentMovement == BOT_ACTIONS.backward){
            return new BotMovement(BOT_DIRECTION.down, new BOT_ACTIONS[] {BOT_ACTIONS.forward});
        }
        //When bot is in LEFT direction
        else if(previousDirection == BOT_DIRECTION.left && currentMovement == BOT_ACTIONS.forward){
            return new BotMovement(BOT_DIRECTION.up, new BOT_ACTIONS[] {BOT_ACTIONS.right, BOT_ACTIONS.forward});
        }
        else if(previousDirection == BOT_DIRECTION.left && currentMovement == BOT_ACTIONS.right){
            return new BotMovement(BOT_DIRECTION.right,new BOT_ACTIONS[] {BOT_ACTIONS.right, BOT_ACTIONS.right, BOT_ACTIONS.forward});
        }
        else if(previousDirection == BOT_DIRECTION.left && currentMovement == BOT_ACTIONS.left){
            return new BotMovement(BOT_DIRECTION.left, new BOT_ACTIONS[] {BOT_ACTIONS.forward});
        }
        else if(previousDirection == BOT_DIRECTION.left && currentMovement == BOT_ACTIONS.backward){
            return new BotMovement(BOT_DIRECTION.down, new BOT_ACTIONS[] {BOT_ACTIONS.left, BOT_ACTIONS.forward});
        }
        //When bot is in RIGHT direction
        else if(previousDirection == BOT_DIRECTION.right && currentMovement == BOT_ACTIONS.forward){
            return new BotMovement(BOT_DIRECTION.up, new BOT_ACTIONS[] {BOT_ACTIONS.left, BOT_ACTIONS.forward});
        }
        else if(previousDirection == BOT_DIRECTION.right && currentMovement == BOT_ACTIONS.right){
            return new BotMovement(BOT_DIRECTION.right, new BOT_ACTIONS[] {BOT_ACTIONS.forward});
        }
        else if(previousDirection == BOT_DIRECTION.right && currentMovement == BOT_ACTIONS.left){
            return new BotMovement(BOT_DIRECTION.left, new BOT_ACTIONS[] {BOT_ACTIONS.right, BOT_ACTIONS.right, BOT_ACTIONS.forward});
        }
        else if(previousDirection == BOT_DIRECTION.right && currentMovement == BOT_ACTIONS.backward){
            return new BotMovement(BOT_DIRECTION.down, new BOT_ACTIONS[] {BOT_ACTIONS.right, BOT_ACTIONS.forward});
        }
        //Other case return an empty action
        else{
            return new BotMovement(previousDirection, new BOT_ACTIONS[]{});
        }
    }
    

}

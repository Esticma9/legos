package com.maze;

import controller.BotCalls.BOT_ACTIONS;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Maze {
	
    private static final int ROAD = 0;
    private static final int WALL = 1;
    private static final int START = 2;
    private static final int EXIT = 3;
    private static final int PATH = 4;

    private int[][] maze;
    private boolean[][] visited;
    private Coordinate start;
    private Coordinate end;

    public Maze(File maze) throws FileNotFoundException {
        String fileText = "";
        try (Scanner input = new Scanner(maze)) {
            while (input.hasNextLine()) {
                fileText += input.nextLine() + "\n";
            }
        }
        initializeMaze(fileText);
    }
    
    public Maze(String input) {
    	initializeMaze(input);
    }

    private void initializeMaze(String text) {
        if (text == null || (text = text.trim()).length() == 0) {
            throw new IllegalArgumentException("empty lines data");
        }

        String[] lines = text.split("[\r]?\n");
        maze = new int[lines.length][lines[0].length()];
        visited = new boolean[lines.length][lines[0].length()];

        for (int row = 0; row < getHeight(); row++) {
            /*if (lines[row].length() != getWidth()) {
                throw new IllegalArgumentException("line " + (row + 1) + " wrong length (was " + lines[row].length() + " but should be " + getWidth() + ")");
            }*/

            for (int col = 0; col < getWidth(row); col++) {
                if (lines[row].charAt(col) == '#')
                    maze[row][col] = WALL;
                else if (lines[row].charAt(col) == 'S') {
                    maze[row][col] = START;
                    start = new Coordinate(row, col);
                } else if (lines[row].charAt(col) == 'E') {
                    maze[row][col] = EXIT;
                    end = new Coordinate(row, col);
                } else
                    maze[row][col] = ROAD;
            }
        }
    }

    public int getHeight() {
        return maze.length;
    }

    public int getWidth(int row) {
        return maze[row].length;
    }

    public Coordinate getEntry() {
        return start;
    }

    public Coordinate getExit() {
        return end;
    }

    public boolean isExit(int x, int y) {
        return x == end.getX() && y == end.getY();
    }

    public boolean isStart(int x, int y) {
        return x == start.getX() && y == start.getY();
    }

    public boolean isExplored(int row, int col) {
        return visited[row][col];
    }

    public boolean isWall(int row, int col) {
        return maze[row][col] == WALL;
    }

    public void setVisited(int row, int col, boolean value) {
        visited[row][col] = value;
    }

    public boolean isValidLocation(int row, int col) {
        if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth(row)) {
            return false;
        }
        return true;
    }

    public List<BOT_ACTIONS> printPath(List<Coordinate> path) {
        List<BOT_ACTIONS> _result = new ArrayList<>();
        int[][] tempMaze = Arrays.stream(maze)
            .map(int[]::clone)
            .toArray(int[][]::new);
        int currentX = 0;
        int currentY = 0;
        int previousX = -1;
        int previousY = -1;
        for (Coordinate coordinate : path) {
            if (isStart(coordinate.getX(), coordinate.getY()) || isExit(coordinate.getX(), coordinate.getY())) {
                continue;
            }
            tempMaze[coordinate.getX()][coordinate.getY()] = PATH;
            currentX = coordinate.getX();
            currentY = coordinate.getY();
            
            if(previousX == -1 || previousY == -1) {
            	previousX = coordinate.getX();
                previousY = coordinate.getY();
            	continue;
            }
            
            if(currentX > previousX) {
            	//avanzar
                _result.add(BOT_ACTIONS.backward);
            	//System.out.println("Adelante");
            	
            	if(currentY > previousY) {
            		//gira a la derecha
                        _result.add(BOT_ACTIONS.right);
            		//System.out.println("Derecha");
            	} else if(currentY < previousY) {
            		//gira a la izquierda
                        _result.add(BOT_ACTIONS.left);
            		//System.out.println("Izquierda");
            	} else {
            		//No dobles
            	}
            } else if(currentX < previousX) {
            	//retrocede
                _result.add(BOT_ACTIONS.forward);
            	//System.out.println("Atr�s");
            	
            	if(currentY > previousY) {
            		//gira a la derecha
                        _result.add(BOT_ACTIONS.right);
            		//System.out.println("Derecha");
            	} else if(currentY < previousY) {
            		//gira a la izquierda
                        _result.add(BOT_ACTIONS.left);
            		//System.out.println("Izquierda");
            	} else {
            		//No dobles
            	}
            } else {
            	//qu�date ah�
            	
            	if(currentY > previousY) {
            		//gira a la derecha
                        _result.add(BOT_ACTIONS.right);
            		//System.out.println("Derecha");
            	} else if(currentY < previousY) {
            		//gira a la izquierda
                        _result.add(BOT_ACTIONS.left);
            		//System.out.println("Izquierda");
            	} else {
            		//No dobles
            	}
            }
            
            previousX = coordinate.getX();
            previousY = coordinate.getY();
            //System.out.println(coordinate.getX()+","+coordinate.getY());
        }
        //System.out.println(toString(tempMaze));
        return _result;
    }

    public String toString(int[][] maze) {
        StringBuilder result = new StringBuilder(getWidth(0) * (getHeight() + 1));
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(row); col++) {
                if (maze[row][col] == ROAD) {
                    result.append(' ');
                } else if (maze[row][col] == WALL) {
                    result.append('#');
                } else if (maze[row][col] == START) {
                    result.append('S');
                } else if (maze[row][col] == EXIT) {
                    result.append('E');
                } else {
                    result.append('.');
                }
            }
            result.append('\n');
        }
        return result.toString();
    }

    public void reset() {
        for (int i = 0; i < visited.length; i++)
            Arrays.fill(visited[i], false);
    }

}

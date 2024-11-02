package main;

import classes.Maze;
import classes.PathFinder;

import java.io.*;

public class main {
    public static void main(String[] args) throws IOException {
            Maze maze = new Maze("C:\\Users\\Zalman\\IdeaProjects\\New Path Finder\\src\\main\\path.txt",28,0);
            System.out.println(maze.toString());


            PathFinder pathFinder = new PathFinder(maze);
            pathFinder.main();
    }
}

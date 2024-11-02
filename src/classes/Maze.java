package classes;

import utils.Colors;

import java.io.*;
import java.util.Arrays;

public class Maze {
    private int width;
    private int height;
    private int[][] maze;

    private Pointer pointer;

    public String toString() {
        String s = "";
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                s += (this.pointer.getX() == j && this.pointer.getY() == i ? Colors.RED+"X"+Colors.RESET : (
                        this.maze[i][j] == 1 ? Colors.GREEN+this.maze[i][j]+Colors.RESET : this.maze[i][j]
                        ));
            }
            s += "\n";
        }
        return s;
    }

    public void movePointer(int x, int y) {
        if (this.isEmptyAt(x,y)) {
            this.pointer.setX(x);
            this.pointer.setY(y);
        } else {
            throw new Error(String.format("Cannot move pointer to position (%s;%s)",x,y));
        }
    }

    public Pointer getPointer() {
        return this.pointer;
    }

    public Maze(String link, int x, int y) throws IOException {
        File file = new File(link);
        BufferedReader br = new BufferedReader(new FileReader(file));
        int i = 0;
        String st;
        while ((st = br.readLine()) != null){
            if (i==0) {
                String[] size = st.split(" ");
                this.width = Integer.parseInt(size[0]);
                this.height = Integer.parseInt(size[1]);
                this.maze = new int[this.height][this.width];
            } else {
                int[] line = new int[this.width];
                for (int j = 0; j < st.length(); j++) {
                    line[j] = Character.getNumericValue(st.charAt(j));
                }
                this.maze[i-1] = line;
            }
            i+=1;
        }

        this.pointer = new Pointer(x,y);
    }

    public boolean isEmptyAt(int x, int y) {
        if (x >= this.width || y >= this.height || x < 0 || y < 0) return false;
        return this.maze[y][x] == 1;
    }
}

package classes;

import utils.FindUtils;

import java.util.ArrayList;
import java.util.Scanner;

public class PathFinder {
    private Memory memory;
    private Maze maze;



    public PathFinder(Maze m) {
        this.memory = new Memory();
        this.maze = m;
    }


    public void main() {
        Scanner scanner = new Scanner(System.in);
        boolean manual = false;
        while (this.maze.getPointer().getY() != 0 || this.maze.getPointer().getX() != 0) {
            if (manual) {
                System.out.println("Enter '' to proceed to the next step or 'm' to view memory:");
                String input = scanner.nextLine();

                while (!input.equalsIgnoreCase("") && !input.equalsIgnoreCase("m")) {
                    System.out.println("Invalid input. Please enter 'n' to proceed or 'm' to view memory:");
                    input = scanner.nextLine();
                }

                if (input.equalsIgnoreCase("")) {
                    this.findNextMovement();
                    this.execute();
                    System.out.println(this.maze);
                } else if (input.equalsIgnoreCase("m")) {
                    System.out.println(this.memory);
                }
            } else {
                this.findNextMovement();
                this.execute();
                System.out.println(this.maze);
            }
        }
        System.out.println(String.format("La position du pointer à la fin de la boucle est (%d,%d)", this.maze.getPointer().getX(), this.maze.getPointer().getY()));
    }


    public void execute() {
        String[] f = this.memory.getFirstInstruction();
        if (f != null) {

            this.maze.movePointer(
                    Integer.parseInt(f[1]),
                    Integer.parseInt(f[2])
            );
        }
    }


    /*
        La méthode findNextMovement va trouver les mouvements
        que le pointer pourra effectué et va les stocker en mémoire.
        Le dernier mouvement effectué passe en priorité sur les mouvements à effectuer
        s'il existe.

        On précise qu'on ne peut pas accéder à une position d'où on vient (AKA on supprime
        l'élément opposé à la dernière instruction).


        Problème soulevé :
        Si on arrive à un endroit où il y a plusieurs possibilités,
        il faut pouvoir gérer cela en mémoire et faire en sorte de ne pas
        re-essayer les possibilités déjà effectuées.
     */


    private void findNextMovement() {
        int x = this.maze.getPointer().getX();
        int y = this.maze.getPointer().getY();


        String[] f = this.memory.getFirstInstruction();
        int[] g = this.memory.getMultipleInstructionItem(x,y);

        ArrayList<String[]> instructions = new ArrayList<>();



        // Peut aller à droite
        if (this.maze.isEmptyAt(x+1,y)
                && (f == null || f[0] != "L")
                && (g == null || g[2] == -1)
        ) {
            System.out.println("Peut aller à droite !");
             String[] args = {"R",Integer.toString(x+1),Integer.toString(y)};
             instructions.add(args);
        }
        // Peut aller à gauche
        if (this.maze.isEmptyAt(x-1,y)
                && (f == null || f[0] != "R")
                && (g == null || g[3] == -1)
        ) {
            System.out.println("Peut aller à gauche !");
            String[] args = {"L",Integer.toString(x-1),Integer.toString(y)};
            instructions.add(args);
        }
        // Peut aller en bas
        if (this.maze.isEmptyAt(x,y+1)
                && (f == null || f[0] != "U")
                && (g == null || g[4] == -1)
        ) {
            System.out.println("Peut aller en bas !");
            String[] args = {"D",Integer.toString(x),Integer.toString(y+1)};
            instructions.add(args);
        }
        // Peut aller en haut
        if (this.maze.isEmptyAt(x,y-1)
                && (f == null || f[0] != "D")
                && (g == null || g[5] == -1)
        ) {
            System.out.println("Peut aller en haut !");
            String[] args = {"U",Integer.toString(x),Integer.toString(y-1)};
            instructions.add(args);
        }


        if (f != null) {
            int $ = FindUtils.isInInstructions(instructions, f);
            if ($ != -1) {
                instructions.add(0,instructions.get($));
                instructions.remove($+1);
            }
        }
        if (instructions.size() > 1) {
            System.out.println(String.format("Multiple instructions at (%d,%d)",x,y));
            this.memory.storeMultipleInstructionItem(x,y);
        }
        if (this.memory.getPointer() == -1) {
            this.memory.setPointer();
        } else {
            this.memory.removeFirstInstruction();
        }

        for (String[] $ : instructions) this.memory.store($);



    }


}

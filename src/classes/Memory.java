package classes;

import utils.Colors;
import utils.FindUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class Memory {
    /*
        data contient un tableau de String:
        (0) Instruction
        (1) Position x
        (2) Position y
     */
    private ArrayList<String[]> data = new ArrayList<>();

    /*
        multipleInstructions contient un tableau d'integer:
        (0) x
        (1) y
        (2) état de R
        (3) état de L
        (4) état de D
        (5) état de U
     */
    private ArrayList<int[]> multipleInstructions = new ArrayList<>();
    // Obtenir le prochain élément à exécuter (devrait être toujours 0)
    private int pointer = -1;

    public String[] getFirstInstruction() {
        if (data.size() > 0) return data.get(0);
        else return null;
    }

    public int getPointer() {
        return this.pointer;
    }

    public void setPointer() {
        this.pointer = 0;
    }

    public void removeFirstInstruction() {
        if (data.size() > 0) data.remove(0);
    }

    public void store(String[] args) {
        if (args.length != 3) throw new Error("Cannot accept instructions that does not contain 3 elements.");
        this.data.add(this.pointer, args);
    }


    public int[] getMultipleInstructionItem(int x, int y) {
        int $ = 0;
        while ($ < this.multipleInstructions.size() && (this.multipleInstructions.get($)[0] != x || this.multipleInstructions.get($)[1] != y)) {
            $++;
        }
        return $ >= this.multipleInstructions.size() ? null : this.multipleInstructions.get($);
    }

    public void storeMultipleInstructionItem(int x, int y) {
        System.out.println("STORING MULTIPLE INSTRUCTIONS");
        if (this.getFirstInstruction() != null) {
            int[] h = this.getMultipleInstructionItem(x,y);
            if (h != null) {
                System.out.println("Regenerating a couple based on couple"+ Arrays.toString(h));
                this.multipleInstructions.remove(h);
                h[2+FindUtils.charIndex(this.getFirstInstruction()[0])] = 1;
                this.multipleInstructions.add(h);
            } else {
                System.out.println("Creating a couple");

                int[] $ = {
                        x,y, -1, -1, -1, -1
                };
                $[2+FindUtils.charIndex(this.getFirstInstruction()[0])] = 1;
                this.multipleInstructions.add($);
            }


        }

    }



    public String toString() {
        String $ = "";
        $+=Colors.PURPLE+"MEMORY\n"+Colors.RESET;
        $+=Colors.PURPLE+"X      Y      ORDER\n"+Colors.RESET;
        for (String[] s : this.data) {
            String y = s[0] + new String(new char[7-s[0].length()]).replace("\0", " ");
            String x = s[1] + new String(new char[7-s[1].length()]).replace("\0", " ");
            String order = "";
            for (int i = 2; i < s.length; i++) {
                if (s[i] != null) order+=s[i] + " ";
            }
            $+=Colors.PURPLE + y + x + order + Colors.RESET+"\n";
        }


        $+=Colors.PURPLE+"\n\nMEMORY FOR MULTIPLE INSTRUCTIONS\n"+Colors.RESET;
        $+=Colors.PURPLE+"X      Y      R      L      D      U\n"+Colors.RESET;
        for (int[] s : this.multipleInstructions) {
            String y = Integer.toString(s[0]) + new String(new char[7-Integer.toString(s[0]).length()]).replace("\0", " ");
            String x = Integer.toString(s[1]) + new String(new char[7-Integer.toString(s[1]).length()]).replace("\0", " ");
            String L = Integer.toString(s[2]) + new String(new char[7-Integer.toString(s[2]).length()]).replace("\0", " ");
            String R = Integer.toString(s[3]) + new String(new char[7-Integer.toString(s[3]).length()]).replace("\0", " ");
            String U = Integer.toString(s[4]) + new String(new char[7-Integer.toString(s[4]).length()]).replace("\0", " ");
            String D = Integer.toString(s[5]) + new String(new char[7-Integer.toString(s[5]).length()]).replace("\0", " ");

            $+=Colors.PURPLE + y + x + L + R + U + D+"\n" + Colors.RESET;
        }
        return $;
    }



}

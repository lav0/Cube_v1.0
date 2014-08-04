/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cube;

import java.awt.Color;

/**
 *
 * @author Nevermind
 */
public class face {
    int[] ii = new int[4];
    int[] jj = new int[4];
    int[] kk = new int[4];
    Color clr;
    private int c;
    public face(){
        c=0;
        clr = Color.white;
    }
    public void set(int i, int j, int k){
        ii[c] = i;
        jj[c] = j;
        kk[c] = k;
        c++;
    }


    @Override
    public String toString(){
        String s = "";
        for(int i=0; i<4; i++){
            s = "(" + ii[i] + ", " + jj[i] + ", " + kk[i] + ")\n";
        }
        return s;
    }
}

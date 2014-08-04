/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cube;

/**
 *
 * @author Nevermind
 */
public class plane {
    double A, B, C, D;
    /*
     * n - вектор, перпендикулярный плоскости
     * P - точка, через которую проходит плоскость
     */
    public plane(vector n, vector P){
        A = n.x;
        B = n.y;
        C = n.z;
        D = - n.scal(P);
    }

    public boolean isItOnThePlane(vector P){
        if ((A*P.x + B*P.y + C*P.z + D >= -18)&&(A*P.x + B*P.y + C*P.z + D <= 18)) return true;
        return false;
    }

    @Override
    public String toString(){
        return A + " x + " + B + " y + " + C + " z + " + D;
    }
}

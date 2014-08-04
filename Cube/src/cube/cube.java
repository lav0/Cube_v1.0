/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cube;

/**
 *
 * @author Nevermind
 * арех - координаты вершин куба;
 * е - единичный вектор, вокруг которого происходит вращение;
 * fi - угол повора вокруг вектора е;
 *
 */
public class cube {
    public single_vector e;
    public double fi, R;
    public vector[][][] apex = new vector[2][2][2];
    public boolean isVisible = false;
    public orient ornt = new orient();
    public face[] cubeface = new face[6];

    public cube(){

    }
    public cube(double r){
        e = new single_vector(new vector(1,0,0));
        fi = 0;
        R = r;
        
        int[] sign = new int[3];
        for(int i=0; i<=1; i++){
            for(int j=0; j<=1; j++){
                for(int k=0; k<=1; k++){
                    
                    if (i==0) sign[0] = -1;
                    else sign[0] = 1;
                  
                    if (j==0) sign[1] = -1;
                    else sign[1] = 1;
                 
                    if (k==0) sign[2] = -1;
                    else  sign[2] = 1;
                 
                    apex[i][j][k] = new vector(sign[0]*R, sign[1]*R, sign[2]*R);
                }
            }
        }
        for(int i=0; i<6; i++){
            cubeface[i] = new face();
        }
        
    }

    public void sort(){
        for(int i=1; i<6; i++){
            for(int j=i; j>0; j--){
                face tmp = new face();
                vector eye = new vector(0,0, - new graphics_component().g1);
                vector v1 = apex[cubeface[j].ii[0]][cubeface[j].jj[0]][cubeface[j].kk[0]].sum(eye);
                vector v2 = apex[cubeface[j].ii[2]][cubeface[j].jj[2]][cubeface[j].kk[2]].sum(eye);
                vector vs = v1.sum(v2).multiplyOn(0.5);
                vector w1 = apex[cubeface[j-1].ii[0]][cubeface[j-1].jj[0]][cubeface[j-1].kk[0]].sum(eye);
                vector w2 = apex[cubeface[j-1].ii[2]][cubeface[j-1].jj[2]][cubeface[j-1].kk[2]].sum(eye);
                vector ws = w1.sum(w2).multiplyOn(0.5);
                if(ws.dlina() > vs.dlina()){
                    tmp = cubeface[j-1];
                    cubeface[j-1] = cubeface[j];
                    cubeface[j] = tmp;
                }
            }
        }
    }

    @Override
    public String toString(){
        return this.apex[0][0][0].toString() + "\n"
               + this.apex[0][0][1].toString() + "\n"
               + this.apex[0][1][0].toString() + "\n"
               + this.apex[0][1][1].toString() + "\n"
               + this.apex[1][0][0].toString() + "\n"
               + this.apex[1][0][1].toString() + "\n"
               + this.apex[1][1][0].toString() + "\n"
               + this.apex[1][1][1].toString() + "\n";
    }
}

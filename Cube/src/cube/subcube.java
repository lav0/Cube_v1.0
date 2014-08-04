/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cube;

import java.awt.Color;

/**
 *
 * @author Nevermind
 *
 * класс subcube, наследник класса cube,
 * описывает 27 субкубиков из которых состоит
 * сам Кубик Рубика;
 * base_v - базисный вектор. вместе с вращением всего кубика
 * вращаем и базисный вектор для того, чтобы знать
 * вокруг чего делать sub_turn
 */
public class subcube extends cube {
    public cube[][][] subcubes = new cube[3][3][3];
    public single_vector[] base_v = new single_vector[3];
    public position[][][] posit = new position[3][3][3];
    public double fi, psi;
    int gap = 22;

    private void base_vector(){
        base_v[0] = new single_vector(new vector(1, 0, 0));
        base_v[1] = new single_vector(new vector(0, 1, 0));
        base_v[2] = new single_vector(new vector(0, 0, 1));
    }
    public subcube(){
        super();
        base_vector();
    }
    public subcube(double r){
        super(r);
        int gap_i = -gap / 2, gap_j = -gap / 2, gap_k = -gap / 2;
        for(int k1 = 0; k1 < 3; k1++){
            for(int k2 = 0; k2 <3; k2++){
                for(int k3 = 0; k3 < 3; k3++){
                    subcubes[k1][k2][k3] = new cube(270);
                    for(int i = 0; i < 2; i++){ gap_i *= -1;
                        for(int j = 0; j < 2; j++){ gap_j *= -1;
                            for(int k = 0; k < 2; k++){ gap_k *= -1;

                                subcubes[k1][k2][k3].apex[i][j][k] = new vector(-R/2 + R*(k1+i)/3 + gap_i,
                                                                                    -R/2 + R*(k2+j)/3 + gap_j,
                                                                                        -R/2 + R*(k3+k)/3 + gap_k);
                            //    posit[i][j][k] = new position(i, j, k);
                            }
                        }
                    }
                    for(int i=0; i<6; i++){
                        subcubes[k1][k2][k3].cubeface[i] = new face();
                    }
                    subcubes[k1][k2][k3].cubeface[0].set(0, 0, 0);
                    subcubes[k1][k2][k3].cubeface[0].set(0, 0, 1);
                    subcubes[k1][k2][k3].cubeface[0].set(0, 1, 1);
                    subcubes[k1][k2][k3].cubeface[0].set(0, 1, 0);
                    subcubes[k1][k2][k3].cubeface[1].set(1, 0, 0);
                    subcubes[k1][k2][k3].cubeface[1].set(1, 0, 1);
                    subcubes[k1][k2][k3].cubeface[1].set(1, 1, 1);
                    subcubes[k1][k2][k3].cubeface[1].set(1, 1, 0);

                    subcubes[k1][k2][k3].cubeface[2].set(0, 0, 0);
                    subcubes[k1][k2][k3].cubeface[2].set(1, 0, 0);
                    subcubes[k1][k2][k3].cubeface[2].set(1, 0, 1);
                    subcubes[k1][k2][k3].cubeface[2].set(0, 0, 1);
                    subcubes[k1][k2][k3].cubeface[3].set(0, 1, 0);
                    subcubes[k1][k2][k3].cubeface[3].set(1, 1, 0);
                    subcubes[k1][k2][k3].cubeface[3].set(1, 1, 1);
                    subcubes[k1][k2][k3].cubeface[3].set(0, 1, 1);

                    subcubes[k1][k2][k3].cubeface[4].set(0, 0, 0);
                    subcubes[k1][k2][k3].cubeface[4].set(1, 0, 0);
                    subcubes[k1][k2][k3].cubeface[4].set(1, 1, 0);
                    subcubes[k1][k2][k3].cubeface[4].set(0, 1, 0);
                    subcubes[k1][k2][k3].cubeface[5].set(0, 0, 1);
                    subcubes[k1][k2][k3].cubeface[5].set(1, 0, 1);
                    subcubes[k1][k2][k3].cubeface[5].set(1, 1, 1);
                    subcubes[k1][k2][k3].cubeface[5].set(0, 1, 1);
                }
            }
        }
        Color FON = Color.BLACK;
        for(int k1=0; k1<3; k1++){
            for(int k2=0; k2<3; k2++){
                subcubes[0][k1][k2].cubeface[0].clr = Color.YELLOW;
                subcubes[0][k1][k2].cubeface[1].clr = FON;
                subcubes[1][k1][k2].cubeface[0].clr = FON;
                subcubes[1][k1][k2].cubeface[1].clr = FON;
                subcubes[2][k1][k2].cubeface[0].clr = FON;
                subcubes[2][k1][k2].cubeface[1].clr = Color.GRAY;
//
               subcubes[k1][0][k2].cubeface[2].clr = Color.BLUE;
               subcubes[k1][0][k2].cubeface[3].clr = FON;
               subcubes[k1][1][k2].cubeface[2].clr = FON;
               subcubes[k1][1][k2].cubeface[3].clr = FON;
               subcubes[k1][2][k2].cubeface[2].clr = FON;
               subcubes[k1][2][k2].cubeface[3].clr = Color.GREEN;

               subcubes[k1][k2][0].cubeface[4].clr = Color.RED;
               subcubes[k1][k2][0].cubeface[5].clr = FON;
               subcubes[k1][k2][1].cubeface[4].clr = FON;
               subcubes[k1][k2][1].cubeface[5].clr = FON;
               subcubes[k1][k2][2].cubeface[4].clr = FON;
               subcubes[k1][k2][2].cubeface[5].clr = Color.MAGENTA;
            }
        }
        base_vector();


    }
    /*
     * ищет девять субкубиков которые расположены
     * в плоскости (c, n), где с - x, y или z
     * n - 0, 1, 2 - одна из трех плоскостей
     */
    public position[] search(char c, int n){
        position[] p = new position[9];
        int i1 = 0;
         for(int k1=0; k1<3; k1++){
                        for(int k2=0; k2<3; k2++){
                            for(int k3=0; k3<3; k3++){
                                if(c=='x'){
                                if(posit[k1][k2][k3].x == n){
                                    p[i1] = new position(k1, k2, k3);
                                    i1++;
                                }
                                }
                                if(c=='y'){
                                    if(posit[k1][k2][k3].y == n){
                                    p[i1] = new position(k1, k2, k3);
                                    i1++;
                                }
                                }
                                if(c=='z'){
                                    if(posit[k1][k2][k3].z == n){
                                    p[i1] = new position(k1, k2, k3);
                                    i1++;
                                }
                                }
                            }
                        }
                    }
        return p;
    }

    public double aprAng(position[] p, char c, int n){
        vector AC = new vector(270, 270, 270);
        vector BD = new vector(0, 270, 270);
        position[] pos = new position[9];
        if(n==1){
            pos = search(c, n-1);
        }
        if(n==0 || n==2){
            pos = p;
        }
            position p1 = new position(-1,0,0);
            vector A_v = new vector(1,1,1);
            vector B_v = new vector(1,1,1);
            vector C_v = new vector(0,0,0);
            vector D_v = new vector(0,0,0);
            boolean swtch = false;
            for(position p2:pos){
                if((p2.x == 1 && p2.y == 1)||
                        (p2.x == 1 && p2.z == 1)||
                                (p2.y == 1 && p2.z == 1))
                                p1 = p2;
            }
            for(int i=0; i<2; i++){
                for(int j=0; j<2; j++){
                    for(int k=0; k<2; k++){
                        for(int l = 0; l < 2; l++){
                            for(int m = 0; m < 2; m++){
                                for(int o = 0; o < 2; o++){
                                    vector v1 = this.subcubes[1][1][1].apex[i][j][k];
                                    vector v2 = this.subcubes[p1.x][p1.y][p1.z].apex[l][m][o];
                                    vector sprt_vec = v1.razn(v2);
                                    if (sprt_vec.dlina() < gap + 2){
                                        if(!swtch){
                                            A_v = v1;
                                            B_v = v2;
                                            swtch = true;
                                        }
                                        else{
                                            C_v = v1;
                                            D_v = v2;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            AC = C_v.razn(A_v);
            BD = D_v.razn(B_v);
        double cos_ret = AC.scal(BD) / (AC.dlina()*BD.dlina());
        //System.out.println("cos_ret = " + cos_ret + "dlina AC = " + AC.dlina() + " dlina BD = " + BD.dlina());
        if (cos_ret <= 1)return Math.acos(AC.scal(BD) / (AC.dlina()*BD.dlina()));
        else return 0.0;
    }

    public double distToSubcube(int i, int j, int k){
        vector A = subcubes[i][j][k].apex[0][0][0];
        A = new vector(A.x, A.y, A.z + new graphics_component().g1);
        vector B = subcubes[i][j][k].apex[1][1][1];
        B = new vector(B.x, B.y, B.z + new graphics_component().g1);
        return new vector((A.x + B.x)/2, (A.y + B.y)/2, (A.z + B.z)/2).dlina();
    }

    public dni[] dists(){
        dni[] arr = new dni[27];
        int c = 0;
        for(int k1=0; k1<3; k1++){
            for(int k2=0; k2<3; k2++){
                for(int k3=0; k3<3; k3++){
                    arr[c] = new dni();
                    arr[c].d = distToSubcube(k1, k2, k3);
                    arr[c].x = k1;
                    arr[c].y = k2;
                    arr[c].z = k3;
                    c++;
                }
            }
        }
        dni tmp;
        for(int i=1; i < arr.length; i++){
            for(int j=i; j>0 && arr[j-1].d > arr[j].d; j--){
                tmp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = tmp;
            }
        }
        return arr;
    }

    public int[] getSub(position p){
        int[] k = new int[3];
        for(int k1=0; k1<3; k1++){
            for(int k2=0; k2<3; k2++){
                for(int k3=0; k3<3; k3++){
                    if (this.posit[k1][k2][k3].equals(p)){
                        k[0] = k1; k[1] = k2; k[2] = k3;
                        return k;
                    }
                }
            }
        }
        return k;
    }

}

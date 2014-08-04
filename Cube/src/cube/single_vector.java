package cube;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nevermind
 * класс single_vector описывает вектор единичной длины
 */
public class single_vector{

    public double i, j, k;
    //public double x, y, z;

    public single_vector(vector v){

        if ((v.x==0)&&(v.y==0)&&(v.z==0)){
            i = 0; j = 0; k = 1;
        }
        else{
            i = v.x / v.dlina();
            j = v.y / v.dlina();
            k = v.z / v.dlina();
        }
    }
    public single_vector(double x, double y, double z){
        this(new vector(x, y, z));
    }
    /*
     * нормирование вектора - приведение его длины к единице
     */
    public single_vector fix_me(){
        if ((this.i==0)&&(this.j==0)&&(this.k==0)){
            return new single_vector(new vector(0, 0, 1));}
        else{
            double norma = Math.sqrt(this.i*this.i + this.j*this.j + this.k*this.k);
            this.i = this.i / norma;
            this.j = this.j / norma;
            this.k = this.k / norma;
            return this;
        }
    }

    public vector multyplyOn(double a){
        return new vector(a*this.i, a*this.j, a*this.k);
    }
    @Override
    public String toString(){
        return "i = " + Double.toString(this.i) +
                " j = " + Double.toString(this.j) +
                " k = " + Double.toString(this.k);
    }


}

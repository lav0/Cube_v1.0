/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cube;

/**
 *
 * @author Nevermind
 */
public class vector {

    public double x, y, z;

    public vector(double xx, double yy, double zz){
        this.x = xx;
        this.y = yy;
        this.z = zz;
    }
    public vector(single_vector sv){
        this.x = sv.i;
        this.y = sv.j;
        this.z = sv.k;
    }
    /*
     * скалярное произведение векторов
     */
    public double scal(vector y)
    {
        return this.x*y.x + this.y*y.y + this.z*y.z;
    }
    /*
     * векторное произведение векторов
     */
    public vector vect(vector y){
        vector r = new vector(0,0,0);
        r.x = this.y*y.z - this.z*y.y;
        r.y = this.z*y.x - this.x*y.z;
        r.z = this.x*y.y - this.y*y.x;

        return r;
    }
    public vector razn(vector v){
        return new vector(this.x - v.x, this.y - v.y, this.z - v.z);
    }
    public vector sum(vector v){
        return new vector(this.x + v.x, this.y + v.y, this.z + v.z);
    }
    public vector multiplyOn(double a){
        return new vector(a*this.x, a*this.y, a*this.z);
    }
    /*
     * длина вектора (евклидова норма)
     */
    public double dlina(){
        return Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
    }
    /*
     * конвёрт вектора в кватернион
     */
    public quaternionus toQuaternion(){
        return new quaternionus(0, this.x, this.y, this.z);
    }
    /*
     * близость векторов порядка эпсилон
     * (или длинна вектора разности двух данных векторов
     *  не превышает епсилон)
     */
    public boolean equals(vector v, double epsn){
        return Math.sqrt((this.x - v.x)*(this.x - v.x) + (this.y - v.y)*(this.y - v.y) + (this.z- v.z)*(this.z- v.z)) < epsn;
    }
    @Override
    public String toString(){
        return "( " + Double.toString(this.x) +
                ", " + Double.toString(this.y) +
                ", " + Double.toString(this.z) + " )";
    }

  
}

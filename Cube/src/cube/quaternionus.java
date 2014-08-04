/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cube;

/**
 *
 * @author Nevermind
 * класс описывает гиперкомплексные числа - кватернионы;
 */
public class quaternionus {

    double q0, q1, q2, q3;  // параметры кватерниона

    public quaternionus(double a, double i, double j, double k){
        q0 = a;
        q1 = i;
        q2 = j;
        q3 = k;
    }
    public quaternionus(double a, vector v){
        q0 = a;
        q1 = v.x;
        q2 = v.y;
        q3 = v.z;
    }
    //скалярная часть
    public double scal_part(){
        return this.q0;
    }
    //векторная часть
    public vector vect_part(){
        return new vector(this.q1, this.q2, this.q3);
    }
    // норма кватерниона
    public double norma(){

        return this.q0*this.q0 + this.q1*this.q1 + this.q2*this.q2 + this.q3*this.q3;

    }
    //сопряжённый кватернион
    public quaternionus T(){
       
        return new quaternionus(this.q0, -this.q1, -this.q2, -this.q3);

    }
    //обратный кватернион
    public quaternionus backward(){
        return new quaternionus(this.q0 / this.norma(),
                                    this.q1 / this.norma(),
                                        this.q2 / this.norma(),
                                            this.q3 / this.norma()).T();
    }
    //операция сложения кватернионов
    public quaternionus plus(quaternionus u){
        return new quaternionus(this.q0+u.q0, this.q1+u.q1, this.q2+u.q2, this.q3+u.q3);
    }
    //операция умножения кватернионов
    public quaternionus multiplyOn(quaternionus u){
        return new quaternionus(this.q0*u.q0 - this.q1*u.q1 - this.q2*u.q2 - this.q3*u.q3,
                                    this.q0*u.q1 + this.q1*u.q0 + this.q2*u.q3 - this.q3*u.q2,
                                        this.q0*u.q2 + this.q2*u.q0 + this.q3*u.q1 - this.q1*u.q3,
                                            this.q0*u.q3 + this.q3*u.q0 + this.q1*u.q2 - this.q2*u.q1);
    }

    public quaternionus multiplyOn(double a){
        return new quaternionus(a*this.q0, a*this.q1, a*this.q2, a*this.q3);
    }
    @Override
    public String toString(){
        return "q0 = " + Double.toString(q0) +
                " q1 = " + Double.toString(q1) +
                " q2 = " + Double.toString(q2) +
                " q3 = " + Double.toString(q3);

    }
    // сравнение двух кватернионов
    public boolean equals(quaternionus v){
        return ((this.q0==v.q0)&&(this.q1==v.q1)&&(this.q2==v.q2)&&(this.q3==v.q3));
    }

}

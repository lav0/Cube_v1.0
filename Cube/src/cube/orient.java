/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cube;

/**
 *
 * @author Nevermind
 */
/*
 * класс отвечает за ориентацию каждого конретного субкуба
 */
public class orient {
    vector x, y, z;
    private final double cos = Math.cos(Math.PI/4);
    private final double sin = Math.sin(Math.PI/4);
    public orient(){
        this.x = new vector(1,0,0);
        this.y = new vector(0,1,0);
        this.z = new vector(0,0,1);
    }
    /*
     * функция изменения ориентации при повороте грани
     * вокруг оси x, y или z на угол равны Пи пополам
     */
    public orient convFunct(char c, int dir){
        if (dir==1) return convFunct(c);
        if (dir==-1) return convFunct(c).convFunct(c).convFunct(c);
        return this;
    }
    private orient convFunct(char c){
        if(c=='x') {
            quaternionus Q = new quaternionus(cos, new single_vector(1,0,0).multyplyOn(sin));
            this.y = Q.multiplyOn(this.y.toQuaternion()).multiplyOn(Q.T()).vect_part();
            this.z = Q.multiplyOn(this.z.toQuaternion()).multiplyOn(Q.T()).vect_part();
        }
        if(c=='y'){
            quaternionus Q = new quaternionus(cos, new single_vector(0,1,0).multyplyOn(sin));
            this.x = Q.multiplyOn(this.x.toQuaternion()).multiplyOn(Q.T()).vect_part();
            this.z = Q.multiplyOn(this.z.toQuaternion()).multiplyOn(Q.T()).vect_part();
        }
        if(c=='z'){
            quaternionus Q = new quaternionus(cos, new single_vector(0,0,1).multyplyOn(sin));
            this.x = Q.multiplyOn(this.x.toQuaternion()).multiplyOn(Q.T()).vect_part();
            this.y = Q.multiplyOn(this.y.toQuaternion()).multiplyOn(Q.T()).vect_part();
        }
        this.x.x = (int) this.x.x; this.x.y = (int) this.x.y; this.x.z = (int) this.x.z;
        this.y.x = (int) this.y.x; this.y.y = (int) this.y.y; this.y.z = (int) this.y.z;
        this.z.x = (int) this.z.x; this.z.y = (int) this.z.y; this.z.z = (int) this.z.z;
        return this;
    }
    @Override
    public String toString(){
        return "orient = (" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}

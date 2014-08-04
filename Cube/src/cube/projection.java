/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cube;

import java.awt.Point;

/**
 *
 * @author Nevermind
 *  класс реализует проэкцию трехмерного вектора
 *  на плоскость z = -p; глаз находиться в точке G(0, 0, -g)
 */
public class projection {
    public double x, y;    // координаты проэкций на
    double p, g;       //    плоскость z = p

    public projection(vector v, double pp, double gg)// </editor-fold>
{
        p = pp; g = gg;
        double coof = 0;
        if ((p>=v.z)&&(g>p)) coof = (g - p)/(v.z + g);
        x = v.x*coof;
        y = v.y*coof;
    }

    public Point getPoint(){
        return new Point((int)this.x,(int)this.y);
    }
    public boolean isAround(Point check, double epsn){
        check.x -= new graphics_component().getWidth()/2;
        check.y -= new graphics_component().getHeight()/2;
        return ((check.x - this.x)*(check.x - this.x) + (check.y - this.y)*(check.y - this.y)) <= epsn*epsn;
    }
   
}

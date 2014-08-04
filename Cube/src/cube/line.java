/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cube;

import java.awt.*;
/**
 *
 * @author Nevermind
 */
public class line {
    double A, B, C;
    double lyamda;
    String str;
    public line(double x1, double y1, double x2, double y2){
        A = y1 - y2;
        B = x2 - x1;
        C = x1 * y2 - x2*y1;
        if(C!=0) lyamda = - Math.signum(C) / Math.sqrt(A*A + B*B);
        else lyamda = 1 / Math.sqrt(A*A + B*B);
    }
    public line(Point p1, Point p2){
        
        A = p1.y - p2.y;
        B = p2.x - p1.x;
        C = p1.x * p2.y - p2.x*p1.y;
        if(C!=0) lyamda = - Math.signum(C) / Math.sqrt(A*A + B*B);
        else lyamda = 1 / Math.sqrt(A*A + B*B);
        str = (A) + " x + " + (B) + " y + " + (C);
        
    }
    public line(Point p1, vector v){
        Point p2 = new Point((int)(p1.x + v.x), (int)(p1.y + v.y));
        A = p1.y - p2.y;
        B = p2.x - p1.x;
        C = p1.x * p2.y - p2.x*p1.y;
        if(C!=0) lyamda = - Math.signum(C) / Math.sqrt(A*A + B*B);
        else lyamda = 1 / Math.sqrt(A*A + B*B);
    }
    /*
     * возвращаем расстояние от прямой до точки p
     */
    public double distantion(Point p){
        //System.out.println("lyamda * A= " + lyamda * A);
        //System.out.println("lyamda * B= " + lyamda * B);
        //System.out.println("lyamda * C= " + lyamda * C);
        return lyamda * (A*p.x + B*p.y + C);
    }
    // используется для определения того, по какую сторону лежит передаваемая точка
    public double distNo(Point p){
        return A*p.x + B*p.y + C;
    }
    /*
     * получая массив p[], отвечает на вопрос:
     * "все ли точки из етого массива лежат
     * по одну сторону от текущей прямой?"
     */
    public boolean isItEdge(Point p[]){
        int ind;
        if(p.length != 1){
            for(int i=1; i<=p.length - 1; i++){
                ind = i-1;
                while (distantion(p[i])==0 && i+1 != p.length){
                   i++;
                                  // System.out.println("dist  = " + this.lyamda);

                }
               // System.out.println("p[" + i + "] " + distantion(p[i]) + "   p[" + (ind) + "] " + distantion(p[ind]));
                if (distantion(p[i]) * distantion(p[ind]) < 0) return false;
            }
            return true;
        }
        else{return true;}
    }

}

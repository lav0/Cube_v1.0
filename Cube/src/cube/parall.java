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
public class parall{
    Point p1, p2, p3, p4;
    int num;
    public parall(){
        num = 1;
    }
    public parall(Point p1, Point p2, Point p3, Point p4){
        this.p1 = p1; this.p2 = p2; this.p3=p3; this.p4=p4;
    }
    public parall(Point[] p){
        if (p.length != 4) System.out.println("Глянь в класс параллелограм. Ошибка!");
        p1 = p[0]; p2 = p[1]; p3 = p[2]; p4 = p[3];
    }
    // не работает
    public boolean check(){
        double A1 = new line(p1, p2).A; double B1 = new line(p1, p2).B;
        double A2 = new line(p3, p4).A; double B2 = new line(p3, p4).B;
        double A3 = new line(p3, p2).A; double B3 = new line(p3, p2).B;
        double A4 = new line(p1, p4).A; double B4 = new line(p1, p4).B;
        if ((A1 == A2) && (A3 == A4) && (B1 == B2) && (B3 == B4)) return true;
        return false;
    }
    // проверяет внутри ли параллелипипеда (четырехугольника) находится точка
    public boolean isInside(Point check_p){
        if (((new line(p1, p2).distNo(check_p) * new line(p4, p3).distNo(check_p)) < 0) &&
                ((new line(p2, p3).distNo(check_p) * new line(p1, p4).distNo(check_p)) < 0)) return true;
        else return false;
    }

    // проверяет является ли точка вершиной текущего параллелограмма
    public boolean isIncludePoint(Point p){
        if ((this.p1.x == p.x && this.p1.y == p.y)||
                (this.p2.x == p.x && this.p2.y == p.y)||
                    (this.p3.x == p.x && this.p3.y == p.y)||
                        (this.p4.x == p.x && this.p4.y == p.y)) return true;
        return false;
    }

    public Point findPoint(Point p){
        if (this.p1 == p) return p1;
        if (this.p2 == p) return p2;
        if (this.p3 == p) return p3;
        if (this.p4 == p) return p4;
        return new Point(0, 0);
    }

    /*
     * для заполнения четырех вершин в нужном порядке
     */
    public int next(Point p){
        if (num==1) {p1=p; num++; return 1;}
        if (num==2) {p2=p; num++; return 2;}
        if (num==3) {p3=p; num++; return 3;}
        if (num==4) {p4=p; num=1; return 4;}
        return 0;
    }
    /*
     * проверяет в правильном ли порядке расположены вершины
     * (т.е. соседние вершины образуют грань)
     * если нет, то сортирует в нужном порядке
     */
    public boolean isItSort(){
        Point[] ar_p = new Point[]{p1, p2, p3, p4};
        if(new line(p1, p2).isItEdge(ar_p) &&
                new line(p2, p3).isItEdge(ar_p) &&
                    new line(p3, p4).isItEdge(ar_p) &&
                        new line(p1, p4).isItEdge(ar_p)){
            return true;
        }
        return false;
    }
    public boolean sort(){
        if (isItSort()) return true;
        Point tmp = new Point();
        tmp = p1; p1=p2; p2=tmp;
        if (isItSort()) return true;
        tmp = p2; p2=p3; p3=tmp;
        if (isItSort()) return true;
        return false;
    }

    @Override
    public String toString(){
        return this.p1 + " " + this.p2 + " " + this.p3 + " " + this.p4;
    }
}

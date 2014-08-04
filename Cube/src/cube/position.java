/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cube;

/**
 *
 * @author Nevermind
 */
public class position {
     int x, y, z;
    

     position(int xx, int yy, int zz){
         if(!(xx>2 || yy>2 || zz>2)){
         this.x = xx;
         this.y = yy;
         this.z = zz;
         }
        

     }
     /*
      *  получает в виде char
      *  x, y или z - ось, вокруг которой происходит вращение.
      *  сама функция возвращает позицию сабкуба в большом кубе
      *  при повороте его вокруг вышесказанной оси на 90 градусов.
      *  позиция субкуба в кубе - есть трехмерная переменная, например
      *  (0, 0, 0)
      */
     public position convFunct(char c, int dir){
         position psn = new position(-1,0,0);
         if (c!='x' && c!='y' && c!='z') return psn;
         if (c == 'x') psn = new position(this.x, 2 - this.z, this.y);
         if (c == 'y') psn = new position(this.z, this.y, 2 - this.x);
         if (c == 'z') psn = new position(2 - this.y, this.x, this.z);
         if(dir==-1){
             for(int i=0; i<2; i++){
                 psn = psn.convFunct(c);
             }
         }
         return psn;
     }
     private position convFunct(char c){
         if (c == 'x') return new position(this.x, 2 - this.z, this.y);
         if (c == 'y') return new position(this.z, this.y, 2 - this.x);
         if (c == 'z') return new position(2 - this.y, this.x, this.z);
         return new position(-1,0,0);
     }
     // проверяет смежность двух позиций
     public boolean isAdjacent(position p){
         if((this.x == p.x && this.y == p.y)||
            (this.x == p.x && this.z == p.z)||
            (this.y == p.y && this.z == p.z)) return true;
         else return false;
     }
     public boolean isAdjacentToAll(position p[]){
         for(int i=0; i<p.length; i++){
             if(p[i]!=null){
             if(!((this.x == p[i].x && this.y == p[i].y)||
            (this.x == p[i].x && this.z == p[i].z)||
            (this.y == p[i].y && this.z == p[i].z))){
                 return false;
             }
             }
         }
         return true;
     }
     // проверяет наличение данной вершины в передаваемом массиве
     public boolean isIncludedBy(position p[]){
         for(int i=0; i<p.length; i++){
             if (p[i].x == this.x && p[i].y == this.y && p[i].z == this.z) return true;
         }
         return false;
     }

     public boolean equals(position p){
         if (this.x == p.x && this.z == p.z && this.y == p.y) return true;
         return false;
     }

     @Override
     public String toString(){
         return "(" + this.x + ", " + this.y + ", " + this.z + ")";
     }
}

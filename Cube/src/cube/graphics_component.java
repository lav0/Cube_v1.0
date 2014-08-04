/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cube;

 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.*;
/**
 *
 * @author Nevermind
 */
public class graphics_component extends JComponent implements MouseListener, MouseMotionListener{
    /*
     * p - растояние от центра координат до плоскости проецирования
     * g1 - (оно же g) есть третья координа точки G, т.е. G = {0, 0, -g1}
     * kr_z - критическое значение,
     * плоскость z = kr_z - есть плоскость, разделяющая сферу,
     * описываемую вершинами кубика, на "дальние" и "ближние" точки
     * kr_r - радиус круга, в котором находятся все проэцируемые точки
     */
    double p = 250, g1 = 600, kr_z, kr_r;
    double dfi = Math.PI / 2;
    double tetta = 0, fi;
   // public cube myCube = new cube(100);


    public javax.swing.Timer myTimer = new javax.swing.Timer(25, new ClockListener());
    // p1[][][] - используется для непосретственного рисования
    public Point[][][] p1 = new Point[2][2][2];
    // p2[][][] - проекции вершинсамого куба на плоскость
    public Point[][][] p2 = new Point[2][2][2];

    public double r = 130;
    public double R = r * Math.sqrt(3);
    public subcube subcube = new subcube(2*R/Math.sqrt(3));

    public int w, h;

    public vector v1 = new vector(0,0,0);
    public vector v2 = new vector(0,0,0);
    public single_vector sbtrn_v = new single_vector(v1);

    position vrps = new position(-2, -2, -2);
    int cubeInd;

    public graphics_component(){
        
        for(int i = 0; i<=1; i++){
            for(int j = 0; j<=1; j++){
                for(int k = 0; k<=1; k++){
                        p1[i][j][k] = new Point(0, 0);
                     
                }
            }
        }
        
        this.setSize(new Dimension(400, 400));
        this.setPreferredSize(this.getSize());
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for(int k1 = 0; k1 < 3; k1++){
            for(int k2 = 0; k2 < 3; k2++){
                for(int k3 = 0; k3 < 3; k3++){
                    subcube.fi = Math.PI / 180;
                    subcube.posit[k1][k2][k3] = new position(k1, k2, k3);
                }
            }
        }
        
        w = this.getWidth()/2; h = this.getHeight()/2;
        kr_z = -R*R/g1;//(g1-p)*t(0, (g1-p)*Math.tan(Math.asin(Math.sqrt(3)*135/g1)), 135) - g1;
        kr_r = (g1-p)*Math.tan(Math.asin(R/g1));
      //  myTimer.start();
        this.repaint();
    }

 
    public void paintIt(){
        this.repaint();

    }

    @Override
    public void paintComponent(Graphics g){
      g.setColor(Color.white);
      g.fillRect(0, 0, 2*w, 2*h);
      g.setColor(Color.red);
      dni[] kubiki = subcube.dists();

      for(int dnind = kubiki.length-1; dnind >=0; dnind--){
            int k1 = kubiki[dnind].x;
            int k2 = kubiki[dnind].y;
            int k3 = kubiki[dnind].z;
                  for(int i = 0; i < 2; i++){
                      for(int j = 0; j < 2; j++){
                          for(int k = 0; k < 2; k++){
                              p1[i][j][k].x = (int) new projection(subcube.subcubes[k1][k2][k3].apex[i][j][k], p, g1).x;
                              p1[i][j][k].y = (int) new projection(subcube.subcubes[k1][k2][k3].apex[i][j][k], p, g1).y;
                              // определяем все восемь вершин самого куба
                              p2[i][j][k] = new Point((int) new projection(subcube.subcubes[2*i][2*j][2*k].apex[i][j][k], p, g1).x,
                                      (int) new projection(subcube.subcubes[2*i][2*j][2*k].apex[i][j][k], p, g1).y);
                          }
                      }
                  }
              int[] xx = new int[4];
              int[] yy = new int[4];
              subcube.subcubes[k1][k2][k3].sort();
                  for(int i=0; i<6; i++){
                      for(int j=0; j<4; j++){
                          int ir = subcube.subcubes[k1][k2][k3].cubeface[i].ii[j];
                          int jr = subcube.subcubes[k1][k2][k3].cubeface[i].jj[j];
                          int kr = subcube.subcubes[k1][k2][k3].cubeface[i].kk[j];
                          xx[j] = (int) new projection(subcube.subcubes[k1][k2][k3].apex[ir][jr][kr], p, g1).x + w;
                          yy[j] = (int) new projection(subcube.subcubes[k1][k2][k3].apex[ir][jr][kr], p, g1).y + h;
                      }
                  

      g.setColor(subcube.subcubes[k1][k2][k3].cubeface[i].clr);

      g.fillPolygon(xx, yy,4);
        
        }
        }
    }
    char[] c = {'x', 'y', 'z'};
    int rdt = 0;
    public void mouseClicked(MouseEvent e){
        
    }
    // отвечает за тот факт, что вблизи ли вершины находится курсор
    boolean isNear = false, swt = true, justcome = true, 
            subturnIsWorking = false, mouseWasDragged = false, cursorIsInside;
    // отвечает за знак перед корнем из дескриминанта
    int D_sign, ind = -1;
    int max_ind1=-1, max_ind2=-1;
    int direction = 1;
    public void mousePressed(MouseEvent e) {
        if(!subturnIsWorking){
            cursorIsInside = findWhereCursorIs(e.getX() - w, e.getY() - h);}
        /*
         * просматрвиаем все вершины куба (8 штук)
         * проецируем каждую вершину на нашу плоскость
         * и проверяем рядом ли находится курсор
         * если рядом, то сохраняем текущую вершину как вектор v1
         */
        D_sign = -1;
        v1 = comeback(new Point(e.getX()-w, e.getY()-h), r);
        for(int k1=0; k1<=1; k1++){
            for(int k2=0; k2<=1; k2++){
                for(int k3=0; k3<=1; k3++){
                    if (subcube.subcubes[2*k1][2*k2][2*k3].isVisible && e.getButton()==1){
                       projection ppp = new projection(subcube.subcubes[2*k1][2*k2][2*k3].apex[k1][k2][k3], p, g1);
                       if(ppp.isAround(new Point(e.getX(), e.getY()), 6)){
                           v1 = subcube.subcubes[2*k1][2*k2][2*k3].apex[k1][k2][k3];
                           if (v1.z >= kr_z) D_sign = 1;
                           else{ D_sign = -1;}
                           isNear = true;
                       }
                    }
                }
            }
        }
        mouseWasDragged = false;
        justcome = true;
        swt = isInsideCircle(e.getX() - w, e.getY() - h);
    }

    public void mouseReleased(MouseEvent e) {  
        if(!myTimer.isRunning() && !isNear && mouseWasDragged && cursorIsInside){
            subcube.psi *= direction;// * Math.PI / 360;
            subturnIsWorking = true;
            myTimer.start();
        }

        isNear = false;
    }

    public void mouseEntered(MouseEvent e) {    }

    public void mouseExited(MouseEvent e) {    }

    public void mouseDragged(MouseEvent e) {
        double x1 = e.getX() - w;
        double y1 = e.getY() - h;
        mouseWasDragged = true;
        /*
         * при выходе или при входе курсора в окружность
         * меняем знак перед дескриминантом.
         * при выходе из окружности точку курсора
         * инверсируем относительно той же окружности
         * т.о. при выходе курсора за границы достижимости вершин
         * куба, "схваченная" вершина продолжаем вращаться "вглубь" или
         * наоборот "вблизь"
         */
        if(isNear){
            if(isInsideCircle(x1, y1) && !swt){
                D_sign *= -1;
                swt = true;
            }
            if(!isInsideCircle(x1, y1)){
                // инверсия
                double nrmkv = x1*x1 + y1*y1;
                x1 = kr_r*kr_r*x1 / nrmkv;
                y1 = kr_r*kr_r*y1 / nrmkv;
                if (swt) {
                    D_sign *= -1;
                    swt = false;
                }
            }

            v2 = comeback(new Point((int)x1, (int)y1), r);
            if(!(v1.scal(v2) / (v1.dlina() * v2.dlina()) > 1))
                subcube.fi = Math.acos(v1.scal(v2) / (v1.dlina() * v2.dlina()));
            else subcube.fi = 0;
            subcube.subcubes[0][0][0].e = new single_vector(v1.vect(v2));
            quaternionus myQ = new quaternionus(Math.cos(subcube.fi/2),
                           subcube.subcubes[0][0][0].e.multyplyOn(Math.sin(subcube.fi / 2)));
            /*
             * вращаем базис вектора для того,
             * чтобы знать вокруг чего делать subturn()
             */
                 for(int i=0; i<3; i++){
                     vector support_v = new vector(subcube.base_v[i].i,subcube.base_v[i].j,subcube.base_v[i].k);
                     support_v = myQ.multiplyOn(support_v.toQuaternion()).multiplyOn(myQ.T()).vect_part();
                     subcube.base_v[i] = new single_vector(support_v);
                 }

                  for(int k1 = 0; k1 < 3; k1++){
                    for(int k2 = 0; k2 < 3; k2++){
                      for(int k3 = 0; k3 < 3; k3++){
                              for(int i = 0; i < 2; i++){
                                  for(int j = 0; j < 2; j++){
                                      for(int k = 0; k < 2; k++){
                                          subcube.subcubes[k1][k2][k3].apex[i][j][k] = myQ.multiplyOn(subcube.subcubes[k1][k2][k3].apex[i][j][k].toQuaternion()).multiplyOn(myQ.T()).vect_part();
                                      }
                                  }
                              }
                          }
                      }
              }

            paintIt();
            v1 = v2;
        }
        /*
         * если курсор мыши при нажатии левой кнопки был не вблизи
         * одной из вершин, то срабатывает следующий блок кода.
         * здесь ищется один из векторов массива base_v[], вокруг которого
         * будет происходить вращение одной из граней куба
         */
        else{isNear = false;
            if (!subturnIsWorking && cursorIsInside){
                double max_scal1 = 0, max_scal2 = 0;
                D_sign = -1;
                v2 = comeback(new Point((int) x1, (int) y1), r);
                if (!(v1.scal(v2) / (v1.dlina() * v2.dlina()) > 1)) {
                    subcube.psi = Math.acos(v1.scal(v2) / (v1.dlina() * v2.dlina()));
                }
                if (justcome) {
                    justcome = false;
                    for (int i = 0; i < 3; i++) {
                        if (max_scal1 < Math.abs(new vector(subcube.base_v[i]).scal(v2.razn(v1)))) {
                            max_scal1 = Math.abs(new vector(subcube.base_v[i]).scal(v2.razn(v1)));
                            max_ind1 = i;
                        }
                        if (max_scal2 < Math.abs(new vector(subcube.base_v[i]).scal(v1))) {
                            max_scal2 = Math.abs(new vector(subcube.base_v[i]).scal(v1));
                            max_ind2 = i;
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        if (i != max_ind1 && i != max_ind2) {
                            ind = i;
                        }
                    }
                }
                if ((v1.vect(v2).scal(subcube.base_v[ind].multyplyOn(1)))
                        > (v1.vect(v2).scal(subcube.base_v[ind].multyplyOn(-1)))) {
                    direction = 1;
                    sbtrn_v = subcube.base_v[ind];
                } else {
                    sbtrn_v = new single_vector(subcube.base_v[ind].multyplyOn(-1));
                    direction = -1;
                }

                v1 = v2;
                if (ind == 2) {
                    cubeInd = vrps.z;
                }
                if (ind == 1) {
                    cubeInd = vrps.y;
                }
                if (ind == 0) {
                    cubeInd = vrps.x;
                }
                rdt = ind;
                subturn(c[rdt], cubeInd);
            }
        }
    }

    public void mouseMoved(MouseEvent e) {    }

    /*
     * из точки на плоскости z = -p возвращает соответствующий
     * ей вектор длины 1.7*R (квадратный корень из трех на R)
     * т.е. это обратное действие по отношению к проекции
     *
     */
    public vector comeback(Point point, double R){
        double t = t(point.x, point.y, R);
        return new vector(point.x*t, point.y*t, (g1-p)*t - g1);
    }
    /*
     * t(x,y,R)  - корень квадратного уравнения полученного
     * из подставновки параметрического уровнения прямой соединяющие точки
     * G(0,0,-g) и P(x, y, -p) в уравнение сферы, которую описыют вершины кубика.
     * D - дескриминант этого уравнения
     * если D < 0, то точка (x, y) находится за пределами окружности, получаемой
     * проекцией сферы на плоскость z = -p
     */
    private double D(double x, double y, double R){
        return g1*g1*(g1-p)*(g1-p) - (x*x + y*y + (g1-p)*(g1-p))*(g1*g1 - 3*R*R);
    }
    private double t(double x, double y, double R){
        double D = D(x, y, R);
        if (D<0) D = 0;
        return (g1*(g1-p) + D_sign * Math.sqrt(D)) / (x*x + y*y + (g1-p)*(g1-p));
    }
    private boolean isInsideCircle(double xx, double yy){
        return xx*xx + yy*yy <= kr_r*kr_r;
    }

    /*
     *  выполняет четыре нижеследующие функии и находит позицию, в которую тыкнули
     */
    public boolean findWhereCursorIs(int x, int y){
         subcube.subcubes[0][0][0].isVisible = false;   subcube.subcubes[0][0][2].isVisible = false;
         subcube.subcubes[0][2][0].isVisible = false;   subcube.subcubes[0][2][2].isVisible = false;
         subcube.subcubes[2][0][0].isVisible = false;   subcube.subcubes[2][0][2].isVisible = false;
         subcube.subcubes[2][2][0].isVisible = false;   subcube.subcubes[2][2][2].isVisible = false;
         foundEdges(); foundNearest();
         parall face_parall = findFace(x,y);
         if (face_parall!=null){
             vrps = findPressedSubcube(face_parall, x, y);
             if(vrps.x == -3) return false;
         }
         else return false;
         paintIt();
         return true;
    }
    /*
     * находит граничные точки
     *
     */
    private void foundEdges(){
        Point[] point = new Point[8];
        // binEdge - бинарный вектор, отвечающий "видимым" вершинам
        int[] binEdge = {0, 0, 0, 0, 0, 0, 0, 0};
        int count = 0;
        // запихиваем все проэкции вершин кубика в один массив
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                for(int k=0; k<2; k++){
                    point[count] = p2[i][j][k];
                    count++;
                }
            }
        }
        /*
         * заполняем вектор бинЕдж вершинами, которые образуют при
         * проекции линейную оболочку
         */
        for(int i=0; i<=6; i++){
            for(int j=i+1; j<=7; j++){
                if(new line(point[i], point[j]).isItEdge(point)){
                    binEdge[i] = 1;
                    binEdge[j] = 1;
                }
            }
        }
        for(int i=0 ; i<2; i++){
            for(int j=0; j<2; j++){
                for(int k=0; k<2; k++){
                    // перевод двоичного числа в десятичное реальзовано в []
                    if(binEdge[i*4 + j*2 + k] == 1) 
                    subcube.subcubes[2*i][2*j][2*k].isVisible = true;

                }
            }
        }
        
    }
    //находим близжайщую вершину, и записываем, что мы ее видем
    private void foundNearest(){
        int n = 0, m = 0, l = 0;
        double value = subcube.subcubes[0][0][0].apex[0][0][0].z;
        for(int i=0 ; i<2; i++){
            for(int j=0; j<2; j++){
                for(int k=0; k<2; k++){
                   if (subcube.subcubes[2*i][2*j][2*k].apex[i][j][k].z < value){
                       value = subcube.subcubes[2*i][2*j][2*k].apex[i][j][k].z;
                       n = i; m = j; l = k;
                   }
                }
            }
        }
        subcube.subcubes[2*n][2*m][2*l].isVisible = true;
    }
    private int getNumberOfVisiblePoints(){
        int n = 0;
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                for(int k=0; k<2; k++){
                    if(subcube.subcubes[2*i][2*j][2*k].isVisible) n++;
                }
            }
        }
        return n;
    }

    /*
     * по параллелограмму, который является проекцией грани всего куба, ищет
     * параллелограмм, уже который образован проекцией одного из сабкубиков
     */
    public position findPressedSubcube(parall par, int x, int y){
        if (par==null) return new position(-1, -1, -1);
        parall[][] subpar = new parall[3][3];
        position[][] subpos = new position[3][3];
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                subpar[i][j] = new parall();
                subpos[i][j] = new position(0,0,0);
            }
        }
        position[] grn;
        // по "точке" Р и вектору n далее будет построена плоскость
        // и по пренадлежности к которой будут искаться все векторы
        // которые образуют грань куба
        vector P[] = new vector[4], n = new vector(0,0,0);
        char ch = 'x';
        int planeInd = -1;
        int[] kk1 = new int[4];
        int[] kk2 = new int[4];
        int[] kk3 = new int[4];
        int count = 0;
        for(int k1=0; k1<2; k1++){
            for(int k2=0; k2<2; k2++){
                for(int k3=0; k3<2; k3++){
                    projection proj = new projection(subcube.subcubes[2*k1][2*k2][2*k3].apex[k1][k2][k3], p, g1);
                    Point proj_p = new Point((int) proj.x, (int) proj.y);
                    if(par.isIncludePoint(proj_p)){
                        P[count] = subcube.subcubes[2*k1][2*k2][2*k3].apex[k1][k2][k3];
                        kk1[count] = subcube.posit[2*k1][2*k2][2*k3].x;
                        kk2[count] = subcube.posit[2*k1][2*k2][2*k3].y;
                        kk3[count] = subcube.posit[2*k1][2*k2][2*k3].z;
                        count++;
                    }
                }
            }
        }
        /* ищем вокруг какой оси идет вращение.
         * если все элементы одного из массивов kk1, kk2 или kk3
         * равны между собой, то именно то, что нам нужно
         */
        boolean bool[] = {true, true, true};
        for(int i=1; i<kk1.length; i++){
           if(kk1[i]!=kk1[i-1]) bool[0] = false;
           if(kk2[i]!=kk2[i-1]) bool[1] = false;
           if(kk3[i]!=kk3[i-1]) bool[2] = false;
        }
        if(bool[0]){ ch = 'x'; planeInd = kk1[0]; n = new vector(subcube.base_v[0]);}
        if(bool[1]){ ch = 'y'; planeInd = kk2[0]; n = new vector(subcube.base_v[1]);}
        if(bool[2]){ ch = 'z'; planeInd = kk3[0]; n = new vector(subcube.base_v[2]);}
        grn = subcube.search(ch, planeInd);
        plane pl = new plane(n, P[0]);
        int c1=0, c2=0, cc=0;
        for(position psn: grn){
            for(int i=0; i<2; i++){
               for(int j=0; j<2; j++){
                   for(int k=0; k<2; k++){
                       if (pl.isItOnThePlane(subcube.subcubes[psn.x][psn.y][psn.z].apex[i][j][k])){
                           projection proj = new projection(subcube.subcubes[psn.x][psn.y][psn.z].apex[i][j][k], p, g1);
                           subpar[c1][c2].next(new Point((int) proj.x, (int) proj.y));
                           subpos[c1][c2] = subcube.posit[psn.x][psn.y][psn.z];
                           cc++;
                       }
                   }
               }
           }
           c2++;
           if (c2==3){ c2=0; c1++;}
        }
        System.out.println("cc= " + cc);
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                subpar[i][j].sort();
                parall prl = new parall();
                prl = subpar[i][j];
                
                if (prl.isInside(new Point(x, y))) {
                    return subpos[i][j];
                }
            }
        }
        return new position(-3, -3, -3);
    }
    /*
     * жестяной алгоритм. стыдно за такое
     *
     * ну двумерный массив - ето же массив массивов.
     * и как передать один из элементов этого внешнего
     * массива, который(элемент) является массивом, в качестве
     * параметра функции?
     */
    private parall findFace(int x, int y){
            // запихиваем в массив pos[] позиции видимых вершин
            position[] pos = new position[getNumberOfVisiblePoints()];
            int count = 0;
            for(int k1=0; k1<2; k1++){
                for(int k2=0; k2<2; k2++){
                    for(int k3=0; k3<2; k3++){
                        if (subcube.subcubes[2*k1][2*k2][2*k3].isVisible){
                            pos[count] = subcube.posit[2*k1][2*k2][2*k3];
                            count++;
                        }
                    }
                }
            }
            /*
             * создаём массив из целых чисел такой же длины как и pos[]
             * и для каждой позиции ищем количество смежным вершин
             */
            int[] numOfNextDoor = new int[pos.length];
            for(int ind0=0; ind0<pos.length; ind0++){
                for(int j=0; j<pos.length; j++){
                    if(ind0!=j){
                        if(pos[ind0].isAdjacent(pos[j])){
                            numOfNextDoor[ind0]++;
                        }
                    }
                }
            }
            /*
             * далее рассматриваем три случая:
             * видимых вершин 4, 6 или 7
             * разбиваем видимые точки на три параллелограмма....
             */
            if(getNumberOfVisiblePoints()==7){
                position[] pos1 = new position[4]; count = 0;
                position[] pos2 = new position[4];
                position[] pos3 = new position[4];
                //вспомогательный массив sprt[]
                //в него идут четыре вершины смежность которых равна трем
                position[] sprt = new position[4];
                for(int i=0; i<pos.length; i++){
                    if(numOfNextDoor[i]==3) {sprt[count] = pos[i]; count++;}
                }
                count = 0;
                for(int i=0; i<sprt.length; i++){
                    //одну из вершин в sprt[] запихиваем во все pos1, pos2 и pos3
                    // то есть ту, которая смежна всем вершниам в самом sprt[]
                    if(sprt[i].isAdjacentToAll(sprt)){
                        pos1[count] = sprt[i];
                        pos2[count] = sprt[i];
                        pos3[count] = sprt[i];
                        count++;
                    }
                }

                int[] ind0 = {1, 1, 1};
                boolean[] bool = {true, false, false};
                for(int i=0; i<sprt.length; i++){
                    if(sprt[i]!=pos1[0] && bool[2]){
                        pos2[ind0[1]]=sprt[i]; ind0[1]++;
                        pos3[ind0[2]]=sprt[i]; ind0[2]++;
                        bool[2]=false;
                    }
                    if(sprt[i]!=pos1[0] && bool[1]){
                        pos1[ind0[0]]=sprt[i]; ind0[0]++;
                        pos3[ind0[2]]=sprt[i]; ind0[2]++;
                        bool[1]=false; bool[2]=true;
                    }
                    if(sprt[i]!=pos1[0] && bool[0]){
                        pos1[ind0[0]]=sprt[i]; ind0[0]++;
                        pos2[ind0[1]]=sprt[i]; ind0[1]++;
                        bool[0]=false; bool[1]=true;
                    }
                }
                for(int i=0; i<pos.length; i++){
                    if(pos[i]!=pos1[0]){
                        if(pos[i].isAdjacent(pos1[1]) && pos[i].isAdjacent(pos1[2])){
                            pos1[3] = pos[i];
                        }
                        if(pos[i].isAdjacent(pos2[1]) && pos[i].isAdjacent(pos2[2])){
                            pos2[3] = pos[i];
                        }
                        if(pos[i].isAdjacent(pos3[1]) && pos[i].isAdjacent(pos3[2])){
                            pos3[3] = pos[i];
                        }
                    }
                }
                Point[] par_p1 = getPointsOutOfPositions(pos1);
                Point[] par_p2 = getPointsOutOfPositions(pos2);
                Point[] par_p3 = getPointsOutOfPositions(pos3);
                parall[] par = {new parall(par_p1[0], par_p1[1], par_p1[2], par_p1[3]),
                                 new parall(par_p2[0], par_p2[1], par_p2[2], par_p2[3]),
                                  new parall(par_p3[0], par_p3[1], par_p3[2], par_p3[3])};
                for(int i=0; i<par.length; i++){
                    if(par[i].isInside(new Point(x, y))) return par[i];
                }
            }
            if(getNumberOfVisiblePoints()==6){
                // разбиваем pos на два массива pos1 и pos2
                // из которых, далее строим параллелограмы
                position[] pos1 = new position[4]; count = 0;
                position[] pos2 = new position[4];
                // если количество смежных вершин у данной вершины
                // равно трем, то она входит и в pos1 и в pos2
                for(int i=0; i<numOfNextDoor.length; i++){
                    if(numOfNextDoor[i]==3){
                        pos1[count] = pos[i];
                        pos2[count] = pos[i];
                        count++;
                    }
                }
                /*
                 * следующим циклом while сначала ищутся две
                 * смежные вершины, за исключением вершин с максимальным
                 * количеством смежным вершин (т.е. таких, что numOfNextDoor[i]==3)
                 * после их нахождения, они добываляются в pos1, а оставшиеся
                 * идут в pos2
                 */
                int ind1=0, ind2=0;
                while(numOfNextDoor[ind1]==3) {ind1++; ind2=ind1;}
                boolean whilebool = true;
                while(whilebool){
                    ind2++;
                    while(numOfNextDoor[ind2]==3) {if (ind2!=pos.length-1) ind2++;}
                        if(pos[ind1].isAdjacent(pos[ind2])){
                            pos1[count] = pos[ind1];
                            pos1[count+1] = pos[ind2];
                            for(int i=0; i<pos.length; i++){
                                // в pos2 идут те из pos, которых нет в pos1
                                if(!pos[i].isIncludedBy(pos1)){
                                    pos2[count] = pos[i];
                                    count++;
                                }
                            }
                            whilebool = false;
                        }
                    if (ind2==pos.length-1){ while(numOfNextDoor[ind1]==3){ if (ind1!=pos.length-1){ind1++; ind2=ind1+1;}}}
                }
                Point[] par_p1 = getPointsOutOfPositions(pos1);
                Point[] par_p2 = getPointsOutOfPositions(pos2);
                parall[] par = {new parall(par_p1[0], par_p1[1], par_p1[2], par_p1[3]),
                                 new parall(par_p2[0], par_p2[1], par_p2[2], par_p2[3])};
                for(int i=0; i<par.length; i++){
                    if(par[i].isInside(new Point(x, y))) return par[i];
                }
            }
            if(getNumberOfVisiblePoints()==4){
                Point[] par_p = getPointsOutOfPositions(pos);
                parall par = new parall(par_p);
                if(par.isInside(new Point(x, y))) {return par;}
            }
            return null;
    }
    // вспомогательная функция для предыдущей
    private Point[] getPointsOutOfPositions(position[] pos1){
        Point[] par_p = new Point[4]; int count = 0;
                pos1 = sort(pos1);
                for(int i=0; i<pos1.length; i++){
                    for(int k1=0; k1<2; k1++){
                        for(int k2=0; k2<2; k2++){
                            for(int k3=0; k3<2; k3++){
                                if(pos1[i] == subcube.posit[2*k1][2*k2][2*k3]){
                                    projection proj = new projection(subcube.subcubes[2*k1][2*k2][2*k3].apex[k1][k2][k3], p, g1);
                                    par_p[count] = new Point((int) proj.x, (int) proj.y);
                                    count++;
                                }
                            }
                        }
                    }
                }
       return par_p;
    }
    // сортирует позишины для подачи на параллелограмм
    // и проверки того, внутри ли него точка или нет
    // (ето реализует фунция findFace())
    private position[] sort(position pos[]){
        // сортируем массив pos, так, чтобы соседние в массиве элементы
                // были смежными в смысле позиции
                position tmp = new position(-1, -1, -1);
                for(int index=0; index<pos.length-1; index++){
                    for(int j=index+1; j<pos.length; j++){
                         if(!((pos[index].x == pos[j].x && pos[index].y == pos[j].y)||
                                (pos[index].x == pos[j].x && pos[index].z == pos[j].z)||
                                    (pos[index].y == pos[j].y && pos[index].z == pos[j].z))){
                             tmp = pos[j];
                             for(int s=j; s<pos.length-1; s++){
                                 pos[s] = pos[s+1];
                             }
                             pos[pos.length-1] = tmp;
                         }
                    }
                }
                return pos;
    }
    double halfpi_ang = 0, sum_ang = 0, sprt_ang = 0;
    private void subturn(char c, int n){
        System.out.println("sumang = " + sum_ang);
        quaternionus myQ = new quaternionus(Math.cos(subcube.psi/2),
                       sbtrn_v.multyplyOn(Math.sin(subcube.psi / 2)));
            if(c=='x'){
                sprtTurn(myQ, 'x', n);
                halfpi_ang += direction*subcube.psi;
                if(Math.abs(halfpi_ang) >= Math.PI / 2){
                    halfpi_ang = 0;
                    sprt_ang = sum_ang;
                    position[] k11 = subcube.search('x', n);
                    for(int l=0; l<9; l++){
                            subcube.posit[k11[l].x][k11[l].y][k11[l].z] =
                                    subcube.posit[k11[l].x][k11[l].y][k11[l].z].convFunct('x', direction);
                            subcube.subcubes[k11[l].x][k11[l].y][k11[l].z].ornt =
                                    subcube.subcubes[k11[l].x][k11[l].y][k11[l].z].ornt.convFunct('x', direction);
                    }
                }
            }
            if(c=='y'){
                sprtTurn(myQ, 'y', n);
                halfpi_ang += direction * subcube.psi;
                if(Math.abs(halfpi_ang) >= Math.PI / 2){
                    halfpi_ang = 0;
                    sprt_ang = sum_ang;
                    position[] k11 = subcube.search('y', n);
                    for(int l=0; l<9; l++){
                            subcube.posit[k11[l].x][k11[l].y][k11[l].z] =
                                    subcube.posit[k11[l].x][k11[l].y][k11[l].z].convFunct('y', direction);
                            subcube.subcubes[k11[l].x][k11[l].y][k11[l].z].ornt =
                                    subcube.subcubes[k11[l].x][k11[l].y][k11[l].z].ornt.convFunct('y', direction);
                    }
                }
            }
            if(c=='z'){
                sprtTurn(myQ, 'z', n);
                halfpi_ang += direction * subcube.psi;
                if(Math.abs(halfpi_ang) >= Math.PI / 2){
                    halfpi_ang = 0;
                    sprt_ang = sum_ang;
                    position[] k11 = subcube.search('z', n);
                    for(int l=0; l<9; l++){
                            subcube.posit[k11[l].x][k11[l].y][k11[l].z] =
                                    subcube.posit[k11[l].x][k11[l].y][k11[l].z].convFunct('z', direction);
                            subcube.subcubes[k11[l].x][k11[l].y][k11[l].z].ornt =
                                    subcube.subcubes[k11[l].x][k11[l].y][k11[l].z].ornt.convFunct('z', direction);
                    }
                }
            }
            sum_ang += direction * subcube.psi;
             paintIt();
        }
    public void sprtTurn(quaternionus myQ, char c, int n){
            for(int k1=0; k1<3; k1++){
                    for(int k2=0; k2<3; k2++){
                        for(int k3=0; k3<3; k3++){
                            int chp=-1;
                            if(c=='x') chp = subcube.posit[k1][k2][k3].x;
                            if(c=='y') chp = subcube.posit[k1][k2][k3].y;
                            if(c=='z') chp = subcube.posit[k1][k2][k3].z;
                            if(chp == n){
                                for(int i = 0; i < 2; i++){
                                 for(int j = 0; j < 2; j++){
                                  for(int k = 0; k < 2; k++){
                                      subcube.subcubes[k1][k2][k3].apex[i][j][k]
                                              = myQ.multiplyOn(subcube.subcubes[k1][k2][k3].apex[i][j][k].toQuaternion()).multiplyOn(myQ.T()).vect_part();
                                  }
                                 }
                                }
                            }
                        }
                    }
                }
        }
    class ClockListener implements ActionListener{
          boolean bool = true, b1 = true;
        public void actionPerformed(ActionEvent e){
                subturn(c[rdt], cubeInd);
        }
        /*
         * поворачивает одну из плоскостей куба,
         * определяемую с помощью char c - в какой из
         * плоскостей x, y или z вращаем и с помощью n
         * какую из трех параллельных плоскостей вращаем :)
         */
        private void subturn(char c, int n){
            int dr = direction;
            double coef = 1.2;
            if (Math.abs(subcube.psi) > Math.PI / 45) subcube.psi = dr * Math.PI / 45;
            sum_ang += subcube.psi;
            System.out.println("sumang = " + sum_ang);
            if(c=='x'){
               quaternionus myQ = new quaternionus(Math.cos(subcube.psi/2),
                       subcube.base_v[0].multyplyOn(Math.sin(subcube.psi / 2)));
                sprtTurn(myQ, 'x', n);
                if ((Math.abs(sum_ang) % (Math.PI/2) >= -0.1) && (Math.abs(sum_ang) % (Math.PI/2) <= 0.1)){
                    halfpi_ang = 0;
                    int m;
                    if(Math.abs(sprt_ang - sum_ang)>0.5) m=1;
                    else m=-1;
                    position[] k11 = subcube.search('x', n);
                    double apr_ang = subcube.aprAng(k11, 'x', n);
                    double cos = Math.cos(-m*direction * apr_ang * 0.5);
                    double sin = Math.sin(-m*direction * apr_ang * 0.5);
                    myQ.q0 = cos;
                    myQ.q1 = subcube.base_v[0].i * sin;
                    myQ.q2 = subcube.base_v[0].j * sin;
                    myQ.q3 = subcube.base_v[0].k * sin;
                    sprtTurn(myQ, 'x', n);
                    if(Math.abs(sprt_ang - sum_ang)>0.5){
                        for(int l=0; l<9; l++){
                                subcube.posit[k11[l].x][k11[l].y][k11[l].z] =
                                        subcube.posit[k11[l].x][k11[l].y][k11[l].z].convFunct('x', dr);
                                subcube.subcubes[k11[l].x][k11[l].y][k11[l].z].ornt =
                                        subcube.subcubes[k11[l].x][k11[l].y][k11[l].z].ornt.convFunct('x', direction);
                        }
                    }
                }
            }
            if(c=='y'){
                quaternionus myQ = new quaternionus(Math.cos(subcube.psi/2),
                       subcube.base_v[1].multyplyOn(Math.sin(subcube.psi / 2)));
                sprtTurn(myQ, 'y', n);
                if ((Math.abs(sum_ang) % (Math.PI/2) >= -0.1) && (Math.abs(sum_ang) % (Math.PI/2) <= 0.1)){
                    halfpi_ang = 0;
                    int m;
                    if(Math.abs(sprt_ang - sum_ang)>0.5) m=1;
                    else m=-1;
                    position[] k11 = subcube.search('y', n);
                    double apr_ang = subcube.aprAng(k11, 'y', n);
                    double cos = Math.cos(-m*direction * apr_ang * 0.5);
                    double sin = Math.sin(-m*direction * apr_ang * 0.5);
                    myQ.q0 = cos;
                    myQ.q1 = subcube.base_v[1].i * sin;
                    myQ.q2 = subcube.base_v[1].j * sin;
                    myQ.q3 = subcube.base_v[1].k * sin;
                    System.out.println("ugl do = " + apr_ang);
                    sprtTurn(myQ, 'y', n);
                    System.out.println("ugl pe = " + subcube.aprAng(k11, 'y', n));
                    if(Math.abs(sprt_ang - sum_ang)>0.5){
                        for(int l=0; l<9; l++){
                                subcube.posit[k11[l].x][k11[l].y][k11[l].z] =
                                        subcube.posit[k11[l].x][k11[l].y][k11[l].z].convFunct('y', dr);
                                subcube.subcubes[k11[l].x][k11[l].y][k11[l].z].ornt =
                                        subcube.subcubes[k11[l].x][k11[l].y][k11[l].z].ornt.convFunct('y', direction);
                        }
                    }
                    
                }
            }
            if(c=='z'){
                quaternionus myQ = new quaternionus(Math.cos(subcube.psi/2),
                       subcube.base_v[2].multyplyOn(Math.sin(subcube.psi / 2)));
                sprtTurn(myQ, 'z', n);

                if ((Math.abs(sum_ang) % (Math.PI/2) >= -0.1) && (Math.abs(sum_ang) % (Math.PI/2) <= 0.1)){
                    halfpi_ang = 0;
                    int m;
                    if(Math.abs(sprt_ang - sum_ang)>0.5) m=1;
                    else m=-1;
                    position[] k11 = subcube.search('z', n);
                    double apr_ang = subcube.aprAng(k11, 'z', n);
                    double cos = Math.cos(-m*direction * apr_ang * 0.5);
                    double sin = Math.sin(-m*direction * apr_ang * 0.5);
                    myQ.q0 = cos;
                    myQ.q1 = subcube.base_v[2].i * sin;
                    myQ.q2 = subcube.base_v[2].j * sin;
                    myQ.q3 = subcube.base_v[2].k * sin;
                    System.out.println(m + " ugl do = " + subcube.aprAng(k11, 'z', n));
                    sprtTurn(myQ, 'z', n);
                    System.out.println("ugl pe = " + subcube.aprAng(k11, 'z', n));
                    if(Math.abs(sprt_ang - sum_ang)>0.5){
                        for(int l=0; l<9; l++){
                                subcube.posit[k11[l].x][k11[l].y][k11[l].z] =
                                        subcube.posit[k11[l].x][k11[l].y][k11[l].z].convFunct('z', dr);
                                subcube.subcubes[k11[l].x][k11[l].y][k11[l].z].ornt =
                                        subcube.subcubes[k11[l].x][k11[l].y][k11[l].z].ornt.convFunct('z', direction);
                        }
                    }
                }
            }
            if ((Math.abs(sum_ang) % (Math.PI/2) >= -0.1) && (Math.abs(sum_ang) % (Math.PI/2) <= 0.1)){
                sum_ang = 0; sprt_ang = 0;
                subturnIsWorking = false;
                myTimer.stop();
            }
            paintIt();
        }
    }
}


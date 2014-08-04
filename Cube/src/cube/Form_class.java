/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cube;

import javax.swing.*;
import java.awt.*;

public class Form_class extends JFrame{
    public graphics_component myComponent = new graphics_component();
    public Form_class(){
       JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT));

       pnl.setPreferredSize(new Dimension(400, 400));
       pnl.add(myComponent);
       this.setContentPane(pnl);
       this.pack();


      myComponent.paintIt();
     
    }
  

    public static void main(String[] args) {
          Form_class form = new Form_class();
         
          form.setDefaultCloseOperation(Form_class.EXIT_ON_CLOSE);
          form.setLocationRelativeTo(null);
          form.setResizable(false);
          form.setVisible(true);
          try{
          orient or = new orient();
          //System.out.println(Math.acos(new vector(1,0.5,0).scal(new vector(-0.1,-1,0))));
          }
          catch(Exception e) { System.out.println("OOps");}

    }
    
}

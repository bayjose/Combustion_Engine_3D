/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Bayjose
 */
public class Window {
    
    public Window(String title, Display display){
        display.setPreferredSize(new Dimension(Display.WIDTH, Display.HEIGHT));
        display.setMaximumSize(new Dimension(Display.WIDTH, Display.HEIGHT));
        display.setMinimumSize(new Dimension(Display.WIDTH, Display.HEIGHT));
        JFrame frame = new JFrame(title);
        frame.add(display);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        display.start();
    }
    
}

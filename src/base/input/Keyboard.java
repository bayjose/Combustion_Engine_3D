/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Base.input;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Bayjose
 */
public class Keyboard extends KeyAdapter{

    public static boolean A = false;
    public static boolean B = false;
    public static boolean C = false;
    public static boolean D = false;
    public static boolean E = false;
    public static boolean F = false;
    public static boolean G = false;
    public static boolean H = false;
    public static boolean I = false;
    public static boolean J = false;
    public static boolean K = false;
    public static boolean L = false;
    public static boolean M = false;
    public static boolean N = false;
    public static boolean O = false;
    public static boolean P = false;
    public static boolean Q = false;
    public static boolean R = false;
    public static boolean S = false;
    public static boolean T = false;
    public static boolean U = false;
    public static boolean V = false;
    public static boolean W = false;
    public static boolean X = false;
    public static boolean Y = false;
    public static boolean Z = false;
    
    public static boolean bool0 = false;
    public static boolean bool1 = false;
    public static boolean bool2 = false;
    public static boolean bool3 = false;
    public static boolean bool4 = false;
    public static boolean bool5 = false;
    public static boolean bool6 = false;
    public static boolean bool7 = false;
    public static boolean bool8 = false;
    public static boolean bool9 = false;

    public static boolean SPACE = false;
    public static boolean ESC = false;
    public static boolean UP = false;
    public static boolean DOWN = false;
    public static boolean LEFT = false;
    public static boolean RIGHT = false;
            
    public Keyboard(){
        
    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();    
        if(key == KeyEvent.VK_0){
            loop:{
                if(bool0){
                    bool0=false;
                    break loop;
                }
                if(bool0==false){
                    bool0=true;
                    break loop;
                }
            
            }
        }
        
        if(key == KeyEvent.VK_1){
            loop:{
                if(bool1){
                    bool1=false;
                    break loop;
                }
                if(bool1==false){
                    bool1=true;
                    break loop;
                }
            
            }
        }
        
        if(key == KeyEvent.VK_2){
            loop:{
                if(bool2){
                    bool2=false;
                    break loop;
                }
                if(bool2==false){
                    bool2=true;
                    break loop;
                }            
            }
        }
        
        if(key == KeyEvent.VK_3){
            loop:{
                if(bool3){
                    bool3=false;
                    break loop;
                }
                if(bool3==false){
                    bool3=true;
                    break loop;
                }
            }

        }
        
        if(key == KeyEvent.VK_4){
            loop:{
                if(bool4){
                    bool4=false;
                    break loop;
                }
                if(bool4==false){
                    bool4=true;
                    break loop;
                }
            
            }
        }
        
        if(key == KeyEvent.VK_5){
            loop:{
                if(bool5){
                    bool5=false;
                    break loop;
                }
                if(bool5==false){
                    bool5=true;
                    break loop;
                }
            
            }

        }
        
        if(key == KeyEvent.VK_6){
            loop:{
                if(bool6){
                    bool6=false;
                    break loop;
                }
                if(bool6==false){
                    bool6=true;
                    break loop;
                }
            
            }

        }
        
        if(key == KeyEvent.VK_7){
            loop:{
                if(bool7){
                    bool7=false;
                    break loop;
                }
                if(bool7==false){
                    bool7=true;
                    break loop;
                }
            
            }

        }
        
        if(key == KeyEvent.VK_8){
            loop:{
                if(bool8){
                    bool8=false;
                    break loop;
                }
                if(bool8==false){
                    bool8=true;
                    break loop;
                }
            
            }
        }
        
        if(key == KeyEvent.VK_9){
            loop:{
                if(bool9){
                    bool9=false;
                    break loop;
                }
                if(bool9==false){
                    bool9=true;
                    break loop;
                }
            
            }
        }
        if(key == KeyEvent.VK_W){
            Keyboard.W = true;
        }
        if(key == KeyEvent.VK_S){
            Keyboard.S = true;
        }
        if(key == KeyEvent.VK_A){
            Keyboard.A = true;
        }
        if(key == KeyEvent.VK_D){
            Keyboard.D = true;
        }
        if(key == KeyEvent.VK_Q){
            Keyboard.Q = true;
        }
        if(key == KeyEvent.VK_E){
            Keyboard.E = true;
        }
        if(key == KeyEvent.VK_SPACE){
            Keyboard.SPACE = true;
        }
        if(key == KeyEvent.VK_ESCAPE){
            Keyboard.ESC = true;
        }
        if(key == KeyEvent.VK_UP){
            Keyboard.UP = true;
        }
        if(key == KeyEvent.VK_DOWN){
            Keyboard.DOWN = true;
        }
        if(key == KeyEvent.VK_LEFT){
            Keyboard.LEFT = true;
        }
        if(key == KeyEvent.VK_RIGHT){
            Keyboard.RIGHT = true;
        }

    }
    
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W){
            Keyboard.W = false;
        }
        if(key == KeyEvent.VK_S){
            Keyboard.S = false;
        }
        if(key == KeyEvent.VK_A){
            Keyboard.A = false;
        }
        if(key == KeyEvent.VK_D){
            Keyboard.D = false;
        }
        if(key == KeyEvent.VK_Q){
            Keyboard.Q = false;
        }
        if(key == KeyEvent.VK_E){
            Keyboard.E = false;
        }
        if(key == KeyEvent.VK_SPACE){
            Keyboard.SPACE = false;
        }
        if(key == KeyEvent.VK_ESCAPE){
            Keyboard.ESC = false;
        }
        if (key == KeyEvent.VK_UP) {
            Keyboard.UP = false;
        }
        if (key == KeyEvent.VK_DOWN) {
            Keyboard.DOWN = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            Keyboard.LEFT = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            Keyboard.RIGHT = false;
        }
    }
    
    public static boolean getKey(String keyValue){
        keyValue = keyValue.toUpperCase();
        if(keyValue.equals("A")){
            return Keyboard.A;
        }
        if(keyValue.equals("B")){
            return Keyboard.B;
        }
        if(keyValue.equals("C")){
            return Keyboard.C;
        }
        if(keyValue.equals("D")){
            return Keyboard.D;
        }
        if(keyValue.equals("E")){
            return Keyboard.E;
        }
        if(keyValue.equals("F")){
            return Keyboard.F;
        }
        if(keyValue.equals("G")){
            return Keyboard.G;
        }
        if(keyValue.equals("H")){
            return Keyboard.H;
        }
        if(keyValue.equals("I")){
            return Keyboard.I;
        }
        if(keyValue.equals("J")){
            return Keyboard.J;
        }
        if(keyValue.equals("K")){
            return Keyboard.K;
        }
        if(keyValue.equals("L")){
            return Keyboard.L;
        }
        if(keyValue.equals("M")){
            return Keyboard.M;
        }
        if(keyValue.equals("N")){
            return Keyboard.N;
        }
        if(keyValue.equals("O")){
            return Keyboard.O;
        }
        if(keyValue.equals("P")){
            return Keyboard.P;
        }
        if(keyValue.equals("Q")){
            return Keyboard.Q;
        }
        if(keyValue.equals("R")){
            return Keyboard.R;
        }
        if(keyValue.equals("S")){
            return Keyboard.S;
        }
        if(keyValue.equals("T")){
            return Keyboard.T;
        }
        if(keyValue.equals("U")){
            return Keyboard.U;
        }
        if(keyValue.equals("V")){
            return Keyboard.V;
        }
        if(keyValue.equals("W")){
            return Keyboard.W;
        }
        if(keyValue.equals("X")){
            return Keyboard.X;
        }
        if(keyValue.equals("Y")){
            return Keyboard.Y;
        }
        if(keyValue.equals("Z")){
            return Keyboard.Z;
        }
        
        //utilites
        if(keyValue.equals("SPACE")){
            return Keyboard.SPACE;
        }
        if(keyValue.equals("ESC")){
            return Keyboard.ESC;
        }
        if(keyValue.equals("UP")){
            return Keyboard.UP;
        }
        if(keyValue.equals("DOWN")){
            return Keyboard.DOWN;
        }
        if(keyValue.equals("LEFT")){
            return Keyboard.LEFT;
        }
        if(keyValue.equals("RIGHT")){
            return Keyboard.RIGHT;
        }
        //mouse
        if(keyValue.equals("LEFTMOUSE")){
            return MouseInput.IsPressed;
        }
        if(keyValue.equals("RIGHTMOUSE")){
            return MouseInput.IsPressed&&MouseInput.IsRightClick;
        }
        
        
        System.err.println("[KeyInput] key:"+keyValue+" was not recognised.");
        return false;
    }
    
}

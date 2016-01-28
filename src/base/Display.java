/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import Base.input.Keyboard;
import Base.input.MouseInput;
import Base.input.MousePositionLocator;
import Loot.GemCase;
import Loot.GemRegistry;
import Loot.GemSlot;
import Loot.MouseGem;
import graphics.Screen;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 *
 * @author Bailey
 */
public class Display extends Canvas implements Runnable{
    
    public static final String TITLE = "3D Bitches";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    
    private Thread thread;
    private Screen screen;
    private boolean running = false;
    private BufferedImage img;
    private int[] pixels;
    private Game game;
    
    private GemRegistry gemRegistry;
    public static GemCase gemCase;
    
    public Display(){
        Dimension size = new Dimension(WIDTH, HEIGHT);
        gemRegistry = new GemRegistry();
        screen = new Screen(WIDTH, HEIGHT);
        game = new Game();
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        this.addKeyListener(new Keyboard());
        this.addMouseListener(new MouseInput());
        this.addMouseMotionListener(new MousePositionLocator());
        gemCase = new GemCase();
    }
    
    public void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop(){
        if(!running){
            return;
        }
        running = false;
        try{
            thread.join();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    
    @Override
    public void run() {
        int frames = 0;
        double unprocessedSeconds = 0;
        long previousTime = System.nanoTime();
        double secondsPerTick = 1/60.0;
        int tickCount = 0;
        boolean ticked = false;
        while(running){
            long currentTime = System.nanoTime();
            long pastTime = currentTime - previousTime;
            previousTime = currentTime;
            unprocessedSeconds += pastTime/1000000000.0;
            
            while(unprocessedSeconds > secondsPerTick){
                tick();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;
                tickCount++;
                if(tickCount % 60 == 0){
                    System.out.println("FPS:"+frames);
                    previousTime +=1000;
                    frames = 0;
                }
            }
            if(ticked){
                render();
                frames++;
            }
//            render();
//            frames++;
        }
    }

    private void tick() {
        game.tick();
        screen.tick();
    }

    private void render() {
       
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        
        screen.render3D(game);
        
        for(int i = 0; i < WIDTH * HEIGHT; i++){
            pixels[i] = screen.pixels[i];
        }
        
        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH + 16, HEIGHT + 16, null);
        screen.render(g);
        if(Keyboard.bool3){
            gemCase.render(g);
        }
        MouseGem.render(g);
        g.dispose();
        bs.show();
    }
    
    public static void main(String[] args){
        Display game = new Display();
        Window window = new Window(TITLE, game);
    }
}

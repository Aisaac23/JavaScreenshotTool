/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javascreenshottool;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author root
 */
public class ScreenshotThread extends Thread {

    private volatile boolean keepGoing;
    private float spinnerValue;
    private SimpleDateFormat formatter;
    private String monthAndDay;
    private String current;
    private File shot;
    private Rectangle screen;
    private Calendar now;
    private Robot robot;

    public ScreenshotThread() {
        this.keepGoing = false;
        this.spinnerValue = (float) 0.5;
        this.now = Calendar.getInstance();
        this.formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.current = System.getProperty("user.dir");
        this.monthAndDay = "/" + formatter.format( now.getTime() ) +"/";
        this.formatter = new SimpleDateFormat("yyyyMMdd hh mm ss");
        this.screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        
    }

    public void setKeepGoing(boolean keepGoing) {
        this.keepGoing = keepGoing;
    }

    public boolean isKeepGoing() {
        return keepGoing;
    }

    public void setSpinnerValue(float spinnerValue) {
        this.spinnerValue = spinnerValue;
    }
    
    @Override
    public void run() {
     
        try {
            this.robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(ScreenshotThread.class.getName()).log(Level.SEVERE, null, ex);
        }

            while(true)
            {
                if(this.keepGoing)
                {
                    
                    try {
                        this.now = Calendar.getInstance();
                        this.shot = new File(this.current + this.monthAndDay) ;
                        if(!shot.exists())
                                shot.mkdir();
                        BufferedImage screenshot = robot.createScreenCapture( this.screen );
                        this.shot = new File(this.current + this.monthAndDay + formatter.format( this.now.getTime() ) + ".jpg" );
                        ImageIO.write( screenshot, "JPG", this.shot );
                        
                    } catch (IOException ex) {
                        System.out.println("IO exception: " + ex.getMessage());
                    }
                        try {
                            Thread.sleep((long) (this.spinnerValue*1000));
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ScreenshotThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
            }      
            
    }

    public void startShooting() {
        this.keepGoing = true;
       this.start();
    }
    
}

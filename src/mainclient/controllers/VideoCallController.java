/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainclient.controllers;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
//import javafx.scene.shape.Rectangle;
import javax.imageio.ImageIO;
import mainclient.PeerHandler;
import mainclient.Start;
import mainclient.video_tools.FrameController;
import mainclient.video_tools.MattoByte;
import mainclient.video_tools.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author harshit
 */
public class VideoCallController {

    @FXML
    ImageView videoframe;
    @FXML ImageView share_screen;
    Socket mysocket;
    OutputStream os;
    InputStream is;
    ObjectOutputStream foos;
    ObjectInputStream fois;
    FrameController fc;
    @FXML
    Circle endcall;
    @FXML
    void initialize() {
        System.out.println(Start.user.getName());
        endcall.setFill(new ImagePattern(new Image("/icons8-end-call-50.png") {
        }));
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        endcall.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {

            endcall.setEffect(colorAdjust);

        });
        endcall.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            endcall.setEffect(null);
        });
        if (ProfileController.framesocket != null) {
            mysocket = ProfileController.framesocket;

        } else {
            mysocket = PeerHandler.framesock;
        }
        try {
             os=mysocket.getOutputStream();
             is=mysocket.getInputStream();
            foos = new ObjectOutputStream(os);
            fois = new ObjectInputStream(is);
            
            fc = new FrameController();
            fc.startCamera(foos);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            MattoByte mat = (MattoByte) fois.readObject();
                            Mat frame = new Mat(mat.getRows(), mat.getCols(), mat.getType());

                            frame.put(0, 0, mat.getMatArray());
                            Image image = Utils.mat2Image(frame);
                            updateImageView(videoframe, image);
                        }
                    } catch (Exception ex) {
                       ex.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        

    }

    private void updateImageView(ImageView view, Image image) {
        Utils.onFXThread(view.imageProperty(), image);
    }

    @FXML
    public void end_call() {
        System.out.println("end it");
        fc.setClosed();
        try {
            mysocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    public void sharescreen()
    {
        fc.setClosed();
        new Thread(() -> {
            while(true)
            {
            try {
                Robot robot = new Robot();
 
            Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
            foos.flush();
            BufferedImage image=Utils.convertTo3ByteBGRType(bufferedImage);
            Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
            byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            mat.put(0, 0, pixels);
            Size size = new Size(200,200);
                        Mat finalFrame = new Mat();
                        Imgproc.resize(mat, finalFrame, size);
                        mat = finalFrame;
            MattoByte frame=new MattoByte(mat);
            foos.writeObject(frame);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            }
        }).start();
    }
    

}

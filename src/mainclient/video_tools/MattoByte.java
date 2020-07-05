/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainclient.video_tools;

import java.io.Serializable;
import org.opencv.core.Mat;

/**
 *
 * @author harshit
 */
public class MattoByte implements Serializable {
    private byte[] matArray;
    private int rows,cols,type;

    public MattoByte(Mat mat) {
        rows=mat.rows();
        cols=mat.cols();
        type=mat.type();
        matArray=new byte[(int) (mat.total()*mat.elemSize())];
        mat.get(0,0,matArray);
    }

    public byte[] getMatArray() {
        return matArray;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getType() {
        return type;
    }
}

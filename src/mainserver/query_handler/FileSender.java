/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.query_handler;

/**
 *
 * @author harshit
 */
import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class FileSender {

    public SocketChannel createSocketChannel(String clientIp) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress(clientIp, 9000);
            socketChannel.connect(socketAddress);
            System.out.println("Connected with server for File I/O");
            return socketChannel;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendFile(SocketChannel socketChannel, String path) {
        try {
            RandomAccessFile aFile = null;
            File file = new File(path);
            System.out.println(file.exists());
            aFile = new RandomAccessFile(file, "r");
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) > 0) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }
            System.out.println("End of file reached..");
            socketChannel.close();
            aFile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

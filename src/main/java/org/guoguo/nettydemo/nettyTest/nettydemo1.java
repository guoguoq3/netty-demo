package org.guoguo.nettydemo.nettyTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.util.HashMap;

public class nettydemo1 {
    public static void main(String[] args) {
        //输入输出流
        try (FileChannel fileChannel = new FileInputStream("data.txt").getChannel()) {
            //准备缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            //从文件通道中读取数据向缓冲区写入
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
             byte b=byteBuffer.get();
                System.out.println((char)b);
            }
        } catch (IOException e) {
        }

    }
}

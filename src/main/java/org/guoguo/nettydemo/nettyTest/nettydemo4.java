package org.guoguo.nettydemo.nettyTest;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.RandomAccess;

public class nettydemo4 {
    public static void main(String[] args) {


        ByteBuffer buffer = StandardCharsets.UTF_8.encode("hello world");
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello world");
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello world");

        try (FileChannel fileChannel = new RandomAccessFile("D:\\test.txt", "rw").getChannel()) {
            fileChannel.write(new ByteBuffer[]{buffer1,buffer,buffer2});
        } catch (IOException e) {
        }
    }
}

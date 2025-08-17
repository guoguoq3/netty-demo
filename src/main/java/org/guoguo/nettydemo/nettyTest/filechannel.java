package org.guoguo.nettydemo.nettyTest;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.HashMap;

public class filechannel {
    public static void main(String[] args) {

            try ( FileChannel channel = new FileInputStream("D:\\test.txt").getChannel();
                  FileChannel fileChannel = new FileOutputStream("D:\\tes1t.txt").getChannel()){

            channel.transferTo(0,channel.size(),fileChannel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        HashMap<String,String> map = new HashMap<>();

    }
}

package org.guoguo.nettydemo.nettyTest;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class nettydemo3 {
    public static void main(String[] args) {
        //字符数组转为bytebuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.put("world".getBytes());


        //charset
        ByteBuffer buffer = StandardCharsets.UTF_8.encode("hello");
        System.out.println(StandardCharsets.UTF_8.decode(buffer));

        //wrap
        ByteBuffer buffer1 = ByteBuffer.wrap("hello".getBytes());

    }
}

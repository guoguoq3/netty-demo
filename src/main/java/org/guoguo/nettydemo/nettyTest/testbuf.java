package org.guoguo.nettydemo.nettyTest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class testbuf {
    public static void main(String[] args) {


        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);

       buf.writeBytes(new byte[]{1,2,3,4});
        System.out.println( buf);
        System.out.println( buf.readableBytes());

        System.out.println(buf.getByte(0));

        ByteBuf slicedBuf = buf.slice();
        System.out.println("Sliced buffer readable bytes: " + slicedBuf.readableBytes());
    }
}
package org.guoguo.nettydemo.nettyTest;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class nettydemo5 {
    public static void main(String[] args) throws IOException {
        Files.walkFileTree(Paths.get("D:\\ziliao"),new SimpleFileVisitor<>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("=======dir:"+dir);
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("=======file:"+file);
                return super.visitFile(file, attrs);
            }
        });
    }
}

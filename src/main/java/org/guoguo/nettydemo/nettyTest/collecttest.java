package org.guoguo.nettydemo.nettyTest;

import java.util.ArrayList;
import java.util.Collections;

public class collecttest {
    public static void main(String[] args) {
        ArrayList<Integer> list=new ArrayList<>();
        list.add(45);
        list.add(233);
        list.add(3);
        list.add(43);
        list.add(5);

        Collections.shuffle( list);
        System.out.println(list);
    }
}

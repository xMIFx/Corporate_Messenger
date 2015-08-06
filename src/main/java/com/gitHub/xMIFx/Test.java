package com.gitHub.xMIFx;

import com.gitHub.xMIFx.domain.Worker;

import java.io.*;
import java.util.Map;

public class Test {
    private static String filePathW = "D:\\files\\repo\\workers.out";
    private static String filePathD = "D:\\files\\repo\\departments.out";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Worker w = new Worker();
        w.setId(1L);
        w.setName("MIF");
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePathW);
             ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
         w.writeExternal(out);
        }
        Worker wW = new Worker();
        try (FileInputStream fileInputStream = new FileInputStream(filePathW);
             ObjectInputStream in = new ObjectInputStream(fileInputStream)) {

            wW.readExternal(in);
        }
        System.err.println(w);
        System.err.println(wW);


    }
}

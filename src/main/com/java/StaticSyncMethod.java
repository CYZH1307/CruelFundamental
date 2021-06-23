package com.java;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StaticSyncMethod {

    synchronized static void f() {
        while (true) {
            System.out.println("f()");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(StaticSyncMethod.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    synchronized static void g() {
        while (true) {
            System.out.println("g()");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(StaticSyncMethod.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    

    public static void main(String[] args) {
        Thread tf = new Thread() {
            public void run() {
                StaticSyncMethod.f();
            }
        };
        Thread tg = new Thread() {
            public void run() {
                StaticSyncMethod.g();
            }
        };    
        
        tf.start();
        tg.start();
    }
}

/*
f()
f()
f()
f()
f()
f()
f()
f()
f()
f()
f()
f()
f()
f()
f()
f()
f()
*/



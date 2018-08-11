package com.aliang.wenda;

/**
 * @Description
 * @Author Aliang
 * @Date 2018/8/9 16:00
 * @Version 1.0
 **/
class MyThread extends Thread{
    private int id;

    public MyThread(int id){
        this.id = id;
    }

    @Override
    public void run() {
        try{
            for(int i = 0; i < 10; i++){
                Thread.sleep(1000);
                System.out.println(String.format("%d:%d", id, i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

public class MultiThreadTests {

    public static void testThread(){
        for(int i = 0; i < 10; i++){
            final int fi = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        for(int j = 0; j < 10; j++){
                            Thread.sleep(1000);
                            System.out.println(String.format("%d:%d", fi, j));
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private static  Object obj = new Object();

    public static void testSynchronized(){

    }
    public static void main(String[] args) {
        testThread();
    }

}

package test;

import org.junit.Test;

/**
 * Created by yuanguanghui on 2016/9/22.
 */
public class TestStack2 {
    private static int count = 0;
    public static void recursion(long a, long b, long c) throws InterruptedException {
        long d = 0, e = 0, f = 0;
        count++;
        recursion(a,b,c);
        //this.wait(10000);
        System.gc();
    }

    @Test
    public void testStack(){
        try {
            recursion(1L, 2L, 3L);
        } catch (Throwable e) {
            System.out.println("deep of stack is " + count);
            e.printStackTrace();
        }
    }

    @Test
    public void testBasic(){

        float f = 1.212345678976f;
        System.out.println(f);

        double d = 2.12345676896;
        System.out.println(d);

        char c = 'a';
        System.out.println(c);

        short s1 = 1;
        //s1 = s1 + 1;
        s1 = (short) (s1 + 1);

        short s2 = 1;
        s2 += 1;
    }

}

package test;

/**
 * Created by yuanguanghui on 2016/11/30.
 */
public class RadixTest {
    private static final long hex = 0x5DEECE66DL;

    private static final int octal = 011;

    public static void main(String[] args) {
        System.out.println("十进制转成二进制 ：" + Integer.toBinaryString(12));

        System.out.println("十进制转成八进制 ：" + Integer.toOctalString(12));

        System.out.println("十进制转成十六进制：" + Integer.toHexString(12));

        System.out.println("十转二：" + Integer.toBinaryString(120));
        System.out.println("十转八：" + Integer.toOctalString(120));
        System.out.println("十转十六：" + Integer.toHexString(120));
        System.out.println("二转十：" + Integer.valueOf("1010",2));
        System.out.println("八转十：" + Integer.valueOf("125",8));
        System.out.println("十六转十：" + Integer.valueOf("ABCDEF",16));

        System.out.println("十六转十：" + Integer.valueOf("abcdef",16));


        System.out.println(hex);
        System.out.println(octal);


        //Long.toUnsignedString(12);
        Long.highestOneBit(12);
        Long.lowestOneBit(12);
    }
}

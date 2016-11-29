package test.classload;

import java.io.*;

/**
 * 用加密算法生成要隐藏的字节码文件
 * Created by yuanguanghui on 2016/9/29.
 */
public class ClassEncrypt extends MyClassLoader {
    public static void main(String[] args) throws IOException {
        //要加密的字节码.class文件
        String srcPath = "D:\\workspaces\\seckill\\target\\classes\\test\\classload\\ClassTest.class";
        //加密之后输出的字节码.class文件的位置
        String destPath = "D:/ClassTest.class";
        FileInputStream fis = new FileInputStream(srcPath);
        FileOutputStream ofs = new FileOutputStream(destPath);
        cypher(fis, ofs);//加密
        fis.close();
        ofs.close();
    }

    //简单的加密，用于测试。将所有二进制位取反，即0变成1，1变成0
    private static void cypher(InputStream in, OutputStream out) throws IOException {
        int b = -1;
        while ((b = in.read()) != -1) {
            out.write(b ^ 0xff);
        }
    }
}

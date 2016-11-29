package test.classload;

import java.io.*;

/**
 * Created by yuanguanghui on 2016/9/29.
 */
public class MyClassLoader extends ClassLoader {

    /**
     * 因为类加载器是基于委托机制，所以我们只需要重写findClass方法。
     * 它会自动向父类加载器委托，如果父类没有找到，就会再去调用我们重写的findClass方法加载
     */

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            //需要加载的.class字节码的位置
            String classPath = "D:/ClassTest.class";

            FileInputStream fis = new FileInputStream(classPath);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            cypher(fis, bos);
            fis.close();
            byte[] bytes = bos.toByteArray();
            //return defineClass(bytes, 0, bytes.length);
            return defineClass(null, bytes, 0, bytes.length, null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return super.findClass(name);

    }

    //相应的字节码解密类，在加载E盘根目录下的被加密过的ClassTest.class字节码的时候，进行相应的解密。


    private static void cypher(InputStream in, OutputStream out) throws IOException {
        int b = -1;
        while ((b = in.read()) != -1) {
            out.write(b ^ 0xff);

        }

    }

}

package test.jvm;

/**
 * Created by yuanguanghui on 2016/9/28.
 */
public class ReferenceCountingGC {

    /**
     * -verbose:gc -XX:+PrintGCDetails -Xms30M -Xmx30M -Xmn10M
     */
    public static final int _1M = 1024 * 1024;
    private ReferenceCountingGC ref = null;
    private byte[] content = new byte[_1M * 1];

    public void testGC() {
        ReferenceCountingGC a = new ReferenceCountingGC();
        ReferenceCountingGC b = new ReferenceCountingGC();
        a.ref = a;
        b.ref = b;
        a = null;
        b = null;
        System.gc();

    }

    public static void main(String[] args) {
        new ReferenceCountingGC().testGC();
    }

}

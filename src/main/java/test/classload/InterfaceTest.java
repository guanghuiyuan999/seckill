package test.classload;

/**
 * Created by yuanguanghui on 2016/9/29.
 */

/**
 * 2  * 要加载类的接口，加载该接口的子类时，可以用接口引用，而不需要利用反射来实现。
 * 3
 */
public interface InterfaceTest {
    public void name();

    public void age();
}
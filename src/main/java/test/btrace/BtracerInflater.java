package test.btrace;
import static com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.annotations.*;

import java.lang.management.MemoryUsage;

/**
 * @Author:YuanGuangHui
 * @Description:
 * @Date:Created in 下午2:25 2017/11/14
 */


@BTrace public class BtracerInflater{
//    @OnMethod(
//            clazz="java.util.zip.Inflater",
//            method="/.*/"
//    )
//
//    public static void traceCacheBlock(){
//        println("Who call java.util.zip.Inflater's methods :");
//        jstack();
//    }
//
    @OnMethod(clazz = "java.lang.System", method = "gc")
    public static void onSystemGC() {

        println("entered System.gc()");

        jstack();// print the stack info.

    }

//    //监控某一个方法的执行时间

//    @OnMethod(clazz = "com.meituan.service.campaign.persistent.mapper.CampaignBaseMapper", method = "getModifyTimeAndIds",
//            location=@Location
//            (Kind.RETURN))
//
//    public static void printMethodRunTime(@ProbeClassName String probeClassName, @Duration long duration){
//
//        println(probeClassName + ",duration:" + duration / 1000000 + " ms");
//
//    }

    /**
     *
     此示例跟踪内存阈值超过。
     *需要指定要观看的内存池
     *出和使用门槛。 你可以写
     *在dump上通过dumpHeap堆转储的脚本
     *的门槛，而不是只是打印一条消息。
     *请注意，旧的名字是依赖的
     * GC算法。 使用ParallelGC，名称是“PS Old Gen”。
     * @param mu
     */
    @OnLowMemory(
            pool = "Tenured Gen",
            threshold=6000000
    )
    public static void onLowMem(MemoryUsage mu) {
        println(mu);
    }

    /**
     *
      这个BTrace程序演示了死锁
     *内置功能。 这个例子打印
     *死锁（如果有的话）每4秒一次。
     * @param s
     */
    @OnTimer(40000)
    public static void printDeadLock(String s) {
        deadlocks();
    }

    /*
     * 指定内存区域低于一定的界限的时候才内存使用打印数据<br> 也可以指定时间间隔打印内存使用
     */
    @OnLowMemory(pool = "Tenured Gen", threshold = 6000000)
    public static void printMem(MemoryUsage mu) {
        print("MemoryUsage : ");
        println(mu);
        print("FreeMem : ");
        println(freeMemory());
        print("Heap:");
        println(heapUsage());
        print("Non-Heap:");
        println(nonHeapUsage());
    }

    /**
     * 简单的BTrace程序打印内存
       *每2秒使用一次。 有可能的
       *修改这个依赖于转储堆
       *使用跨越阈值或其他的内存
       *这样的条件。 [dumpHeap是一个内置函数]。
     */
    @OnTimer(40000)
    public static void printMem() {
        print("堆 Heap:");
        println(Sys.Memory.heapUsage());
        print("非堆 Non-Heap:");
        println(Sys.Memory.nonHeapUsage());
    }

//    @OnMethod(
//            clazz="java.util.zip.Deflater",
//            method="deflate"
//    )
//    public static void traceDefCacheBlock(){
//        println("java.util.zip.Deflater 调用 deflate?");
//    }

//    @OnMethod(
//            clazz="java.util.zip.Deflater",
//            method="deflate"
//            //location=@Location(Kind.RETURN)
//    )
//    /*主要两个参数是对象自己的引用 和 返回值，其它参数都是方法调用时传入的参数*/
//    public static void traceExecute2(){
//        println("java.util.zip.Deflater 调用堆栈！！");
//        jstack();
//    }


//    @OnMethod(
//            clazz="java.util.zip.Inflater",
//            method="Inflater"
//    )
//    public static void traceCacheBlock(){
//        println("java.util.zip.Inflater 调用 Inflater");
//    }

    @OnMethod(
            clazz="java.util.zip.Inflater",
            method="<init>"
    )
    /*主要两个参数是对象自己的引用 和 返回值，其它参数都是方法调用时传入的参数*/
    public static void traceExecute(){
        println("java.util.zip.Inflater <init> 调用堆栈！！");
        jstack();
    }

    @OnMethod(
            clazz="java.util.zip.Inflater",
            method="<init>"
    )
    /*主要两个参数是对象自己的引用 和 返回值，其它参数都是方法调用时传入的参数*/
    public static void traceExecute23(){
        println("java.util.zip.Inflater <init> 调用堆栈！！");
        jstack();
    }


    @OnMethod(
            clazz="java.util.zip.Inflater",
            method="Inflater"
    )
    /*主要两个参数是对象自己的引用 和 返回值，其它参数都是方法调用时传入的参数*/
    public static void traceExecuteIn(){
        println("java.util.zip.Inflater Inflater 调用堆栈！！");
        jstack();
    }

}
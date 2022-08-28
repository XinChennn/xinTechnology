package cn.ixinjiu.javaEE.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Lock;

/**
 * Created by XinChen on 2022-08-28
 *
 * @Description:TODO 几种常见数组的区别
 *  ArrayList基于数组，LinkedList基于链表 -> 所以ArrayList适合查询，LinkedList适合插入
 *  LinkedList不需要扩容，链表是不需要扩容的
 *
 *  synchronized保证线程同步
 *
 * ArrayList 是基于数组实现的，LinkedList 是基于双向链表实现的，ArrayList 在新增和删除元素时，因为涉及到数组复制，所以效率比 LinkedList 低，而在遍历的时候，ArrayList 的效率要高于 LinkedList。
 * ArrayList 是基于动态数组实现的非线程安全的集合。当底层数组满的情况下还在继续添加的元素时，ArrayList则会执行扩容机制扩大其数组长度。ArrayList查询速度非常快，使得它在实际开发中被广泛使用。美中不足的是插入和删除元素较慢，同时它并不是线程安全的。
 * 扩容机制：通过扩容机制判断原数组是否还有空间，若没有则重新实例化一个空间更大的新数组，把旧数组的数据拷贝到新数组中，先判断下标是否越界，再扩容。若插入的下标为i，则通过复制数组的方式将i后面的所有元素，往后移一位，新数据替换下标为i的旧元素。
 * LinkedList 是基于双向链表实现的非线程安全的集合，它是一个链表结构，不能像数组一样随机访问，必须是每个元素依次遍历直到找到元素为止。其结构的特殊性导致它查询数据慢。
 * 查询时先判断元素是靠近头部，还是靠近尾部，然后再查询。
 *
 * 如果要使用线程安全的集合使用：
 * Vector 的数据结构和使用方法与ArrayList差不多。最大的不同就是Vector是线程安全的。几乎所有的对数据操作的方法都被synchronized关键字修饰。synchronized是线程同步的，当一个线程已经获得Vector对象的锁时，其他线程必须等待直到该锁被释放。从这里就可以得知Vector的性能要比ArrayList低。
 * 总结：
 * ArrayList和LinkedList都不是线程安全的，小并发量的情况下可以使用Vector，若并发量很多，且读多写少可以考虑使用CopyOnWriteArrayList。【靠批昂外特】
 * 因为CopyOnWriteArrayList底层使用ReentrantLock【瑞俺锤老可】锁，比使用synchronized关键字的Vector能更好的处理锁竞争的问题。
 */
public class MyTest {
    public static void main(String[] args) {
        /**
         *
         */
        List arrayList = new ArrayList();
        boolean add = arrayList.add(1);
        arrayList.add(2);
        arrayList.forEach(System.out::println);

        System.out.println("----------------------------");

        List<Integer> linkedList = new LinkedList<Integer>();
        linkedList.add(3);
        linkedList.add(4);
        linkedList.forEach(System.out::println);

        System.out.println("----------------------------");

        Vector vector = new Vector();
        vector.add(5);
        vector.add(6);
        vector.forEach(System.out::println);
    }

    synchronized void test () {
    }

}

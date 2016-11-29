package test.treemap;

/**
 * Created by yuanguanghui on 2016/10/10.
 */
public class Student implements Comparable<Student> {

    String name;
    int score;

    public int compareTo(Student o) {
        if (o.score < this.score) {
            return 1;
        }
        if (o.score > this.score) {
            return -1;
        }
        return 0;
    }
}

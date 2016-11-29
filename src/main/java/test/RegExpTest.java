package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuanguanghui on 2016/11/23.
 */
public class RegExpTest {

    public static void main(String[] args) {
        String str = "北京市(朝阳区)(西城区)(海淀区)";
        Pattern p = Pattern.compile(".*?(?=\\()");
        Matcher m = p.matcher(str);
        if(m.find()) {
            System.out.println(m.group());
        }

        String str1 = "2hi1";
        Pattern p1 = Pattern.compile("\\bhi\\b");
        Matcher m1 = p1.matcher(str1);
        if(m1.find()) {
            System.out.println(m1.group());
        }
    }

}


import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author db
 * @date 2021/2/8 - 13:30
 */
public class test {
    public static void main(String[] args) {
        List list = new ArrayList<>();
        list.add("11");
        list.add("12");
        list.add("13");
        String join = StringUtils.join(list.toArray(), ",");
        System.out.println(join);
    }
}

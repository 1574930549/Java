

import java.util.ArrayList;
import java.util.List;

/**
 * @author spondere
 * @version 1.8
 */
public class Test2 {
    public static void main( String[] args ) {
        UserVO2 t = new UserVO2();
        t.setUserid("1814010130-1");
        t.setUsername("test2");
        t.setPassword("321");
        t.setBirthday("2021-04-03");
        t.setUserico("C:\\Users\\zlh\\Desktop\\图片\\test.png");

        Product p1 = new Product();
        p1.setProductname("矿泉水");
        p1.setAmount("5");
        Product p2 = new Product();
        p2.setProductname("酸奶");
        p2.setAmount("2");
        Product p3 = new Product();
        p3.setProductname("薯片");
        p3.setAmount("7");
        List<Product> productList = new ArrayList<Product>();
        productList.add(p1);
        productList.add(p2);
        productList.add(p3);
        t.setproduct(productList);
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.insert(t);

        List<UserVO2> l = userDao.queryByProductName("矿泉水");
        System.out.println(l);
    }
}

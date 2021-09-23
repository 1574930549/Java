

import java.util.ArrayList;
import java.util.List;

/**
 * @author spondere
 * @version 1.8
 */
public class Test {
    public static void main( String[] args ) {
        UserVO2 t = new UserVO2();
        t.setUserid("1814010130");
        t.setUsername("test");
        t.setPassword("123");
        t.setBirthday("2021-04-02");
        t.setUserico("C:\\Users\\zlh\\Desktop\\图片\\Snipaste_2021-04-06_17-22-17.png");

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
        UserDao2 userDao = new UserDaoImpl();
        userDao.insert(t);

        UserVO2 u = userDao.queryById("1814010130");
        System.out.println(u);
    }
}

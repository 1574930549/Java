import java.util.ArrayList;
import java.util.List;

public class ProductTest {
    public static void main(String[] args) {
        Dao dao = new Dao();
        UserVO vo = new UserVO();
        vo.setUserId("0101");
        vo.setUserName("吕大龙");
        vo.setPassWord("123456");
        vo.setBirthday("1999-09-09");
        vo.setUserico("D:\\ldl.jpg");
        Product p1 = new Product();
        p1.setproductName("矿泉水");
        p1.setamount("5");
        Product p2 = new Product();
        p2.setproductName("酸奶");
        p2.setamount("2");
        Product p3 = new Product();
        p3.setproductName("薯片");
        p3.setamount("7");
        List<Product> productList = new ArrayList<Product>();
        productList.add(p1);
        productList.add(p2);
        productList.add(p3);
        vo.setProduct(productList);
        dao.insert(vo);
        UserVO user = dao.queryById("0101");
        System.out.println(user);
    }
}

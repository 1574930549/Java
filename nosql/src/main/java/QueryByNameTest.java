
import java.util.ArrayList;
import java.util.List;

public class QueryByNameTest {
    public static void main(String[] args) {
        Dao dao = new Dao();
        UserVO vo = new UserVO();
        vo.setUserId("0102");
        vo.setUserName("王大脸");
        vo.setPassWord("654321");
        vo.setBirthday("1988-08-08");
        vo.setUserico("D:\\wdl.jpg");
        Product p1 = new Product();
        p1.setproductName("矿泉水");
        p1.setamount("5");
        Product p2 = new Product();
        p2.setproductName("酸奶");
        p2.setamount("2");
        List<Product> productList = new ArrayList<Product>();
        productList.add(p1);
        productList.add(p2);
        vo.setProduct(productList);
        dao.insert(vo);
        List<UserVO> l = dao.queryByProductName("矿泉水");
        System.out.println(l);
    }
}

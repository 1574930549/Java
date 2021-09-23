import java.util.List;
import java.util.Scanner;

/**
 * @author spondere
 * @version 1.8
 */
public class redis {
    public static void main(String[] args)throws Exception {
//        int a=10;
//        Scanner input=new Scanner(System.in);
//        while(a!=0){
//            a=input.nextInt();
//            switch (a){
//                case 1:
//                    Insert();
//                    System.out.println("Insert");
//                    break;
//                case 2:
//                    QueryAll();
//                    System.out.println("QueryAll");
//                    break;
//                case 3:
//                    QueryByName();
//                    System.out.println("QueryByName");
//                    break;
//                case 4:
//                    QueryById();
//                    System.out.println("QueryById");
//                    break;
//                case 5:
//                    Update();
//                    System.out.println("Update");
//                    break;
//                case 6:
//                    Delete();
//                    System.out.println("Delete");
//                    break;
//                case 0:
//                    break;
//                default :
//                    break;
//            }
//        }
        String u=null;
		Insert();
        System.out.println("Insert");
        QueryAll();
        System.out.println("QueryAll");
		u=QueryByName();
        System.out.println("QueryByName");
		QueryById(u);
        System.out.println("QueryById");
        Update(u);
        System.out.println("Update");
        Delete(u);
        System.out.println("Delete");

    }



    public static void Insert() throws Exception{

        UserDao userDao = (UserDao) new UserDaoRedisImpl();

        UserVO user1 = new UserVO();
        user1.setUserName("test1");
        user1.setVocation("testt1");
        user1.setPassword("111");
        userDao.insert(user1);

        UserVO user2 = new UserVO();
        user2.setUserName("test2");
        user2.setVocation("testt2");
        user2.setPassword("111");
        userDao.insert(user2);

        UserVO user3 = new UserVO();
        user3.setUserName("test3");
        user3.setVocation("testt3");
        user3.setPassword("111");
        userDao.insert(user3);


        UserVO user4 = new UserVO();
        user4.setUserName("test4");
        user4.setVocation("testt4");
        user4.setPassword("111");
        userDao.insert(user4);

    }


    public static void Delete(String u)throws Exception{
        UserDao userDao = (UserDao) new UserDaoRedisImpl();
        UserVO vo = userDao.queryById(u);
        userDao.delete(vo);
    }

    public static void Update(String u)throws Exception{
        UserDao userDao = (UserDao) new UserDaoRedisImpl();
        System.out.println(userDao);
        UserVO vo = userDao.queryById(u);
        System.out.println(vo);
        vo.setPassword("222");
        vo.setVocation("testt");
        userDao.update(vo);
    }

    public static String QueryByName()throws Exception{
        String userid = null;
        UserDao userDao = (UserDao) new UserDaoRedisImpl();
        List<UserVO> vos = userDao.queryByName("test4");
        for(UserVO vo : vos){
            System.out.println(vo);
            userid=vo.getUserId();
        }
//        System.out.println(userid);
        return userid;
    }

    public static void QueryAll()throws Exception{
        UserDao userDao = (UserDao) new UserDaoRedisImpl();
        List<UserVO> vos = userDao.queryAll();
        for(UserVO vo : vos){
            System.out.println(vo);
        }
    }

    public static void QueryById(String u)throws Exception{
        UserDao userDao = (UserDao) new UserDaoRedisImpl();
        UserVO vo = userDao.queryById(u);
        System.out.println(vo);
    }
}
   
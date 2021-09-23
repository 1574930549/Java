
public interface UserDao2 {
        public UserVO2 queryById(String userId);
        public void insert(UserVO2 user);
        public void update(UserVO2 user);
        public void delete(UserVO2 user);
}

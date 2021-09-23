import redis.clients.jedis.Jedis;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class test3 {
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    private static Jedis jedis = new Jedis("121.89.197.4",6379);
    public static void main(String[] args) {
        String path1="C:\\Users\\zlh\\Desktop\\图片\\Snipaste_2021-04-06_17-22-17.png";
        String path2="C:\\Users\\zlh\\Desktop\\图片\\test.png";
        String key="图片";
        System.out.println(uploadFile(key, path1));
        downloadFile(key,path2);
    }

    /**
     * 将图片转换成二进制
     *
     * @return
     */
    static String uploadFile(String key, String path) {
//        jedis.auth("123");
        File f = new File(path);
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);  //经测试转换的图片是格式这里就什么格式，否则会失真
            byte[] bytes = baos.toByteArray();
            jedis.set(key, encoder.encodeBuffer(bytes).trim());
            return encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制转换为图片
     *
     * @param key,path
     */
    static void downloadFile(String key, String path) {
        try {
            String base64String = jedis.get(key);
            byte[] bytes1 = decoder.decodeBuffer(base64String);

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File w2 = new File(path);// 可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

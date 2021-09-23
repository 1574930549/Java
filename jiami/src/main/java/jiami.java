public class jiami {
    public static void main(String[] args) {
    System.out.println("Hello World");
        /**
         * RSA加密
         * @param content 待加密内容
         * @return byte[]
         */
        public static byte[] RSAEncrypt(final String content) {
            return processCipher(content.getBytes(), keyPair.getPrivate(), Cipher.ENCRYPT_MODE , ALGORITHM_RSA);
        }

        /**
         * RSA解密
         * @param encoderContent 已加密内容
         * @return byte[]
         */
        public static byte[] RSADecrypt(final byte[] encoderContent) {
            return processCipher(encoderContent, keyPair.getPublic(), Cipher.DECRYPT_MODE, ALGORITHM_RSA);
        }
}
}

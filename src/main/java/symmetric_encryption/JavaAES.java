package symmetric_encryption;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *  AES:比DES使用更广泛,DES的替代者
 *
 *  jdk: 秘钥长度128,198,256, 默认128 工作模式 ECB CBC等, 填充方式 NoPadding PKCS5Padding等;
 *  (256位秘钥长度需要获取无政策限制权限文件)
 *
 *  BC: 秘钥长度128,198,256, 默认128 工作模式 ECB CBC等, 填充方式 ZeroBytePadding,PKCS7Padding等;
 *
 */
public class JavaAES {
    public static final String src = "Java AES";

    public static void main(String[] args) {
        jdkAES();
        bcAES();

    }

    // 用jdk实现:
    public static void jdkAES() {
        try {
            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            // 产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 获取密钥
            byte[] keyBytes = secretKey.getEncoded();

            // KEY转换
            Key key = new SecretKeySpec(keyBytes, "AES");

            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk aes encrypt:" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);
            System.out.println("jdk aes decrypt:" + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 用bouncy castle实现:
    public static void bcAES() {
        try {
            Security.addProvider(new BouncyCastleProvider());

            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "BC");
            keyGenerator.getProvider();
            keyGenerator.init(128);
            // 产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 获取密钥
            byte[] keyBytes = secretKey.getEncoded();


            // KEY转换
            Key key = new SecretKeySpec(keyBytes, "AES");


            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("bc aes encrypt:" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);
            System.out.println("bc aes decrypt:" + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package symmetric_encryption;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * DES: 违反科克霍夫原则: 公开算法,秘钥隐藏, 存在安全问题
 * <p>
 * 三重DES(DESede): 秘钥长度提高, 迭代次数提高
 * <p>
 * jdk: 秘钥长度112,168, 默认168 工作模式 ECB CBC等, 填充方式 NoPadding PKCS5Padding等;
 * bc: 秘钥长度128,198, 默认168 工作模式 ECB CBC等, 填充方式 PKCS7Padding等;
 */
public class Java3DES {
    public static final String src = "java triple DES";

    public static void main(String[] args) {
        jdk3DES();
        bc3DES();

    }

    // 用jdk实现:
    public static void jdk3DES() {
        try {
            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            // 必须长度是：112或168
//			keyGenerator.init(168);
            //根据不同算法生成默认长度的key
            keyGenerator.init(new SecureRandom());
            // 产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 获取密钥
            byte[] bytesKey = secretKey.getEncoded();


            // KEY转换
            DESedeKeySpec desKeySpec = new DESedeKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            Key convertSecretKey = factory.generateSecret(desKeySpec);


            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk 3des encrypt:" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(result);
            System.out.println("jdk 3des decrypt:" + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 用bouncy castle实现:
    public static void bc3DES() {
        try {
            Security.addProvider(new BouncyCastleProvider());

            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede", "BC");
            keyGenerator.getProvider();
            keyGenerator.init(168);
            // 产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 获取密钥
            byte[] bytesKey = secretKey.getEncoded();


            // KEY转换
            DESedeKeySpec desKeySpec = new DESedeKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            Key convertSecretKey = factory.generateSecret(desKeySpec);


            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("bc 3des encrypt:" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(result);
            System.out.println("bc 3des decrypt:" + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

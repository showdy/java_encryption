package symmetric_encryption;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 *  PBE: Password Based Encryption 基于口令加密,加盐扰乱
 *  PBE: 结合消息摘要算法和对称加密算法的优点
 *
 *  实现方: JDK BC
 *  JAVA : 算法: PBEWithMD5AndDES 秘钥长度:56 默认56 工作方式 CBC 填充方式 PKCS5Padding
 *  BC : 算法: PBEWithMD5AndDES 秘钥长度:64 默认64 工作方式 CBC 填充方式 PKCS7Padding等
 */
public class JavaPBE {
    public static final String src = "Java PBE";

    public static void main(String[] args) {
        jdkPBE();
        bcPBE();

    }

    // 用jdk实现:
    public static void jdkPBE() {
        try {
            // 初始化盐
            SecureRandom random = new SecureRandom();
            byte[] salt = random.generateSeed(8);

            // 口令与密钥
            String password = "showdy";
            //口令转换成秘钥
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
            Key key = factory.generateSecret(pbeKeySpec);

            // 加密--迭代次数100
            PBEParameterSpec pbeParameterSpac = new PBEParameterSpec(salt, 100);
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpac);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk pbe encrypt:" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpac);
            result = cipher.doFinal(result);
            System.out.println("jdk pbe decrypt:" + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //BC实现--??
    public static void bcPBE(){

        try {
            Security.addProvider(new BouncyCastleProvider());
            // 初始化盐
            SecureRandom random = new SecureRandom();
            byte[] salt = random.generateSeed(8);

            // 口令与密钥
            String password = "showdy";
            //口令转换成秘钥
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
            Key key = factory.generateSecret(pbeKeySpec);

            // 加密--迭代次数100
            PBEParameterSpec pbeParameterSpac = new PBEParameterSpec(salt, 100);
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpac);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("BC pbe encrypt:" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpac);
            result = cipher.doFinal(result);
            System.out.println("BC pbe decrypt:" + new String(result));

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}

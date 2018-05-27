package msg_digest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.Security;

public class JavaMD5 {
    public static final String message = "Java md5";

    public static void main(String[] args) {
        jdkMD5();
        jdkMD2();

        bcMD4();
        bcMD5();

        bc2jdkMD4();

        ccMD5();
        ccMD2();

    }

    // 用jdk实现:MD5
    public static void jdkMD5() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(message.getBytes());
            System.out.println("JDK MD5:" + Hex.encodeHexString(md5Bytes));

//            md.update(message.getBytes());
//            byte[] digest = md.digest();
//            System.out.println("JDK MD5:" + Hex.encodeHexString(digest));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 用jdk实现:MD2
    public static void jdkMD2() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] md2Bytes = md.digest(message.getBytes());
            System.out.println("JDK MD2:" + Hex.encodeHexString(md2Bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 用bouncy castle实现:MD5
    public static void bcMD5() {
        MD5Digest digest = new MD5Digest();
        digest.update(message.getBytes(), 0, message.getBytes().length);
        byte[] md5Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md5Bytes, 0);
        System.out.println("bouncy castle MD5:" + Hex.encodeHexString(md5Bytes));

    }


    // 用bouncy castle实现:MD4
    public static void bcMD4() {
        Digest digest = new MD4Digest();
        digest.update(message.getBytes(), 0, message.getBytes().length);
        byte[] md4Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md4Bytes, 0);
        System.out.println("bouncy castle MD4:" + Hex.encodeHexString(md4Bytes));
    }

    // 用bouncy castle与jdk结合实现:MD4
    public static void bc2jdkMD4() {
        try {
            //jre下的java.security配置一样:
            //Providers can be dynamically registered instead by calls to
            //either the addProvider or insertProviderAt method in the Security class.
            Security.addProvider(new BouncyCastleProvider());
            MessageDigest md = MessageDigest.getInstance("MD4");
            byte[] md4Bytes = md.digest(message.getBytes());
            System.out.println("bc and JDK MD4:" + Hex.encodeHexString(md4Bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 用common codes实现实现:MD5
    public static void ccMD5() {
        System.out.println("common codes MD5:" + DigestUtils.md5Hex(message.getBytes()));
    }

    // 用common codes实现实现:MD2
    public static void ccMD2() {
        System.out.println("common codes MD2:" + DigestUtils.md2Hex(message.getBytes()));
    }

}

import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
public class RSAExample implements Serializable {
    private KeyPairGenerator keyGen;
    private KeyPair keyPair;
    PublicKey publicKey;
    PrivateKey privateKey;
    private Cipher cipher;
    public RSAExample() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        keyGen = KeyPairGenerator.getInstance("RSA", "BC");
        keyGen.initialize(2048);
        keyPair = keyGen.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
    }

    public byte[] Encode(String plainText,PublicKey publicKey1) throws Exception, IOException {
        cipher.init(Cipher.ENCRYPT_MODE, publicKey1);
        byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));
        return cipherText;
    }

    public String Decode(byte[] cipherText) throws Exception {
        System.out.println(privateKey);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedText = cipher.doFinal(cipherText);
        String decryptedString = new String(decryptedText, "UTF-8");
//        System.out.println(decryptedString);
        return decryptedString;
    }

//    public static void main(String[] args) throws Exception {
//        RSAExample rsaExample=new RSAExample();
//        RSAExample rsaExample1=new RSAExample();
//        System.out.println(rsaExample.publicKey);
//        System.out.println(rsaExample1.publicKey);
//        String s="D:\\rsa1.txt";
//        String s2="D:\\rsa2.txt";
//        rsaExample.postPublicket(s);
//        rsaExample1.postPublicket(s2);
//        rsaExample.getPublicKey(s2);
//        rsaExample1.getPublicKey(s);
//        String ss="hello world";
//        byte[] c=rsaExample.Encode(ss, rsaExample.publicKey);
//        System.out.println(Arrays.toString(c));
//        byte[] c2=rsaExample1.Encode(ss, rsaExample1.publicKey);
//        System.out.println(Arrays.toString(c2));
//        System.out.println(rsaExample.Decode(c2));
//        System.out.println(rsaExample1.Decode(c));
//    }
    public String postPublicket(String s) throws Exception{
        File f=new File(s);
        FileOutputStream o=new FileOutputStream(f);
        OutputStreamWriter os=new OutputStreamWriter(o);
        BufferedWriter ps = new BufferedWriter(os);
        String ws= Base64.getEncoder().encodeToString(publicKey.getEncoded());
        ps.write(ws);
        ps.close();
        return  ws;
    }
    public PublicKey getPublicKey(String s) throws Exception {
        File f=new File(s);
        FileInputStream o=new FileInputStream(f);
        InputStreamReader os=new InputStreamReader(o);
        BufferedReader ps = new BufferedReader(os);
        String ws= ps.readLine();
        byte[] pub=Base64.getDecoder().decode(ws);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec=new X509EncodedKeySpec(pub);
        PublicKey p=keyFactory.generatePublic(spec);
//        publicKey=p;
        ps.close();
        return  p;
    }
}
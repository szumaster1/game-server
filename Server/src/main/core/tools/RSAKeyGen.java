package core.tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * A class to generate a simple 1024-bit RSA pair
 */
public class RSAKeyGen {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair keypair = keyGen.genKeyPair();
            PrivateKey privateKey = keypair.getPrivate();
            PublicKey publicKey = keypair.getPublic();

            RSAPrivateKeySpec privSpec = factory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

            writeKey("rsapriv", privSpec.getModulus(), privSpec.getPrivateExponent());

            RSAPublicKeySpec pubSpec = factory.getKeySpec(publicKey, RSAPublicKeySpec.class);

            writeKey("rsapub", pubSpec.getModulus(), pubSpec.getPublicExponent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write key.
     *
     * @param file     the file
     * @param modulus  the modulus
     * @param exponent the exponent
     */
    public static void writeKey(String file, BigInteger modulus, BigInteger exponent) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("private static final BigInteger RSA_MODULUS = new BigInteger(\"" + modulus.toString() + "\");");
            writer.newLine();
            writer.newLine();
            writer.write("private static final BigInteger RSA_EXPONENT = new BigInteger(\"" + exponent.toString() + "\");");
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

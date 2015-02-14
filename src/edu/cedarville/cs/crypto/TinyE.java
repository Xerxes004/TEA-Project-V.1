package edu.cedarville.cs.crypto;

public class TinyE {
	
	public static enum Mode { ECB, CBC, CTR };
	private final String DELTA = "9E3779B9";
	
	public Integer[] encrypt(Integer[] plaintext, Integer[] key, Mode mode, Integer[] iv) {
        /* REMINDERS
            64 bit block size (32 bits per L and R side)
            128 bit key
                - break key into four 32 bit ints
            32 rounds
        */
        
        /* TEA Encryption algorithm (slide 82, Cryptography slides)
            // KEEP IN MIND that this algorithm is for a single 64-bit block,
            // so it will have to be repeated until all of the plaintext is
            // ciphered. We are concatenating each block Big-Endian style.

            Integer[] key = {int0,int1,int2,int3};
            Integer[] plaintext = new Integer[2];
            plaintext = {L,R];
                //DEFINED above
            delta = 0x9e3779b9;
            int sum = 0;
            for (32 times) {
                sum += delta;
                L += ((R << 4)+K[0])^(R+sum)^((R >> 5)+K[1]);
                R += ((L << 4)+K[2])^(L+sum)^((L >> 5)+K[3]);
            }
            cipherText = (L,R);
        */
        
			return null;
	}

	public Integer[] decrypt(Integer[] ciphertext, Integer[] key, Mode mode, Integer[] iv) {
        /* TEA Decryption algorithm (slide 83, Cryptography slides)
            // KEEP IN MIND that this algorithm is for a single 64-bit block,
            // so it will have to be repeated until all of the plaintext is
            // deciphered. We are concatenating each block Big-Endian style.

            Integer[] key = {int0,int1,int2,int3};
            Integer[] ciphertext;
                //DEFINED above
            delta = 0x9e3779b9;
            int sum = delta << 5;
            for (32 times) {
                sum += delta;
                R -= ((L << 4)+K[2])^(L+sum)^((L >> 5)+K[3]);
                L += ((R << 4)+K[0])^(R+sum)^((L >> 5)+K[1]);
            }
            Integer[] cipherText = new Integer[2];
            cipherText = {L,R};
        */
            return null;
	}
	
}

package edu.cedarville.cs.crypto;

import static edu.cedarville.cs.crypto.Tools.convertFromHexStringToInts;



public class TinyE {

    public static enum Mode {

        ECB, CBC, CTR
    };
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
        
        Integer[] ciphertext = {0};
        Integer[] K = {0};
        for(Integer k = 0; k < 3; k++) {
            K[k] = key[k];
        }
        Integer[] delta = convertFromHexStringToInts(DELTA);
        long sum = 0;
        if(mode == Mode.ECB) {
            for(Integer i = 0; i < plaintext.length; i+=2) {
                Integer L = plaintext[i];
                Integer R = plaintext[i+1];
                for(Integer j = 0; j < 32; j++) {
                    sum += (((long) delta[0] << 32) & 0xffffffff00000000l)|((long)(delta[1] & 0x00000000ffffffffl));
                    L += ((R << 4)+K[0])^(R+(int)sum)^((R >> 5)+K[1]);
                    R += ((L << 4)+K[2])^(L+(int)sum)^((L >> 5)+K[3]);    
                }
                sum = 0;
                ciphertext[i] = L;
                ciphertext[i+1] = R;
            }
        } else if(mode == Mode.CBC) {
            
        } else {
            
        }
        return ciphertext;
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
        
        Integer[] plaintext = {0};
        Integer[] K = {0};
        for(Integer k = 0; k < 3; k++) {
            K[k] = key[k];
        }
        Integer[] delta = convertFromHexStringToInts(DELTA);
        long sum = (((long) delta[0] << 32) & 0xffffffff00000000l)|((long)(delta[1] & 0x00000000ffffffffl)) << 5;
        if(mode == Mode.ECB) {
            for(Integer i = 0; i < ciphertext.length; i+=2) {
                Integer L = ciphertext[i];
                Integer R = ciphertext[i+1];
                for(Integer j = 0; j < 32; j++) {
                    sum -= (((long) delta[0] << 32) & 0xffffffff00000000l)|((long)(delta[1] & 0x00000000ffffffffl));
                    R -= ((L << 4)+K[2])^(L+(int)sum)^((L >> 5)+K[3]);
                    L -= ((R << 4)+K[0])^(R+(int)sum)^((R >> 5)+K[1]);
    
                }
                sum = 0;
                plaintext[i] = L;
                plaintext[i+1] = R;
            }
        } else if(mode == Mode.CBC) {
            
        } else {
            
        }
        return plaintext;
    }

}

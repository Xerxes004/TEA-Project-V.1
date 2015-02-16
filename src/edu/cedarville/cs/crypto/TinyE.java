package edu.cedarville.cs.crypto;

import static edu.cedarville.cs.crypto.Tools.convertFromHexStringToInts;



public class TinyE {

    public static enum Mode {

        ECB, CBC, CTR
    };
    private final String DELTA = "9E3779B9";
    
    public static void main(String[] args) {
        Integer[] plain = {5,5,443,3,45};
        Integer[] cipher = new Integer[plain.length];
        Integer[] key1 = {42314,2345,27554,10};
        Integer[] IV = {0};
        Mode curMode = Mode.ECB;
        TinyE tea = new TinyE();
        cipher = tea.encrypt(plain, key1, curMode, IV);
        for(Integer i = 0; i < cipher.length; i++){
            System.out.print(cipher[i]);            
        }
        System.out.println("");
        
        plain = tea.decrypt(cipher, key1, curMode, IV);
        for(Integer i = 0; i < plain.length; i++){
            System.out.print(plain[i]);            
        }
        System.out.println("");        
        
    }

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
        
        Integer[] ciphertext = new Integer[plaintext.length];

        Integer[] delta = convertFromHexStringToInts(DELTA);
        System.out.println(delta.length);
        long sum = 0;
        if(mode == Mode.ECB) {
            for(Integer i = 0; i < plaintext.length; i+=2) {
                long L = plaintext[i];
                long R = plaintext[i+1];
                for(Integer j = 0; j < 32; j++) {
                    sum += (((long) delta[0] << 32) & 0xffffffff00000000l)|((long)(delta[1] & 0x00000000ffffffffl));
                    L += ((R << 4)+key[0])^(R+sum)^((R >> 5)+key[1]);
                    R += ((L << 4)+key[2])^(L+sum)^((L >> 5)+key[3]);    
                }
                sum = 0;
                ciphertext[i] = (int)L;
                ciphertext[i+1] = (int)R;
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
        
        Integer[] plaintext = new Integer[ciphertext.length];

        Integer[] delta = convertFromHexStringToInts(DELTA);
        long sum = (((long) delta[0] << 32) & 0xffffffff00000000l)|((long)(delta[1] & 0x00000000ffffffffl)) << 5;
        if(mode == Mode.ECB) {
            for(Integer i = 0; i < ciphertext.length; i+=2) {
                long L = ciphertext[i];
                long R = ciphertext[i+1];
                for(Integer j = 0; j < 32; j++) {
                    sum -= (((long) delta[0] << 32) & 0xffffffff00000000l)|((long)(delta[1] & 0x00000000ffffffffl));
                    R -= ((L << 4)+key[2])^(L+(int)sum)^((L >> 5)+key[3]);
                    L -= ((R << 4)+key[0])^(R+(int)sum)^((R >> 5)+key[1]);
    
                }
                sum = (((long) delta[0] << 32) & 0xffffffff00000000l)|((long)(delta[1] & 0x00000000ffffffffl)) << 5;
                plaintext[i] = (int)L;
                plaintext[i+1] = (int)R;
            }
        } else if(mode == Mode.CBC) {
            
        } else {
            
        }
        return plaintext;
    }

}

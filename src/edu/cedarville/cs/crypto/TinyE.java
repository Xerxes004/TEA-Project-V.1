package edu.cedarville.cs.crypto;

import static edu.cedarville.cs.crypto.Tools.convertFromHexStringToInts;
import static edu.cedarville.cs.crypto.Tools.convertFromIntsToHexString;

public class TinyE {
    
    public static enum Mode {
        
        ECB, CBC, CTR
    };
    //private final String DELTA = "9E3779B9";
    private static final int DELTA = 0x9e3779b9;
    
    public static void main(String[] args) {
        System.out.println("Delta is : " + Integer.toHexString(DELTA));
        /*//Integer[] plain = {1234, 238, 4832, 3};
         //Integer[] cipher = new Integer[plain.length];
         //Integer[] key1 = {42314,2345,27554,10};
         //Integer[] IV = {0};
         Mode curMode = Mode.ECB;
         TinyE tea = new TinyE();
         cipher = tea.encrypt(plain, key1, curMode, IV);
         for(Integer i = 0; i < cipher.length; i++){
         System.out.print(cipher[i] + " ");            
         }
         System.out.println("");
        
         plain = tea.decrypt(cipher, key1, curMode, IV);
         for(Integer i = 0; i < plain.length; i++){
         System.out.print(plain[i] + " ");            
         }
         System.out.println(""); */        
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
        for (int m = 0; m < plaintext.length; m++) {
            System.out.print(Integer.toHexString(plaintext[m]) + " ");
        }
        System.out.println("");
        
        Integer[] ciphertext = new Integer[plaintext.length + (plaintext.length % 2 != 0 ? 1 : 0)];

        //Integer[] delta = convertFromHexStringToInts(DELTA);
        System.out.println("Encrypt key is : " + Integer.toHexString(key[0]) + " " + Integer.toHexString(key[1]) + " " + Integer.toHexString(key[2]) + " " + Integer.toHexString(key[3]));
        int sum = 0;
        if (mode == Mode.ECB) {
            for (Integer i = 0; i < plaintext.length; i += 2) {
                int L = plaintext[i];
                int R = plaintext[i + 1];
                for (Integer j = 0; j < 32; j++) {
                    sum += DELTA;
                    L += ((R << 4) + key[0]) ^ (R + sum) ^ ((R >> 5) + key[1]);
                    R += ((L << 4) + key[2]) ^ (L + sum) ^ ((L >> 5) + key[3]);                    
                }
                sum = 0;
                ciphertext[i] = L;
                ciphertext[i + 1] = R;
            }
            for (int k = 0; k < ciphertext.length; k++) {
                System.out.print(Integer.toHexString(ciphertext[k]) + " ");
            }
            System.out.println("");
            
        } else if (mode == Mode.CBC) {
            Integer[] xorer = new Integer[2];
            xorer[0] = iv[0];
            xorer[1] = iv[1];
            
            for (Integer i = 0; i < plaintext.length; i += 2) {
                int L = plaintext[i] ^ xorer[0];
                int R = plaintext[i + 1] ^ xorer[1];
                for (Integer j = 0; j < 32; j++) {
                    sum += DELTA;
                    L += ((R << 4) + key[0]) ^ (R + sum) ^ ((R >> 5) + key[1]);
                    R += ((L << 4) + key[2]) ^ (L + sum) ^ ((L >> 5) + key[3]);                 
                }
                sum = 0;
                xorer[0] = L;
                xorer[1] = R;
                ciphertext[i] = L;
                ciphertext[i + 1] = R;
            }
            /*for(int k = 0; k < ciphertext.length; k++) {
             System.out.print("Hex: " + Integer.toHexString(ciphertext[k]) + " ");
             }*/
            System.out.println("");
            
        } else {
            Integer[] xorer = new Integer[2];
            xorer[0] = iv[0];
            xorer[1] = iv[1];
            
            for (Integer i = 0; i < plaintext.length; i += 2) {
                int L = xorer[0];
                int R = xorer[1];
                for (Integer j = 0; j < 32; j++) {
                    sum += DELTA;
                    L += ((R << 4) + key[0]) ^ (R + sum) ^ ((R >> 5) + key[1]);
                    R += ((L << 4) + key[2]) ^ (L + sum) ^ ((L >> 5) + key[3]);                    
                }
                sum = 0;
                xorer[0] += 1;
                ciphertext[i] = L ^ plaintext[i];
                ciphertext[i + 1] = R ^ plaintext[i + 1];
            }
            for (int k = 0; k < ciphertext.length; k++) {
                System.out.print(ciphertext[k] + " ");
            }
            System.out.println("");            
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

        //Integer[] delta = convertFromHexStringToInts(DELTA);
        int sum = DELTA << 5;
        if (mode == Mode.ECB) {
            System.out.println("Decrypt key is : " + Integer.toHexString(key[0]) + " " + Integer.toHexString(key[1]) + " " + Integer.toHexString(key[2]) + " " + Integer.toHexString(key[3]));
            for (Integer i = 0; i < ciphertext.length; i += 2) {
                int L = ciphertext[i];
                int R = ciphertext[i + 1];
                //sum = DELTA << 5;
                for (Integer j = 0; j < 32; j++) {
                    
                    R -= ((L << 4) + key[2]) ^ (L + sum) ^ ((L >> 5) + key[3]);
                    L -= ((R << 4) + key[0]) ^ (R + sum) ^ ((R >> 5) + key[1]);
                    sum -= DELTA;
                }
                sum = DELTA << 5;
                plaintext[i] = L;
                plaintext[i + 1] = R;
            }
        } else if (mode == Mode.CBC) {
            Integer[] xorer = new Integer[2];
            xorer[0] = iv[0];
            xorer[1] = iv[1];
            
            for (Integer i = 0; i < ciphertext.length; i += 2) {
                int L = ciphertext[i];
                int R = ciphertext[i + 1];
                for (Integer j = 0; j < 32; j++) {
                    
                    R -= ((L << 4) + key[2]) ^ (L + (int) sum) ^ ((L >> 5) + key[3]);
                    L -= ((R << 4) + key[0]) ^ (R + (int) sum) ^ ((R >> 5) + key[1]);
                    sum -= DELTA;
                }
                sum = DELTA << 5;
                plaintext[i] = L ^ xorer[0];
                plaintext[i + 1] = R ^ xorer[1];
                xorer[0] = L;
                xorer[1] = R;                
            }            
        } else {
            Integer[] xorer = new Integer[2];
            xorer[0] = iv[0];
            xorer[1] = iv[1];
            
            for (Integer i = 0; i < ciphertext.length; i += 2) {
                int L = xorer[0];
                int R = xorer[1];
                sum = 0;
                for (Integer j = 0; j < 32; j++) {
                    sum += DELTA;
                    L += ((R << 4) + key[0]) ^ (R + sum) ^ ((R >> 5) + key[1]);
                    R += ((L << 4) + key[2]) ^ (L + sum) ^ ((L >> 5) + key[3]);                    
                }
                
                plaintext[i] = L ^ ciphertext[i];
                plaintext[i + 1] = R ^ ciphertext[i + 1];
                xorer[1] += 1;
            }            
        }
        return plaintext;
    }
    
}

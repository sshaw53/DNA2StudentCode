import static java.lang.Math.pow;
import static jdk.internal.org.jline.utils.Colors.h;

/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: SIERRA SHAW
 *</p>
 */

public class DNA {
    // Typically, a radix would be 256 if dealing with characters but since our alphabet is just A,C,T,G we only need
    // a radix of 4 to solve this, and p is typically a large prime number
    public static int RADIX = 4;
    public static int p = 9737333;

    public static int STRCount(String sequence, String STR) {
        int max = 0;
        int seq_len = sequence.length();
        int strlen = STR.length();

        // Assigning integer values for the STR and the beginning of the sequence to help create a constant-time check
        int num_STR = hash(STR, strlen, 0);
        int num_current = hash(sequence, strlen, 0);

        // Go through each character and look for every instance where the STR matches the sequence
        for (int i = 0; i < seq_len; i++) {
            // When we find a match, call the find() function to skip ahead and look for number of repeats
            if (num_STR == num_current) {
                int current_reps = find(i, sequence, num_STR, num_current, strlen, seq_len);
                // If we find a larger value, change the max index
                if (current_reps > max) {
                    max = current_reps;
                }
            }

            // Create the numerical version of the next chunk (cut off the max number, add the next number)
            if (i + strlen < seq_len) {
                num_current = rehash(num_current, sequence, strlen, i);
            }
        }
        return max;
    }

    public static int find(int idx, String seq, int num_STR, int n_current, int strlen, int seq_len) {
        int current = idx;
        int finds = 0;
        int num_current = n_current;

        // If we haven't gotten to the end of the String and the next characters align with the STR, continue to traverse
        // and increase finds by 1
        while (current + 2 * strlen < seq_len && num_current == num_STR) {
            current += strlen;
            finds += 1;

            // Now shift the characters to find the number value of the next STR-length chunk
            num_current = hash(seq, strlen, current);
        }

        // If not, it will immediately exit the loop and return the number of finds
        return finds;
    }

    public static int hash(String str, int len, int idx) {
        // Modifies a string to a unique number utilizing hash functions (Horner's method)
        int hashed = 0;
        for(int i = idx; i < idx + len; i++) {
            hashed = (RADIX * hashed + str.charAt(i)) % p;
        }
        return hashed;
    }

    public static int rehash(int toHash, String str, int len, int idx) {
        // Adds the next letter, using the Rabin-Karp fingerprinting algorithm
        // Need to subtract the first term, multiply by R, and add the next term
        int rehashed;
        rehashed = ((toHash + p) - str.charAt(idx) * (int)Math.pow(RADIX, len - 1) % p) % p;
        rehashed = ((rehashed * RADIX) + str.charAt(idx + len)) % p;
        return rehashed;
    }
}

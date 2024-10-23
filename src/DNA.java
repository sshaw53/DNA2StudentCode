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
    // a radix of 4 to solve this, and p is typically a large prime number but we don't need p in this instance because
    // the numbers are small enough that there won't be overlap
    public static int RADIX = 4;

    public static int STRCount(String sequence, String STR) {
        int max = 0;
        int seq_len = sequence.length();
        int strlen = STR.length();
        long power_to_add = (long)Math.pow(RADIX, strlen - 1);

        // Assigning integer values for the STR and the beginning of the sequence to help create a constant-time check
        long num_STR = hash(STR, strlen, 0);
        long num_current = hash(sequence, strlen, 0);

        // Go through each character and look for every instance where the STR matches the sequence
        for (int i = 0; i < seq_len; i++) {
            // When we find a match, call the find() function to skip ahead and look for number of repeats
            if (num_STR == num_current) {
                int found[] = find(i, sequence, num_STR, num_current, strlen, seq_len);
                int current_reps = found[0];
                // If we find a larger value, change the max index
                if (current_reps > max) {
                    max = current_reps;
                }
                if (found[1] + strlen >= seq_len) {
                    return max;
                }
                num_current = hash(sequence, strlen, found[1]);
                i = found[1] - 1;
            }
            // Create the numerical version of the next chunk (cut off the max number, add the next number)
            else if (i + strlen < seq_len) {
                num_current = rehash(num_current, sequence, strlen, i, power_to_add);
            }
        }
        return max;
    }

    // This function iterates through the sequence looking for the number of consecutive STRs in a row
    // Returns an array of size 2, with the first index representing the number of finds and the second index for the
    // next index to look at
    public static int[] find(int idx, String seq, long num_STR, long n_current, int strlen, int seq_len) {
        int current = idx;
        int[] finds = new int[2];
        long num_current = n_current;

        // If we haven't gotten to the end of the String and the next characters align with the STR, continue to traverse
        // and increase finds by 1
        while (num_current == num_STR) {
            finds[0] += 1;

            // Now shift the characters to find the number value of the next STR-length chunk
            if (current + 2 * strlen <= seq_len) {
                current += strlen;
                num_current = hash(seq, strlen, current);
            }
            else {
                finds[1] = current + 1;
                return finds;
            }
        }

        // If not, it will immediately exit the loop and return the number of finds
        // It should also return the next index to look at in the for-loop (the second letter of the last instance of the
        // STR)
        finds[1] = current - strlen + 1;
        return finds;
    }

    public static long hash(String str, int len, int idx) {
        // Modifies a string to a unique number utilizing hash functions (Horner's method)
        long hashed = 0;
        for(int i = idx; i < idx + len; i++) {
            hashed = (RADIX * hashed + str.charAt(i));
        }
        return hashed;
    }

    public static long rehash(long toHash, String str, int len, int idx, long power_to_add) {
        // Adds the next letter, using the Rabin-Karp fingerprinting algorithm
        // Need to subtract the first term, multiply by R, and add the next term
        long rehashed;
        rehashed = toHash - str.charAt(idx) * power_to_add;
        rehashed = (rehashed * RADIX) + str.charAt(idx + len);
        return rehashed;
    }
}

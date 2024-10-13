import static java.lang.Math.pow;

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
    public static int STRCount(String sequence, String STR) {
        int max = 0;
        int seq_len = sequence.length();
        int strlen = STR.length();
        // Assigning integer values for all different letters to help create a constant-time check
        int a = 1;
        int g = 2;
        int c = 3;
        int t = 4;
        int num_STR = 0;
        int num_current = 0;

        // Convert both the STR and the first chunk in the seq of length STR to a unique number value to then
        // compare in constant time
        for (int i = 0; i < strlen; i++) {
            if (Character.toUpperCase(STR.charAt(i)) == 'A') {
                num_STR += a;
            }
            else if (Character.toUpperCase(STR.charAt(i)) == 'G') {
                num_STR += g;
            }
            else if (Character.toUpperCase(STR.charAt(i)) == 'C') {
                num_STR += c;
            }
            else if (Character.toUpperCase(STR.charAt(i)) == 'T') {
                num_STR += t;
            }

            if (Character.toUpperCase(sequence.charAt(i)) == 'A') {
                num_current += a;
            }
            else if (Character.toUpperCase(sequence.charAt(i)) == 'G') {
                num_current += g;
            }
            else if (Character.toUpperCase(sequence.charAt(i)) == 'C') {
                num_current += c;
            }
            else if (Character.toUpperCase(sequence.charAt(i)) == 'T') {
                num_current += t;
            }
            if (i != strlen - 1) {
                num_STR *= 10;
                num_current *= 10;
            }
        }

        // Go through each character and find every instance of the first letter of the STR in the sequence
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
                num_current = num_current % (int) Math.pow(10, strlen - 1);
                num_current *= 10;
                if (sequence.charAt(i + strlen) == 'A') {
                    num_current += a;
                }
                else if (sequence.charAt(i + strlen) == 'G') {
                    num_current += g;
                }
                else if (sequence.charAt(i + strlen) == 'C') {
                    num_current += c;
                }
                else if (sequence.charAt(i + strlen) == 'T') {
                    num_current += t;
                }
            }
        }
        return max;
    }

    public static int find(int idx, String seq, int num_STR, int n_current, int strlen, int seq_len) {
        int current = idx;
        int finds = 0;
        // Assigning integer values for all different letters to help create a constant-time check
        int a = 1;
        int g = 2;
        int c = 3;
        int t = 4;
        int num_current = n_current;

        // If we haven't gotten to the end of the String and the next characters align with the STR, continue to traverse
        // and increase finds by 1
        while (current + strlen < seq_len && num_current == num_STR) {
            current += strlen;
            finds += 1;

            num_current = 0;
            // Now shift the characters to find the number value of the next STR-length chunk
            for (int i = 0; i < strlen; i++) {
                if (seq.charAt(i + current) == 'A') {
                    num_current += a;
                }
                else if (seq.charAt(i + current) == 'G') {
                    num_current += g;
                }
                else if (seq.charAt(i + current) == 'C') {
                    num_current += c;
                }
                else if (seq.charAt(i + current) == 'T') {
                    num_current += t;
                }
                if (i != strlen - 1) {
                    num_current *= 10;
                }
            }
        }

        // If not, it will immediately return false
        return finds;
    }

    /*
    public static int STRCount(String sequence, String STR) {
        char first = STR.charAt(0);
        int max = 0;

        // Go through each character and find every instance of the first letter of the STR in the sequence
        for (int i = 0; i < sequence.length(); i++) {
            // When we find it, call the find() function to look for how many in a row there are at that given start idx
            if (sequence.charAt(i) == first) {
                int current = find(i, sequence, STR);
                // If we find a larger value, change the max index
                if (current > max) {
                    max = current;
                }
            }
        }
        return max;
    }

    public static int find(int idx, String seq, String STR) {
        int current = idx;
        int finds = 0;
        // Assigning integer values for all different letters to help create a constant-time check
        int a = 1;
        int g = 2;
        int c = 3;
        int t = 4;
        int num_STR = 0;
        int num_current = 0;
        int strlen = STR.length();

        // Convert both the STR and the first chunk in the seq of length STR to a unique number value to then
        // compare in constant time
        for (int i = 0; i < strlen; i++) {
            if (STR.charAt(i) == 'a') {
                num_STR += a;
            }
            else if (STR.charAt(i) == 'g') {
                num_STR += g;
            }
            else if (STR.charAt(i) == 'c') {
                num_STR += c;
            }
            else if (STR.charAt(i) == 't') {
                num_STR += t;
            }

            if (seq.charAt(i + idx) == 'a') {
                num_current += a;
            }
            else if (seq.charAt(i + idx) == 'g') {
                num_current += g;
            }
            else if (seq.charAt(i + idx) == 'c') {
                num_current += c;
            }
            else if (seq.charAt(i + idx) == 't') {
                num_current += t;
            }
            if (i != strlen - 1) {
                num_STR *= 10;
                num_current *= 10;
            }
        }

        // If we haven't gotten to the end of the String and the next characters align with the STR, continue to traverse
        // and increase finds by 1
        while (current + STR.length() < seq.length() && num_current == num_STR) {
            current += STR.length();
            finds += 1;

            // Now shift the character based on
            // XXX
        }

        // If not, it will immediately return false
        return finds;
    }

    */
}

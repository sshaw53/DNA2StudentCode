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

    /**
     * TODO: Complete this function, STRCount(), to return longest consecutive run of STR in sequence.
     */
    public static int STRCount(String sequence, String STR) {
        char first = STR.charAt(0);
        int max = 0;

        // Go through each chatacter and find every instance of the first letter of the STR in the sequence
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
        // If we haven't gotten to the end of the String and the next characters align with the STR, continue to traverse
        // and increase finds by 1
        while (current + STR.length() < seq.length() && seq.substring(current, current + STR.length()).equals(STR)) {
            current += STR.length();
            finds += 1;
        }

        // If not, it will immediately return false
        return finds;
    }
}

package hw1cs3310_Khan;

/**
 * This class has static methods for linear and binary searches on 2-dimensional arrays
 */
class Search {

    /**
     * Searches in received 2-dimensional array using linear search
     * @param bags receives a 2-dimensional array
     * @param toFind receives the hw1cs3310_Khan.Item object to look for
     * @return if found, an array holding location (2-d) in first two indexes and current strength of the
     * found item in third; null otherwise
     */
    static int[] linear (Item[][] bags, Item toFind) {
        int[] toReturn = new int[3];
        for(int i = 0; i < bags.length; i++){
            for(int j = 0; j < 25; j++){
                if(bags[i][j] == null) {
                    System.out.println("A position in bags two-dimensional array is empty\n" +
                            "The program will therefore exit");
                    System.exit(3);
                }
                if(bags[i][j].equals(toFind)) {
                    toReturn[0] = i;
                    toReturn[1] = j;
                    toReturn[2] = bags[i][j].getCurrentStrength();
                    return toReturn;
                }
            }
        }
        return null;
    }

    /**
     * Searches in received 2-dimensional array using binary search
     * @param bags receives a 2-dimensional array
     * @param toFind receives the hw1cs3310_Khan.Item object to look for
     * @return if found, an array holding location (2-d) in first two indexes and current strength of the
     * found item in third; null otherwise
     */
    static int[] binary (Item[][] bags, Item toFind) {
        int[] toReturn = new int[3];
        int min, max, mid;
        for(int i = 0; i < bags.length; i++){
            min = 0;
            max = 24;
            while (min <= max){
                mid = min + (max - min)/2;
                if(bags[i][mid] == null) {
                    System.out.println("A position in bags two-dimensional array is empty\n" +
                            "The program will therefore exit");
                    System.exit(4);
                }
                if(bags[i][mid].equals(toFind)) {
                    toReturn[0] = i;
                    toReturn[1] = mid;
                    toReturn[2] = bags[i][mid].getCurrentStrength();
                    return toReturn;
                } else if (bags[i][mid].compareTo(toFind) < 0){
                    min = mid + 1;
                } else {
                    max = mid - 1;
                }
            }
        }
        return null;
    }

}

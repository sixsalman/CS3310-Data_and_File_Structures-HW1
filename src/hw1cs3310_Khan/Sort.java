package hw1cs3310_Khan;

/**
 * This class has multiple variations of static merge sort methods
 */
class Sort {

    /**
     * Divides received list completely (recursively) and gets its parts merged back in a sorted manner
     * @param toSort receives a hw1cs3310_Khan.DoublyLinkedList object
     * @return sorted version of the received list
     */
    static DoublyLinkedList mergeSort(DoublyLinkedList toSort) {
        DoublyLinkedList[] parts;
        int mid;
        if(toSort.size() == 1)
            return toSort;
        mid = toSort.size() / 2;
        parts = toSort.partAt(mid);
        parts[0] = mergeSort(parts[0]);
        parts[1] = mergeSort(parts[1]);
        return merge(parts[0], parts[1]);
    }

    /**
     * Merges two sorted DoublyLinkedLists into one
     * @param partOne receives a sorted hw1cs3310_Khan.DoublyLinkedList object
     * @param partTwo receives another sorted hw1cs3310_Khan.DoublyLinkedList object
     * @return a sorted hw1cs3310_Khan.DoublyLinkedList containing all elements from both parts received
     */
    private static DoublyLinkedList merge(DoublyLinkedList partOne, DoublyLinkedList partTwo) {
        DoublyLinkedList toReturn = new DoublyLinkedList();
        while(partOne.size() != 0 || partTwo.size() != 0) {
            if(partOne.size() == 0) {
                toReturn.add(partTwo.getNodeData(partTwo.getFirst()));
                partTwo.moveFirstOneForward();
            } else if (partTwo.size() == 0){
                toReturn.add(partOne.getNodeData(partOne.getFirst()));
                partOne.moveFirstOneForward();
            } else if(partOne.getNodeData(partOne.getFirst()).compareTo(partTwo.getNodeData(partTwo.getFirst())) > 0) {
                toReturn.add(partTwo.getNodeData(partTwo.getFirst()));
                partTwo.moveFirstOneForward();
            } else {
                toReturn.add(partOne.getNodeData(partOne.getFirst()));
                partOne.moveFirstOneForward();
            }
        }
        return toReturn;
    }

    /**
     * Divides received list completely (recursively) and gets its parts merged back in a sorted manner
     * @param toSort receives an array
     * @return sorted version of the received array
     */
    private static Item[] mergeSort(Item[] toSort) {
        Item[] partOne;
        Item[] partTwo;
        int mid;
        if(toSort.length == 1)
            return toSort;
        mid = toSort.length / 2;
        partOne = new Item[mid];
        for (int i = 0; i < mid; i++) {
            partOne[i] = toSort[i];
        }
        partTwo = new Item[toSort.length - mid];
        int j = 0;
        for (int i = mid ; i < toSort.length; i++) {
            partTwo[j] = toSort[i];
            j++;
        }
        partOne = mergeSort(partOne);
        partTwo = mergeSort(partTwo);
        return merge(partOne, partTwo);
    }

    /**
     * Merges two sorted arrays into one
     * @param partOne receives a sorted array
     * @param partTwo receives another sorted array
     * @return a sorted array containing all elements from both parts received
     */
    private static Item[] merge(Item[] partOne, Item[] partTwo) {
        Item[] toReturn = new Item[partOne.length + partTwo.length];
        int indexToRet = 0;
        int indexPOne = 0;
        int indexPTwo = 0;
        while(partOne.length != indexPOne || partTwo.length != indexPTwo) {
            if(partOne.length == indexPOne) {
                toReturn[indexToRet] = partTwo[indexPTwo];
                indexToRet++;
                indexPTwo++;
            } else if (partTwo.length == indexPTwo){
                toReturn[indexToRet] = partOne[indexPOne];
                indexToRet++;
                indexPOne++;
            } else if(partOne[indexPOne].compareTo(partTwo[indexPTwo]) > 0) {
                toReturn[indexToRet] = partTwo[indexPTwo];
                indexToRet++;
                indexPTwo++;
            } else {
                toReturn[indexToRet] = partOne[indexPOne];
                indexToRet++;
                indexPOne++;
            }
        }
        return toReturn;
    }

    /**
     * Merge sorts every index in the received 2-dimensional array
     * @param toSort receives a 2-dimensional array
     * @return sorted version of the received array
     */
    static Item[][] mergeSortBags(Item[][] toSort) {
        for (int i = 0; i < toSort.length; i++) {
            toSort[i] = mergeSort(toSort[i]);
        }
        return toSort;
    }

    /**
     * Gets sorted arrays (in a 2-d array) merged into a sorted hw1cs3310_Khan.DoublyLinkedList
     * @param toSort receives a 2-dimensional array
     * @return a hw1cs3310_Khan.DoublyLinkedList containing merge sorted version of received 2-d array
     */
    static DoublyLinkedList multiMergeSort(Item[][] toSort) {
        DoublyLinkedList toReturn = new DoublyLinkedList();
        Item[] sortedArr = multiMergeSortStep2(toSort);
        for(int i = 0; i < sortedArr.length; i++) {
            toReturn.add(sortedArr[i]);
        }
        return toReturn;
    }

    /**
     * Merge sorts the received 2-dimensional array into a 1-dimensional array
     * @param toSort receives a 2-dimensional array
     * @return sorted 1-dimensional version of the received array
     */
    private static Item[] multiMergeSortStep2(Item[][] toSort) {
        Item[][] partOne;
        Item[][] partTwo;
        int mid;
        if(toSort.length == 1)
            return toSort[0];
        mid = toSort.length / 2;
        partOne = new Item[mid][25];
        for (int i = 0; i < mid; i++) {
            for (int j = 0; j < 25; j++)
                partOne[i][j] = toSort[i][j];
        }
        partTwo = new Item[toSort.length - mid][25];
        int k = 0;
        for (int i = mid ; i < toSort.length; i++) {
            for (int j = 0; j < 25; j++) {
                partTwo[k][j] = toSort[i][j];
            }
            k++;
        }
        partOne[0] = multiMergeSortStep2(partOne);
        partTwo[0] = multiMergeSortStep2(partTwo);
        return mergeUniqueElements(partOne[0], partTwo[0]);
    }

    /**
     * Merges two sorted arrays into one, eliminating duplicates
     * @param partOne receives a sorted array
     * @param partTwo receives another sorted array
     * @return a sorted array containing all elements (without repetition) from both parts received
     */
    private static Item[] mergeUniqueElements(Item[] partOne, Item[] partTwo) {
        Item[] toReturn = new Item[partOne.length + partTwo.length];
        int indexToRet = 0;
        int indexPOne = 0;
        int indexPTwo = 0;
        boolean skip;
        int reduceArrSizeBy = 0;
        while(partOne.length != indexPOne || partTwo.length != indexPTwo) {
            skip = false;
            for(int i = 0; i < toReturn.length; i++) {
                if(toReturn[i] == null)
                    break;
                if(partTwo.length != indexPTwo && toReturn[i].fullyEquals(partTwo[indexPTwo])) {
                    indexPTwo++;
                    skip = true;
                    break;
                } else if (partOne.length != indexPOne && toReturn[i].fullyEquals(partOne[indexPOne])) {
                    indexPOne++;
                    skip = true;
                    break;
                }
            }
            if (skip) {
                reduceArrSizeBy++;
                continue;
            }
            if(partOne.length == indexPOne) {
                toReturn[indexToRet] = partTwo[indexPTwo];
                indexToRet++;
                indexPTwo++;
            } else if (partTwo.length == indexPTwo){
                toReturn[indexToRet] = partOne[indexPOne];
                indexToRet++;
                indexPOne++;
            } else if(partOne[indexPOne].compareTo(partTwo[indexPTwo]) > 0) {
                toReturn[indexToRet] = partTwo[indexPTwo];
                indexToRet++;
                indexPTwo++;
            } else {
                toReturn[indexToRet] = partOne[indexPOne];
                indexToRet++;
                indexPOne++;
            }
        }
        if(reduceArrSizeBy > 0) {
            Item[] adjustedArr = new Item[toReturn.length - reduceArrSizeBy];
            for (int i = 0; i < adjustedArr.length; i++) {
                adjustedArr[i] = toReturn[i];
            }
            return adjustedArr;
        }
        return toReturn;
    }

}

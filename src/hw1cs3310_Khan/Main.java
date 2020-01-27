package hw1cs3310_Khan;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.IOException;

/**
 * This program reads details of items (separated by commas) from an input file, stores the items in a list and then
 * in bags of capacity 25, searches them for random items (linearly) - (merge) Sorts items within bags, then (multi)merges 
 * bags into a sorted list, also (merge) sorts the original list, compares times for both sorts - Searches (sorted) bags 
 * again (binary) and compares times for both searches.
 *
 * @author M. Salman Khan
 */
public class Main {

    /**
     * Main method takes inputs, calls other methods to accomplish certain tasks and outputs
     * @param args not used
     * @throws IOException can be thrown if input file is missing in directory
     */
    public static void main(String[] args) throws IOException {

        Item[][] bags;
        Scanner items = new Scanner(new File("items.txt"));
        items.nextLine();
        DoublyLinkedList itemsLinkedList = new DoublyLinkedList();
        DoublyLinkedList tempListIterator = new DoublyLinkedList();
        String[] itemData;
        Random rand = new Random();
        Item toSearch;
        int[] searchRes;
        DoublyLinkedList bagsMultiMergeSorted;
        long stTime, endTime, timeSum, timeMerge, timeMultiMerge;
        PrintWriter smpleOut;
        String toPrint;

        // STEP # 8
        for (int n = 1; n <= 10000; n *= 10) {
            toPrint = String.format("Sample Output File (Case: n = %d).txt", n);
            smpleOut = new PrintWriter(new FileWriter(toPrint));
            toPrint = String.format("n=%d\n", n);
            System.out.print(toPrint);
            smpleOut.print(toPrint);
            bags = new Item[n][25];

            // STEP # 1
            while(items.hasNext()) {
                itemData = items.nextLine().split(",");
                itemsLinkedList.add(new Item(itemData[0], Integer.parseInt(itemData[1]), Integer.parseInt(itemData[2]),
                        itemData[3]));
            }

            // STEP # 2
            tempListIterator.add(itemsLinkedList.getFirst());
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < 25; j++) {
                    if(tempListIterator.size() == 0)
                        tempListIterator.add(itemsLinkedList.getFirst());
                    bags[i][j] = tempListIterator.getNodeData(tempListIterator.getFirst());
                    bags[i][j].randAssignCurrStrength();
                    tempListIterator.moveFirstOneForward();
                }
            }
            if (n <= 10) {
                toPrint = "Bags before sorting:\n";
                System.out.print(toPrint);
                smpleOut.print(toPrint);
                for (int i = 0; i < bags.length; i++) {
                    toPrint = String.format("Bag %d:\n", (i + 1));
                    System.out.print(toPrint);
                    smpleOut.print(toPrint);
                    for (int j = 0; j < 5; j++) {
                        toPrint = String.format("\t\t%s\n", bags[i][j]);
                        System.out.print(toPrint);
                        smpleOut.print(toPrint);
                    }
                    toPrint = "\t\t...\n";
                    System.out.print(toPrint);
                    smpleOut.print(toPrint);
                }
                toPrint = "\n";
                System.out.print(toPrint);
                smpleOut.print(toPrint);
            } else {
                toPrint = "Before sorting:\n";
                System.out.print(toPrint);
                smpleOut.print(toPrint);
            }

            // STEP # 3
            toSearch = itemsLinkedList.getIndexData(rand.nextInt(itemsLinkedList.size()));
            toPrint = String.format("Searching for %s %s...\n", toSearch.getRarity(), toSearch.getName());
            System.out.print(toPrint);
            smpleOut.print(toPrint);

            stTime = System.nanoTime();
            searchRes = Search.linear(bags, toSearch);
            endTime = System.nanoTime();


            if (searchRes != null) {
                toPrint = String.format("Found in bag %d, slot %d. Strength: %d.\n", (searchRes[0] + 1),
                        (searchRes[1] + 1), searchRes[2]);
                System.out.print(toPrint);
                smpleOut.print(toPrint);
            } else {
                toPrint = "Not found.\n";
                System.out.print(toPrint);
                smpleOut.print(toPrint);
            }

            toPrint = String.format("Single search time: %d nanoseconds.\n", (endTime-stTime));
            System.out.print(toPrint);
            smpleOut.print(toPrint);

            // STEP # 4
            timeSum = 0;
            for (int i = 0; i < 100; i++) {
                toSearch = itemsLinkedList.getIndexData(rand.nextInt(itemsLinkedList.size()));
                stTime = System.nanoTime();
                Search.linear(bags, toSearch);
                endTime = System.nanoTime();
                timeSum += (endTime - stTime);
            }
            toPrint = String.format("Average search time: %d nanoseconds.\n", (timeSum / 100));
            System.out.print(toPrint);
            smpleOut.print(toPrint);

            // STEP # 5
            stTime = System.nanoTime();
            itemsLinkedList = Sort.mergeSort(itemsLinkedList); // sorts entire inventory
                                                               // see Item class's compareTo method and
                                                               // first two methods in Sort class for further details
                                                               // about sorting same name items by current strength
            endTime = System.nanoTime();
            timeMerge = endTime - stTime;

            toPrint = String.format("Whole inventory sort time: %d nanoseconds.\n", timeMerge);
            System.out.print(toPrint);
            smpleOut.print(toPrint);

            // STEP # 6
            stTime = System.nanoTime();
            bags = Sort.mergeSortBags(bags);
            bagsMultiMergeSorted = Sort.multiMergeSort(bags);
            endTime = System.nanoTime();
            timeMultiMerge = endTime - stTime;

            toPrint = String.format("Multi-merge sort time: %d nanoseconds.\n", timeMultiMerge);
            System.out.print(toPrint);
            smpleOut.print(toPrint);

            if((timeMultiMerge - timeMerge) > 0) {
                toPrint = String.format("Multi-merge sort took %d nanoseconds more.\n", (timeMultiMerge - timeMerge));
                System.out.print(toPrint);
                smpleOut.print(toPrint);
            } else {
                toPrint = String.format("Whole inventory sort took %d nanoseconds more.\n",
                        (timeMerge - timeMultiMerge));
                System.out.print(toPrint);
                smpleOut.print(toPrint);
            }

            if (n <= 10) {
                toPrint = "Bags after sorting:\n";
                System.out.print(toPrint);
                smpleOut.print(toPrint);
                for (int i = 0; i < bags.length; i++) {
                    toPrint = String.format("Bag %d:\n", (i + 1));
                    System.out.print(toPrint);
                    smpleOut.print(toPrint);
                    for (int j = 0; j < 5; j++) {
                        toPrint = String.format("\t\t%s\n", bags[i][j]);
                        System.out.print(toPrint);
                        smpleOut.print(toPrint);
                    }
                    toPrint = String.format("\t\t...\n");
                    System.out.print(toPrint);
                    smpleOut.print(toPrint);
                }
                toPrint = "\n";
                System.out.print(toPrint);
                smpleOut.print(toPrint);
            } else {
                toPrint = "After sorting:\n";
                System.out.print(toPrint);
                smpleOut.print(toPrint);
            }

            // STEP # 7 - repetition of Steps 3 and 4 with replacement of linearSearch with binarySearch
            toSearch = itemsLinkedList.getIndexData(rand.nextInt(itemsLinkedList.size()));
            toPrint = String.format("Searching for %s %s...\n", toSearch.getRarity(), toSearch.getName());
            System.out.print(toPrint);
            smpleOut.print(toPrint);

            stTime = System.nanoTime();
            searchRes = Search.binary(bags, toSearch);
            endTime = System.nanoTime();


            if (searchRes != null) {
                toPrint = String.format("Found in bag %d, slot %d. Strength: %d.\n", (searchRes[0] + 1),
                        (searchRes[1] + 1), searchRes[2]);
                System.out.print(toPrint);
                smpleOut.print(toPrint);
            } else {
                toPrint = "Not found.";
                System.out.print(toPrint);
                smpleOut.print(toPrint);
            }

            toPrint = String.format("Single search time: %d nanoseconds.\n", (endTime-stTime));
            System.out.print(toPrint);
            smpleOut.print(toPrint);


            timeSum = 0;
            for (int i = 0; i < 100; i++) {
                toSearch = itemsLinkedList.getIndexData(rand.nextInt(itemsLinkedList.size()));
                stTime = System.nanoTime();
                Search.binary(bags, toSearch);
                endTime = System.nanoTime();
                timeSum += (endTime - stTime);
            }
            toPrint = String.format("Average search time: %d nanoseconds.\n\n", (timeSum / 100));
            System.out.print(toPrint);
            smpleOut.print(toPrint);

            smpleOut.close();
        }

    }

}

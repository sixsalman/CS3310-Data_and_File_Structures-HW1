package hw1cs3310_Khan;

/**
 * Objects of this class stores first and last nodes of a series of nodes which are (2-way) linked with each other
 */
class DoublyLinkedList {

    /**
     * A node holds an hw1cs3310_Khan.Item object along with next and previous nodes.
     */
    private class Node {
        Item data;
        Node next;
        Node prev;

        /**
         * Creates a node object storing received parameters
         * @param data receives an hw1cs3310_Khan.Item object
         * @param next receives the node which is to be linked as next node
         * @param prev receives the node which is to be linked as previous node
         */
        Node(Item data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

    }

    private Node first;
    private Node last;
    private Node tempNodeHolder;

    /**
     * Adds the received data at end of the list
     * @param data receives an hw1cs3310_Khan.Item object
     */
    void add(Item data) {
        if(first == null) {
            first = new Node (data, null, null);
            last = first;
        } else {
            last = new Node(data, null, last);
            last.prev.next = last;
        }
    }

    /**
     * Adds the received data at end of the list only if it does not match any existing member of list
     * @param data receives an hw1cs3310_Khan.Item object
     */
    void addIfUnique(Item data) {
        tempNodeHolder = first;
        while (tempNodeHolder != null) {
            if((tempNodeHolder.data).fullyEquals(data))
                return;
            tempNodeHolder = tempNodeHolder.next;
        }
        add(data);
    }

    /**
     * Adds the received node at end of the list
     * @param node receives a Node object
     */
    void add(Node node) {
        if(first == null) {
            first = node;
            last = node;
        } else {
            last.next = node;
            last.next.prev = last;
        }
        while(last.next != null) {
            last = last.next;
        }
    }

//    void addToIndex(int index, Item data) {
//        if (size() <= index || index < 0) {
//            System.out.println("Addition to an out of bound index has been attempted in Linked List\n" +
//                    "The program will therefore exit");
//            System.exit(1);
//        }
//        tempNodeHolder = first;
//        for (int i = 0; i < index - 1; i++){
//            tempNodeHolder = tempNodeHolder.next;
//        }
//        tempNodeHolder.next = new Node(data, tempNodeHolder.next, tempNodeHolder);
//        tempNodeHolder.next.next.prev = tempNodeHolder.next;
//    }

    /**
     * Returns the data stored at received index
     * @param index receives the index whose data is to be returned
     * @return data stored in the received index
     */
    Item getIndexData(int index) {
        if (size() <= index || index < 0) {
            System.out.println("Extraction of an out of bound index has been attempted in Linked List\n" +
                    "The program will therefore exit");
            System.exit(5);
        }
        tempNodeHolder = first;
        for (int i = 0; i < index; i++){
            tempNodeHolder = tempNodeHolder.next;
        }
        return tempNodeHolder.data;
    }

    /**
     * Partitions the linked list at the index received
     * @param index receives the index from which the second partition is to start
     * @return an array (length 2) holding both partitions
     */
    DoublyLinkedList[] partAt(int index) {
        if (size() <= index || index < 0) {
            System.out.println("Partition at an out of bound index has been attempted in Linked List\n" +
                    "The program will therefore exit");
            System.exit(2);
        }
        DoublyLinkedList[] toReturn = new DoublyLinkedList[2];
        toReturn[0] = new DoublyLinkedList();
        toReturn[0].add(first);
        tempNodeHolder = first;
        for (int i = 1; i < index; i++) {
            tempNodeHolder = tempNodeHolder.next;
        }
        toReturn[1] = new DoublyLinkedList();
        toReturn[1].add(tempNodeHolder.next);
        tempNodeHolder.next = null;
        toReturn[1].first.prev = null;
        return toReturn;
    }

    /**
     * Calculates and returns size of the list
     * @return size of the list
     */
    int size() {
        if (first == null)
            return 0;
        tempNodeHolder = first;
        int size = 1;
        while(tempNodeHolder.next != null) {
            size++;
            tempNodeHolder = tempNodeHolder.next;
        }
        return size;
    }

    /**
     * Returns first node in list
     * @return first node
     */
    Node getFirst() {
        return first;
    }

    /**
     * Returns data contained within a received node
     * @param node receives a Node object
     * @return data contained in the node received
     */
    Item getNodeData(Node node) {
        return node.data;
    }

    /**
     * Makes first reference the node next to it in list
     */
    void moveFirstOneForward() {
        if(size() > 1) {
            first = first.next;
        } else {
            first = null;
            last = null;
        }
    }

}

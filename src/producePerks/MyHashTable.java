/**
 * This is where you will implement your hash table.
 */
package producePerks;

/**
 * @author Katie Timmerman
 * @author < your name >
 *
 * Course: Data Structures and Algorithms Semester:
 */
public class MyHashTable {

    private final int HASH_TABLE_SIZE; //Capacity of the table
    private Record[] hashTable; // the array of records
    private int currentSize; //Number of values in the table

    public MyHashTable(int size) {
        this.HASH_TABLE_SIZE = size;
        hashTable = new Record[HASH_TABLE_SIZE];
        currentSize = 0;
        for (int i = 0; i < HASH_TABLE_SIZE; i++) {
            Record r = new Record();
            hashTable[i] = r;
        }
    }

    /**
     * This is a private method only to be used internally. It returns the index
     * where the record with the key is stored in the table. It returns -1 if
     * the key is not found in the table.
     *
     * @param key
     * @return
     */
    private int indexOf(int key) {
        int homeIndex = key % HASH_TABLE_SIZE; //the home index
        int original = homeIndex; //making a new variable that we can increment if we need to probe
        if (hashTable[homeIndex].isNormal() && hashTable[homeIndex].getKey() == key){ //if the key is at the home index
            return homeIndex; //return the home index, because the key is there
        }
        while (hashTable[original].isNormal() || hashTable[original].isTombstone()) { //while we are not at an empty spot in the hash table (key isn't in the table if we arrive at a spot that's always been empty)       
            original = (original + 1) % this.HASH_TABLE_SIZE; //move to next spot in the hash table
            if (hashTable[original].isNormal() && hashTable[original].getKey() == key) { //if they key is at the current index (original)
                return original; //return our current index, which is original
            }
            else{ //check to see if we have looped through the entire hash table; if so, they key is not in the hash table
                if (original == homeIndex) { //if we are back at the home index (where we began searching from)
                    return -1; //the key is not in the hash table
                }
            }
        }
        return -1; //if we have reached a spot in the hash table that's always been empty, the key is not in the hash table
    }

    /*Finds an element with a certain key and returns the associated Customer*/
    public Customer find(int key) {
        int index = indexOf(key); //call indexOf so we can see if the key is in the list in an O(1) operation
        if (index == -1 || hashTable[index].isTombstone()){ //if the key is not in the list, we return null
            return null; 
        }
        return hashTable[index].getValue(); //if the key is in the list, return the Customer at that location
    }

    /*Inserts the key/value into the hashtable*/
    public boolean insert(int key, Customer value) {
        if (indexOf(key) != -1 || currentSize == HASH_TABLE_SIZE){ //key is already in hash table or the table is full
            return false; //insert cannot be executed because the inputted key is already in the table
        }
        else{ //we can insert the inputted key
            boolean foundIt = false; //flag that tells us when we have inserted the new key (and can stop probing)
            Record temp = new Record(key, value); //creating a new Record that we can insert into the hash table that has the given key and value
            int homeIndex = key % HASH_TABLE_SIZE; //our home index from our hash function
            int original = homeIndex; //making a new variable that we can increment if we need to probe
            if (hashTable[homeIndex].isEmpty() || hashTable[homeIndex].isTombstone()){ //if we are at a location in the hash table that is not currently occupied
                hashTable[homeIndex] = temp; //insert the new Record at the home index in the hash table
                foundIt = true; //we have inserted the new Record; we don't need to keep probing
            }
            while (!foundIt){ //if the home index is occupied, we need to repeatedly probe to see if we can find a spot where the insert can be executed
                original = (original + 1) % this.HASH_TABLE_SIZE; //move to the next spot in the hash table
                if (hashTable[original].isEmpty() || hashTable[original].isTombstone()){ //check to see if we are at a location that is not currently occupied
                    hashTable[original] = temp; //insert the new Record at our current location in the hash table
                    foundIt = true; //we have inserted the new Record; we don't need to keep probing
                }
            }
            currentSize++; //increment the current size of the hash table by 1 because we just inserted a new element
            return true; //we have completed the insert
        }
    }

    //Kills a table key and returns the associated value
    public Customer remove(int key) {
        int index = indexOf(key); //call indexOf so we can see if the key is in the list in an O(1) operation
        if (index == -1){ //if the key is not in the list, we return null
            return null;
        }
        else{ //the inputted key is in the list, and we need to remove it
            Customer temp = hashTable[index].getValue(); //temp is the Customer object that we need to remove
            hashTable[index].deleteRecord(); //delete the Record at hashTable[index] (so it becomes a tombstone)
            return temp; //return the Customer object we removed to the user
        }
    }
       
    /**
     * returns a string representation of the hash table
     * @return 
     */
    public String toString() {
        String table = "";
        for (int i = 0; i < this.HASH_TABLE_SIZE; i++) {
            table += i + ". " + hashTable[i].toString() + "\n";
        }
        return table;
    }

}

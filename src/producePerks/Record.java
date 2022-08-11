/** 
 * Each record holds an integer key and a Customer.
 * 
 * The only other class that interacts with the record class is
 * MyHashTable.java
 */
 
package producePerks;

/**
 * @author Jake Heyser
 */


// There are three types of records in a closed hash:
// Normal records, empty records, and tombstones
enum RecordType { normalRecord, emptyRecord, tombstone };


public class Record{

    private int key;
    private Customer value;
    private RecordType type;

    public Record() {
        key = 0;
        type = RecordType.emptyRecord;
    }

    public Record(int key, Customer c) {
        this.key = key;
        this.value = c;
        type = RecordType.normalRecord;
    }

    // Convert a record to a tombstone
    public void deleteRecord() {
        type = RecordType.tombstone;
    }

    // Get the integer key of a record
    public int getKey() {
        return key;
    }

    // Get the value of a record
    public Customer getValue() {
        return value;
    }

    // Check if a record is empty
    public boolean isEmpty() {
        return (type == RecordType.emptyRecord);
    }

    // Check if a record is a normal record
    public boolean isNormal() {
        return (type == RecordType.normalRecord);
    }

    // Check if a record is a tombstone
    public boolean isTombstone() {
        return (type == RecordType.tombstone);
    }

    // Overload the toString operator for printing records
    @Override
    public String toString() {
        if (isTombstone()) {
            return "<<Tombstone>>";
        } else if (isEmpty()) {
            return "<<Empty>>";
        } else {
            return "Key: " + key + ", Value: " + value;
        }
    }

}

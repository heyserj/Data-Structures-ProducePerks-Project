/**
 * This is the driver class.
 * Note that it does NOT interact with the Record class.
 *
 * MyHashTable.java is the creation of a Hash Table.
 * Record.java is a record object that is stored in the hash table.
 * Transaction.java is a storage object that is formed based on one line of the data file.
 * Customer.java is a storage object that represents one person who uses produce perk.
 *
 * Links between classes
 * The hash table has an array of Records.
 * Each Record holds a (key, value) pair where the key is the SNAP-ID and the value is the Customer.
 * Each Customer has two ArrayLists (dynamic arrays) to hold their transactions: distributed and redeemed.
 */
package producePerks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Jake Heyser
 */
public class ProducePerksDriver {

    private static ArrayList<Integer> customer_keys;
    private static MyHashTable table;

    public static void main(String[] args) throws Exception {
        debuggingFunctions();
        loadData();
        completeAnalysis();

    }

    public static void debuggingFunctions() {
        MyHashTable myTable = new MyHashTable(7);
        ArrayList<Integer> addedKeys = new ArrayList<Integer>();
        System.out.println("TESTING ADDING ELEMENTS");
        for (int i = 0; i < 5; i++) {
            int key_value = (int) (Math.random() * 20);
            if (myTable.insert(key_value, new Customer(key_value))) {
                addedKeys.add(key_value);
            } else {
                i--; //Don't count the insert if key already in table
            }
        }
        System.out.println(myTable);

        System.out.println("\nTESTING FINDING ELEMENTS");
        for (int i = 0; i < addedKeys.size(); i += 2) {
            int key = addedKeys.get(i);
            System.out.println("The value " + key + " found: " + myTable.find(key));
        }
        System.out.println(myTable);

        System.out.println("\nTESTING REMOVING ELEMENTS");
        for (int i = 0; i < addedKeys.size(); i += 2) {
            int key = addedKeys.get(i);
            System.out.println("The value " + key + " removed: " + myTable.remove(key));
        }
        System.out.println(myTable);

        System.out.println("\nTESTING FINDING REMOVED ELEMENTS");
        for (int i = 0; i < addedKeys.size(); i += 2) {
            int key = addedKeys.get(i);
            System.out.println("The value " + key + " found: " + myTable.find(key));
        }
        System.out.println(myTable);

        System.out.println("\nTESTING FINDING VALID ELEMENTS AFTER REMOVAL");
        for (int i = 1; i < addedKeys.size(); i += 2) {
            int key = addedKeys.get(i);
            System.out.println("The value " + key + " found: " + myTable.find(key));
        }
        System.out.println(myTable);

    }

    public static void loadData() throws Exception {
        customer_keys = getCustomerIds();
        table = new MyHashTable(12007);

        //Create and Add Customers
        for (int i = 0; i < customer_keys.size(); i++) {
            int key = customer_keys.get(i);
            table.insert(key, new Customer(key));
        }

        //Read in transactions
        readInTransactions(2019, 1);
        readInTransactions(2020, 1);
        readInTransactions(2019, 2);
        readInTransactions(2020, 2);

        //System.out.println(table); //FOR DEBUGGING PURPOSES ONLY
    }

    private static ArrayList<Integer> getCustomerIds() {
        ArrayList<Integer> keys = new ArrayList();

        try {
            File file_ids = new File("Customer Ids.csv");
            Scanner in = new Scanner(file_ids);

            while (in.hasNext()) {
                keys.add(in.nextInt());
            }

            in.close();

        } catch (FileNotFoundException ex) {
            System.err.println("File Customer Ids.csv not found");
            System.exit(0);
        }

        return keys;

    }

    /**
     * This method reads in transactions from the file <Type> <year>
     * transactions.csv Each line of the file is a transaction. See the
     * transaction class for details of how that line should be organized. The
     * transaction is added to the corresponding Customer in the class Hash
     * Table table.
     *
     * @param year year of the file to be uploaded.
     * @param type pass 1 for distribution or 2 for redeemed
     */
    private static void readInTransactions(int year, int type) throws Exception {
        String fileName = "Distributed " + year + " transactions.csv";
        if (type == 2) {
            fileName = "Redeemed " + year + " transactions.csv";
        } else if (type != 1) {
            throw new Exception("type must be 1 (distributed) or 2 (redeemed)");
        }

        try {
            File file = new File(fileName);
            Scanner in = new Scanner(file);

            in.nextLine(); //remove headers

            while (in.hasNext()) {
                String line = in.nextLine();
                Transaction t = new Transaction(line);
                int id = t.getId();

                Customer c = table.find(id);
                if (c == null) {
                    System.err.println("Customer " + id + " was not located");
                } else if (type == 1) {
                    c.addDistributed(t);
                } else if (type == 2) {
                    c.addRedeemed(t);
                } // end else if
            } // end while in.hasNext

            in.close();

        } catch (FileNotFoundException ex) {
            System.err.println("File " + fileName + " not found.");
        }
    }

    /**
     * This method represents the code that answers question 3. It looks at two types of transactions:
     * those that used one coupon and those that used 2+ coupons. It prints the average spent per redeemed
     * transaction and the percentage spent per redeemed transaction for the following categories: produce 
     * bought, other bought and total. The output is printed to a csv file.
     * 
     * @throws FileNotFoundException 
     */
    private static void completeAnalysis() throws FileNotFoundException {
        double OCProdBought = 0; //the total amount of money spent on produce for customers who used one coupon
        double OCOtherBought = 0; //the total amount of money spent on "other" items for customers who used one coupon
        double OCTotalBought = 0; //the total amount of money spent for customers who used one coupon
        double OCProdPercent = 0; //the percentage spent on produce for customers who used one coupon
        double OCOtherPercent = 0; //the percentage spent on "other" items for customers who used one coupon
        double OCTotalPercent = 0; //the total percentage of money spent on produce and "other" items for customers who used one coupon (should be 100)
        double ProdBought = 0; //the total amount of money spent on produce for customers who used 2+ coupons
        double OtherBought = 0; //the total amount of money spent on "other" items for customers who used 2+ coupons
        double TotalBought = 0; //the total amount of money spent for customers who used 2+ coupons
        double ProdPercent = 0; //the percentage spent on produce for customers who used 2+ coupons
        double OtherPercent = 0; //the percentage spent on "other" items for customers who used 2+ coupons
        double TotalPercent = 0; //the total percentage of money spent on produce and "other" items for customers who used 2+ coupons (should be 100)

        int OCTransactions = 0; //running index for the number of transactions where customer used only one coupon
        int Transactions = 0; //running index for the number of transactions where the customer used 2+ coupons
        
        for (int i = 0; i < customer_keys.size(); i++) { //looping through every customer key so we can access all of the customers
            int temp = customer_keys.get(i); //temp = current customer key
            Customer c = table.find(temp); //c = the customer that responds to the key we just found
            ArrayList<Transaction> list = c.getRedeemed(); //creating an arrayList that holds all the transactions for that customer
            for (int j = 0; j < list.size(); j++) { //looping through all the transactions for the customer
                Transaction t = list.get(j); //getting the transaction at index j in list
                if (t.getCountOfCouponsIssued() == 1) { //checking to see if the customer used only one coupon
                    OCProdBought = OCProdBought + t.getTotalSpentOnFruitAndVegatables(); //updating the total amount that customers who used one coupon spent on produce
                    OCOtherBought = OCOtherBought + t.getTotalSpentOnOtherItems(); //updating the total amount that customers who used only one coupon spent on "other" items
                    OCTotalBought = OCOtherBought + OCProdBought; //updating the total amount that customers who used only one coupon spent on produce and "other" items
                    OCTransactions++; //incrementing the number of transactions where customer used only one coupon
                } else { //the customer used 2+ coupons
                    ProdBought = ProdBought + t.getTotalSpentOnFruitAndVegatables(); //updating the total amount that customers who used 2+ coupons spent on produce
                    OtherBought = OtherBought + t.getTotalSpentOnOtherItems(); //updating the total amount that customers who used 2+ coupons spent on "other" items
                    TotalBought = OtherBought + ProdBought; //updating the total amount that customers who used 2+ coupons spent on produce and "other" items
                    Transactions++; //incrementing the number of transactions where customer used 2+ coupons
                }
            }
        }
        OCProdPercent = (OCProdBought / OCTotalBought) * 100; //calculating the percentage of total money that customers who used only one coupon spent on produce
        OCOtherPercent = (OCOtherBought / OCTotalBought) * 100; //calculating the percentage of total money that customers who used only one coupon spent on "other" items
        OCTotalPercent = OCProdPercent + OCOtherPercent; //calculating the percentage of total money that customers who used only one coupon spent on produce and "other" items
        ProdPercent = (ProdBought / TotalBought) * 100; //calculating the percentage of total money that customers who used 2+ coupons spent on produce
        OtherPercent = (OtherBought / TotalBought) * 100; //calculating the percentage of total money that customers who used 2+ coupons spent on "other" items
        TotalPercent = ProdPercent + OtherPercent; //calculating the percentage of total money that customers who used 2+ coupons spent on produce and "other" items
        
        PrintWriter out = new PrintWriter("output.csv"); //creating a PrintWriter object to help us output our results to a csv file
        out.println("Group, One coupon average, one coupon percent, 2+ coupons average, 2+ coupons percent"); //the model for how we will print the different results we found
        out.println("Produce bought, " + OCProdBought / OCTransactions + ", " + OCProdPercent + ", " + 
                ProdBought / Transactions + ", " + ProdPercent); //printing our results for what customers spent on produce
        out.println("Other bought, " + OCOtherBought / OCTransactions + ", " + OCOtherPercent + ", " +
                OtherBought / Transactions + ", " + OtherPercent); //printing our results for what customers spent on "other" items
        out.println("Total bought, " + (OCTotalBought / OCTransactions) + ", " + OCTotalPercent + ", " +
                TotalBought / Transactions + ", " + TotalPercent); //printing our results for what customers spent on produce and "other" items
        out.close(); //closing the stream and releasing the system resources associated with it
    }
}

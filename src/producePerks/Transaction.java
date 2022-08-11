/**
 * The transaction will be parsed from a line following this formula
 * Store,Date,Time,Term,Trans,SNAP_ID,Total Spent with SNAP,Total Spent on Fruit & Vegatables,
 *               Total Spent on Other Items,Value of Coupons Issued,Count of Coupons Issued,CASH,
 *               CHECKS,EBT,EBT-OTHER,WIC,CREDIT CARDS,DEBIT CARDS,OTHER TENDERS
 * 
 */
package producePerks;

import java.time.LocalDateTime;

/**
 *
 * @author timmermank1
 */
public class Transaction {

    private static final int INDEX_STORE = 0;
    private static final int INDEX_DATE = 1;
    private static final int INDEX_TIME = 2;
    private static final int INDEX_TERM = 3;
    private static final int INDEX_TRANS = 4;
    private static final int INDEX_SNAP_ID = 5;
    private static final int INDEX_TOTAL_SPENT_WITH_SNAP = 6;
    private static final int INDEX_TOTAL_SPENT_ON_FRUITS_AND_VEGATABLES = 7;
    private static final int INDEX_TOTAL_SPENT_ON_OTHER_ITEMS = 8;
    private static final int INDEX_VALUE_OF_COUPONS_ISSUED = 9;
    private static final int INDEX_COUNT_OF_COUPONS_ISSUED = 10;
    private static final int INDEX_CASH = 11;
    private static final int INDEX_CHECKS = 12;
    private static final int INDEX_EBT = 13;
    private static final int INDEX_EBT_OTHER = 14;
    private static final int INDEX_WIC = 15;
    private static final int INDEX_CREDIT_CARDS = 16;
    private static final int INDEX_DEBIT_CARDS = 17;
    private static final int INDEX_OTHER_TENDERS = 18;

    private int store;
    private LocalDateTime date;
    private int term;
    private int trans;
    private int id;
    private double totalSpentWithSNAP;
    private double totalSpentOnFruitAndVegatables;
    private double totalSpentOnOtherItems;
    private double valueOfCouponsIssued;
    private int countOfCouponsIssued;
    private double cash;
    private double checks;
    private double ebt;
    private double ebtOther;
    private double wic;
    private double creditCards;
    private double debitCards;
    private double otherTenders;

    public Transaction(String recordLine) {
        String[] line = recordLine.split(",");
        store = Integer.parseInt(line[INDEX_STORE]);        
        String d = line[INDEX_DATE];
        d = d.substring(0, 4) + "-"+d.substring(4,6) + "-"+d.substring(6); // make 20190101 2019-01-01
        String t = line[INDEX_TIME]; 
        if(t.length() == 5){
            t = "0" + t;
        }
        t = t.substring(0,2) + ":" + t.substring(2,4) + ":" + t.substring(4);// make 130001 13:00:01
        d = d + "T" + t; // to make 2007-12-03T10:15:30
        date = LocalDateTime.parse(d); 
        term = Integer.parseInt(line[INDEX_TERM]);
        trans = Integer.parseInt(line[INDEX_TRANS]);
        id = Integer.parseInt(line[INDEX_SNAP_ID]);
        totalSpentWithSNAP = Double.parseDouble(line[INDEX_TOTAL_SPENT_WITH_SNAP]);
        totalSpentOnFruitAndVegatables = Double.parseDouble(line[INDEX_TOTAL_SPENT_ON_FRUITS_AND_VEGATABLES]);
        totalSpentOnOtherItems = Double.parseDouble(line[INDEX_TOTAL_SPENT_ON_OTHER_ITEMS]);
        valueOfCouponsIssued = Double.parseDouble(line[INDEX_VALUE_OF_COUPONS_ISSUED]);
        countOfCouponsIssued = Integer.parseInt(line[INDEX_COUNT_OF_COUPONS_ISSUED]);
        cash = Double.parseDouble(line[INDEX_CASH]);
        checks = Double.parseDouble(line[INDEX_CHECKS]);
        ebt = Double.parseDouble(line[INDEX_EBT]);
        ebtOther = Double.parseDouble(line[INDEX_EBT_OTHER]);
        wic = Double.parseDouble(line[INDEX_WIC]);
        creditCards = Double.parseDouble(line[INDEX_CREDIT_CARDS]);
        debitCards = Double.parseDouble(line[INDEX_DEBIT_CARDS]);
        otherTenders = Double.parseDouble(line[INDEX_OTHER_TENDERS]);
    }

    /**
     * @return the store
     */
    public int getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(int store) {
        this.store = store;
    }

    /**
     * @return the date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * @return the term
     */
    public int getTerm() {
        return term;
    }

    /**
     * @param term the term to set
     */
    public void setTerm(int term) {
        this.term = term;
    }

    /**
     * @return the trans
     */
    public int getTrans() {
        return trans;
    }

    /**
     * @param trans the trans to set
     */
    public void setTrans(int trans) {
        this.trans = trans;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the totalSpentWithSNAP
     */
    public double getTotalSpentWithSNAP() {
        return totalSpentWithSNAP;
    }

    /**
     * @param totalSpentWithSNAP the totalSpentWithSNAP to set
     */
    public void setTotalSpentWithSNAP(double totalSpentWithSNAP) {
        this.totalSpentWithSNAP = totalSpentWithSNAP;
    }

    /**
     * @return the totalSpentOnFruitAndVegatables
     */
    public double getTotalSpentOnFruitAndVegatables() {
        return totalSpentOnFruitAndVegatables;
    }

    /**
     * @param totalSpentOnFruitAndVegatables the totalSpentOnFruitAndVegatables to set
     */
    public void setTotalSpentOnFruitAndVegatables(double totalSpentOnFruitAndVegatables) {
        this.totalSpentOnFruitAndVegatables = totalSpentOnFruitAndVegatables;
    }

    /**
     * @return the totalSpentOnOtherItems
     */
    public double getTotalSpentOnOtherItems() {
        return totalSpentOnOtherItems;
    }

    /**
     * @param totalSpentOnOtherItems the totalSpentOnOtherItems to set
     */
    public void setTotalSpentOnOtherItems(double totalSpentOnOtherItems) {
        this.totalSpentOnOtherItems = totalSpentOnOtherItems;
    }

    /**
     * @return the valueOfCouponsIssued
     */
    public double getValueOfCouponsIssued() {
        return valueOfCouponsIssued;
    }

    /**
     * @param valueOfCouponsIssued the valueOfCouponsIssued to set
     */
    public void setValueOfCouponsIssued(double valueOfCouponsIssued) {
        this.valueOfCouponsIssued = valueOfCouponsIssued;
    }

    /**
     * @return the countOfCouponsIssued
     */
    public int getCountOfCouponsIssued() {
        return countOfCouponsIssued;
    }

    /**
     * @param countOfCouponsIssued the countOfCouponsIssued to set
     */
    public void setCountOfCouponsIssued(int countOfCouponsIssued) {
        this.countOfCouponsIssued = countOfCouponsIssued;
    }

    /**
     * @return the cash
     */
    public double getCash() {
        return cash;
    }

    /**
     * @param cash the cash to set
     */
    public void setCash(double cash) {
        this.cash = cash;
    }

    /**
     * @return the checks
     */
    public double getChecks() {
        return checks;
    }

    /**
     * @param checks the checks to set
     */
    public void setChecks(double checks) {
        this.checks = checks;
    }

    /**
     * @return the ebt
     */
    public double getEbt() {
        return ebt;
    }

    /**
     * @param ebt the ebt to set
     */
    public void setEbt(double ebt) {
        this.ebt = ebt;
    }

    /**
     * @return the ebtOther
     */
    public double getEbtOther() {
        return ebtOther;
    }

    /**
     * @param ebtOther the ebtOther to set
     */
    public void setEbtOther(double ebtOther) {
        this.ebtOther = ebtOther;
    }

    /**
     * @return the wic
     */
    public double getWic() {
        return wic;
    }

    /**
     * @param wic the wic to set
     */
    public void setWic(double wic) {
        this.wic = wic;
    }

    /**
     * @return the creditCards
     */
    public double getCreditCards() {
        return creditCards;
    }

    /**
     * @param creditCards the creditCards to set
     */
    public void setCreditCards(double creditCards) {
        this.creditCards = creditCards;
    }

    /**
     * @return the debitCards
     */
    public double getDebitCards() {
        return debitCards;
    }

    /**
     * @param debitCards the debitCards to set
     */
    public void setDebitCards(double debitCards) {
        this.debitCards = debitCards;
    }

    /**
     * @return the otherTenders
     */
    public double getOtherTenders() {
        return otherTenders;
    }

    /**
     * @param otherTenders the otherTenders to set
     */
    public void setOtherTenders(double otherTenders) {
        this.otherTenders = otherTenders;
    }

}

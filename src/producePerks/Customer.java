/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producePerks;

import java.util.ArrayList;

/**
 * @author Jake Heyser
 */

public class Customer {
    private int id;
    private ArrayList<Transaction> distributed;
    private ArrayList<Transaction> redeemed;
    
   
   
    public Customer(int id){
        this.id = id;
        distributed = new ArrayList();
        redeemed = new ArrayList();
    }
    
    public void addDistributed(Transaction t){
        getDistributed().add(t);
    }
    
    public void addRedeemed(Transaction t){
        getRedeemed().add(t);
    }
    
    public String toString(){
        return "ID " + id + " - Num Dist Trans " + getDistributed().size() 
                + " - Num Rede Trans " + getRedeemed().size();
    }

    /**
     * @return the distributed
     */
    public ArrayList<Transaction> getDistributed() {
        return distributed;
    }

    /**
     * @return the redeemed
     */
    public ArrayList<Transaction> getRedeemed() {
        return redeemed;
    }
    
}

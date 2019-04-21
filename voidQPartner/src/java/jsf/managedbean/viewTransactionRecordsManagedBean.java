/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transaction;

/**
 *
 * @author terencetay
 */
@Named(value = "viewTransactionRecordsManagedBean")
@ViewScoped
public class viewTransactionRecordsManagedBean implements Serializable {
    
    
    
    private List<Transaction> transactions;

    /**
     * Creates a new instance of viewTransactionRecordsManagedBean
     */
    public viewTransactionRecordsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        
    }
}

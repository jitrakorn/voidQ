/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.entity.TransactionEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author terencetay
 */

public interface TransactionSessionBeanLocal {
    public List<TransactionEntity> getAllTransactionsByClinicId(Long clinicId);
}

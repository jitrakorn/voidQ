/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.ws.rest;

/**
 *
 * @author terencetay
 */
public class RetrieveCurrentBookingQueuePositionRsp {
    private Integer queueNumber;
    
    public RetrieveCurrentBookingQueuePositionRsp(){
        
    }
    
    public RetrieveCurrentBookingQueuePositionRsp(Integer queueNumber) {
        this.queueNumber = queueNumber;
    }

    public Integer getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(Integer queueNumber) {
        this.queueNumber = queueNumber;
    }
}

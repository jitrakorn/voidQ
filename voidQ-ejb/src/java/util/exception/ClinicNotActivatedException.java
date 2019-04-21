/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author terencetay
 */
public class ClinicNotActivatedException extends Exception {

    /**
     * Creates a new instance of <code>ClinicNotActivatedException</code>
     * without detail message.
     */
    public ClinicNotActivatedException() {
    }

    /**
     * Constructs an instance of <code>ClinicNotActivatedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ClinicNotActivatedException(String msg) {
        super(msg);
    }
}

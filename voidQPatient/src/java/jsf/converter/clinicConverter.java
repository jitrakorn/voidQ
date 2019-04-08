/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.converter;

import ejb.entity.ClinicEntity;
import ejb.session.stateless.PartnerSessionBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author terencetay
 */
@FacesConverter("clinicConverter")
public class clinicConverter implements Converter {

    PartnerSessionBeanLocal partnerSessionBean = lookupPartnerSessionBeanLocal();
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value){
        return partnerSessionBean.getPartnerById(Long.valueOf(value));
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object clinicValue) {
        return String.valueOf(((ClinicEntity) clinicValue).getClinicId());
    }

    private PartnerSessionBeanLocal lookupPartnerSessionBeanLocal() {
        try {
            Context c = new InitialContext();
            return (PartnerSessionBeanLocal) c.lookup("java:global/voidQ/voidQ-ejb/PartnerSessionBean!ejb.session.stateless.PartnerSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

package util.email;

import ejb.entity.AdminEntity;
import ejb.entity.BookingEntity;
import ejb.entity.ClinicEntity;
import ejb.entity.PatientEntity;
import ejb.entity.StaffEntity;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailManager {

    private final String emailServerName = "mailauth.comp.nus.edu.sg";
    private final String mailer = "JavaMailer";
    private String smtpAuthUser;
    private String smtpAuthPassword;

    public EmailManager() {
    }

    public EmailManager(String smtpAuthUser, String smtpAuthPassword) {
        this.smtpAuthUser = smtpAuthUser;
        this.smtpAuthPassword = smtpAuthPassword;
    }

    public Boolean emailResetPassword(AdminEntity admin, String fromEmailAddress, String newPW) {
        String emailBody = "";

        emailBody += "Dear : " + admin.getFirstName() + "," + "your new password is " + newPW + "\n\n";
        try {
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", emailServerName);
            props.put("mail.smtp.port", "25");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.debug", "true");
            javax.mail.Authenticator auth = new SMTPAuthenticator(smtpAuthUser, smtpAuthPassword);
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            Message msg = new MimeMessage(session);

            if (msg != null) {
                msg.setFrom(InternetAddress.parse(fromEmailAddress, false)[0]);
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(admin.getEmail(), false));
                msg.setSubject("Reset Password Completed Successfully!");
                msg.setText(emailBody);
                msg.setHeader("X-Mailer", mailer);

                Date timeStamp = new Date();
                msg.setSentDate(timeStamp);

                Transport.send(msg);

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    public Boolean emailClinic(BookingEntity bookingEntity, List<StaffEntity> staffEntity) {
        String emailBody = "";

        for (StaffEntity se : staffEntity) {

            emailBody += "Dear Doctor " + se.getLastName() + "," + "your have a new booking " + "Patient details : " + bookingEntity.getPatientEntity().getFirstName() + "Visit reason: " + bookingEntity.getVisitReason() + "\n\n";
            try {
                Properties props = new Properties();
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.host", emailServerName);
                props.put("mail.smtp.port", "25");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.debug", "true");
                javax.mail.Authenticator auth = new SMTPAuthenticator(smtpAuthUser, smtpAuthPassword);
                Session session = Session.getInstance(props, auth);
                session.setDebug(true);
                Message msg = new MimeMessage(session);

                if (msg != null) {
                    msg.setFrom(InternetAddress.parse("ohmx93@comp.nus.edu.sg", false)[0]);
                    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(se.getEmail(), false));
                    msg.setSubject("New Booking!");
                    msg.setText(emailBody);
                    msg.setHeader("X-Mailer", mailer);

                    Date timeStamp = new Date();
                    msg.setSentDate(timeStamp);

                    Transport.send(msg);

                    return true;
                } else {
                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();

                return false;
            }
        }
        return true;
    }

}

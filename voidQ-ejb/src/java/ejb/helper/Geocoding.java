/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.helper;

/**
 *
 * @author mingxuan
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import org.apache.commons.io.IOUtils;

public class Geocoding {

    private static final String URL = "https://maps.googleapis.com/maps/api/geocode/json";

    public static String getJSONByGoogle(String fullAddress) throws IOException {

    
        URL url = new URL(URL + "?address=" + URLEncoder.encode(fullAddress, "UTF-8") + "&sensor=false&key=AIzaSyALrp6_N7M-7YdEN1vsXtt4akb92umGkI0");

        URLConnection conn = url.openConnection();

        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);

        IOUtils.copy(conn.getInputStream(), output);

        output.close();

        return output.toString();
    }
    
    public static String addressLookUp(String fullAddress) throws IOException {

    
        URL url = new URL(URL + "?latlng=" + URLEncoder.encode(fullAddress, "UTF-8") + "&key=AIzaSyALrp6_N7M-7YdEN1vsXtt4akb92umGkI0");

        URLConnection conn = url.openConnection();

        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);

        IOUtils.copy(conn.getInputStream(), output);

        output.close();

        return output.toString();
    }
}

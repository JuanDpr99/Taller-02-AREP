/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escuelaing.edu.co.mavenprojectserver;

import java.net.URI;

/**
 *
 * @author juan.parroquiano
 */
public class HttpRequest {

    URI requri = null;
    
    HttpRequest(URI requestUri) {
        requri = requestUri;
    }
   
    public String getValue(String paramName)
    {
        String val = requri.getQuery().split("=")[1];
        return val;
    }
}

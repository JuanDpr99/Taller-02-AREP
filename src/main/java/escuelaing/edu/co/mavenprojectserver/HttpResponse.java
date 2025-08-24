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
public class HttpResponse {

    URI resuri = null;
    
    HttpResponse(URI requestUri) {
        resuri = requestUri;
    }
}

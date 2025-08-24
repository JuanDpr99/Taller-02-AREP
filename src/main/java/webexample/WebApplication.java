/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package webexample;
import static escuelaing.edu.co.mavenprojectserver.HttpServer.get;
import static escuelaing.edu.co.mavenprojectserver.HttpServer.staticfiles;
import static escuelaing.edu.co.mavenprojectserver.HttpServer.startServer;
import escuelaing.edu.co.mavenprojectserver.Service;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author juan.parroquiano
 */
public class WebApplication 
{
    public static Map<String,Service> services = new HashMap<String,Service>();
    
    public static void main(String[] args) throws IOException, URISyntaxException 
    {
        staticfiles("/webroot");
        get("/hello", (req, resp) -> "Hello " + req.getValue("name"));
        get("/pi", (req, resp) -> { return String.valueOf(Math.PI);});
                
        startServer(args);
    }
  
}

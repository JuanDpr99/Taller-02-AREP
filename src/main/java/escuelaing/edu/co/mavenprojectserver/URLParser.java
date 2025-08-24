/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escuelaing.edu.co.mavenprojectserver;

import java.net.URL;

/**
 *
 * @author juan.parroquiano
 */
public class URLParser {
    public static void main(String[] args) throws Exception {
     URL myUrl = new URL("https://homes.cs.washington.edu:8080/~mernst/advice/write-technical-paper.html)?val=7#publicaciones");
     System.out.println("Protocol " + myUrl.getProtocol());
     System.out.println("Authority " + myUrl.getAuthority());
     System.out.println("Host " + myUrl.getHost());
     System.out.println("Port " + myUrl.getPort());
     System.out.println("Path " + myUrl.getPath());
     System.out.println("Query " + myUrl.getQuery());
     System.out.println("File " + myUrl.getFile());
     System.out.println("Ref " + myUrl.getRef());
    }
    
    /* try (BufferedReader reader
    new BufferedReader(new InputStreamReader(google.openStream()))) {
 String inputLine = null;
while ((inputLine = reader.readLine()) != null) {
 System.out.println(inputLine);
}
 } catch (IOException x) {
System.err.println(x);
 }*/
}

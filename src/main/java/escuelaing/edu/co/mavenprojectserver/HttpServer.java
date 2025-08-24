/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escuelaing.edu.co.mavenprojectserver;
import java.net.*;
import java.io.*;
import java.nio.file.*;
import static webexample.WebApplication.services;
/**
 *
 * @author juan.parroquiano
 */
public class HttpServer {
    private static String staticRoot = null;
    private static final Path PUBLIC_DIR = Paths.get("src", "main", "resources", "webroot").toAbsolutePath();
    
    public static void startServer(String[] args) throws IOException, URISyntaxException 
    {
        ServerSocket serverSocket = null;
        boolean      running = true;
        Socket       clientSocket = null;

        try 
        {
            serverSocket = new ServerSocket(35000);
            while (running) 
            {
                try {
                    System.out.println("Listo para recibir ...");
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }

                OutputStream   rawOut = clientSocket.getOutputStream();
                PrintWriter    out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String         inputLine, outputLine = null;
                boolean        isFirstLine = true;
                URI            requestUri = null;

                while ((inputLine = in.readLine()) != null) 
                {
                    if (isFirstLine) {
                        requestUri = new URI(inputLine.split(" ")[1]);
                        isFirstLine = false;
                    }
                    if (!in.ready())
                        break;
                }
                if (requestUri.getPath().startsWith("/app")) 
                {
                    outputLine = processRequest(requestUri);
                } 
                else 
                {
                    boolean served = tryServeStatic(requestUri, rawOut);
                    if (!served)
                        out.println(http404());                    
                }
                out.println(outputLine);
                out.close();
                in.close();
                clientSocket.close();
            }
            serverSocket.close();
        } 
        catch (IOException e) 
        {
            System.err.println("No puede escuchar por el puerto: 35000.");
            System.exit(1);
        }
    }
    
    private static String processRequest(URI requestUri) 
    {
        String  serviceRoute = requestUri.getPath().substring(4);
        Service service = services.get(serviceRoute);
        
        if (service != null)
        {
            HttpRequest  req = new HttpRequest(requestUri);
            HttpResponse res = new HttpResponse(requestUri);

            String header = "HTTP/1.1 200 OK\n\r"
                            + "Content-Type: text/html\n\r"
                            + "\n\r" ;

            return header + service.executeService(req, res);
        }        
        return http404();
    }
    
    private static boolean tryServeStatic(URI uri, OutputStream out) 
    {
        if (staticRoot == null)
            return false;

        try 
        {
            String path = uri.getPath();  //ejm "/img/logo.png"
            if ("/".equals(path)) {
                path = "/index.html";
            }
            if (path.contains("..")) {
                return false;
            }

            String root = staticRoot.replaceFirst("^/+", "");// "webroot"
            String rel  = path.replaceFirst("^/+", "");      // "img/logo.png"
            String cp   = root + "/" + rel;                  // "webroot/img/logo.png"

            byte[] body = null;
            Path p = PUBLIC_DIR.resolve(rel).normalize();
            if (java.nio.file.Files.exists(p) && java.nio.file.Files.isRegularFile(p)) 
            {
                body = java.nio.file.Files.readAllBytes(p);
            }

            if (body == null) {
                return false; // no encontrado
            }
            String mime = guessMime(path);
            String headers
                    = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: " + mime + "\r\n"
                    + "Content-Length: " + body.length + "\r\n"
                    + "Connection: close\r\n\r\n";

            out.write(headers.getBytes(java.nio.charset.StandardCharsets.US_ASCII));
            out.write(body);
            out.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }    
        
    /** Respuesta 404 para archivo no encontrado. */
    private static String http404() {
        String body = "<h1>404 Not Found</h1>";
        return "HTTP/1.1 404 Not Found\r\n" +
               "Content-Type: text/html; charset=utf-8\r\n" +
               "Content-Length: " + body.getBytes(java.nio.charset.StandardCharsets.UTF_8).length + "\r\n" +
               "Connection: close\r\n\r\n" +
               body;
    }
        
    private static String guessMime(String path) {
        String p = path.toLowerCase();
        if (p.endsWith(".html") || p.endsWith(".htm")) return "text/html";
        if (p.endsWith(".css")) return "text/css";
        if (p.endsWith(".js")) return "application/javascript";
        if (p.endsWith(".png")) return "image/png";
        if (p.endsWith(".jpg") || p.endsWith(".jpeg")) return "image/jpeg";
        if (p.endsWith(".gif")) return "image/gif";
        if (p.endsWith(".svg")) return "image/svg+xml";
        return "application/octet-stream";
    }

    @SuppressWarnings("empty-statement")
    private static String helloService(URI requestUri) {
        
        String response = "HTTP/1.1 200 OK\n\r"
                        + "Content-Type: application/json\n\r"
                        + "\n\r";
        
        String name = requestUri.getQuery().split("=")[1];
        
        response = response + "{\"mensaje\": \"Hola "+ name +"\"}";
        return response;
    }
    
    public static void get(String route, Service s)
    {
        services.put(route, s);
    }
    
    public static void staticfiles(String route) 
    {
        if (route.startsWith("/"))
        {
            staticRoot = route;
        }
        else
        {
            staticRoot = ("/" + route);
        }
    }
    
    public static String defaultResponse()
    {
        return "HTTP/1.1 200 OK\n\r"
                        + "Content-Type: text/html\n\r"
                        + "\n\r"
                        + "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<title>Form Example</title>\n" +
                        "<meta charset=\"UTF-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<h1>Form with GET</h1>\n" +
                        "<form action=\"/hello\">\n" +
                        "<label for=\"name\">Name:</label><br>\n" +
                        "<input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n" +
                        "<input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                        "</form>\n" +
                        "<div id=\"getrespmsg\"></div>\n" +
                        " \n" +
                        "<script>\n" +
                        "function loadGetMsg() {\n" +
                        "let nameVar = document.getElementById(\"name\").value;\n" +
                        "const xhttp = new XMLHttpRequest();\n" +
                        "xhttp.onload = function() {\n" +
                        "document.getElementById(\"getrespmsg\").innerHTML =\n" +
                        "this.responseText;\n" +
                        "}\n" +
                        "xhttp.open(\"GET\", \"app/hello?name=\"+nameVar);\n" +
                        "xhttp.send();\n" +
                        "}\n" +
                        "</script>\n" +
                        " \n" +
                        "<h1>Form with POST</h1>\n" +
                        "<form action=\"/hellopost\">\n" +
                        "<label for=\"postname\">Name:</label><br>\n" +
                        "<input type=\"text\" id=\"postname\" name=\"name\" value=\"John\"><br><br>\n" +
                        "<input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n" +
                        "</form>\n" +
                        " \n" +
                        "<div id=\"postrespmsg\"></div>\n" +
                        " \n" +
                        "<script>\n" +
                        "function loadPostMsg(name){\n" +
                        "let url = \"/hellopost?name=\" + name.value;\n" +
                        " \n" +
                        "fetch (url, {method: 'POST'})\n" +
                        ".then(x => x.text())\n" +
                        ".then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n" +
                        "}\n" +
                        "</script>\n" +
                        "</body>\n" +
                        "</html>";
    }


}

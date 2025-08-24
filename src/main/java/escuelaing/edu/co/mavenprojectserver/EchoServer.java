/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escuelaing.edu.co.mavenprojectserver;
import java.net.*;
import java.io.*;
/**
 *
 * @author juan.parroquiano
 */
public class EchoServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept(); // Espera solicitudes y crea el socket de cliente
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);// Flujo de salida sobre ese socket
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Flujo de entrada sobre ese socket
        String inputLine, outputLine;
        
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Mensaje:" + inputLine);
            outputLine = "Respuesta: " + inputLine; //Crea mensaje de respuesta
            out.println(outputLine); // Envia por el flujo de salida el mensaje
            if (outputLine.equals("Respuesta: Bye.")) { // Si digito Bye. se cierra el servidor
                break;
            }
        }
        
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}

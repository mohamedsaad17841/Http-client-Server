import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.util.Scanner;

public class Client 
{
    public static int port = 1234;
    
    public static void main(String args[]) throws IOException
    {
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the method you want : ");
        String method = sc.nextLine();
        System.out.println("Enter the page you want : ");
        String page = sc.nextLine();
        if(page.equals(""))
        {
            page = "index.html";
        }
        
        try
        {
            if(method.equals("GET"))
            {
                Client.GET("127.0.0.1", port, page);
            }
            else if (method.equals("HEAD"))
            {
                Client.HEAD("127.0.0.1", port, page);
            }
            else if(method.equals("CONNECT"))
            {
                Client.CONNECT("127.0.0.1", port, page);
            }
            
         
        } catch(Exception ex)
        {
            
        }
    }
    public static void GET(String Host, int port, String pageFile) throws IOException
    {
        String responseMessage;
        //opening connetion
        Socket clientSocket = null;
        clientSocket = new Socket(Host, port);  
        
        System.out.println("-------------------");
        System.out.println("Connected");
        System.out.println("-------------------");
        
        /* PrintStream is used to write data in bytes but PrintWriter is used where it's 
        required to write data in characters.
        - PrintStream is a byte stream (subclass of Outputstream).
        - PrintWriter is a character stream (subclass of a Writer). */
        
        //Writer and listener to this URL.
        PrintWriter request = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader response = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        //Sending request to the server, making the header of HTTP request.

        request.print("GET /" + pageFile + "/ HTTP/1.1\r\n"); 
        request.print("Host: " + Host + "\r\n");
        request.print("Connection: close\r\n");
        request.print("User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n");
        request.print("\r\n");
        request.print("\r\n");
        request.flush();
        
        System.out.println("Request Sent!");
        System.out.println("---------------------");
        
        
        // Receiving response from server
        while( (responseMessage = response.readLine()) != null)
        {
            System.out.println(responseMessage) ; 
        }
        System.out.println("----------------------------------");
        System.out.println("Response Recieved..");
        System.out.println("----------------------------------");
        response.close() ; 
        request.close() ; 
        clientSocket.close();
    }
    
    public static void HEAD(String Host, int port, String pageFile) throws IOException
    {
        String responseMessage;
        //opening connetion
        Socket clientSocket = null;
        clientSocket = new Socket(Host, port);  
        
        System.out.println("-------------------");
        System.out.println("Connected");
        System.out.println("-------------------");
        
        //Writer and listener to this URL.
        PrintWriter request = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader response = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        //Sending request to the server, making the header of HTTP request.
        request.print("HEAD /" + pageFile + "/ HTTP/1.1\r\n"); 
        request.print("Host: " + Host + "\r\n");
        request.print("Connection: close\r\n");
        request.print("User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n");
        request.print("\r\n");
        request.print("\r\n");
        request.flush();
        
        System.out.println("Request Sent!");
        System.out.println("---------------------");
        
        
        // Receiving response from server
        while( (responseMessage = response.readLine()) != null)
        {
            System.out.println(responseMessage) ; 
        }
        System.out.println("----------------------------------");
        System.out.println("Response Recieved..");
        System.out.println("----------------------------------");
        response.close() ; 
        request.close() ; 
        clientSocket.close();
    }
    
    public static void CONNECT(String Host, int port, String pageFile) throws IOException
    {
        String responseMessage;
        //opening connetion
        Socket clientSocket = null;
        clientSocket = new Socket(Host, port);  
        
        System.out.println("-------------------");
        System.out.println("Connected");
        System.out.println("-------------------");
        
        //Writer and listener to this URL.
        PrintWriter request = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader response = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        //Sending request to the server, making the header of HTTP request.
        request.print("CONNECT /" + pageFile + "/ HTTP/1.1\r\n"); 
        request.print("User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n");
        request.print("\r\n");
        request.print("\r\n");
        request.flush();
        
        System.out.println("Request Sent!");
        System.out.println("---------------------");
        
        
        // Receiving response from server
        while( (responseMessage = response.readLine()) != null)
        {
            System.out.println(responseMessage) ; 
        }
        System.out.println("----------------------------------");
        System.out.println("Response Recieved..");
        System.out.println("----------------------------------");
        response.close() ; 
        request.close() ; 
        clientSocket.close();
    }
}



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server implements Runnable
{ 
    public static void main(String args[]) throws IOException, InterruptedException
    {
        // initializing the Socket Server
        ThreadClass socketServer;
        socketServer = new ThreadClass(1234);
        socketServer.start();
    }

    private Socket client;
    public Server(Socket client) 
    {
        this.client = client;
    }
    
    @Override   //override on thread.start().
    public void run()
    {
            try 
            {
                    System.out.println("Thread started with name:"
                                    + Thread.currentThread().getName());
                    readClientRequest();
                    return;
            } catch (IOException e) {
                    e.printStackTrace();
            } catch (InterruptedException e) {
                    e.printStackTrace();
            }
    }
    
    private static ServerSocket serverSocket;   
    private void readClientRequest() throws IOException, InterruptedException 
    {

        try 
        {

            BufferedReader request = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            
            BufferedWriter response = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                            
            String requestHeader = "";
            String line = ".";
            while (!line.equals(""))
            {
                    line = request.readLine();
                    System.out.println(line);
                    requestHeader += line + "\n";
            }

            // Get the method from HTTP header
            StringBuilder sb = new StringBuilder();
            String file = requestHeader.split("\n")[0].split(" ")[1].split("/")[1];

            if (requestHeader.split("\n")[0].contains("GET") && validPage(file) )    //Check URL 
            {
                constructResponseHeader(200, sb);
                response.write(sb.toString());
                response.write(getData(file));
                sb.setLength(0);
                response.flush();
            } 
            else if(requestHeader.split("\n")[0].contains("HEAD") && validPage(file))   //Bonus funciton
            {
                constructResponseHeader(200, sb);
                response.write(sb.toString());
                sb.setLength(0);
                response.flush();
            }
            else if(requestHeader.split("\n")[0].contains("CONNECT") && validPage(file))   //Bonus funciton
            {
                constructConnectResponse(200, sb);
                response.write(sb.toString());
                sb.setLength(0);
                response.flush();
            }
            else
            {
                // Enter the error code
                // 404 page not found
                constructResponseHeader(404, sb);
                response.write(sb.toString());
                sb.setLength(0);
                response.flush();
            }

            request.close();
            response.close();

            client.close();
            serverSocket.close();
            return;
        } 
        catch (Exception e)
        {
        }
    }

    private static boolean validPage(String file)
    {
        File myFile = new File(file);
        return myFile.exists() && !myFile.isDirectory();
    }
     
    private static void constructResponseHeader(int responseCode, StringBuilder builder)
    {

        if(responseCode == 200)
        {
            builder.append("HTTP/1.1 200 OK\r\n");
            builder.append("Date: " + timeFunction() + "\r\n");
            builder.append("Server: localhost\r\n");
            builder.append("Connection: close");
            builder.append("Content-Type: text/htm; charset=ISO-8859-1");
            builder.append("\r\n\r\n");
        }
       else if(responseCode == 404)
        {
            builder.append("HTTP/1.1 404 Not Found\r\n");
            builder.append("Date: " + timeFunction() + "\r\n");
            builder.append("Server: localhost\r\n");
            builder.append("\r\n\r\n");
        }
        else if(responseCode == 304)
        {
            builder.append("HTTP/1.1 304 Not Modified\r\n");
            builder.append("Date: " + timeFunction() + "\r\n");
            builder.append("Server: localhost\r\n");
            builder.append("\r\n\r\n");
        }
    }
    private static void constructConnectResponse(int responseCode, StringBuilder builder)
    {
        builder.append("HTTP/1.1 200 Connection established \r\n");
        builder.append("Date: " + timeFunction() + "\r\n");
        builder.append("Server: localhost\r\n");
        builder.append("\r\n\r\n");
    }
  
     // TimeStamp
    private static String timeFunction() {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
            String formattedDate = sdf.format(date);
            return formattedDate;
    }      

    private static String getData(String file)
    {

        File myFile = new File(file);
        String responseToClient = "";
        BufferedReader reader;
        
        try {
                reader = new BufferedReader(new FileReader(myFile));
                String line = null;
                while (!(line = reader.readLine()).contains("</html>"))
                {
                        responseToClient += line;
                        responseToClient += "\n" ;
                }
                responseToClient += line;
                reader.close();

        } catch (Exception e) {

        }
        return responseToClient;
    }
}
       
   

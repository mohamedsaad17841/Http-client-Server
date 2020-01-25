import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A simple socket server
 * 
 */
public class ThreadClass {
    public static void main(String args[]) throws IOException, InterruptedException
    {
        try
        {
            start();
        } catch(Exception ex){}   
    }

	private static ServerSocket serverSocket;
	private static int port;

	public ThreadClass(int port) {
		this.port = port;
	}

	public static void start() throws IOException, InterruptedException {

		serverSocket = new ServerSocket(port);
		System.out.println("Starting the socket server at port:" + port);

		Socket client = null;
                
		while (true) {
			System.out.println("Waiting for clients...");
			client = serverSocket.accept();
			System.out.println("The following client has connected:"
					+ client.getInetAddress().getCanonicalHostName());
			// A client has connected to this server. Send welcome message
			Thread thread = new Thread((Runnable) new Server(client));
			thread.start();
		}
	}

}
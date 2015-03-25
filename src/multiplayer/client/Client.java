package multiplayer.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
	
	void runClient(String server, int port, String password) throws UnknownHostException, IOException, InterruptedException {
		
		Socket socket = new Socket(server, port);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
		out.println("password " + password);
		in.wait();
		String passwordTest = in.readLine();
		if (passwordTest == "false") {
			//TODO homescreen
		}
		
		while(true) {
			
			String input = in.readLine();
			String[] inputParsed = input.split(" ");
			
		}
		
	}

}

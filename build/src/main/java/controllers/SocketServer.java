package controllers;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements java Socket server
 * @author pankaj
 *
 */
public class SocketServer {

	//static ServerSocket variable
	private static ServerSocket server;
	//socket server port on which it will listen
	private static int port = 22222;

	public static void main(String args[]) throws IOException, ClassNotFoundException {
		//create the socket server object
		server = new ServerSocket(port);
		//keep listens indefinitely until receives 'exit' call or program terminates
		try {
			while (true) {
				System.out.println("Waiting for the client request");
				//creating socket and waiting for client connection
				Socket socket = server.accept();
				//read from socket to ObjectInputStream object
				DataInputStream ois = new DataInputStream(socket.getInputStream());
				//convert ObjectInputStream object to String
				String message = (String) ois.readUTF();
				System.out.println("Message Received: " + message);
				ois.close();
				socket.close();
				//terminate the server if client sends exit request
				if (message.equalsIgnoreCase("exit"))
					break;
			}
			System.out.println("Shutting down Socket server!!");
			//close the ServerSocket object
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			server.close();
		}
	}

}

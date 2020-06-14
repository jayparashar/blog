package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

public class Server {
	public static void main(String[] args) throws IOException {

		ServerSocket ss = new ServerSocket(22221);
		try {
			while (true) {
				
				Socket s = ss.accept();//establishes connection   

				InputStream input = s.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				String line = reader.readLine(); // reads a line of text
				if (line != null) {
					System.out.println(line);

				}
			}

		} catch (Exception e) {
			StringWriter outError = new StringWriter();
			e.printStackTrace(new PrintWriter(outError));
			String errorString = outError.toString();
			System.err.println(new Date() + "  " + errorString);
		} finally {
			ss.close();
		}
	}
}
package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

public class Singleton1 extends Thread{
	
	private static Singleton1 uniq; 
	private ObserverMessage app; 
	
	private Singleton1() {
		
	}
	public void suscripcion(ObserverMessage app) {
		this.app = app; 
	}
	
	public static Singleton1 getInstance() {
		if(uniq == null) {
			uniq = new Singleton1(); 
			uniq.start();
		}
		return uniq; 
	}
	//globales
	Socket socket; 
	BufferedReader reader;
	BufferedWriter writer; 
	// 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerSocket server = new ServerSocket(6000);
			//esperando
			System.out.println("server esperando");
			  socket = server.accept();
			  System.out.println("cliente conectado");
			  //reader
			  InputStream is = socket.getInputStream(); 
			  InputStreamReader isr = new InputStreamReader(is); 
			  reader = new BufferedReader(isr);
			  
			  //writer
			  OutputStream os = socket.getOutputStream(); 
			  OutputStreamWriter osw = new OutputStreamWriter(os); 
			  writer = new BufferedWriter(osw); 
			  
			  while(true) {
				  String line = reader.readLine(); 
				 // System.out.println("se ha leido desde el server "+line);
				 
				  app.readmsg(line);				  
				 
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void sendMessage(String ms) {
		new Thread(
				()->{
					try {
						writer.write(ms+"\n");
						writer.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				).start();
	}
	

}

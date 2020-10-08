package view;

import com.google.gson.Gson;

import processing.core.PApplet;

public class Main extends PApplet implements ObserverMessage {
	User userOne; 
	Singleton1 tcp; 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("view.Main");

	}
	public void settings() {
		size(500,500); 

	}

	public void setup() {
		//System.out.println((int)Math.floor( Math.random()*255));
		tcp = Singleton1.getInstance(); 
		tcp.suscripcion(this);
			 
	}

	public void draw() {
		background(255,255,50); 
		if(userOne != null) {
			fill(userOne.getCol().r,userOne.getCol().g,userOne.getCol().b); 
			text(userOne.getName(),userOne.getCord().x-25,userOne.getCord().y-30); 
			ellipse(userOne.getCord().x, userOne.getCord().y, 50,50); 
		}
		
	}
	
	@Override
	public void readmsg(String alo) {
		// TODO Auto-generated method stub
		System.out.println(alo);
		 Gson gson = new Gson();  
		 Generic tipo = gson.fromJson(alo, Generic.class);
		
		 if(tipo.type.equals("coordenada")) {
			// cordenadaPro =  gson.fromJson(alo, Coordenada.class);
			 userOne.setCord(gson.fromJson(alo, Coordenada.class));
		 }
		 if(tipo.type.equals("color")) {
			 userOne.setCol(gson.fromJson(alo, ColorFill.class));
				
		 }
		 if(tipo.type.equals("user")) {
			 userOne = gson.fromJson(alo, User.class);
			 System.out.println(userOne.getName());
				
		 }
		 
		
	}

}

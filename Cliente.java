import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Cliente {
private String nick;
public Cliente(String nick) {
	this.nick=nick;
}
public static void main(String[]args) throws UnknownHostException, IOException, ParseException, ClassNotFoundException{
	 Socket s = new Socket("localhost", 3001);
	 BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
	 Scanner sc = new Scanner(System.in);
	 boolean logueado=false;
		 String nick=null;
		 JSONObject jsonObject2 = new JSONObject();
		 while(!logueado){
			 System.out.println("Nick:");
			 nick = sc.nextLine();
			 jsonObject2.put("user", nick);
			 jsonObject2.put("action","login");
			 if(!nick.equals(""))
			 logueado=true;
		 }
		 if(logueado) {
			 bw.write(jsonObject2 + "\n");
			 bw.flush();
			 System.out.println("Logueado");
		 }
		 HiloEscucha hilo = new HiloEscucha(s,nick);
		 hilo.start();
		 while(true) {
		 System.out.println("Mensaje: ");
		 String mensaje= sc.nextLine();
		 if(mensaje.equalsIgnoreCase("logout")){
			 System.out.println("Has sido desconectado.");
			 break;
		 }
		 System.out.println("Para quien: ");
		 String quien=sc.nextLine();
		 if(mensaje.equalsIgnoreCase("logout")) {
			 JSONObject jsonObject3 = new JSONObject();
			 jsonObject3.put("action","logout");
			 jsonObject3.put("user", nick);
			 bw.write(jsonObject3 + "\n");
			 bw.flush();
			 break;
		 }
		 else if (!mensaje.equalsIgnoreCase("logout") && quien.equalsIgnoreCase("todos")){
			 JSONObject jsonObject = new JSONObject();
			 jsonObject.put("action","mensaje");
			 jsonObject.put("value",mensaje);
			 jsonObject.put("to", quien);
			 jsonObject.put("from",nick);
		 	bw.write(jsonObject + "\n");
		 	bw.flush();
		 }
		 else{
			 JSONObject jsonObject4 = new JSONObject();
			 jsonObject4.put("action", "mensaje");
			 jsonObject4.put("value", mensaje);
			 jsonObject4.put("to", quien);
			 jsonObject4.put("from",nick);
			 bw.write(jsonObject4 + "\n");
			 bw.flush();
		 }
		 }
		 sc.close();
}
}

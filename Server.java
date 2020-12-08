import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Server{
	 static ArrayList<HiloLogeo> clientes = new ArrayList<HiloLogeo>();

	public static String meterUser(Socket s) throws IOException, ParseException {
		while(true) {
			System.out.println("caveiralogin");
			BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String linea;
			while((linea=bf.readLine())!=null) {
				JSONObject jsonobject = new JSONObject();
				JSONParser jsonParser = new JSONParser();
				jsonobject= (JSONObject) jsonParser.parse(linea);
				System.out.println(jsonobject);
				return (String) jsonobject.get("user");
			}
		}
	}
	public static void eliminarUser(Socket s,JSONObject json) {
		for(HiloLogeo c : clientes) {
			if(c.getS() == s) {
				clientes.remove(c);
				System.out.println(json);
			}
		}
	}
	public static void broadcast(Socket cliente,JSONObject msg) throws IOException{
		for(HiloLogeo ss: clientes){
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ss.getS().getOutputStream()));
			//if(ss.getS()!=cliente) {
				bw.write(msg + "\n");
				bw.flush();
			//}	
		}
		System.out.println(msg);
	}
public static void main(String[]args) throws IOException, ParseException, ClassNotFoundException{
		ServerSocket serverSocket = new ServerSocket(3001);
		System.out.print("Servidor escuchando\n");
		while(true) {
			Socket s = serverSocket.accept();
			HiloLogeo hilo= new HiloLogeo(s,"guest");
			hilo.start();
			clientes.add(hilo);
		}
}
}

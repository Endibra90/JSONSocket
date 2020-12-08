import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/*
 * action(msg,login,logout)
 * value
 * from
 * to all/o a un usuario
 */
public class HiloEscucha extends Thread {
     Socket s;
	private BufferedReader bf;
     public HiloEscucha(Socket s,String nick) throws IOException, ParseException, ClassNotFoundException{
    	 this.bf=new BufferedReader(new InputStreamReader(s.getInputStream()));
     }
    @Override
    public void run(){
    	String linea;
    	try {
			while((linea=bf.readLine())!=null) {
				System.out.print("caveiraescucha");
				JSONObject jsonobject = new JSONObject();
				JSONParser jsonParser = new JSONParser();
				try {
					jsonobject= (JSONObject) jsonParser.parse(linea);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(jsonobject.get("action").equals("logout")) {
					Server.eliminarUser(s, jsonobject);
				}else {
					Server.broadcast(s,jsonobject);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

}

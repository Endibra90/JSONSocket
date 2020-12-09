import java.io.IOException;
import java.net.Socket;

import org.json.simple.parser.ParseException;

public class HiloLogeo extends Thread {
	private Socket s;
	private String nick;
	HiloLogeo(Socket s,String nick){
		this.s=s;
		this.nick=nick;
	}
	 public Socket getS() {
		return s;
	}
	public void setS(Socket s) {
		this.s = s;
	}
	public String getNick(){
		return nick;
	}
	public void setNick(String nick){
		this.nick=nick;
	}
	@Override
	    public void run(){
		 HiloEscucha hiloescucha = null;
		try {
			this.nick = Server.meterUser(s);
			hiloescucha = new HiloEscucha(s,nick);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 hiloescucha.start();
	 }
}

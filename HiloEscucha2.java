import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HiloEscucha2 extends Thread {
	public Socket socket;

    public HiloEscucha2(Socket socket){
        this.socket=socket;
    }
	@Override
    public void run() {
            try {
                BufferedReader entrada;
                entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter salidaServidor;
                salidaServidor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String mensajeCliente;
                while((mensajeCliente = entrada.readLine()) != null){
                      System.out.println(mensajeCliente);
                }

                } catch (HeadlessException | IOException e) {
                    e.printStackTrace();
                }

        }
}

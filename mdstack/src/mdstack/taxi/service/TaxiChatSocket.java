package mdstack.taxi.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.itextpdf.text.pdf.codec.Base64.InputStream;

public class TaxiChatSocket extends Thread {
	
	Socket socket;
	
	public TaxiChatSocket(Socket s){
		this.socket = s;
	}
	
	public void out(String out){
		try {
			socket.getOutputStream().write(out.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			String line = null;
			while((line = bf.readLine()) != null){
				TaxiChatManager.getTaxiChatManager().publish(this, line);
			}
			bf.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

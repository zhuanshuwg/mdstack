package mdstack.taxi.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class TaxiServerListener extends Thread {
	@Override
	public void run() {
		try {
			//1~65535
			ServerSocket serverSocket = new ServerSocket(55269);
			while (true) {
				//阻塞线程
				Socket socket = serverSocket.accept();
				//建立连接
//				JOptionPane.showConfirmDialog(null, "客户端连接到55269端口");
				//讲socket传递给新的线程
				TaxiChatSocket cs = new TaxiChatSocket(socket);
				cs.start();
				TaxiChatManager.getTaxiChatManager().add(cs);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

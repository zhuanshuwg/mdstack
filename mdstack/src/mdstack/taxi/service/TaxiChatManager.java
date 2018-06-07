package mdstack.taxi.service;

import java.util.Vector;

public class TaxiChatManager {
	//由于一个聊天服务器，只能有一个manager、所以要做单例处理
	//类的单例化
	private TaxiChatManager(){
		
	}
	
	private static final TaxiChatManager cm = new TaxiChatManager();
	
	public static TaxiChatManager getTaxiChatManager(){
		return cm;
	}
	
	
	Vector<TaxiChatSocket> vector = new Vector<TaxiChatSocket>();
	
	public void add(TaxiChatSocket cs){
		vector.add(cs);
	}
	
	public void publish(TaxiChatSocket cs, String out){
		for (int i = 0; i < vector.size(); i++) {
			TaxiChatSocket csTaxiChatSocket = vector.get(i);
			if(!cs.equals(csTaxiChatSocket)){
				csTaxiChatSocket.out(out);
			}
		}
	}
}

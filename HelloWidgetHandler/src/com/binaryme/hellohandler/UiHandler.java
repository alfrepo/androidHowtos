package com.binaryme.hellohandler;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

public class UiHandler {
	protected static List<Handler> clients = new ArrayList<Handler>();
	
	static void registerClient(Handler client) {
		if(!clients.contains(client)) {
			clients.add(client);
		}
	}
	
	static void unregisterClient(Handler client) {
		while(clients.contains(client)) {
			clients.remove(client);
		}
	}
	
	static void updateClients() {
		for (Handler client : clients) {
			client.sendEmptyMessage(0);
		}
	}
}

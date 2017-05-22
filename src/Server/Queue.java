package Server;

import java.net.Socket;

public class Queue {
	
	private Item first;
	private Item last;
	
	public Queue(){
		first = null;
		last = null;
	}
	
	public boolean isEmpty(){
		return first == null;
	}
	
	public void insert(Socket s){
		Item prev = last;
		last = new Item(s);
		last.next = null;
		if(isEmpty()){
			first = last;
		}else{
			prev.next = last;
		}
	}//insert
	
	public Socket remove(){
		if(isEmpty()){
			return null;
		}else{
			Socket item = first.socket;
			first = first.next;
			if(isEmpty()){
				last = null;
			}
			return item;
		}
	}//remove
	
	public Socket peek(){
		return first.socket;
	}
	
	
}

class Item{
	
	public Socket socket;
	public Item next;
	
	public Item(Socket s){
		this.socket = s;
	}
}

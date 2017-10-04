package LobDemo.LobDemo;

/*
 * Class that represents a basic letter structure 
 */
public class Letter {

	private Address from, to;
	private String message;
	public Letter(Address from, Address to, String message) {
		// TODO Auto-generated constructor stub
		this.from = from;
		this.to = to;
		this.message = message;
	}
	
	public Address getFromAddress(){
		return from;
	}
	
	public Address getToAddress(){
		return to;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String toString(){
		return "[from = " + from + ", to = " + to + ", message = " + message + "]"; 
	}

}

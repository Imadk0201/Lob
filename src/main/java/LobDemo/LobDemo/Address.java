package LobDemo.LobDemo;


/*
 Class that represents a basic address structure
 */
public class Address {

	private String name, country, zip, city, state, line2, line1;
	public Address(String name, String country, String zip, 
			String city, String state, String line2, String line1) {
		this.name = name;
		this.country = country;
		this.zip = zip;
		this.city = city;
		this.state = state;
		this.line1 = line1;
		this.line2 = line2;
	}
	
	public String getName(){
		return name;
	}
	
	public String getCountry(){
		return country;
	}
	
	public String getZip(){
		return zip;
	}
	
	public String getCity(){
		return city;
	}
	
	public String getState(){
		return state;
	}
	
	public String getLine2(){
		return line2;
	}
	
	public String getLine1(){
		return line1;
	}
	
	public String toString(){
		return " [name = " + name + ", zip = " + zip + ", city = " 
	+ city + ", state = " + state + ", line2 = " + line2 + ", line1 = " + line1 + "] ";
	}
}

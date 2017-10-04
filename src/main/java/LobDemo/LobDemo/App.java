package LobDemo.LobDemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lob.client.AsyncLobClient;
import com.lob.client.LobClient;
import com.lob.protocol.request.AddressRequest;
import com.lob.protocol.request.LetterRequest;
import com.lob.protocol.response.LetterResponse;

/**
 * Main App class which fetches the nearest official's address to a given
 * user, parses the sender and reciever's information into a letter, and
 * sends the letter while returning a PDF of it using Lob's API
 *
 */
public class App {
	
	public static Address from, to;
	
	public static String pdfUrl, html = "<html style='padding-top: 3in; margin: .5in;'>";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

			Scanner scanner = new Scanner(System.in);
			
			System.out.println("From Name: ");
			String name = scanner.nextLine();	
			System.out.println("From Address Line 1: ");
			String line1 = scanner.nextLine();
			System.out.println("From Address Line 2: ");		
			String line2 = scanner.nextLine();
			System.out.println("From City: ");
			String city = scanner.nextLine();
			System.out.println("From State: ");
			String state = scanner.nextLine();
			System.out.println("From Country: ");
			String country = scanner.nextLine();
			System.out.println("From Zip Code: ");
			String zip = scanner.nextLine();
			System.out.println("From Message: ");
			String message = scanner.nextLine();
			
			scanner.close(); 
			
			// Basic conditions to test whether the input is valid
			if(name.length() > 0 && country.length() > 0 && zip.matches("[0-9]{5}|([0-9]{5}-[0-9]{4})")
					&& city.length() > 0 && line1.length() > 0 && message.length() > 0 && state.matches("[a-zA-z]{2}")) {
				
				// Constructs the from address object and html message
				from = new Address(name, country, zip, city, state, line2, line1);
				html += message + "</html>";

				try {
					// Fetches the address of the closest official with input address
					to = getOfficialByAddress(line1 + " " + line2 + " " + city);
					
					Letter toSend = new Letter(from, to, message);
					
					// PDF of letter if all fields are valid
					pdfUrl = sendLetterGeneratePDF(toSend);
					
					if(pdfUrl != null && pdfUrl.length() > 0)
						System.out.println("Success! Generated PDF below:\n" + pdfUrl);
						
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println("Error. Input address was invalid. Please try again.");
				}
			} else {
				System.out.println("Error. There was a problem with the given input. Please"
						+ " check all fields and try again.");
			}
	}
	
	/*
	  Sends the letter to the selected official with Lob's API and returns the PDF 
	  document containing the letter if all inputs are valid. If there are any 
	  errors, they get captured in an exception and printed out.
	 */
	private static String sendLetterGeneratePDF(Letter letter) {
		
		final LobClient client = AsyncLobClient.createDefault("test_185f2293dfa2a01c71dcc151fd9cf7f65c4");
		
		// Lob API letter request
		final LetterRequest letterRequest = LetterRequest.builder()
			.to(AddressRequest.builder()
				.description(letter.getMessage())
				.name(letter.getToAddress().getName())
				.line1(letter.getToAddress().getLine1())
				.city(letter.getToAddress().getCity())
				.state(letter.getToAddress().getState())
				.zip(letter.getToAddress().getZip())
				.country(letter.getToAddress().getCountry())
				.build())
			.from(AddressRequest.builder()
				.description(letter.getMessage())
				.name(letter.getFromAddress().getName())
				.line1(letter.getFromAddress().getLine1())
				.city(letter.getFromAddress().getCity())
				.state(letter.getFromAddress().getState())
				.zip(letter.getFromAddress().getZip())
				.country(letter.getFromAddress().getCountry())
				.build())
				.file(html)
				.color(true)
				.build();
		try {
			final LetterResponse letterRes = client.createLetter(letterRequest).get();

			return letterRes.getUrl();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error. The process was interrupted. Please try again.");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error. There was a problem during execution. Please check  inputs"
					+ "and try again.");

		}
		return null;
	}
	
	/*
	  Performs HTTP get request to get Official's address based on sender's address. An official
	  is randomly selected and the letter is sent to them. 
	  @param address the address of the sender of the letter
	  @return the address of a randomly selected Official local to the sender
	 */
	private static Address getOfficialByAddress(String address) throws Exception {
		String USER_AGENT = "Mozilla/5.0";

		String name = "", country = "US", zip = "", city = "", state = "", line1 = "", line2 = "";
		Address to = null;
		// Replaces all strings with '%20' for URL completion
		address = address.replace(" ", "%20");
		String urlString = "https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyAEU9J6KzUL_gXPGi-4S6XekJuEC0JRWjA&address=" + address;
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		// By default it is GET request
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		// Reading response from input Stream
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	  
		String output;
		StringBuffer response = new StringBuffer();
		while ((output = in.readLine()) != null) {
			response.append(output);	
		}
		in.close();
		
		// Constructs JSONOBject to parse the response
		JSONObject object = new JSONObject(response.toString());
		JSONArray addresses = object.getJSONArray("officials");
		
		// Takes array of individuals response and selects last index of it by default (may be optimized for more specific selections)
		JSONObject person = addresses.getJSONObject(addresses.length()-1);
		JSONArray fields = person.getJSONArray("address");
		JSONObject addressTo = fields.getJSONObject(0);
				
		if(person.has("name"))
			name = person.getString("name");
		
		if(addressTo.has("zip"))
			zip = addressTo.getString("zip");
		
		if(addressTo.has("city"))
			city = addressTo.getString("city");
		
		if(addressTo.has("state"))
			state = addressTo.getString("state");
		
		if(addressTo.has("line2"))
			line2 = addressTo.getString("line2");
		
		if(addressTo.has("line1"))
			 line1 = addressTo.getString("line1");
		
		to = new Address(name, country, zip, city, state, line2, line1);
		

		return to;
		
	 
	 }

}

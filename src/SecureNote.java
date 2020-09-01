import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class SecureNote {
	public static String folderName="C:\\Users\\aweso\\Desktop\\";
	public static String input = new String();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("\r\n" + 
				"  _  __          _        __ _ _        _            _             \r\n" + 
				" | |/ /         ( )      / _(_) |      | |          | |            \r\n" + 
				" | ' / ___ _ __ |/ ___  | |_ _| | ___  | | ___   ___| | _____ _ __ \r\n" + 
				" |  < / _ \\ '_ \\  / __| |  _| | |/ _ \\ | |/ _ \\ / __| |/ / _ \\ '__|\r\n" + 
				" | . \\  __/ | | | \\__ \\ | | | | |  __/ | | (_) | (__|   <  __/ |   \r\n" + 
				" |_|\\_\\___|_| |_| |___/ |_| |_|_|\\___| |_|\\___/ \\___|_|\\_\\___|_|   \r\n" + 
				"                                                                   \r\n" + 
				"                                                                   \r\n" + 
				"");
		System.out.println("Current save directory: " +folderName);
		System.out.println("Use the settings command to change directory");
		var aes = new aesUtils();
		while(!input.equals("exit")) {
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Available commands encrypt,decrypt,settings,exit");
			input = sc.nextLine().toString();
			
			if(input.equals("encrypt"))
			{	
				System.out.println("Enter file name (eg mySecretFile):");
				//get inputs
				String fileName = sc.nextLine()+".ser";
				System.out.println("Enter text:");
				String note = sc.nextLine();
				System.out.println("Enter password:");
				String password = sc.next();
				var cipher = new HashMap<String,String>();
				try {
					cipher = aes.encrypt(password,note);					
				}catch(Exception e) {
					e.printStackTrace();
					System.out.println("Could not encrypt");
				}//save cipher to a file
				try {
					FileOutputStream fos = new FileOutputStream(folderName+fileName);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(cipher);
					oos.close();
					fos.close();
					System.out.println(fileName +" saved at " + folderName);
				}catch(Exception e) {
					e.printStackTrace();
					System.out.println("Could not write to file");
				}
			}
			
			else if(input.equals("decrypt")){
				System.out.println("Enter file name(eg mySecretFile):");
				String fileName = sc.nextLine()+".ser";
				
				//read the hashmap from file 
				try {
					FileInputStream fis = new FileInputStream(folderName+fileName);
					ObjectInputStream ois = new ObjectInputStream(fis);
					HashMap<String,String> map = (HashMap<String,String>)ois.readObject();
					System.out.println("Enter password");
					String password = sc.nextLine();
					map.put("password", password);
					try {
						System.out.println(aes.decryt(map));
					}catch(Exception e) {
						System.out.println("Could not decrypt...Check password and try again");
					}
				}catch(Exception e) {
					//e.printStackTrace();
					System.out.println("Could not read file... Current directory: " + folderName);
					System.out.println("try changing file folder using settings");
				}
				
			}
			else if(input.equals("settings")) {
				System.out.println("current save directory: "+folderName);
				System.out.println("Change folder (y/n)");
				//Scanner sc= new Scanner(System.in);
				String choice = sc.nextLine();
				if(choice.equals("y") | choice.equals("Y")) {
					System.out.println("Enter new save directory");
					folderName = sc.nextLine();
				//	sc3.close();
				}
			}	
			else if(input.equals("exit")){
				//do nothing here
			}
			else
				System.out.println("Command not found");
		}
	}

}

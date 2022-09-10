import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.io.InputStream; 
import java.util.ArrayList; 
import java.util.Arrays; 
import java.util.HashMap; 
import java.util.Properties; 
import java.util.Scanner; 
import java.util.concurrent.CompletableFuture; 
import java.util.concurrent.ExecutionException; 
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 
public class Main { 
	ArrayList<String> devices = null; ArrayList<String> users_ids = null; 
	Integer devices_count = null; 
	// This method is used to initialise data from config.properties 
	public void initialiseData() 
	{ 
		Properties properties = new Properties(); 
		try (InputStream inputStream = new FileInputStream("config.properties")) 
		{ 
			properties.load(inputStream); devices = new ArrayList<String>(Arrays.asList(properties.getProperty("devices").split(","))); 
			users_ids = new ArrayList<String>(Arrays.asList(properties.getProperty("users_ids").split(","))); 
			devices_count = Integer.parseInt(properties.getProperty("devices_count")); 
		} 
		catch (FileNotFoundException e) 
		{ // TODO Auto-generated catch block 
			System.out.println(e.getMessage()); 
		} 
		catch (IOException e) 
		{ // TODO Auto-generated catch block 
			System.out.println(e.getMessage()); 
		} 
	} 
	// sets common storage to all the users and returns the storage object. 
	public Storage setStorage(ArrayList<String> devices, Integer devices_count) 
	{
		Storage storage = new Storage(devices, devices_count); 
		User.setStorage(storage); 
		return storage; 
	} 
	public HashMap<String, User> genrateUsersFromUsersId(ArrayList<String> users_ids) 
	{ 
		HashMap<String, User> usersId = new HashMap<>(); 
		for (int i = 0; i < users_ids.size(); i++) 
		{ 
			usersId.put(users_ids.get(i), new User()); 
		} 
		return usersId; 
	} 
	public static void main(String[] args) 
	{ 
		Main main = new Main(); 
		Console console = new Console(); // Initializing data from configproperties file. 
		main.initialiseData(); // set common storage to all the users and returns the storage object. 
		Storage storage = main.setStorage(main.devices, main.devices_count); // userid is a key and user object as a value 
		HashMap<String, User> usersDB = main.genrateUsersFromUsersId(main.users_ids); 
		try (Scanner scanner = new Scanner(System.in)) 
		{ 
			System.out.println("1. for user"); 
			System.out.println("2. for admin"); 
			int task_number = scanner.nextInt(); 
			switch (task_number) 
			{ 
				case 1:
					console.userConsole(usersDB);
					break; 
				case 2: 
					console.adminConsole(storage); 
					break; 
				default: 
					break; 
			} 
		} 
	} 
}
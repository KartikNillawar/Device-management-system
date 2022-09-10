import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Console {
	public void userConsole(HashMap<String, User> users_id) 
	{ 
		final int tcount = Runtime.getRuntime().availableProcessors(); 
		ExecutorService executorService = Executors.newFixedThreadPool(tcount); 
		try (Scanner scanner = new Scanner(System.in)) 
		{ 
			while (true) 
			{ 
				System.out.println("Enter User Id"); 
				String id = scanner.next(); 
				User user = users_id.get(id); 
				if (user == null) 
				{ 
					throw new UserNotFoundException("User not found in database."); 
				} 
			System.out.println("Enter the device name"); 
			String deviceName = scanner.next(); 
			System.out.println("Enter the operation do you want to perform"); 
			System.out.println("1. Assign " + deviceName); 
			System.out.println("2. Unassign " + deviceName); 
			System.out.println("3. Get count of " + deviceName);
			int action_id = scanner.nextInt(); 
			Boolean isAssinged = false; 
			Boolean isUnAssinged = false; 
			Future<Boolean> future; 
			switch (action_id) 
			{ 
			case 1: 
				future = executorService.submit(() -> user.assignDevice(deviceName)); 
				try 
				{ 
					isAssinged = future.get(); 
				} 
				catch (InterruptedException | ExecutionException e) 
				{ // TODO Auto-generated catch block 
					System.out.println(e.getMessage()); 
				} 
				if (isAssinged) 
					System.out.println(deviceName + " has been assigned"); 
				else 
					System.out.println(deviceName + " has not been assigned"); 
				break; 
			case 2: 
				future = executorService.submit(() -> user.unassignDevice(deviceName)); 
				try { 
					isUnAssinged = future.get(); 
				} 
				catch (InterruptedException | ExecutionException e) 
				{ // TODO Auto-generated catch block 
					System.out.println(e.getMessage()); 
				} 
				if (isUnAssinged) 
					System.out.println(deviceName + " has been unassigned");
				else 
					System.out.println(deviceName + " has not been unassigned"); 
				break; 
			case 3: 
				Future<Integer> future1; 
				Integer deviceCount = 0; 
				future1 = executorService.submit(() -> { DeviceCount device = user.getDevice(deviceName); 
				if (device != null) 
					return device.getAvailable_count(); 
				else 
					return 0; 
				}); 
				try 
				{ 
					deviceCount = future1.get(); 
				} 
				catch (InterruptedException | ExecutionException e) 
				{ // TODO Auto-generated catch block 
					System.out.println(e.getMessage()); 
				} 
				if (deviceCount != 0) 
					System.out.println("User of id " + id + " has a " + deviceName + " count " + deviceCount); 
				break; 
			default: 
				break; 
			} 
		} 
			} 
		catch (UserNotFoundException e) 
		{ // TODO Auto-generated catch block 
			System.out.println(e.getMessage()); 
		} 
	}

	public void adminConsole(Storage storage) 
	{ 
		final int tcount = Runtime.getRuntime().availableProcessors(); 
		ExecutorService executorService = Executors.newFixedThreadPool(tcount); 
		Future<HashMap<String, DeviceCount>> future; 
		try (Scanner scanner = new Scanner(System.in)) 
		{ 
			while (true) 
			{ 
				System.out.println("Enter the no. of operation do you want to perform"); 
				Integer number = scanner.nextInt(); 
				System.out.println("1. Get all devices that are already assigned and their count."); 
				System.out.println("2. Get all devices that are already assigned and their count."); 
				HashMap<String, DeviceCount> device_map = null; 
				switch (number) 
				{ 
				case 1: 
					future = executorService.submit(() -> storage.getAllAvailableDevicesCount()); 
					try 
					{ 
						device_map = future.get(); 
					} 
					catch (InterruptedException | ExecutionException e) 
					{ // TODO Auto-generated catch block 
						e.printStackTrace(); 
					} 
					device_map.forEach((key, value) -> System.out.println(key + " " + value.getAvailable_count())); 
					break; 
				case 2: 
					future = executorService.submit(() -> storage.getAllAvailableDevicesCount()); 
					device_map = null; 
					try 
					{ 
						device_map = future.get(); 
					} 
					catch (InterruptedException | ExecutionException e) 
					{ // TODO Auto-generated catch block 
						e.printStackTrace(); 
					} 
					device_map.forEach((key, value) -> System.out .println(key + " " + (value.totalCount() - value.getAvailable_count()))); 
					break; 
				default: 
					break; 
				} 
			}
		}
	}
	}

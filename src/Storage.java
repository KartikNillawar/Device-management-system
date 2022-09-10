import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
	HashMap<String, DeviceCount> device_map = new HashMap<>();
// to map devicename to the device object. 
	Integer total_device_count;

//initialise the device name w.r.t to their object 
	public Storage(ArrayList<String> devices, Integer total_device_count) {
		for (String device : devices) {
			device_map.put(device, new DeviceCount(total_device_count));
		}
		this.total_device_count = total_device_count;
	}

//returns the device if present in storage else throws device not available exception. 
	public DeviceCount getDevice(String device_name) throws DeviceNotAvailableException {
		if (device_map.containsKey(device_name))
			return device_map.get(device_name);
		else {
			throw new DeviceNotAvailableException(device_name + " not available.");
		}
	}

//all the devices that are unassigned and their count. 
	public HashMap<String, DeviceCount> getAllAvailableDevicesCount() {
		device_map.forEach((key, value) -> System.out.println(key + " " + value.getAvailable_count()));
		return device_map;
	} // returns all devices that are already assigned and their count

	public HashMap<String, DeviceCount> getAllAssingnedDevicesCount() {
		device_map.forEach(
				(key, value) -> System.out.println(key + " " + (value.totalCount() - value.getAvailable_count())));
		return device_map;
	}
}
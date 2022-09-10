import java.util.HashMap;

public class User {
	static Storage commonStorage; // used by all the users.
	HashMap<String, DeviceCount> userDeviceMap;

	// mapping device name and their count.
	public User() {
		userDeviceMap = new HashMap<>();
	} // returns a device object if present else throws DeviceNotAvailableException

	public DeviceCount getDevice(String deviceName) {
		try {
			if (userDeviceMap.containsKey(deviceName))
				return userDeviceMap.get(deviceName);
			else {
				throw new DeviceNotFoundException("Device not found");
			}
		} catch (Exception e) { // TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;
	}

	// This method assign a device to the user if device is present in // storage
	// else throws device not available exception
	public boolean assignDevice(String deviceName) throws DeviceNotAvailableException {
		synchronized (commonStorage.getDevice(deviceName)) {
			commonStorage.getDevice(deviceName).Decrement();
			DeviceCount device = userDeviceMap.getOrDefault(deviceName, new DeviceCount(0));
			device.Increment();
			userDeviceMap.put(deviceName, device);
			return true;
		}
	}

	// This method takes back the device object from the user and add it to the //
	// storage object.
	public boolean unassignDevice(String deviceName) throws DeviceNotAvailableException {
		if (userDeviceMap.containsKey(deviceName)) {
			DeviceCount device = userDeviceMap.get(deviceName);
			device.Decrement();
			userDeviceMap.put(deviceName, device);
			commonStorage.getDevice(deviceName).Increment();
			return true;
		} else {
			throw new DeviceNotAvailableException(deviceName + "is not been present");
		}
	}

	// returns the available count of that device.
	public Integer getDeviceCount(String deviceName) {
		return userDeviceMap.get(deviceName).getAvailable_count();
	} // to set the storage object

	public static void setStorage(Storage storage) {
		User.commonStorage = storage;
	}
}

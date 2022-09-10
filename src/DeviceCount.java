
public class DeviceCount {
	private Integer available_count;
	private Integer total_device_count;

	public DeviceCount(Integer count) {
		this.available_count = count;
		this.total_device_count = count;
	}

	public Integer totalCount() {
		return total_device_count;
	}

	public Integer getAvailable_count() {
		return available_count;
	}

	public void Increment() {
		available_count++;
	}

	public void Decrement() throws DeviceNotAvailableException {
		if (available_count == 0)
			throw new DeviceNotAvailableException(" device is not available now");
		available_count--;
	}
}
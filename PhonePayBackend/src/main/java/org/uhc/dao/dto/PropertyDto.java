package org.uhc.dao.dto;

public class PropertyDto {
	
	private String address;
	private String city;
	private String state;
	private String zip;
	
	public String getAddress() {
		return address.trim();
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city.trim();
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state.trim();
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip.trim();
	}
	public void setZip(String zipCode) {		
		this.zip =  zipCode.substring(0, 5);
	}
	
}

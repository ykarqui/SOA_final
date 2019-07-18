package ar.edu.iua.soa.cse.model.dto;

public class UserDTO {
	private String username;
	private int days;
	
	public UserDTO(String username, int days) {
		super();
		this.username = username;
		this.days = days;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
}

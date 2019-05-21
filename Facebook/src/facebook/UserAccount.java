package facebook;

import java.io.Serializable;
import java.util.Objects;

/*
 * Abstract method for a user account
 * Contains:
 *    -Username
 *    -Password
 *    -Active
 *    -Hashcode & Equals based on username
 */
public abstract class UserAccount implements Serializable {
	private static final long serialVersionUID = 5929869879325998576L;
	private String username;
	private String password;
	private boolean active;
	
	UserAccount(String username, String password){
		this.username = username;
		this.password = password;
		this.active = true;
	}
	
	public boolean checkPassword(String password) {
		if(Objects.equals(password, this.password))
			return true;
		return false;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public abstract void getPasswordHelp();
	
	public void activeAccount() {
		this.active = true;
	}
	
	public void deactivateAccount() {
		this.active = false;
	}
	
	public boolean isActive() {
		return this.active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account: " + this.username;
	}
}

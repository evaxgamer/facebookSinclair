package facebook;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Extension of userAccount to create a FacebookUser Account
 * Contains
 *    -passwordHint
 *    -friendList
 *    -likeList
 */
public class FacebookUser extends UserAccount implements Comparable<FacebookUser>, Cloneable, Serializable {
	private static final long serialVersionUID = -5152527929140331393L;
	private String passwordHint;
	private ArrayList<FacebookUser> friendList;
	private ArrayList<String> likeList;
	
	public FacebookUser(String username, String password, String passwordHint) {
		super(username, password);
		this.passwordHint = passwordHint;
		this.friendList = new ArrayList<>();
		this.likeList = new ArrayList<>();
	}
	
	public void getPasswordHelp() {
		System.out.println("Password Hint: " + this.passwordHint);
	}
	
	public void friend(FacebookUser newFriend) {
		this.friendList.add(newFriend);
	}
	
	public void defriend(FacebookUser formerFriend) {
		this.friendList.remove(formerFriend);
	}
	
	public ArrayList<FacebookUser> getFriends(){
		ArrayList<FacebookUser> friendsClone = new ArrayList<>();
		for(FacebookUser friend: this.friendList)
			friendsClone.add(friend);
		return friendsClone;
	}
	
	public void like(String like) {
		likeList.add(like);
	}
	
	public void unlike(String unlike) {
		likeList.remove(unlike);
	}
	
	public ArrayList<String> getLikeList(){
		ArrayList<String> likeListClone = new ArrayList<>();
		for(String like: this.likeList)
			likeListClone.add(like);
		return likeListClone;
	}

	@Override
	public int compareTo(FacebookUser order) {
		if(getUsername().compareToIgnoreCase(order.getUsername())!=0)
			return getUsername().compareTo(order.getUsername());
		return 0;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		FacebookUser facebookUserClone = new FacebookUser(getUsername(), getPassword(), this.passwordHint);
		for(FacebookUser friend: this.friendList)
			facebookUserClone.friend(friend);
		for(String like: this.likeList)
			facebookUserClone.like(like);
		return facebookUserClone;
	}
}

package facebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * Facebook Class(Actions pertaining to facebook)
 */
public class Facebook implements Serializable {
	private static final long serialVersionUID = -4874358948311702626L;
	private ArrayList<FacebookUser> usersList;
	private TreeMap<String, ArrayList<FacebookUser>> likeMap;
	
	public Facebook(){
		this.usersList = new ArrayList<>();
		this.likeMap = new TreeMap<>();
	}
	
	public FacebookUser getUserClone(FacebookUser user) throws CloneNotSupportedException {
		FacebookUser userClone = (FacebookUser) this.usersList.get(usersList.indexOf(user)).clone();
		return userClone;
	}
	
	public void listUsers(int userInput) {
		switch(userInput) {
			case 1:
				Collections.sort(this.usersList);
				for(FacebookUser i: this.usersList)
					System.out.println("\n" + i);
				break;
			case 2:
				Collections.sort(this.usersList, new Utilities());
				for(FacebookUser i: this.usersList)
					System.out.println("\n" + i + "\n\tFriends: " + i.getFriends().size());
				break;
		}
	}
	
	public boolean addUser(FacebookUser user) {
		if(usersList.contains(user))
			return false;
		usersList.add(user);
		return true;
	}
	
	public void removeUser(FacebookUser user) {
		this.usersList.remove(user);
	}
	
	public boolean addFriend(FacebookUser user, FacebookUser newFriend) {
		if(usersList.get(usersList.indexOf(user)).getFriends().contains(newFriend) || !usersList.contains(newFriend))
			return false;
		usersList.get(usersList.indexOf(user)).friend(newFriend);
		return true;
	}
	
	public boolean removeFriend(FacebookUser user, FacebookUser formerFriend) {
		if(!usersList.get(usersList.indexOf(user)).getFriends().contains(formerFriend) || !usersList.contains(formerFriend))
			return false;
		usersList.get(usersList.indexOf(user)).defriend(formerFriend);
		return true;
	}
	
	public void listFriends(FacebookUser user) {
		for(FacebookUser insideFriends: usersList.get(usersList.indexOf(user)).getFriends())
			System.out.println(insideFriends);
	}
	
	public TreeSet<FacebookUser> getRecommendedFriends(FacebookUser user, int depth){
		TreeSet<FacebookUser> recommendedFriends = new TreeSet<>();
		depth--;
		for(FacebookUser friend: usersList.get(usersList.indexOf(user)).getFriends()) {
			recommendedFriends.add(friend);
			if(depth != 0) {
				TreeSet<FacebookUser> nextLevel = getRecommendedFriends(friend, depth);
				recommendedFriends.addAll(nextLevel);
			}
		}
		return recommendedFriends;
	}
	
	public String like(FacebookUser user, String likeList) {
		System.out.println(likeList);
		String[] split = likeList.split(" ");
		if((split[0].toLowerCase()).equals("add") || (split[0].toLowerCase()).equals("remove")) {
			if((split[0].toLowerCase()).equals("add"))
				for(int i = 1; i < split.length; i++)
					addLike(user, split[i]);
			else if((split[0].toLowerCase()).equals("remove"))
				for(int i = 1; i < split.length; i++)
					removeLike(user,split[i]);
			if((split[0].toLowerCase()).equals("add"))
				split[0] = "remove";
			else
				split[0] = "add";
			String s = "";
			for(int i = 1; i < split.length; i++)
				s+=split[i] + " ";
			return split[0] + " " + user.getUsername() + " " + s;
		} else {
			System.out.println("Unable to determine add/remove method!");
			return "";
		}
	}

	public void addLike(FacebookUser user, String like) {
		ArrayList<FacebookUser> likeUserList;
		if(!likeMap.containsKey(like)) {
			likeUserList = new ArrayList<>();
			likeUserList.add(user);
			usersList.get(usersList.indexOf(user)).like(like.toLowerCase());
			likeMap.put(like, likeUserList);
			return;
		} else {
			likeUserList = likeMap.get(like);
			if(!likeUserList.contains(user)) {
				likeUserList.add(user);
				usersList.get(usersList.indexOf(user)).like(like.toLowerCase());
				likeMap.put(like, likeUserList);
				return;
			}
		}
		System.out.println("Unable to add like " + like);
		return;
	}
	
	public void removeLike(FacebookUser user, String unlike) {
		ArrayList<FacebookUser> likeUserList;
		if(likeMap.containsKey(unlike)) {
			likeUserList = likeMap.get(unlike);
			if(likeUserList.contains(user)) {
				likeUserList.remove(user);
				usersList.get(usersList.indexOf(user)).unlike(unlike.toLowerCase());
				likeMap.put(unlike, likeUserList);
				if(likeUserList.isEmpty())
					likeMap.remove(unlike);
				return;
			}
		}
		System.out.println("Unable to remove like " + unlike);
		return;
	}
	
	public void listLikeMap(String viewType) {
		TreeSet<FacebookUser> usersThatLike = new TreeSet<>();
		if((viewType.toLowerCase()).equals("revealed")) {
			System.out.print("\nThings Liked\tWhoLikes");
			for(String key: likeMap.keySet()) {
				System.out.print("\n" + key);
				usersThatLike.addAll(likeMap.get(key));
				if(key.length() < 8)
					System.out.print("\t");
				System.out.print("\t" + usersThatLike);
				usersThatLike.clear();
			}
			System.out.println("\n");
		} else {
			System.out.print("\nThings Liked\tAmount of Likes");
			for(String key: likeMap.keySet()) {
				System.out.print("\n" + key);
				usersThatLike.addAll(likeMap.get(key));
				if(key.length() < 8)
					System.out.print("\t");
				System.out.print("\t" + usersThatLike.size());
				usersThatLike.clear();
			}
			System.out.println("\n");
		}
	}
	
	/*
	 * Limit of 2 was placed on this due to large numbers at a depth of 3 was going of screen
	 */
	public GraphViewer display(Facebook facebook, FacebookUser user, String depth) throws NumberFormatException, CloneNotSupportedException {
			if(!depth.equals("0") && !depth.equals("1") && !depth.equals("2")) {
				System.out.println("Please choose a depth between 0 and 2");
				return null;
			}
			System.out.println("Displaying graph for " + user.toString());
			return new GraphViewer(facebook, user, Integer.parseInt(depth));
	}
}

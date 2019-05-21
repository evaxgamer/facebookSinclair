package facebook;

/*
 * Concrete version of Redo/Undo
 * Saves redo's and undo's as strings
 * can undo everything you've ever done
 * can only redo as long as a new action is not performed
 * 		-Undoing is not considered a new action as it adds to the redo stack
 */
public class DoList extends DoListAbstract {
	public <E> void undo(E e) {
		String s, like1, like2;
		String[] split;
		@SuppressWarnings("unused")
		FacebookUser userClone, user, friend;
		s = getUndo().remove(getUndo().size()-1);
		split = s.split(" ");
		try {
			userClone = ((Facebook) e).getUserClone(new FacebookUser(split[1], null, null));
			System.out.println(split[0] + " for " + userClone);
			if(userClone.checkPassword(Menu.getInput("a password")))
				switch(split[0]) {
					case "addFriend":
						friend = new FacebookUser(split[2], null, null);
						addRedo("removeFriend " + split[1] + " " + split[2]);
						break;
					case "removeFriend":
						friend = new FacebookUser(split[2], null, null);
						addRedo("addFriend " + split[1] + " " + split[2]);
						break;
					case "removeUser":
						((Facebook) e).removeUser(userClone);
						addRedo("addUser " + split[1]);
						break;
					case "add":
						like1 = split[0] + " ";
						like2 = "";
						for(int i = 2; i < split.length; i++)
							like2 += split[i] + " ";
						((Facebook) e).like(userClone, like1 + like2);
						addRedo("remove " + userClone.getUsername() + " " + like2);
						break;
					case "remove":
						like1 = split[0] + " ";
						like2 = "";
						for(int i = 2; i < split.length; i++)
							like2 += split[i] + " ";
						((Facebook) e).like(userClone, like1 + like2);
						addRedo("add " + userClone.getUsername() + " " + like2);
						break;
				} else {
					System.err.println("Invalid Password!");
				}
		} catch(IndexOutOfBoundsException ex) {
			System.out.println(split[0] + " for " + split[1]);
			user = new FacebookUser(split[1], Menu.getInput("a password"), Menu.getInput("a password hint"));
			((Facebook) e).addUser(user);
			addRedo("removeUser " + split[1]);
		} catch(CloneNotSupportedException ex) {
			System.err.println("Unable to do undo!");
		}
	}
	
	public <E> void redo(E e) {
		String s, like1, like2;
		String[] split;
		@SuppressWarnings("unused")
		FacebookUser userClone, user, friend;
		s = getRedo().remove(getRedo().size()-1);
		split = s.split(" ");
		try {
			userClone = ((Facebook) e).getUserClone(new FacebookUser(split[1], null, null));
			System.out.println(split[0] + " for " + userClone);
			if(userClone.checkPassword(Menu.getInput("a password")))
				switch(split[0]) {
					case "addFriend":
						friend = new FacebookUser(split[2], null, null);
						addUndo("removeFriend " + split[1] + " " + split[2]);
						break;
					case "removeFriend":
						friend = new FacebookUser(split[2], null, null);
						addUndo("addFriend " + split[1] + " " + split[2]);
						break;
					case "removeUser":
						((Facebook) e).removeUser(userClone);
						addUndo("addUser " + split[1]);
						break;
					case "add":
						like1 = split[0] + " ";
						like2 = "";
						for(int i = 2; i < split.length; i++)
							like2 += split[i] + " ";
						((Facebook) e).like(userClone, like1 + like2);
						addUndo("remove " + userClone.getUsername() + " " + like2);
						break;
					case "remove":
						like1 = split[0] + " ";
						like2 = "";
						for(int i = 2; i < split.length; i++)
							like2 += split[i] + " ";
						((Facebook) e).like(userClone, like1 + like2);
						addUndo("add " + userClone.getUsername() + " " + like2);
						break;
				} else {
					System.err.println("Invalid Password!");
				}
		} catch(IndexOutOfBoundsException ex) {
			System.out.println(split[0] + " for " + split[1]);
			user = new FacebookUser(split[1], Menu.getInput("a password"), Menu.getInput("a password hint"));
			((Facebook) e).addUser(user);
			addUndo("removeUser " + split[1]);
		} catch(CloneNotSupportedException ex) {
			System.err.println("Unable to do redo!");
		}
	}
}

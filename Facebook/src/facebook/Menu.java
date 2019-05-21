package facebook;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * User menu
 *    		----------MENU----------
 *			 1. List Users by name
 *			 2. List users by friends
 *			 3. Add a User
 *			 4. Delete a User
 *			 5. Get Password Hint
 *			 6. Friend Someone
 *			 7. De-Friend Someone
 *			 8. List Friends
 * 		     9. Recommend New Friends
 *			 10. Like Access Menu
 *			 11. List all Likes
 *			 12. Undo
 *			 13. Redo
 *			 14. Graph
 *			 15. Quit
 * 
 */
public class Menu {
	static Scanner input = new Scanner(System.in);
	public static Facebook menuSwitch(Facebook facebook) {
		int userInput = 0;
		String usernameStr = "a username", passwordStr = "a password", passwordHintStr = "a password hint", depth = "10", exit1 = "return to main menu!", exit2 = "continue!";
		FacebookUser user, userClone = null, friend;
		DoList doList = new DoList();
		GraphViewer graph = null;
		while(userInput != 15) {
			userInput = menu();
			if((userInput >= 4 && userInput <= 10) || userInput == 14) {
				try {
					userClone = facebook.getUserClone(new FacebookUser(getInput(usernameStr), null, null));
				} catch(CloneNotSupportedException e) {
					System.err.println("User not Found!");
					userInput = 0;
				} catch(IndexOutOfBoundsException e) {
					System.err.println("User not Found!");
					userInput = 0;
				}
			}
			switch(userInput) {
				//List Users by name
				case 1:
					facebook.listUsers(userInput);
					exitMenuOption(exit1);
					break;
				//List users by friends
				case 2:
					facebook.listUsers(userInput);
					exitMenuOption(exit1);
					break;
				//Add a User
				case 3:
					user = new FacebookUser(getInput(usernameStr), getInput(passwordStr), getInput(passwordHintStr));
					if(!facebook.addUser(user)) {
						System.err.println("Unable to add user!");
						break;
					}
					doList.addUndo("removeUser " + user.getUsername());
					doList.clearRedo();
					exitMenuOption(exit1);
					break;
				//Delete a User
				case 4:
						if(userClone.checkPassword(getInput(passwordStr))) {
							facebook.removeUser(userClone);
							doList.addUndo("addUser " + userClone.getUsername());
							doList.clearRedo();
						} else {
							System.err.println("Invalid Password!");
						}
					exitMenuOption(exit1);
					break;
				//Get Password Hint
				case 5:
					userClone.getPasswordHelp();
					exitMenuOption(exit1);
					break;
				//Friend Someone
				case 6:
					if(userClone.checkPassword(getInput(passwordStr))) {
						System.out.println("Who would you like to friend: ");
						friend = new FacebookUser(getInput(usernameStr), null, null);
						if(!facebook.addFriend(userClone, friend)) {
							System.err.println("Unable to add friend!");
							break;
						}
						doList.addUndo("removeFriend " + userClone.getUsername() + " " + friend.getUsername());
						doList.clearRedo();
					} else {
						System.err.println("Invalid Password!");
					}
					exitMenuOption(exit1);
					break;
				//De-Friend Someone
				case 7:
					if(userClone.checkPassword(getInput(passwordStr))) {
						System.out.println("Who would you like to defriend: ");
						friend = new FacebookUser(getInput(usernameStr), null, null);
						if(!facebook.removeFriend(userClone, friend)) {
							System.err.println("Unable to remove friend!");
							break;
						}
						doList.addUndo("addFriend " + userClone.getUsername() + " " + friend.getUsername());
						doList.clearRedo();
					} else {
						System.err.println("Invalid Password!");
					}
					exitMenuOption(exit1);
					break;
				//List Friends
				case 8:
					if(userClone.checkPassword(getInput(passwordStr)))
						facebook.listFriends(userClone);
					else
						System.err.println("Invalid Password!");
					exitMenuOption(exit1);
					break;
				//Recommend New Friends
				case 9:
					if(userClone.checkPassword(getInput(passwordStr))) {
						System.out.println("Recommended Friends: ");
						for(FacebookUser recommendation: facebook.getRecommendedFriends(userClone, 3))
							System.out.println(recommendation);
					} else {
						System.err.println("Invalid Password!");
					}
					exitMenuOption(exit1);
					break;
				//Like Access Menu
				case 10:
					if(userClone.checkPassword(getInput(passwordStr))) {
						System.out.println("\nLike access menu: \n\t-To remove a like, type remove followed by your like"
								+"\n\t-To add a like, type add followed by your like\n\t-Example \"add apple\" or \"remove apple\""
								+"\n\t-You can also chain multiple removes and adds together for example \"add apple cat dog ocean\"");
						String like = getInput("users likes");
						String likeUndo = facebook.like(userClone, like);
						exitMenuOption(exit2);
						System.out.println("Here is user " + userClone.getUsername() + " list of likes:");
						try {
							getLikeList(facebook, userClone);
						} catch (CloneNotSupportedException e) {
							System.err.println("Unable to get like list!");
						}
						if(!likeUndo.equals("")) {
							doList.addUndo(likeUndo);
							doList.clearRedo();
						}
					} else {
						System.err.println("Invalid Password!");
					}
					exitMenuOption(exit1);
					break;	
				//List All Likes
				case 11:
					System.out.println("\nLike list menu: \n\t-There are two types of viewTypes: \n\t\t+default\n\t\t+revealed"
							+"\n\t-To see revealed viewType please type \"revealed\" otherwise just press enter to continue. . .");
					String viewType = getInput("view type");
					facebook.listLikeMap(viewType);
					exitMenuOption(exit1);
					break;
				//Undo
				case 12:
					try {
						doList.undo(facebook);
					} catch (IndexOutOfBoundsException e) {
						System.err.println("No available undo's!");
					}
					exitMenuOption(exit1);
					break;
				//Redo
				case 13:
					try {
						doList.redo(facebook);
					} catch (IndexOutOfBoundsException e) {
						System.err.println("No available redo's!");
					}
					exitMenuOption(exit1);
					break;	
				//Graph
				case 14:
					if(userClone.checkPassword(getInput(passwordStr))) {
						try {
							while(!depth.equals("0") && !depth.equals("1") && !depth.equals("2")) {
								depth = getInput("a depth");
								graph = facebook.display(facebook, userClone, depth);
							}
						} catch(NumberFormatException e) {
							System.err.println("Invalid depth entry.\nPlease enter a 1 or a 2!");
						} catch(CloneNotSupportedException e) {
						}
					} else {
						System.err.println("Invalid Password!");
					}
					exitMenuOption(exit1);
					depth = "-1";
					if (graph != null) {
						graph.dispose();
					}
					break;
			}
		}
		input.close();
		return facebook;		
	}
	
	/*
	 * Get Like List Clone
	 */
	public static void getLikeList(Facebook facebook, FacebookUser user) throws CloneNotSupportedException {
		ArrayList<String> likeList = facebook.getUserClone(user).getLikeList();
		for(String insideLikeList: likeList)
			System.out.println(insideLikeList);
	}
	
	/*
	 * Get user input as a String
	 */
	public static String getInput(String inputType) {
		System.out.println("Please enter " + inputType + ": ");
		return input.nextLine();
	}
	
	
	/*
	 * Display Menu and get menu input
	 */
	public static int menu() {
		int userInput = 0;
		boolean repeat = true;
		System.out.println("\n----------MENU----------"
						 + "\n1. List Users by name"
						 + "\n2. List users by friends"
						 + "\n3. Add a User"
						 + "\n4. Delete a User"
						 + "\n5. Get Password Hint"
						 + "\n6. Friend Someone"
						 + "\n7. De-Friend Someone"
						 + "\n8. List Friends"
						 + "\n9. Recommend New Friends"
						 + "\n10. Like Access Menu"
						 + "\n11. List all Likes"
						 + "\n12. Undo"
						 + "\n13. Redo"
						 + "\n14. Graph"
						 + "\n15. Quit\n\n");
		while(repeat)
			try {
				userInput = input.nextInt();
				input.nextLine();
				repeat = false;
			} catch(InputMismatchException e) {
				System.out.println("Entered a non integer value!"
								 + "\nPlease enter a integer 1-15 from the menu options: ");
				input.nextLine();
			}
		return userInput;
	}
	
	/*
	 * Exit menu option to hold user in a menu option till they are done viewing or using content
	 */
	public static void exitMenuOption(String exitMenuOption) {
		int userInput = 0;
		System.out.println("Please enter 1 to " + exitMenuOption);
		while(userInput != 1)
			try {
				userInput = input.nextInt();
				if(userInput != 1)
					System.err.println("Please enter 1 to " + exitMenuOption);
			} catch(InputMismatchException e) {
				System.err.println("Invalid input please enter a 1 to " + exitMenuOption);
				input.nextLine();
			}
	}
}

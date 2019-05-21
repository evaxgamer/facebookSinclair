package facebook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 * Extra utilities methods used through out the program.
 * 
 */
public class Utilities implements Comparator<FacebookUser> {
	public <E> ArrayList<E> removeDuplicates(ArrayList<E> list){
		Set<E> removeDups = new LinkedHashSet<>(list);
		for(E inside: list)
			removeDups.add(inside);
		list.clear();
		list.addAll(removeDups);
		return list;
	}
	
	public <E extends Comparable<E>> int linearSearch(E[] list, E key) {
		for(int i = 0; i < list.length; i++)
			if(key.compareTo(list[i])==0)
				return i;
		return -1;
	}
	
//	public <E> E insertionSort(E[] e) {
//		return (E) e;
//	}
	
	public int compare(FacebookUser user1, FacebookUser user2) {
		return user2.getFriends().size() - user1.getFriends().size();
	}
}

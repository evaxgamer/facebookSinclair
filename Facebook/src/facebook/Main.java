/*
 * Studen: Aaron Price
 * CIS.2217.101
 * Eleventh Assignment - Two Options -
 * Professor: Reece Newman
 */
package facebook;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {
	public static void main(String[] args) {
		String file = "userAccountSerialized.dat";
		Facebook facebook = new Facebook();
		facebook = (Facebook)open(facebook, file);
		facebook = Menu.menuSwitch(facebook);
		close(facebook, file);
	}
	
	/*
	 * Open file
	 */
	@SuppressWarnings("unchecked")
	public static <E> Object open(E e, String file) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			e = (E)ois.readObject();
			ois.close();
		} catch(FileNotFoundException ex) {
			System.err.println("Could not open the file \"userAccountSerialized\"");
		} catch(IOException ex) {
			System.err.println("Could not de-serialize the object");
		} catch(ClassNotFoundException ex) {
			System.err.println("Could not cast the de-serialized object");
		}
		return e;
	}
	
	/*
	 * Close file
	 */
	public static <E> void close(E e, String file) {
		try {
			ObjectOutputStream bos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			bos.writeObject(e);
			bos.close();
		} catch(FileNotFoundException ex) {
			System.err.println("Could not create the file \"userAccountSerialized\"");
		} catch(IOException ex) {
			System.err.println("Could not serialize the object");
		}
	}
}

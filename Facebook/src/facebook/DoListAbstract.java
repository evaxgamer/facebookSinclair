package facebook;

import java.util.ArrayList;

/*
 * Abstract version of do for redo/undo
 */
public abstract class DoListAbstract {
	private ArrayList<String> undo;
	private ArrayList<String> redo;
	
	DoListAbstract(){
		this.undo = (new ArrayList<>());
		this.redo = (new ArrayList<>());
	}
	
	public void addUndo(String s) {
		this.undo.add(s);
	}
	
	public ArrayList<String> getUndo() {
		return undo;
	}

	public void addRedo(String s) {
		this.redo.add(s);
	}
	
	public ArrayList<String> getRedo() {
		return redo;
	}

	public void clearRedo() {
		getRedo().clear();
	}
	
	public abstract <E> void undo(E e);
	
	public abstract <E> void redo(E e);
}

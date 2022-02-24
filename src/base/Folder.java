package base;

import java.util.ArrayList;
import java.util.Objects;

public class Folder {

	private ArrayList<Note> notes;
	private String name;
	
	public Folder(String name) {
		this.name = name;
		notes = new ArrayList<Note>();
	}
	
	public void addNote(Note n) {
		notes.add(n);
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Note> getNotes() {
		return notes;
	}
	
	@Override
	public String toString() {
		int nText = 0;
		int nImage = 0;
		
		for (Note n: notes) {
			if (n instanceof TextNote) {
				nText++;
			}
			else if (n instanceof ImageNote) {
				nImage++;
			}
		}
		
		return name + ":" + nText + ":" + nImage;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, notes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Folder)) {
			return false;
		}
		Folder other = (Folder) obj;
		return Objects.equals(name, other.name);
	}
}

package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Folder implements Comparable<Folder>{

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
	
	public void sortNotes() {
		Collections.sort(notes);
	}
	
	public List<Note> searchNotes(String keywords) {
		List<Note> output = new ArrayList<Note>();
		ArrayList<Integer> orLocations = new ArrayList<Integer>();
		List<String> tokens = Arrays.asList(keywords.split(" "));
		
		// Go through list and identify the positions of the ORs
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).equalsIgnoreCase("or")) {
				orLocations.add(i);
			}
		}
		
		for (Note n : notes) {
			for (int j = 0; j < orLocations.size(); j ++) {
				if (!(n.getTitle().toLowerCase().contains(tokens.get(orLocations.get(j)-1).toLowerCase()))
						&& !(n.getTitle().toLowerCase().contains(tokens.get(orLocations.get(j)+1).toLowerCase()))) {
					if (n instanceof TextNote) {
						if (!(((TextNote) n).getContent().toLowerCase().contains(tokens.get(orLocations.get(j)-1).toLowerCase()))
								&& !(((TextNote) n).getContent().toLowerCase().contains(tokens.get(orLocations.get(j)+1).toLowerCase()))) {
							break;
						}
					}
					else {
						break;
					}
				}
				if (j == orLocations.size() - 1) {
					output.add(n);
				}
			}
		}
		
		return output;
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
	
	@Override
	public int compareTo(Folder o) {
		if (this.name.compareTo(o.name) > 0) {
			return 1;
		}
		else if (this.name.compareTo(o.name) < 0) {
			return -1;
		}
		else {
			return 0;
		}
	}
}

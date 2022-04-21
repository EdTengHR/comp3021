package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Folder implements Comparable<Folder>, java.io.Serializable {

	private ArrayList<Note> notes;
	private String name;
	private static final long serialVersionUID = 1L;
	
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
	
	public boolean deleteNote(String noteTitle) {
		for (Note n : notes) {
			if (n.getTitle().equals(noteTitle)) {
				notes.remove(n);
				return true;
			}
		}
		return false;
	}
	
	public List<Note> searchNotes(String keywords) {
		List<Note> intermediate = new ArrayList<Note>();
		List<Note> output = new ArrayList<Note>();
		ArrayList<Integer> orLocations = new ArrayList<Integer>();
		ArrayList<Integer> optionalKeyIndex = new ArrayList<Integer>();		
		ArrayList<Integer> requiredKeyIndex= new ArrayList<Integer>();		// Indices for required keywords
		List<String> tokens = Arrays.asList(keywords.split(" "));
		
		// Go through list and identify the positions of the ORs
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).equalsIgnoreCase("or")) {
				orLocations.add(i);
				optionalKeyIndex.add(i-1);
				optionalKeyIndex.add(i+1);
			}
		}
		
		for (int i = 0; i < tokens.size(); i++) {
			if (!orLocations.contains(i) && !optionalKeyIndex.contains(i)) {
				// For any values of i that aren't in the "or" index list or the optional keys list
				// These values would be the indices for the required keywords
				requiredKeyIndex.add(i);
			}
		}		
		
		// No OR conditions, the string is all just ANDs
		if (orLocations.size() == 0){
			for (Note n : notes) {
				for (int j = 0; j < tokens.size(); j++) {
					if (!(n.getTitle().toLowerCase().contains(tokens.get(j).toLowerCase()))) {
						if (n instanceof TextNote) {
							if (!(((TextNote) n).getContent().toLowerCase().contains(tokens.get(j).toLowerCase()))) {
								break;
							}
						}
						else {
							break;
						}
					} 
					if (j == tokens.size() - 1) {
						output.add(n);
					}
				}
			}
			
			return output;
		}
		else {
			// First handle ORs
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
						intermediate.add(n);
					}
				}
			}
			
			// Then handle required keywords (ANDs)
			for (Note o : intermediate) {
				for (int j = 0; j < requiredKeyIndex.size(); j++) {
					if (!(o.getTitle().toLowerCase().contains(tokens.get(requiredKeyIndex.get(j))))) {
						if (o instanceof TextNote) {
							if (!(((TextNote) o).getContent().toLowerCase().contains(tokens.get(requiredKeyIndex.get(j)).toLowerCase()))) {
								break;
							}
						}
						else {
							break;
						}
					}
					if (j == requiredKeyIndex.size() - 1) {
						output.add(o);
					}
				}
			}
			
			return (output.isEmpty()) ? intermediate : output;
		}
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

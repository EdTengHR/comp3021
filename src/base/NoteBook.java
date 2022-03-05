package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteBook {

	private ArrayList<Folder> folders;
	
	public NoteBook () {
		folders = new ArrayList<Folder>();
	}
	
	public boolean createTextNote (String folderName, String title) {
		TextNote note = new TextNote(title);
		return insertNote(folderName, note);
	}
	
	public boolean createTextNote(String folderName, String title, String content) {
		TextNote note = new TextNote(title, content);
		return insertNote(folderName, note);
	}
	
	public boolean createImageNote (String folderName, String title) {
		ImageNote note = new ImageNote(title);
		return insertNote(folderName, note);
	}
	
	public ArrayList<Folder> getFolders() {
		return folders;
	}
	
	public boolean insertNote (String folderName, Note note) {
		Folder f = null;
		for (Folder f1: folders) {
			if (f1.getName().equals(folderName)) {
				f = f1;
				break;
			}
		}
		
		if (f == null) {
			f = new Folder(folderName);
			folders.add(f);
		}
		
		boolean noteFound = false;
		
		for (Note n: f.getNotes()) {
			if (n.equals(note)) {
				noteFound = true;
				break;
			}
		}
		
		if (noteFound) {
			System.out.println("Creating note " + note.getTitle() + " under folder "
					+ folderName + " failed");
		}
		else {
			f.addNote(note);
		}
		
		return !noteFound;
	}
	
	public void sortFolders() {
		for (Folder f: folders) {
			f.sortNotes();
		}
		Collections.sort(folders);
	}
	
	public List<Note> searchNotes (String keywords) {
		List<Note> output = new ArrayList<Note>();
		
		for (Folder f : folders) {
			output.addAll(f.searchNotes(keywords));
		}
		
		return output;
	}
}

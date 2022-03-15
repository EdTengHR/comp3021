package base;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NoteBook implements java.io.Serializable {

	private ArrayList<Folder> folders;
	private static final long serialVersionUID = 1L;
	
	public NoteBook () {
		folders = new ArrayList<Folder>();
	}
	
	/** 
	 * 
	 * constructor of an object NoteBook from an object serialization on disk
	 * @param file, path of the file for loading the object serialization
	 */
	public NoteBook (String file) {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		
		try {
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
			NoteBook nb = (NoteBook) in.readObject();
			this.folders = nb.folders;
			in.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
	
	/** 
	 * method to save the NoteBook instance to a file
	 * 
	 * @param file, the path of the file to save the object serialization
	 * @return true if save to file succeeds, false otherwise
	 */
	public boolean save (String file) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		
		try {
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}

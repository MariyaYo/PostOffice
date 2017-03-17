package post;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import post.Post.Letter;

public class PostOffice {

	public static boolean postOfficeIsEmpty;
	private ArrayBlockingQueue<Post> allLetters;
	private ConcurrentHashMap<Integer, ConcurrentSkipListSet<Post>> allPostBoxes;
	Random rn = new Random();
	
	public PostOffice(){
		allLetters = new ArrayBlockingQueue(50);
		allPostBoxes = new ConcurrentHashMap<>();
	}
	
	public void addPost(ConcurrentSkipListSet<Letter> letters){
		for(Letter l: letters){
			addOneLetter(l);
		}
	}
	
	public int getAllLettersSize(){
		return allLetters.size();
	}
	
	public synchronized void addOneLetter(Post p){
		allLetters.add(p);
		PreparedStatement st = null;
		try {
			String s = "Insert into allLetters (name, family_name, addres, time, type) Values(?, ?, ?, ?, ?)";
			st = DBManager.getInstance().getConnection().prepareStatement(s);
		} catch (SQLException e) {
			System.out.println("cant conect" + e.getMessage());
		}
			try {
				st.setString(1, p.getReciverName());
				st.setString(2, p.getReciverFamilyName());
				st.setString(3, p.getReciverAddres());
				st.setDate(4, Date.valueOf(p.getTimeOfPosting().toString()));
				if(p.getType().equals(Type.LETTER)){
					st.setString(5, "Letter");
				}else{
					st.setString(5, "Package");
				}
			} catch (SQLException e) {
				System.out.println("Error adding into db");
			}
		}
	
	public void addLetters(int boxNum, Post p){
		if(!p.equals(null)){
			if(!this.allPostBoxes.containsKey(boxNum)){
				this.allPostBoxes.put(boxNum, new ConcurrentSkipListSet<Post>());
				}
			this.allPostBoxes.get(boxNum).add(p);
		}
	}

	public synchronized void checkBox() {
		ArrayList<Post> a = new ArrayList<>();
		for(int i =0; i <allLetters.size()/3; i++){
			a.add(allLetters.poll());
		}
		for(Post p : a){
			if(p.getType().equals(Type.LETTER)){
				try {
					Thread.currentThread().sleep(1000);
					System.out.println("Letter delivered");
				} catch (InterruptedException e) {
					System.out.println("cant send letter");
				}
			}else{
				try {
					Thread.currentThread().sleep(1500);
					System.out.println("Package delivered");
				} catch (InterruptedException e) {
					System.out.println("cant send package");
				}
			}
		}
		
	}
	public synchronized void goGetLetters(){
		int boxnum = rn.nextInt(26);
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("can go to the postbox");
		}
		//to go to the box
			for(int i = 0; i < 12 ; i ++){
				//'cuz 2 hours have 120 minutes
			ArrayList<Post> a = new ArrayList<>();
			while(allPostBoxes.get(boxnum).size()>0){
				a.add(allPostBoxes.get(boxnum).pollLast());
				System.out.println("Oh letter");
			}
		}	
	}
}


package post;

import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;

import post.Post.Letter;
import post.Post.Package;

public class People extends Thread{
	private String name;
	private String familyName;
	static PostOffice office = new PostOffice();
	
	
	public static class Postboy extends People{
		private int staj; //in months
		private ConcurrentSkipListSet<Post> bag = new ConcurrentSkipListSet<>();
		
		public void run() {
			while(true){
				office.checkBox();
			}
		}

		public void setBag(Post p) {
			this.bag.add(p);
		}
		
	}
	
	public static class PostMan extends People{
		
		public void run() {
			while(true){
				office.goGetLetters();
			}
		}	
	}
	
	public static class JustPeople extends People{
		private String addres;
		
		public void run() {
			while(true){
				Random rn = new Random();
				boolean letter = rn.nextBoolean();
				if(letter){
					Post post = new Letter("someone", "oneone", "plovdiv maina");
					boolean postOffice = rn.nextBoolean();
					if(postOffice){
						office.addOneLetter(post);
						try {
							this.sleep(5000);
						} catch (InterruptedException e) {
							System.out.println("Normal people dont sleep");
						}
						System.out.println("Letter left in post office");
					}else{
					int postBoxNum = rn.nextInt(26);
						office.addLetters(postBoxNum, post);
						try {
							this.sleep(5000);
						} catch (InterruptedException e) {
							System.out.println("Normal people dont sleep");
						}
						System.out.println("Letter in postbox");
					}	
				}
				else{
					int size = rn.nextInt(100);
					boolean isFragile = rn.nextBoolean();
					Post post = new Package("someone", "oneone", "pernik brat", size, isFragile);
					office.addOneLetter(post);
					try {
						this.sleep(5000);
					} catch (InterruptedException e) {
						System.out.println("Normal people dont sleep");
					}
					System.out.println("Package left in post office");
				}	
			}
		}
	}
}

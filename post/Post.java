package post;

import java.time.LocalDateTime;

	enum Type{LETTER, PACKAGE};


public abstract class Post implements Comparable<Post>{
	private String reciverName;
	private String reciverFamilyName;
	private String reciverAddres;
	protected double tax;
	protected LocalDateTime timeOfPosting;
	protected Type type;
	
	protected Post(String name, String familyName, String addres){
		this.reciverName = name;
		this.reciverFamilyName = familyName;
		this.reciverAddres = addres;
		this.timeOfPosting = LocalDateTime.now();	
	}
	
	public LocalDateTime getTimeOfPosting() {
		return timeOfPosting;
	}

	public void setTimeOfPosting(LocalDateTime timeOfPosting) {
		this.timeOfPosting = timeOfPosting;
	}

	public String getReciverName() {
		return reciverName;
	}

	public String getReciverFamilyName() {
		return reciverFamilyName;
	}

	public String getReciverAddres() {
		return reciverAddres;
	}
	
	public Type getType() {
		return type;
	}

	public static class Letter extends Post{
		public Letter(String name, String familyName, String addres){
			super(name, familyName, addres);
			this.tax = 0.50;
			this.type = Type.LETTER;
		}

		@Override
		public int compareTo(Post o) {
			return this.timeOfPosting.compareTo(o.timeOfPosting);
		}
	}
	
	public static class Package extends Post{
		
		private int size;
		private boolean isFragile; 
		
		public Package(String name, String familyName, String addres, int size, boolean isFragile){
			super(name, familyName, addres);
			this.isFragile = isFragile;
			this.type = Type.PACKAGE;
			if(size <= 0){
				if(size > 60){
					this.size = size;
					this.tax = 3;
				}else{
					this.tax = 2;
				}
				if(isFragile){
					this.tax += (this.tax/2);
				}	
			}
		}

		@Override
		public int compareTo(Post o) {
			return this.timeOfPosting.compareTo(o.timeOfPosting);
		}
	}
}

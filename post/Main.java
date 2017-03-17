package post;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import post.People.JustPeople;
import post.People.PostMan;
import post.People.Postboy;

public class Main {
	static int count = 1;
	
	public static void main(String[] args) {

		
		ArrayList<JustPeople> people = new ArrayList<>();
		Thread t1 = new Thread(){
			@Override
			public void run() {
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					System.out.println("ops t1 cant sleep now");
				}
				Postboy petko = new Postboy();
				Postboy genko = new Postboy();
				Postboy denko = new Postboy();
				
				PostMan pesho = new PostMan();
				PostMan gosho = new PostMan();
				PostMan desho = new PostMan();
				petko.start();
				genko.start();
				denko.start();
				pesho.start();
				gosho.start();
				desho.start();
			}
		};
		t1.start();
		for(int i =0; i<50; i++){
			people.add(new JustPeople());
		}	
		for(JustPeople jp: people){
			jp.start();
		}	
		
		Thread t2 = new Thread(){		
  			@Override
 			public void run() {
 				while(true){
 					try {
 						sleep(5000);
 					} catch (InterruptedException e) {
 						System.out.println("cant sleep");
 					}
 					File f = new File (count+ ".txt");
 					count++;
 					String str = null;
 					Statement st = null;
 					ResultSet result = null;
 					String s = "Select (name, family_name, addres, time, type) form allLetters ";
 					try {
 						st = DBManager.getInstance().getConnection().createStatement();
 						result = 	st.executeQuery(s);
 					} catch (SQLException e) {
 						System.out.println("db error");
					}
 					FileOutputStream os = null;
 					try {
						while(result.next()){
 							str = result.toString();
 							os = new FileOutputStream(f);
 							for(int i = 0; i < str.length(); i ++){
 								int c = str.charAt(i);
 								os.write(c);	
 							}
 						}
 					} catch (FileNotFoundException e) {
 						System.out.println("file dosent exist");
 					} catch (SQLException e) {
 						System.out.println("db problem");
 					} catch (IOException e) {
 						System.out.println("ops io error");
 					}
 					try {
 						os.close();
 					} catch (IOException e) {
 						System.out.println("close what?");
 					}	
 				}
 			}
 		};	
 		
 		t2.start();
 		
	}
}

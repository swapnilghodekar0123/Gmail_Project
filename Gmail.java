package myproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Gmail {
	ArrayList<User> userList=new ArrayList<User>();
	public void launchApplication(){
		for(; ;){
			System.out.println("\n  *** WELCOME TO GMAIL ***");
			System.out.println("1. CREATE ACCOUNT");
			System.out.println("2. LOGIN");
			System.out.println("3. EXIT");
			System.out.println("\n Enter Your Option : ");
			int option=new Scanner(System.in).nextInt();
			switch(option){
			case 1 ->createAccount();
			case 2 ->login();
			case 3 -> System.exit(0);
			default -> System.out.println("\n INVALID OPTION \n");
			}
		}
	}

	private void login(){
		System.out.println("\n LOGIN MODULE");
		System.out.println("Email Id : ");
		String userMail=new Scanner(System.in).next();
		if(!(userMail.endsWith("@gmail.com"))){
			userMail+="@gmail.com";
		}
		System.out.println("Password : ");
		String userPassword =new Scanner(System.in).next();

		for(User user : userList){
			if(userMail.equals(user.getMail()) &&
					userPassword.equals(user.getPassword()))
			{
				homePage(user);
			}
		}
		System.out.println("\n INVALID CRED \n");
	}

	private void createAccount(){
		System.out.println("\n CREATE ACCOUNT MODULE ");
		System.out.print("First Name : ");
		String firstName=new Scanner(System.in).next();

		System.out.print("Last Name : ");
		String lastName=new Scanner(System.in).next();

		System.out.print("Contact : ");
		long contact=new Scanner(System.in).nextLong();
		String mail=null;

		outerLoop: for(;;){
			System.out.print("Email : ");
			mail=new Scanner(System.in).next();
			//check if email already exists
			for(User ele: userList){
				if(mail.equals(ele.getMail())){
					System.out.println("\n Mail-ID ALREADY EXISTS \n");
					String [] suggestion=suggestions(firstName);

					System.out.println(Arrays.toString(suggestion));
					continue outerLoop;
				}
			}
			break;
		}
		System.out.print("DOB : ");
		String dob=new Scanner(System.in).next();
		System.out.print("Password : ");
		String password=new Scanner(System.in).next();

		User newUser=new User(firstName+" "+ lastName,contact, mail, dob, password);
		userList.add(newUser);
	}

	private String[] suggestions(String name){
		String[] suggestion=new String[3];
		for(int i=0; i<=2; i++){
			String randomNumber="";
			for(int j=1; j<=4; j++){
				int dgt=(int) (Math.random()*10);
				randomNumber+=dgt;
			}
			String gmail=name+randomNumber+"@gmail.com";

			for(User ele : userList){
				if(gmail.equals(ele.getMail())){
					i--;
					continue;
				}
			}
			suggestion[i]=gmail;
		}
		return suggestion;
	}

	private void homePage(User user){
		for (; ; ) {
			System.out.println("\n **** HOME PAGE MODULE *****\n");
			System.out.println("1. Compose Mail");
			System.out.println("2. Draft Mail");
			System.out.println("3. Send Mail");
			System.out.println("4 Inbox Mail");
			System.out.println("5. All Mail");
			System.out.println("6. Starred Mail");
			System.out.println("7. Logout");
			System.out.println("Enter an option : ");
			int option =new Scanner(System.in).nextInt();
			switch(option){
			case 1->composeMail(user);
			case 2-> draftModule(user);
			case 3 -> sendModule(user);
			case 4 -> inboxModule(user);
			case 5 -> allModule(user);
			case 6 -> starredMailModule(user);
			case 7 -> logout(user);
			}
		}
	}

	private void logout(User user){
		System.out.println("\n Thank U " +user.getName()+ " FOR USING GMAIL \n");
		launchApplication();
	}

	private void starredMailModule(User user){
		System.out.println("\n IMP TUM \n");
	}

	private void allModule(User user){
		System.out.println("\n ALL MAIL MODULE \n");
		sendModule(user);
		System.out.println("*************************************");
		inboxModule(user);
	}

	private void inboxModule(User user){
		System.out.println("\n INBOX MODULE \n");
		ArrayList<Mail> inboxList=user.getInboxMail();
		for(Mail ele: inboxList){
			ele.getMailInfo();
			System.out.println("____________________________________________");

		}
	}

	private void sendModule(User user){
		System.out.println("\n SEND MODULE\n ");
		ArrayList<Mail> sendList=user.getSentMail();
		for(Mail ele:sendList){
			ele.getMailInfo();
			System.out.println("________________________________________________");

		}
	}

	private void draftModule(User user){
		System.out.println("\n DRAFT MAIL MODULE \n");
		ArrayList<Mail> draftList=user.getDraftMail();
		for(Mail ele: draftList){
			ele.getMailInfo();
			System.out.println("_________________________________________________");

		}
	}

	private void composeMail(User user){
		User toUser=null;
		System.out.println("\n COMPOSE MAIL \n");
		System.out.println("From : "+user.getMail());

		outerLoop: for(;;){
			System.out.print("To : ");
			String toMail=new Scanner(System.in).next();
			for(User ele: userList){
				if(toMail.equals(ele.getMail())){
					toUser=ele;
					break outerLoop;
				}
			}
			System.out.println("\n USER NOT FOUND \n");
		}
		System.out.print("Subject : ");
		String subject=new Scanner(System.in).nextLine();
		System.out.print("Body : ");
		String body=new Scanner(System.in).nextLine();
		Mail newMail=new Mail(user.getMail(), toUser.getMail(), subject, body);
		System.out.println("DO U WANT TO SEND : ");
		String resp=new Scanner(System.in).next();
		if(resp.equalsIgnoreCase("YES")){
			user.sendMail(newMail);
			toUser.inboxMail(newMail);
		}else{
			user.draftMail(newMail);
		}
	}
}

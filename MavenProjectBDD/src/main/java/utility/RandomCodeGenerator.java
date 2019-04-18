package utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class RandomCodeGenerator {
	
	public static String splitDateAndTime(String fieldName) {
        
		String dateAndTimeToBeSplitted[] = fieldName.split(" ", 2);
		String date = dateAndTimeToBeSplitted[0];
		return date;
	}


public static String dateGenerator(String dateToBeAdded)
	{  String exp=null;

	Date date = new Date();

	Calendar cal = Calendar.getInstance();
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	cal.setTime(date);
		if(dateToBeAdded.contains("oldDate:")) {
		String[]day=dateToBeAdded.split(":");
		String daytoBeadded=day[1];
		int dayToBeadded=Integer.parseInt(daytoBeadded);	
		cal.add(Calendar.DATE, -dayToBeadded); // add 10 days

		date = cal.getTime();

			
		exp= dateFormat.format(date).toString();
	}
		else if(dateToBeAdded.contains("futureDate:")) {
		String[]day=dateToBeAdded.split(":");
		String daytoBeadded=day[1];
		int dayToBeadded=Integer.parseInt(daytoBeadded);	
		cal.add(Calendar.DATE,dayToBeadded); // add 10 days
		date = cal.getTime();
		exp= dateFormat.format(date).toString();
	}
		else if(dateToBeAdded.contains("now")) {
		exp= dateFormat.format(date).toString();
			
	}

	return exp;
		
	}

	public static String panNoGenerator() {
		String characters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numbers="1234567890";
		String panNumber=null;

		StringBuilder sb = new StringBuilder();

		char[] tempFirstFiveChars = characters.toCharArray();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			char firstFiveChars = tempFirstFiveChars[random.nextInt(tempFirstFiveChars.length)];
			sb.append(firstFiveChars);
		}

		char[] tempFourNumbers = numbers.toCharArray();
		for (int i = 0; i < 4; i++) {
			char fourNumbers = tempFourNumbers[random.nextInt(tempFourNumbers.length)];
			sb.append(fourNumbers);
		}

		char[] tempLastChar = characters.toCharArray();
		for (int i = 0; i < 1; i++) {
			char lastChar = tempLastChar[random.nextInt(tempLastChar.length)];
			sb.append(lastChar);
		}

		panNumber = sb.toString();	
		return panNumber;
		
	}

	public static String tanNoGenerator() {
		String characters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numbers="1234567890";
		String tanNumber=null;

		StringBuilder sb = new StringBuilder();

		char[] tempFirstFourChars = characters.toCharArray();
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			char firstFourChars= tempFirstFourChars[random.nextInt(tempFirstFourChars.length)];
			sb.append(firstFourChars);
		}

		char[] tempFiveNumbers = numbers.toCharArray();
		for (int i = 0; i < 5; i++) {
			char fiveNumbers = tempFiveNumbers[random.nextInt(tempFiveNumbers.length)];
			sb.append(fiveNumbers);
		}

		char[] tempLastChar = characters.toCharArray();
		for (int i = 0; i < 1; i++) {
			char lastChar = tempLastChar[random.nextInt(tempLastChar.length)];
			sb.append(lastChar);
		}

		tanNumber = sb.toString();
		return tanNumber;
	}

	public static String petitionNoGenerator() {
		String numbers="1234567890";
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String petitionNumber=null;

		StringBuilder sb=new StringBuilder();

		char[] tempPetitionNumber =numbers.toCharArray();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			char petitionNumberGen = tempPetitionNumber[random.nextInt(tempPetitionNumber.length)];
			sb.append(petitionNumberGen);
		}

		sb.append("/");
		sb.append(year);

		petitionNumber=sb.toString();
		return petitionNumber;
	}

	public static String autoNameGenerator() {
		String characters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb=new StringBuilder();
		sb.append("AUTO");

		char[] tempChars = characters.toCharArray();
		Random random = new Random();
		for (int i = 0; i < 7; i++) {
			char fName = tempChars[random.nextInt(tempChars.length)];
			sb.append(fName);
		}

		String autoGenFirstName=sb.toString();
		return autoGenFirstName;
	
	}

	public static String calenderDateGenerator(String dateToBeAdded){
		String exp=null;
		Date date = new Date();

		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
		cal.setTime(date);
		if(dateToBeAdded.contains("oldDate:")){
			String[]day=dateToBeAdded.split(":");
			String daytoBeadded=day[1];
			int dayToBeadded=Integer.parseInt(daytoBeadded);	
			cal.add(Calendar.DATE, -dayToBeadded); // add 10 days
			date = cal.getTime();
			exp= dateFormat.format(date).toString();
		}
		else if(dateToBeAdded.contains("futureDate:")){
			String[]day=dateToBeAdded.split(":");
			String daytoBeadded=day[1];
			int dayToBeadded=Integer.parseInt(daytoBeadded);	
			cal.add(Calendar.DATE,dayToBeadded); // add 10 days
			date = cal.getTime();
			exp= dateFormat.format(date).toString();
		}
		else if(dateToBeAdded.contains("now")){
			exp= dateFormat.format(date).toString();

		}

		return exp;
	
	}
	
	public static String getCurrentTimeOfApplication(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
		String currentTime = sdf.format(new Date()); 
		return currentTime;
	}
	
	public static String getPinCode(){
		String numbers="1234567890";
		StringBuilder sb = new StringBuilder();
		String pinCode=null;
		Random random = new Random();
		char[] tempSixNumbers = numbers.toCharArray();
		for (int i = 0; i < 6; i++) {
			char fiveNumbers = tempSixNumbers[random.nextInt(tempSixNumbers.length)];
			sb.append(fiveNumbers);
		}
		pinCode=sb.toString();
		return pinCode;
	}
	
	public static String getMembershipNo(){
		String numbers="1234567890";
		StringBuilder sb = new StringBuilder();
		String membershipNo=null;
		Random random = new Random();
		char[] tempSixNumbers = numbers.toCharArray();
		for (int i = 0; i < 6; i++) {
			char fiveNumbers = tempSixNumbers[random.nextInt(tempSixNumbers.length)];
			sb.append(fiveNumbers);
		}
		membershipNo=sb.toString();
		return membershipNo;
	}
	
	public static String timeGenerator(){ 
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String timeToBeAdded=sdf.format(cal.getTime());
		return timeToBeAdded;
	}
	
	public static String timeGeneratorForClaimAdmission(String timeOfIntimation) throws ParseException{ 
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    Date time=sdf.parse(timeOfIntimation);
	    cal.setTime(time);
        cal.add(Calendar.MINUTE,-2);
		String timeToBeAdded=sdf.format(cal.getTime());
        return timeToBeAdded;
     }
	

public static String uniqueIdentificationCodeForPolicyNoGenerator() {
		String numbers="1234567890";
		String uniqueIdentificationCode=null;
		StringBuilder sb=new StringBuilder();
		sb.append("OIC");
		char[] tempuniqueIdentificationCodeNumber =numbers.toCharArray();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			char uniqueIdentificationCodeGen = tempuniqueIdentificationCodeNumber[random.nextInt(tempuniqueIdentificationCodeNumber.length)];
			sb.append(uniqueIdentificationCodeGen);
		}
		sb.append("LB");
		sb.append("01516");
		uniqueIdentificationCode=sb.toString();
		return uniqueIdentificationCode;
	}

	
}


import java.util.Scanner;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;


public class BankAccount {
	private static String _fileName = "log.html";
	private static String _charSet = "UTF-8";
	private Scanner _scanBankAccount;
	private Pattern _pattern1 = Pattern.compile("[\\x00-\\x20]*((\\p{Digit}+)(\\.)?((\\p{Digit}+)?))");//capture decimal numbers such as 55, 55., and 55.05
	private Pattern _pattern2 = Pattern.compile("(\\.)((\\p{Digit}+))");//capture decimal numbers such as .05, .5
	private boolean _validInput;
	private String _amount;//the amount provided by the user as a String.
	private double _amountReal;//the real amount based on user input.
	
	
	/** Remove money from the current account. Called by the ATM class.*/
	public void Withdraw()
	{
		_scanBankAccount = new Scanner(System.in);
		_validInput = false;
		_amount = "";
		_amountReal = 0;
		
		while(!_validInput){
			System.out.println("\nPlease enter an amount to withdraw:");
			_amount = _scanBankAccount.nextLine();
			_validInput = VerifyInput(_amount);
		}
		
		_amountReal = Double.parseDouble(_amount);
		System.out.println("You have Withdrawn: $" + String.format("%.2f", _amountReal));
		
		//update the log file.
		try {
			UpdateLog(String.format("%.2f", -_amountReal));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Add money to the current account. Called by the ATM class.*/
	public void Deposit()
	{
		_scanBankAccount = new Scanner(System.in);
		_validInput = false;
		_amount = "";
		_amountReal = 0;
		
		while(!_validInput){
			System.out.println("\nPlease enter an amount to deposit:");
			_amount = _scanBankAccount.nextLine();
			_validInput = VerifyInput(_amount);
		}
		
		_amountReal = Double.parseDouble(_amount);
		System.out.println("You have Deposited: $" + String.format("%.2f", _amountReal));
		
		//update the log file.
		try {
			UpdateLog(String.format("%.2f", _amountReal));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Return the balance of the current account. Called by the ATM class. */
	public void Balance()
	{
		try{
			System.out.println("The current balance is: $" + ReadLog());
		}
		catch (IOException e){
			System.out.println("Could not read the balance from file");
		}		
	}
	
	/** Verify that the provided user String input is correct. It must be a proper number value, have no more than 2 decimal places, etc.*/
	private boolean VerifyInput(String input)
	{
		//Make sure that the provided input passes/matches the patterns we created up above.
		if(_pattern1.matcher(input).matches() || _pattern2.matcher(input).matches())
		{
			//Determine whether or not there is a decimal in the number.
			if(!input.contains("."))
			{
				//This input passed our initial pattern check and does not contain a decimal point. No further checking required.
				return true;
			}
			else
			{
				// Since this input has a decimal point, make sure there are only 2 decimal places.
				// We should be able to accept 22.95, .95 and 22.
				char[] inputArray = input.toCharArray();
				
				if(inputArray[0] == '.')
				{
					//since the decimal is first, make sure the length is not greater than 3.
					if(inputArray.length <= 3)
					{
						return true;
					}
				}
				else if(inputArray[inputArray.length-1] != '.')
				{
					//since the decimal is not at the end, and not at the beginning, its in the middle.
					String decimals = input.split("\\.")[1];
					if(decimals.length() <= 2)
					{
						//since there are less than 3 decimal places, this input passes.
						return true;
					}
				}
				else
				{
					//decimal must be at the end. The value is essentially valid because the decimals are zeroed.
					return true;
				}
			}
			
		}
		else
		{
			System.out.println(input + " is not a valid input\n");
		}
		
		return false;
	}
	
	/** Update the Log file with the provided String value*/
	private void UpdateLog(String value) throws IOException
	{
		File input = new File(_fileName);
		Document doc = Jsoup.parse(input, _charSet);

		Element content = doc.getElementById("transactions");
		content.child(1).append("<tr><td>" + value + "</td></tr>");
		
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter("log.html"));
			out.write(doc.outerHtml());
			out.close();
		}catch (IOException e)
		{
			//could not write to // create the file.
			System.out.println("Could not Write to or Update the log.html file");
		}
	}
	
	/** Parse the Log file, calculate the balance, and return as a string*/
	private String ReadLog() throws IOException
	{
		double balance = 0;
		File input = new File(_fileName);
		Document doc = Jsoup.parse(input, _charSet);
		Element content = doc.getElementById("transactions");
		Elements transactions = content.child(1).children();
		for(Element transaction : transactions){
			balance += Double.parseDouble(transaction.text());
			//System.out.println(amount.text());
		}
		
		return String.format("%.2f", balance);
	}
}

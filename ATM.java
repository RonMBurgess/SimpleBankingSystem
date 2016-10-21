import java.util.Scanner;



public class ATM {

	public static void main(String[] args) {
		
		System.out.println("Thank you for using this ATM. While we understand we're your only choice, its still greatly appreciated.");
		
		BankAccount bankAccount = new BankAccount();
		Scanner scanATM = new Scanner(System.in);
		String userInput;
		
		boolean deposit, withdraw, balance, exit = false;
		
		while (!exit)
		{
			System.out.println("\nPlease enter in a command (Deposit, Withdraw, Balance, Exit):");
			userInput = scanATM.nextLine();
			
			//determine if a correct input was provided and what was chosen. 
			deposit = (userInput.equalsIgnoreCase("Deposit"));
			withdraw = userInput.equalsIgnoreCase("Withdraw");
			balance = userInput.equalsIgnoreCase("Balance");
			exit = userInput.equalsIgnoreCase("Exit");
			
			if(deposit)
			{
				bankAccount.Deposit();
			}
			else if(withdraw)
			{
				bankAccount.Withdraw();
			}
			else if(balance)
			{
				bankAccount.Balance();
			}
			else if(exit)
			{
				System.out.println("You have chosen to stop using the ATM. Thank you for using us, your only choice.");
			}
			else
			{
				System.out.println("Sorry, but " + userInput + ", is an invalid option.");
				continue;
			}
			
		}
		scanATM.close();
	}

	
}


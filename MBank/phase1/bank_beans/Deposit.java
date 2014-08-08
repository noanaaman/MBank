package bank_beans;

import java.util.Calendar;

import enums.DepositType;

public class Deposit {
	
	private long deposit_id;
	private long client_id;
	private double balance;
	private DepositType type;
	private double estimated_balance;
	private Calendar opening_date;
	private Calendar closing_date;
	
	public Deposit(long deposit_id, long client_id, double balance, DepositType type, double estimated_balance, Calendar opening_date, Calendar closing_date) {
		this.deposit_id = deposit_id;
		this.client_id = client_id;
		this.balance = balance;
		this.type = type;
		this.estimated_balance = estimated_balance;
		this.opening_date = opening_date;
		this.closing_date = closing_date;
	}

	public long getDeposit_id()
	{
		return deposit_id;
	}

	public void setDeposit_id(long deposit_id)
	{
		this.deposit_id = deposit_id;
	}

	public long getClient_id()
	{
		return client_id;
	}

	public void setClient_id(long client_id)
	{
		this.client_id = client_id;
	}

	public double getBalance()
	{
		return balance;
	}

	public void setBalance(double balance)
	{
		this.balance = balance;
	}

	public DepositType getType()
	{
		return type;
	}

	public void setType(DepositType type)
	{
		this.type = type;
	}

	public double getEstimated_balance()
	{
		return estimated_balance;
	}

	public void setEstimated_balance(long estimated_balance)
	{
		this.estimated_balance = estimated_balance;
	}

	public Calendar getOpening_date()
	{
		return opening_date;
	}

	public void setOpening_date(Calendar opening_date)
	{
		this.opening_date = opening_date;
	}

	public Calendar getClosing_date()
	{
		return closing_date;
	}

	public void setClosing_date(Calendar closing_date)
	{
		this.closing_date = closing_date;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Deposit)
		{
			if (((Deposit)obj).getDeposit_id() == this.deposit_id)
			{
				return true;
			}

		}
		return false;
	}
	
	
	
	
}

//MainActivity.java
package com.example.mortgagecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.text.NumberFormat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		
	//purchase price
	private double purchaseAmt = 0.0;
	
	//down payment
	private double downPayAmt = 0.0;
	
	private double monthlyPayments = 0.0;	

	//initial values of purchase amount
	private TextView purchaseAmtDisplayText;
	//and down payment
	private TextView downPayDisplayText;
	//loan total display
	private TextView loanTotalDisplayText;
	private TextView loanAmtTextView;
	//monthly installments text view
	private TextView monthlyTotalDisplayText;
	//custom length of mortgage
	private TextView monthlyAmtTextViewDisplay;
	//
	private double customLength = 0.0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);//call superclass version
		setContentView(R.layout.activity_main);//inflate gui
		
		//references to TextViews, for MainActivity to interact with
		purchaseAmtDisplayText = (TextView) findViewById(R.id.purchaseAmtDisplayText);
		downPayDisplayText = (TextView) findViewById(R.id.downPayDisplayText);
		loanTotalDisplayText = (TextView) findViewById(R.id.loanTotalDisplayText);
		monthlyTotalDisplayText = (TextView) findViewById(R.id.monthlyTotalDisplayText);
		monthlyAmtTextViewDisplay = (TextView) findViewById(R.id.monthlyAmtTextViewDisplay);

		
		//update GUI based on PurchasePrice, DownPayment and Length of Loan
		//purchaseAmtDisplayText.setText(currencyFormat.format(purchaseAmtDisplayText));
		//updateStandard();
		//updateCustom();
		
		//set TextWatcher for purchaseAmtEditText 
		EditText purchaseAmtEditText = (EditText) findViewById(R.id.purchaseAmtEditText);
		purchaseAmtEditText.addTextChangedListener(purchaseAmtEditTextWatcher);
		
		//set TextWatcher for downPayEditText
		EditText downPayEditText = (EditText) findViewById(R.id.downPayEditText);
		downPayEditText.addTextChangedListener(downPayEditTextWatcher);
		
		//seekbar's OnSeekBarChangeListener
		SeekBar customLengthSeekBar = (SeekBar) findViewById(R.id.customLengthSeekBar);
		customLengthSeekBar.setOnSeekBarChangeListener(customLengthSeekBarListener);
	}
	
	//loan amt
	private void updateLoanAmt()
	{
		//calculate cost of loan by subtracting DownPayment from Purchase price
		 double loanAmt = purchaseAmt - downPayAmt;
		
		//display the loan amt
		 loanAmtTextView.setText(currencyFormat.format(loanAmt));	 
	}
	
	//monthly payments
	private void updateMonthlyAmt()
	{
		//get loan amount
		double customLoanAmt = purchaseAmt - downPayAmt;
		
		//get years from seek bar
		monthlyAmtTextViewDisplay.setText(String.valueOf(customLength));
		
		//do math for monthly installments. 
		double monthlyPayments = customLoanAmt/ (12 * customLength); 
		
		//display monthly payments
		monthlyTotalDisplayText.setText(currencyFormat.format(monthlyPayments));
		
		//display years above amount
		monthlyAmtTextViewDisplay.setText(String.valueOf(customLength));
		
	}
	
	private OnSeekBarChangeListener customLengthSeekBarListener = new OnSeekBarChangeListener()
	{
		//update length, then update monthly amt
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
		{
			customLength = progress;
			updateMonthlyAmt();
		}
		
		//onTrackingTouch
		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{
			
		}
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
			
		}
		
	};
	
	//textwatcher for purchase amount edit text
	private TextWatcher purchaseAmtEditTextWatcher = new TextWatcher()
	{
		//called when user enters price
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			//convert purchase amount edit text to a double
			try
			{
				purchaseAmt = Double.parseDouble(s.toString());
			}//end try
			catch (NumberFormatException e)
			{
				purchaseAmt = 0.0;
			}
			
			//display loan amount 
			updateLoanAmt();	
		}
		
		@Override
		public void afterTextChanged(Editable s)
		{
		}//end afterTextChanged
	
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
		}//end beforeTextChanged
		
	};
	
	//textwatcher for purchase amount edit text
	private TextWatcher downPayEditTextWatcher = new TextWatcher()
	{
		//called when user enters price
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			//convert down payment amount edit text to a double
			try
			{
				downPayAmt = Double.parseDouble(s.toString());
			}//end try
			catch (NumberFormatException e)
			{
				downPayAmt = 0.0;
			}
			
			//display loan amount 
			updateMonthlyAmt();	
		}
		
		@Override
		public void afterTextChanged(Editable s)
		{
		}//end afterTextChanged
	
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
		}//end beforeTextChanged
		
	};


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

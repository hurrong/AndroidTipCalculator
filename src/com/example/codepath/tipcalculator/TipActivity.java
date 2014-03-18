package com.example.codepath.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TipActivity extends Activity {
	EditText etBaseAmount;
	TextView tvTipAmount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
		etBaseAmount = (EditText) findViewById(R.id.etBaseAmount);
		tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);
	}

	public void calculateTip(View v) {
		Double baseAmount = Double.valueOf(etBaseAmount.getText().toString());
		double tipPercent = 0.15;
		switch (v.getId()) {
			case R.id.btn10pc: tipPercent = 0.10; break;
			case R.id.btn15pc: tipPercent = 0.15; break;
			case R.id.btn20pc: tipPercent = 0.20; break;
			default: break;
		}
		Double tipAmount = baseAmount * tipPercent;
		tvTipAmount.setText(String.format("%.2f", tipAmount));
	}
}

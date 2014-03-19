package com.example.codepath.tipcalculator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class TipActivity extends Activity {
	EditText etBaseAmount;
	TextView tvTipAmount;
	RadioGroup rgTipPercent;
	TextView etCustomTip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
		etBaseAmount = (EditText) findViewById(R.id.etBaseAmount);
		tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);
		rgTipPercent = (RadioGroup) findViewById(R.id.rgTipPercent);
		rgTipPercent.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				calculateTip();
			}
		});
		OnEditorActionListener listener = new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH ||
		                actionId == EditorInfo.IME_ACTION_DONE ||
		                event.getAction() == KeyEvent.ACTION_DOWN &&
		                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					calculateTip();
					return true;
				}
				
				return false;
			}
		};
		etBaseAmount.setOnEditorActionListener(listener);
		etCustomTip = (EditText) findViewById(R.id.etCustomTip);
		etCustomTip.setOnEditorActionListener(listener);
		etCustomTip.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					rgTipPercent.check(R.id.tipCustom);
					etCustomTip.setTextColor(Color.BLACK);
				}
			}
		});
	}
	
	private void calculateTip() {
		String baseAmtInput = etBaseAmount.getText().toString();
		if (baseAmtInput.isEmpty()) {
			return;
		}
		Double baseAmount = Double.valueOf(etBaseAmount.getText().toString());
		double tipPercent = 0.15;
		switch(rgTipPercent.getCheckedRadioButtonId()) {
			case R.id.tip10pc: tipPercent = 0.10; break;
			case R.id.tip15pc: tipPercent = 0.15; break;
			case R.id.tip20pc: tipPercent = 0.20; break;
			case R.id.tipCustom: tipPercent = getCustomTip(tipPercent); break;
			default: break;
		}
		if (rgTipPercent.getCheckedRadioButtonId() != R.id.tipCustom) {
			etCustomTip.setTextColor(Color.GRAY);
		}
		Double tipAmount = baseAmount * tipPercent;
		tvTipAmount.setText(String.format("%.2f", tipAmount));
	}
	
	private Double getCustomTip(Double tipPercent) {
		String customTipInput = etCustomTip.getText().toString();
		if (customTipInput.isEmpty()) {
			return tipPercent;
		}
		Double percent = Double.valueOf(customTipInput);
		if (percent >= 0) {
			return percent * 0.01;
		} else {
			Toast.makeText(getApplicationContext(), "Invalid percentage!", Toast.LENGTH_SHORT).show();
			rgTipPercent.check(R.id.tip15pc);
			return null;
		}
	}
}

package com.app.SDManeger;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.sdcheck.SDCardCheck;

public class SDCardStat extends Activity{
	
	private TextView aSize;
	private TextView tSize;
	private ProgressBar progressBar ;
	private SDCardCheck sdCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filedetails); 

		aSize = (TextView)findViewById(R.id.asizen);
		tSize = (TextView) findViewById(R.id.tsizen);
		progressBar = (ProgressBar)findViewById(R.id.progressBar1);
		progressBar.setMax(100);
		sdCheck = new SDCardCheck();
			
		putSDCardContext();
		
	}

	public void putSDCardContext(){
		
		sdCheck.SDCardSize();
		long nFreeSize = sdCheck.getnSDFreeSize();
		long nTotalSize = sdCheck.getnSDTotalSize();
		if(nFreeSize > 1024){
			nFreeSize = nFreeSize/1024;
			nTotalSize = nTotalSize/1024;
			aSize.setText("   " + String.valueOf(nFreeSize) + " G");
			tSize.setText("   " + String.valueOf(nTotalSize) + " G");
			
		}else {
			aSize.setText("   " + String.valueOf(nFreeSize) + " MB");
			tSize.setText("   " + String.valueOf(nTotalSize) + " MB");		
		}
		
		
		if(nTotalSize != 0) {
			progressBar.setProgress(100 - (int)(nFreeSize*100/nTotalSize));
		}
		
	}
	
	
	

}

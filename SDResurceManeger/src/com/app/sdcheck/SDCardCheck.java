package com.app.sdcheck;

import java.io.File;

import android.util.Log;


public class SDCardCheck {
	
	private File path;
	private int SDstat;
	
	private long nSDTotalSize; 
	private long nSDFreeSize;
	
	public SDCardCheck() {  
		String sDStateString = android.os.Environment.getExternalStorageState();  
			    
		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {  
			
			Log.d("x", "the SDcard mounted");
			SDstat = 1;
		
		} else if (sDStateString.endsWith(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {  
			
			Log.d("x", "the SDcard just can be read");
			SDstat = 2;
		
		} else if (sDStateString.endsWith(android.os.Environment.MEDIA_UNMOUNTED)) {  
			
			Log.d("x", "the SDcard unmounted");
			SDstat = -1;
			System.exit(0);
		} else if (sDStateString.endsWith(android.os.Environment.MEDIA_REMOVED)) {  
			
			Log.d("x", "the SDcard has been removed");
			SDstat = -2;
			System.exit(0);
		} 
		
		
		
	}
	
	
	public File getSDCardDir() {
				
		if(SDstat > 0) {
			
			path = android.os.Environment.getExternalStorageDirectory(); 
			Log.d("x", path.toString());
			return path;
		}	
		
		return null;
	}
	
	public void SDCardSize() {  
		   
	
		if (SDstat > 0) {  
		   
			path = android.os.Environment.getExternalStorageDirectory(); 
		    android.os.StatFs statfs = new android.os.StatFs(path.getPath());  
		   
		    long nTotalBlocks = statfs.getBlockCount();  
		   
		    long nBlocSize = statfs.getBlockSize();  
		   
		    long nAvailaBlock = statfs.getAvailableBlocks();  
		   
		    long nFreeBlock = statfs.getFreeBlocks();  
		   
		    nSDTotalSize = nTotalBlocks * nBlocSize / 1024 / 1024;  
		   
		    nSDFreeSize = nAvailaBlock * nBlocSize / 1024 / 1024;  
		}
	
	}


	public long getnSDFreeSize() {
		return nSDFreeSize;
	}


	public long getnSDTotalSize() {
		return nSDTotalSize;
	}


	@Override
	public String toString() {
		return "SDCardCheck [path=" + path + ", SDstat=" + SDstat + "]" 
				+ nSDTotalSize + "SDTotalSize"+ "...SDFreeSize" + nSDFreeSize;
	}


	public File getPath() {
		return path;
	}



	public int getSDstat() {
		return SDstat;
	}



	
	
}

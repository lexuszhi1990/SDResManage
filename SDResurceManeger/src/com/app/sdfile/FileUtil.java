package com.app.sdfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

public class FileUtil {

	private long nFileTotalSize;
	private long nFileFreeSize;
	private String fileName;
	private String filePath;
	private String spaces = null;
	private double space = 0;
	private double count = 0;

	public long getnFileTotalSize() {
		return nFileTotalSize;
	}

	public void setnFileTotalSize(long nFileTotalSize) {
		this.nFileTotalSize = nFileTotalSize;
	}

	public long getnFileFreeSize() {
		return nFileFreeSize;
	}

	public void setnFileFreeSize(long nFileFreeSize) {
		this.nFileFreeSize = nFileFreeSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	private void attDir(File file) throws IOException {
		// TODO Auto-generated method stub

		if (file.isHidden()) {
			return;
		}
		File[] liFile = file.listFiles();
		for (File nFile : liFile) {
			if (nFile.isFile()) {
				attFile(nFile);
				count += space;
				// System.out.println(count);
			} else {
				attDir(nFile);
			}
		}

	}

	private void attFile(File file) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream fi = new FileInputStream(file);
		space = fi.available();
		if (space > 1048576) {
			spaces = String.valueOf(space / 1048576).substring(0,
					String.valueOf(space / 1048576).lastIndexOf(".") + 4)
					+ "MB";
		} else {
			spaces = String.valueOf(space / 1024).substring(0,
					String.valueOf(space / 1024).lastIndexOf(".") + 2)
					+ "KB";
		}
	}

	public String getSize(File file) {
		String temp;
		if (file.isFile()) {
			try {
				attFile(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			temp = spaces;
		} else {
			try {
				attDir(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (count > 1048576) {
				temp = String.valueOf(count / 1048576).substring(0,
						String.valueOf(count / 1048576).lastIndexOf(".") + 4)
						+ "MB";
			} else {
				temp = String.valueOf(count / 1024).substring(0,
						String.valueOf(count / 1024).lastIndexOf(".") + 2)
						+ "KB";
			}
		}
		return temp;
	}

	public ArrayList<HashMap<String, Object>> getFileDetails(File file) {

		ArrayList<HashMap<String, Object>> lstDetails = new ArrayList<HashMap<String, Object>>();
		String temp;

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		temp = file.getName();
		setFileName(temp);
		map1.put("list", "Name : ");
		map1.put("source", temp);

		HashMap<String, Object> map2 = new HashMap<String, Object>();
		temp = file.getAbsolutePath();
		setFilePath(temp);
		map2.put("list", "Locattion : ");
		map2.put("source", temp);

		HashMap<String, Object> map3 = new HashMap<String, Object>();
		temp = file.getAbsolutePath();

		map3.put("list", "Size : ");

		temp = getSize(file);

		map3.put("source", temp);

		HashMap<String, Object> map4 = new HashMap<String, Object>();

		map4.put("list", "Readable : ");
		map4.put("source", file.canRead() ? "yes" : "no");

		HashMap<String, Object> map5 = new HashMap<String, Object>();

		map5.put("list", "Writeable : ");
		map5.put("source", file.canWrite() ? "yes" : "no");

		HashMap<String, Object> map6 = new HashMap<String, Object>();

		map6.put("list", "Hidden : ");
		map6.put("source", file.isHidden() ? "yes" : "no");

		HashMap<String, Object> map7 = new HashMap<String, Object>();

		map7.put("list", "Type : ");
		map7.put("source", file.isFile() ? "File" : "Directory");

		lstDetails.add(map1);
		lstDetails.add(map2);
		if(!file.isHidden()){
			lstDetails.add(map3);
		}
		lstDetails.add(map7);
		lstDetails.add(map4);
		lstDetails.add(map5);
		lstDetails.add(map6);

		return lstDetails;
	}

}

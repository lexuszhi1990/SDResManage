package com.app.sdfile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.EditText;

import com.app.SDManeger.R;
import com.app.SDManeger.ResurceDetails;

public class SDFile {

	public ArrayList<HashMap<String, Object>> getFileList(File path) {

		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map;
		String temp;
		int i = 0;

		if (!path.exists() && !path.canRead() && !path.isDirectory()) {
			return lstImageItem;
		}
		if (path.list() == null) {
			return lstImageItem;
		}
		if (path.isHidden()) {
			path.setReadable(true);

		}
		temp = path.getName();
		if (temp.startsWith(".")) {
			temp = temp.substring(1, temp.length());
			i = path.getAbsoluteFile().toString().lastIndexOf("/");
			temp = path.getAbsolutePath().substring(0, i) + File.separator
					+ temp;
			path.renameTo(new File(temp));
			Log.d("x", "int sub");
		}
		for (File f : path.listFiles()) {
			temp = f.getName();
			map = new HashMap<String, Object>();
			if (f.isDirectory()) {
				map.put("ItemImage", R.drawable.filefolder1);
			} else if (f.isFile() && temp.endsWith(".pdf")) {
				map.put("ItemImage", R.drawable.pdf);
			} else if (f.isFile() && temp.endsWith(".mp3")) {
				map.put("ItemImage", R.drawable.mp3);
			} else if (f.isFile() && temp.endsWith(".txt")) {
				map.put("ItemImage", R.drawable.txt);
			}else if (f.isFile()) {
				map.put("ItemImage", R.drawable.file1);
			}
			map.put("ItemText", f.getName());
			lstImageItem.add(map);
		}

		return lstImageItem;
	}

	public Intent openFile(File file) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		String type = getMIMEType(file);
		intent.setDataAndType(Uri.fromFile(file), type);
		return intent;
	}

	/**
	 * 根据不同的文件，选择不同的打开方式。 
	 */
	private String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

      private final String[][] MIME_MapTable = {
			{ ".3gp", "video/3gpp"},
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" },
			{ ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
			{ ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/x-zip-compressed" },
			{ ".lrc", "text/plain" }, { "", "*/*" } };

	public ArrayList<HashMap<String, Object>> getSearchList(File path,
			String str) {
		ArrayList<HashMap<String, Object>> lst;

		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map;
		String temp;
		int i = 0;

		if (!path.exists() && !path.canRead() && !path.isDirectory()) {
			return lstImageItem;
		}
		if (path.list() == null) {
			return lstImageItem;
		}
		if (path.isHidden()) {
			path.setReadable(true);
		}

		for (File f : path.listFiles()) {
			temp = f.getName();
			if (temp.indexOf(str) != -1) {
				map = new HashMap<String, Object>();
				if (f.isDirectory()) {
					map.put("ItemImage", R.drawable.filefolder1);
				} else if (f.isFile()&& temp.endsWith(".html")) {
					map.put("ItemImage", R.drawable.html);
				} else if (f.isFile() && temp.endsWith(".mp3")) {
					map.put("ItemImage", R.drawable.mp3);
				} else if (f.isFile() && temp.endsWith(".txt")) {
					map.put("ItemImage", R.drawable.txt);
				}else if(f.isFile()){
					map.put("ItemImage", R.drawable.file1);
				}
				map.put("ItemText", f.getName());
				lstImageItem.add(map);
			}
		}

		return lstImageItem;

	}

}

package com.app.SDManeger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.app.sdfile.FileUtil;

public class ResurceDetails extends Activity {

	private ListView listView;
	private File file;
	private FileUtil fileUtil;
	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = getIntent();
		String filePath = intent.getStringExtra("fileName");
		Log.d("x", filePath);
		file = new File(filePath);
		if(!file.exists()){
			return;
		}
		fileUtil = new FileUtil();
		listView = new ListView(this);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));
		setContentView(listView);

		listView.setOnItemClickListener(new ListItemLisntener());
	}

	class ListItemLisntener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

			if (arg2 == 0) {
				deleteDialog();
			} else if (arg2 == 1) {
				renameDialog();
			} else if (arg2 == 2) {
				detailsDialog();
			} else if (arg2 == 3) {
				
			}

		}

	}

	private List<String> getData() {

		List<String> data = new ArrayList<String>();
		data.add("delete the file");
		data.add("rename");
		data.add("details of the file");

		return data;
	}

	private void deleteDialog() {
		AlertDialog.Builder builder = new Builder(ResurceDetails.this);
		builder.setMessage("确认删除吗");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Log.d("x", "queding");
				file.delete();
				ResurceDetails.this.setResult(1);
				finish();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Log.d("x", "casel");
			}
		});
		builder.create().show();
	}

	private void renameDialog() {
		AlertDialog.Builder builder = new Builder(ResurceDetails.this);
		final EditText editText = new EditText(ResurceDetails.this);
		builder.setMessage("请输入文件名");
		builder.setTitle("重命名");
		builder.setView(editText);
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				String str = editText.getText().toString();
				String temp = file.getAbsolutePath();
				int i = temp.lastIndexOf("/");
				temp = temp.substring(0,  i);
				Log.d("x", "queding" + str + "teml" + temp);
				File fileTemp = new File(temp + File.separator + str);
				fileTemp.getAbsoluteFile();
				Log.d( "x"	, "sds" + fileTemp.getAbsolutePath());
				if(file.isDirectory()){
					
					file.renameTo(fileTemp);
					
				}else if(file.isFile()){
					file.renameTo(fileTemp);

				}
				ResurceDetails.this.setResult(2);
				finish();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Log.d("x", "casel");
			}
		});
		
		builder.create().show();
	}

	
	
	private void detailsDialog() {
		ArrayList<HashMap<String, Object>> lstDetails = new ArrayList<HashMap<String,Object>>();
		lstDetails = fileUtil.getFileDetails(file);
		ListView listView1 = new ListView(this);
				
		SimpleAdapter sAdapter = new SimpleAdapter(this,lstDetails,
				android.R.layout.simple_expandable_list_item_2, 
				new String[] { "list", "source" },
				new int[] { android.R.id.text1, android.R.id.text2 });
		
		listView1.setAdapter(sAdapter);
		setContentView(listView1);
		
	}
	
	
	
	
	
	
	
}

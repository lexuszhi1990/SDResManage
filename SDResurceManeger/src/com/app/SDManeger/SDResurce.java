package com.app.SDManeger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.sdcheck.SDCardCheck;
import com.app.sdfile.SDFile;

public class SDResurce extends Activity {
	/** Called when the activity is first created. */
	SDCardCheck sdCardCheck;
	SDFile sdFile;
	File SDpath;
	String temp;
	ArrayList<HashMap<String, Object>> lstImageItem;
	private GridView gridview;
	private TextView textView;
	private Button creat;
	private Button search;
	private Button back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		gridview = (GridView) findViewById(R.id.gridview);
		gridview.setOnItemClickListener(mGridViewItemClickListener);
		gridview.setOnItemLongClickListener(mGridViewItemLongClickListener);
		creat = (Button) findViewById(R.id.button1);
		search = (Button) findViewById(R.id.button2);
		back = (Button) findViewById(R.id.button3);

		creat.setOnClickListener(new CreatListener());
		search.setOnClickListener(new searchListener());
		back.setOnClickListener(new FlashListener());

		sdCardCheck = new SDCardCheck();
		sdFile = new SDFile();

		SDpath = sdCardCheck.getSDCardDir();
		reFleshView(SDpath);
	}
	
	
	


	private void setFleshView(ArrayList<HashMap<String, Object>> lst) {
		SimpleAdapter saImageItems = new SimpleAdapter(this, lst,
				R.layout.item, new String[] { "ItemImage", "ItemText" },
				new int[] { R.id.ItemImage, R.id.ItemText });
		gridview.setAdapter(saImageItems);

	}

	private void reFleshView(File filePath) {
		if (filePath != null) {
			lstImageItem = sdFile.getFileList(filePath);
		}
		// TODO Auto-generated method stub
		if (lstImageItem != null) {
			setFleshView(lstImageItem);
		}
	}

	private void creatFile(final String type) {
		AlertDialog.Builder builder = new Builder(SDResurce.this);
		final EditText editText = new EditText(SDResurce.this);
		builder.setMessage("请输入文件名");
		builder.setView(editText);
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				String str = editText.getText().toString();
				File f = new File(SDpath + File.separator + str);
				if (type.equals("file")) {
					try {
						f.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (type.equals("box")) {
					f.mkdir();
				}
				reFleshView(SDpath);
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

	private void creatDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("选择要创建的");
		builder.setTitle("提示");
		builder.setPositiveButton("文件夹", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				creatFile("box");
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("文件", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				creatFile("file");
				Log.d("x", "casel");
			}
		});
		builder.create().show();
	}

	private class CreatListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("x", "in two");
			creatDialog();
		}

	}

	private class FlashListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("x", "in two");
			reFleshView(SDpath);
		}

	}

	private void searchDialog() {
		AlertDialog.Builder builder = new Builder(SDResurce.this);
		final EditText editText = new EditText(SDResurce.this);
		builder.setMessage("请输入关键字");
		builder.setView(editText);
		builder.setPositiveButton("确认", new OnClickListener() {
	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				String str = editText.getText().toString();
				lstImageItem = sdFile.getSearchList(SDpath, str);
				setFleshView(lstImageItem);
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

	private class searchListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			searchDialog();
		}
	}

	class backListener implements OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub

		}

	}

	private GridView.OnItemClickListener mGridViewItemClickListener = new GridView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			HashMap<String, Object> mf = lstImageItem.get(position);
			File f = new File(SDpath + File.separator
					+ mf.get("ItemText").toString());
			if (!f.exists())
				return;
			if (f.isDirectory()) {
				SDpath = f;
				reFleshView(f);
			} else if (f.isFile()) {
				startActivity(sdFile.openFile(f));
			}

		}
	};

	private GridView.OnItemLongClickListener mGridViewItemLongClickListener = new GridView.OnItemLongClickListener() {
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			HashMap<String, Object> mf = lstImageItem.get(position);
			File f = new File(SDpath + File.separator
					+ mf.get("ItemText").toString());
			Log.d("x", "long position and id is " + position + id);
			Log.d("x", mf.get("ItemImage") + "long itemImage");
			Log.d("x", mf.get("ItemText").toString() + "longa  absolutePath"
					+ f.getAbsolutePath());
			if (f.exists()) {
				try {
					Log.d("x", f.getCanonicalPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			Intent longClickIntent = new Intent();
			longClickIntent.putExtra("fileName", f.getAbsolutePath());
			longClickIntent.setClass(SDResurce.this, ResurceDetails.class);
			startActivityForResult(longClickIntent, 0);

			return true;
		}
	};

	public void backEvent() {
		temp = SDpath.getAbsolutePath();
		Log.d("x", temp + "back key itemText");
		if (temp.equals("/mnt/sdcard")) {
			finish();
		}
		int lastFd = temp.lastIndexOf("/");
		if (lastFd == -1) {
			Log.d("x", " failed to index");
		}
		temp = temp.substring(0, lastFd);
		SDpath = new File(temp);
		reFleshView(SDpath);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		backEvent();
//		super.onBackPressed();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, 1, 1, "SDCard Stat");
        menu.add(0, 2, 2, "退出");
        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if(item.getItemId() == 1){
            Intent sdCardState = new Intent();
            sdCardState.setClass(SDResurce.this, SDCardStat.class);
            startActivity(sdCardState);
        }
        else if(item.getItemId() == 2){
        	onDestroy();
        } 
        return true;
    }
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case 1:
			reFleshView(SDpath);
			break;
		case 2:
			reFleshView(SDpath);
			break;
		default:
			break;
		}
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
		System.exit(0);
	}


}
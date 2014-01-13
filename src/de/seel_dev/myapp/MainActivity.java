package de.seel_dev.myapp;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	
	private commentData datasource;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		
		datasource = new commentData(this);
		datasource.open();
		
		List<comment> values = datasource.getAllComments();
		
		// use the SimpleCursorAdapter to show the elements in a ListView
		ArrayAdapter<comment> adapter = new ArrayAdapter<comment>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
	
	// Will be called via the onClick attribute of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<comment> adapter = (ArrayAdapter<comment>) getListAdapter();
		comment comment = null;
		
		switch (view.getId()) {
			case R.id.add:
				String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
				int nextInt = new Random().nextInt(3);
				
				// save the new comment to the database
				comment = datasource.createComment(comments[nextInt]);
				adapter.add(comment);
				break;
			
			case R.id.delete:
				if (getListAdapter().getCount() > 0) {
					comment = (comment) getListAdapter().getItem(0);
					datasource.deleteComment(comment);
					adapter.remove(comment);
				}
				break;
		}
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}

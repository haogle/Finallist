package com.example.haogle.finallist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.OnItemMovedListener;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.TimedUndoAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;

import java.util.Arrays;

//import android.widget.ArrayAdapter;

public class MainActivity extends Activity {
    private static final int INITIAL_DELAY_MILLIS = 300;
    private static final int DELETE_TIME=1000;
    private final static int REQUEST_CODE = 1 ;
    final boolean LOAD_TIME=true;
    final Context context = this;
    public static final String PREFERENCES_TODO = "TODO_List_Shared_Preferences";



    private   MyAdapter adapter = new MyAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
        Load(LOAD_TIME);
        final DynamicListView mDynamiclistview=(DynamicListView)findViewById(R.id.myDynamiclistview);
        mDynamiclistview.setDivider(null);
        final Button addbutton=(Button)findViewById(R.id.add);
        final Button setting_button=(Button)findViewById(R.id.settings);

        final Intent menuintent=new Intent(MainActivity.this,Setting_activity.class);
        setting_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
               startActivity(menuintent);
            }
        });

       //final EditText result=(EditText)findViewById(R.id.three);


        final Intent intent=new Intent(MainActivity.this,new_input.class);

        addbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //setContentView(R.layout.myedit);

                startActivityForResult(intent,REQUEST_CODE);
                adapter.notifyDataSetChanged();

            }




        });



        //mDynamiclistview.addHeaderView(LayoutInflater.from(this).inflate(R.layout.list_header, mDynamiclistview, false));
        //ArrayList<String> objs = new ArrayList<String>();
        //objs.add("Hello");
       // objs.add("world");



       /* for(int i=1;i<1000;++i)
        {
            adapter.add("this is row number: " + String.valueOf(i));
        }*/

        //final ArrayAdapter<String> adapter;
       // adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list,objs);
      // SimpleSwipeUndoAdapter simpleSwipeUndoAdapter = new SimpleSwipeUndoAdapter(adapter,this, new MyOnDismissCallback(adapter));
       TimedUndoAdapter simpleSwipeUndoAdapter=new TimedUndoAdapter(adapter,this,new MyOnTimerCallback(adapter));
        simpleSwipeUndoAdapter.setTimeoutMs(DELETE_TIME);
       /* SimpleSwipeUndoAdapter  simpleSwipeUndoAdapter= new SimpleSwipeUndoAdapter(adapter, MainActivity.this,
                new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            adapter.remove(position);
                        }
                    }
                }
        );*/



      AnimationAdapter animationAdapter = new AlphaInAnimationAdapter(simpleSwipeUndoAdapter);
        animationAdapter.setAbsListView(mDynamiclistview);
       mDynamiclistview.setAdapter(animationAdapter);
        mDynamiclistview.enableDragAndDrop();
        mDynamiclistview.setOnItemLongClickListener(new MyOnItemLongClickListener(mDynamiclistview));
        mDynamiclistview.setDraggableManager(new TouchViewDraggableManager(R.id.list_row_draganddrop_touchview));
        mDynamiclistview.setOnItemMovedListener(new MyOnItemMovedListener(adapter));

        ;
       // adapter.add(0,string2);


        animationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);
//        simpleSwipeUndoAdapter.setAbsListView(mDynamiclistview);
//        mDynamiclistview.setAdapter(simpleSwipeUndoAdapter);
//
       mDynamiclistview.enableSimpleSwipeUndo();







       /* mDynamiclistview.enableSwipeToDismiss(
                new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            adapter.remove(position);
                        }
                    }
                }
        );*/


    }

    protected void onPause()
    {
        super.onPause();
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_TODO,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
// This will remove all entries of the specific SharedPreferences
        editor.clear();
        for (int i = 0; i < adapter.getCount(); ++i){
            // This assumes you only have the list items in the SharedPreferences.
            editor.putString(String.valueOf(i), adapter.getItem(i));
        }
        editor.commit();

    }

protected  void Load(boolean loaded){
    if(loaded=true)
    {
       // adapter.add("todo");
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_TODO,
                MODE_PRIVATE);
        for (int i = 0;; ++i){
            final String str = prefs.getString(String.valueOf(i), "");
            if (!str.equals("")){
                adapter.add(str);
            } else {
                break; // Empty String means the default value was returned.
            }
        }

        loaded=false;
    }
}
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == new_input.RESULT_CODE) {
            if (requestCode == REQUEST_CODE) {
                String string2 = data.getStringExtra("value");

                adapter.add(string2);
                System.out.println(string2);
                Toast.makeText(this,string2, Toast.LENGTH_LONG).show();


               // adapter.add(string2);
            }
        }
    }


    private class  MyOnTimerCallback implements OnDismissCallback
    {
        private final ArrayAdapter<String> mAdapter;

        @Nullable
        private Toast mToast;

        MyOnTimerCallback(final ArrayAdapter<String> adapter) {
            mAdapter = adapter;
        }



        public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                mAdapter.remove(position);
            }

            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(
                    MainActivity.this,
                    getString(R.string.removed_positions, Arrays.toString(reverseSortedPositions)),
                    Toast.LENGTH_LONG
            );
            mToast.show();
        }


    }

    private class MyOnDismissCallback implements OnDismissCallback {

        private final ArrayAdapter<String> mAdapter;

        @Nullable
        private Toast mToast;

        MyOnDismissCallback(final ArrayAdapter<String> adapter) {
            mAdapter = adapter;
        }



        public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                mAdapter.remove(position);
            }

            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(
                    MainActivity.this,
                    getString(R.string.removed_positions, Arrays.toString(reverseSortedPositions)),
                    Toast.LENGTH_LONG
            );
            mToast.show();
        }
    }

    private class MyOnItemMovedListener implements OnItemMovedListener {

        private final ArrayAdapter<String> mAdapter;

        private Toast mToast;

        MyOnItemMovedListener(final ArrayAdapter<String> adapter) {
            mAdapter = adapter;
        }

        @Override
        public void onItemMoved(final int originalPosition, final int newPosition) {
            if (mToast != null) {
                mToast.cancel();
            }

            mToast = Toast.makeText(getApplicationContext(), getString(R.string.moved, mAdapter.getItem(newPosition), newPosition), Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    private static class MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener {

        private final DynamicListView mListView;

        MyOnItemLongClickListener(final DynamicListView listView) {
            mListView = listView;
        }

        @Override
        public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            if (mListView != null) {
                mListView.startDragging(position - mListView.getHeaderViewsCount());
            }
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public  void setListViewHeightBasedOnChildren(DynamicListView listView)
    {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++)
        {
            totalHeight += 42;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
    private class MyAdapter extends ArrayAdapter<String> implements UndoAdapter {

        private final Context mContext;

        MyAdapter(final Context context) {

            mContext = context;

        }


        //        public View getView(int position,View convertView,ViewGroup parent)
//        {
//            TextView tv=(TextView) convertView;
//            if(tv==null)
//            {
//                tv=new TextView(MainActivity.this);
//            }
//
//            tv.setText(getItem(position));
//            tv.setMinimumHeight(48);
//            return tv;
//        }
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        public boolean hasStableIds() {
            return true;
        }

        public View getView(final int position, final View convertView, final ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.mylist, parent, false);
            }
            ((TextView) view.findViewById(R.id.list_row_draganddrop_textview)).setText(getItem(position));
            return view;

        }

        public View getUndoView(final int position, final View convertView, @NonNull final ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.undo_row, parent, false);
            }
            return view;
        }

        @NonNull
        public View getUndoClickView(@NonNull final View view) {
            return view.findViewById(R.id.undo_row_undobutton);
        }



    }



    }


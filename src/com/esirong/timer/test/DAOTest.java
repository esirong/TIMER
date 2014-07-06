package com.esirong.timer.test;
//package com.esirong.aweek.test;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.test.AndroidTestCase;
//import android.widget.EditText;
//
//import com.esirong.aweek.DaoMaster;
//import com.esirong.aweek.DaoMaster.DevOpenHelper;
//import com.esirong.aweek.DaoSession;
//import com.esirong.aweek.Note;
//import com.esirong.aweek.NoteDao;
//import com.esirong.aweek.db.dao.BaseDao.onResultListener;
//import com.esirong.aweek.util.L;
//
///**
// * 测试数据库操作
// * 
// * @author esirong
// * 
// */
//public class DAOTest extends AndroidTestCase {
//	private SQLiteDatabase db;
//
//	private EditText editText;
//
//	private DaoMaster daoMaster;
//	private DaoSession daoSession;
//	private NoteDao noteDao;
//
//	private Cursor cursor;
//	private onResultListener l;
//
//	// 鍒涘缓鏁版嵁搴撴祴璇�
//	public void testCreate() {
//
//		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(),"notes-db", null);
//		db = helper.getWritableDatabase();
//		daoMaster = new DaoMaster(db);
//		daoSession = daoMaster.newSession();
//		noteDao = daoSession.getNoteDao();
//		
//		for(int i=0; i<100000; i++){
//			Note note = new Note();
//			
//			note.setText("情境"+i);
//			noteDao.insert(note);
//		}
//		Note note = new Note();
//		
//		note.setText("");
//		noteDao.insert(note);
//		L.d("DaoExample", "Inserted new note, ID: " + note.getId());
//
//	}
//}

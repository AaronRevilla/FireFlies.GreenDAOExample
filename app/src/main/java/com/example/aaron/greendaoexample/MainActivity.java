package com.example.aaron.greendaoexample;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.aaron.greendaoexample.db.DaoMaster;
import com.example.aaron.greendaoexample.db.DaoSession;
import com.example.aaron.greendaoexample.db.PhoneList;
import com.example.aaron.greendaoexample.db.User;
import com.example.aaron.greendaoexample.db.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_USER_RESULT = 1;
    public static final int EDIT_USER_RESULT = 2;
    DaoSession daoSession;
    LinearLayout showList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showList = ((LinearLayout) findViewById(R.id.userShowList));

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user-v1-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        updateUserView();

    }

    public void updateUserView(){
        showList.removeAllViews();
        UserDao userDao = daoSession.getUserDao();
        List<User> usersList = userDao.loadAll();

        for(User usr: usersList){
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtUsr = new TextView(this);
            txtUsr.setTypeface(txtUsr.getTypeface(), Typeface.BOLD);
            txtUsr.setText(usr.getName() + " " + usr.getAge() + " " + usr.getPhone());
            int idUsr = (int)(long) usr.getId();
            txtUsr.setId(idUsr);
            txtUsr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //editar usuario
                    updateUser(v.getId());
                }
            });
            Button update = new Button(this);
            //update.setBackgroundResource(R.drawable.edit);
            update.setText("Update");
            update.setWidth(10);
            update.setHeight(10);
            update.setId((int)(long) usr.getId());
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //eliminar usuario
                    updateUser(v.getId());
                }
            });
            Button eliminar = new Button(this);
            //eliminar.setBackgroundResource(R.drawable.trash);
            eliminar.setText("Delete");
            eliminar.setHeight(25);
            eliminar.setId((int)(long) usr.getId());
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //eliminar usuario
                    deleteUser(v.getId());
                }
            });

            row.addView(txtUsr);
            row.addView(update);
            row.addView(eliminar);

            showList.addView(row);
        }
    }

    public void addNewUser(View view) {
        Intent addUserIntent = new Intent(this, AddUserActivity.class);
        //addUserIntent.putExtra("Name", personName.getText().toString());
        addUserIntent.putExtra("isUpdate", false);
        startActivityForResult(addUserIntent, NEW_USER_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_RESULT){
            if(resultCode == RESULT_OK){

                User newUser = new User();
                newUser.setName(data.getStringExtra("nameUser"));
                newUser.setAge(Integer.parseInt(data.getStringExtra("ageUser")));
                newUser.setPhone(data.getStringExtra("phoneUser"));

                //PhoneList phone = new PhoneList();
                //phone.setPhone(data.getStringExtra("phoneUser"));
                //phone.setUserId(newUser.getId());

                UserDao usrDao = daoSession.getUserDao();
                usrDao.insert(newUser);

                updateUserView();
            }
        }
        else if(requestCode == EDIT_USER_RESULT){
            if(resultCode == RESULT_OK){

                User newUser = new User();
                newUser.setName(data.getStringExtra("nameUser"));
                newUser.setAge(Integer.parseInt(data.getStringExtra("ageUser")));
                newUser.setPhone(data.getStringExtra("phoneUser"));
                newUser.setId(data.getLongExtra("idUser", 0));

                //PhoneList phone = new PhoneList();
                //phone.setPhone(data.getStringExtra("phoneUser"));
                //phone.setUserId(newUser.getId());

                UserDao usrDao = daoSession.getUserDao();
                usrDao.update(newUser);

                updateUserView();
            }
        }
    }

    public void updateUser(int usrId){
        UserDao usrDao = daoSession.getUserDao();
        User user = usrDao.load((long) usrId);

        Intent addUserIntent = new Intent(this, AddUserActivity.class);
        addUserIntent.putExtra("isUpdate", true);
        addUserIntent.putExtra("nameUser", user.getName());
        addUserIntent.putExtra("ageUser", user.getAge());
        addUserIntent.putExtra("phoneUser", user.getPhone());
        addUserIntent.putExtra("idUser", user.getId());
        startActivityForResult(addUserIntent, EDIT_USER_RESULT);
    }

    public void deleteUser(int usrId){
        UserDao usrDao = daoSession.getUserDao();
        usrDao.deleteByKey((long) usrId);
        updateUserView();
    }
}

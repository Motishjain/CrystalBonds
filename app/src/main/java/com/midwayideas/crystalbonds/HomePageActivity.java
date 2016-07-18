package com.midwayideas.crystalbonds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.midwayideas.constants.AppConstants;
import com.midwayideas.database.DBHelper;
import com.midwayideas.database.User;
import com.midwayideas.util.ImageUtility;
import com.midwayideas.util.KeyboardUtil;
import com.midwayideas.util.ValidationUtil;
import com.midwayideas.view.CustomFontButton;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActivity {

    ImageView backgroundRatingImage;
    Dao<User, Integer> userDao;
    QueryBuilder<User, Integer> queryBuilder;
    List<User> userList = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String activationStatus = sharedPreferences.getString("activationStatus", null);

        if(activationStatus!=null && activationStatus.equals(AppConstants.SUBSCRIPTION_EXPIRED)){
            Intent subscriptionInfo = new Intent(HomePageActivity.this, SubscriptionInfoActivity.class);
            subscriptionInfo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(subscriptionInfo);
        }

        try {
            userDao = OpenHelperManager.getHelper(this, DBHelper.class).getCustomDao("User");
            queryBuilder = userDao.queryBuilder();
            userList = queryBuilder.query();
        } catch (Exception e) {
            e.printStackTrace();
        }

        backgroundRatingImage = (ImageView) findViewById(R.id.backgroundRatingImage);
        backgroundRatingImage.setImageBitmap(ImageUtility.getImageBitmap(this,R.drawable.shopping_bg));
    }

}
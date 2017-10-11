package com.apps.gerdjes.dishlogger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "dishes";
    private static final int DATABASE_VERSION = 1;

    private Context mContext;
    private Dao<Dish, Long> dishDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;


    }
    // Fetch all the com.apps.gerdjes.myormliteexample.Person records

    public List<Dish> GetData() {
        DatabaseHelper helper = new DatabaseHelper(mContext);
        try {
            Dao<Dish, Long> simpleDao = helper.getDishDao();
            return simpleDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    // Add the specified com.apps.gerdjes.myormliteexample.Person objects to the database
    public int addDish(Dish dish)

    {
        try {
            Dao<Dish, Long> dao = getDishDao();
            return dao.create(dish);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public Dao<Dish, Long> getDishDao() throws SQLException {
        if (dishDao == null) {
            dishDao = getDao(Dish.class);
        }
        return dishDao;
    }

// Delete all records in the database

    public void deleteAllDishes()

    {
        try

        {
            Dao<Dish, Long> dao = getDishDao();
            List<Dish> list = dao.queryForAll();
            dao.delete(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            Log.i(DatabaseHelper.class.getName(), "onCreate");

            TableUtils.createTable(connectionSource, Dish.class);

            TableUtils.createTable(connectionSource, DishType.class);
            addDishType(new DishType("Breakfast"));

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Cannot create databases", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion)

    {
        try {

            Log.i(DatabaseHelper.class.getName(), "onUpgrade");

            TableUtils.dropTable(connectionSource, Dish.class, true);
            TableUtils.dropTable(connectionSource, DishType.class, true);

            onCreate(db, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Cannot drop databases", e);
            throw new RuntimeException(e);
        }
    }

    ////////////// New Database Dish_Types ////////////////////////////////////



    private Dao<DishType, Long> dishTypeDao = null;




    public List<DishType> GetAllDishTypes() {
        DatabaseHelper helper = new DatabaseHelper(mContext);
        try {
            Dao<DishType, Long> simpleDao = helper.getDishTypeDao();
            return simpleDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    // Add the objects to the database
    public int addDishType(DishType dishType)

    {
        try {
            Dao<DishType, Long> dao = getDishTypeDao();
            return dao.create(dishType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public Dao<DishType, Long> getDishTypeDao() throws SQLException {
        if (dishTypeDao == null) {
            dishTypeDao = getDao(DishType.class);
        }
        return dishTypeDao;
    }

// Delete all records in the database

    public void deleteAllDishTypes()

    {
        try

        {
            Dao<DishType, Long> dao = getDishTypeDao();
            List<DishType> list = dao.queryForAll();
            dao.delete(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




}

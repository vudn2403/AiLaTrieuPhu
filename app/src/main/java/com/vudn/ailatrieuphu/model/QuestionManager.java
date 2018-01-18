package com.vudn.ailatrieuphu.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 17/12/2017.
 */

public class QuestionManager {
    private static final String DATABASE_PATH = "/data/data/com.vudn.ailatrieuphu/";
    private static final String DATABASE_NAME = "database.db";
    private String desPath;

    private SQLiteDatabase questionDatabase;

    private List<Question> questions;

    public QuestionManager(Context context) {
        copyDatabase(context);
    }

    private void copyDatabase(Context context) {
        desPath = DATABASE_PATH + DATABASE_NAME;
        File desFile = new File(desPath);
        if (desFile.exists()) {
            return;
        }

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("database.db");

            FileOutputStream outputStream = new FileOutputStream(desFile);

            byte[] bytes = new byte[1024];
            int length = inputStream.read(bytes);
            while (length != -1) {
                outputStream.write(bytes, 0, length);
                length = inputStream.read(bytes);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void openDatabase() {
        if (questionDatabase == null || !questionDatabase.isOpen()) {
            questionDatabase = SQLiteDatabase.openDatabase(
                    desPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    private void closeDatabase() {
        if (questionDatabase != null || questionDatabase.isOpen()) {
            questionDatabase.close();
        }
    }

    public List<Question> createListQuestion() {
        questions = new ArrayList<>();
        openDatabase();
        //Create list question
        for (int i = 1; i <= 15; i++) {
            String table = "Question" + i;
            questions.add(createQuestion(table));
        }
        closeDatabase();
        return questions;
    }

    private Question createQuestion(String table) {
        Question question;
        question = new Question();
        String sql = "SELECT * FROM " + table + " ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = questionDatabase.rawQuery(sql, null);
        if (cursor == null) {
            return question;
        }

        if (cursor.getCount() == 0) {
            cursor.close();
            return question;
        }

        cursor.moveToFirst();

        //Create question
        String questionValue = cursor.getString(cursor.getColumnIndex("Question"));
        String caseA = cursor.getString(cursor.getColumnIndex("CaseA"));
        String caseB = cursor.getString(cursor.getColumnIndex("CaseB"));
        String caseC = cursor.getString(cursor.getColumnIndex("CaseC"));
        String caseD = cursor.getString(cursor.getColumnIndex("CaseD"));
        int trueCase = cursor.getInt(cursor.getColumnIndex("TrueCase"));
        question = new Question(questionValue, caseA, caseB, caseC, caseD, trueCase);

        cursor.close();
        return question;
    }
}

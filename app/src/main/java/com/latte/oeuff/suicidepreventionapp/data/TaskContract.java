package com.latte.oeuff.suicidepreventionapp.data;

import android.provider.BaseColumns;

/**
 * Created by Oeuff on 04/07/2016.
 */
public class TaskContract {

    public class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME="tasks";
        public static final String COLUMN_TASK="task";
    }
}

package com.aldc.talha.aldc;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by HP-NPC on 31/07/2017.
 */

public final class TestsContract {

    private TestsContract(){

    }

    public static final String CONTENT_AUTHORITY = "com.aldc.talha.aldc";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TESTS = "tests";
    public static final String PATH_RESULT = "result";

    public static final class TestEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TESTS);

        public static final Uri RESULT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_RESULT);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TESTS;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TESTS;

        public final static String _ID = BaseColumns._ID;

        public final static String test_ID = "idoftest";

        public final static String test_Name = "nameoftest";

        public final static String table_name_of_Qus = "codeoftests";

        public final static String table_name = "tests";

        public final static String table2_name = "tests";

        public final static String qus_ID = "id";

        public final static String Qus = "qus";

        public final static String OP1 = "ops1";

        public final static String OP2 = "ops2";

        public final static String OP3 = "ops3";

        public final static String OP4 = "ops4";

        public final static String Ans = "ans";

        public final static String Result = "result";

        public final static String Per = "per";

        public final static String Total = "total";

        public final static String Point = "point";

        public final static String Currect = "currect";

        public final static String Current = "current";

        public final static String Catagory = "catagori";

        public final static String JSC = "jsc";

        public final static String SSC = "ssc";

        public final static String HSC = "hsc";

        public final static String ADMISSION = "admission";

        public final static String BCS = "bcs";

        public final static String ALL = "all";

    }

}

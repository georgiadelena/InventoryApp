package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;

/**
 * Created by Elena on 14/7/2018.
 */

public class ProductContract {
    /** To prevent someone from accidentally instantiating the contract class, we'll give it
     * an empty constructor
     */
    public ProductContract(){}

    public static final class ProductEntry implements BaseColumns {

        /**
         * Name of the database table for the products
         */
        public static final String TABLE_NAME = "product";

        /**
         * Unique ID number for each product (only for use in the database table)
         * Type: INTEGER
         */
        public static final String _ID = BaseColumns._ID;

        /**
         *  Name of the product
         *  Type: TEXT
         */
        public static final String COLUMN_PRODUCT_NAME = "name";

        /**
         *  Price of the product
         *  Type: INTEGER
         */
        public static final String COLUMN_PRODUCT_PRICE = "price";

        /**
         *  Quantity of the product
         *  Type: INTEGER
         */
        public static String COLUMN_PRODUCT_QUANTITY = "quantity";

        /**
         * Supplier name
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_NAME = "supplier";

        /**
         * Supplier phone number
         * Type: TEXT
         */
        public static String COLUMN_SUPPLIER_PHONE_NR = "phone";
    }
}

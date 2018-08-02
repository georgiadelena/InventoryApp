package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by Elena on 28/7/2018.
 *
 * {@link ProductCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of product data as its data source. This adapter knows
 * how to create list items for each row of product data in the {@link Cursor}.
 */

public class ProductCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link ProductCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the product data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current product can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.product_name);
        TextView priceTextView = (TextView) view.findViewById(R.id.product_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.product_quantity);
        Button saleButton = (Button) view.findViewById(R.id.sale_button);

        // Find the columns of product attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);

        // Read the product attributes from the Cursor for the current product
        final String productName = cursor.getString(nameColumnIndex);
        int productPrice = cursor.getInt(priceColumnIndex);
        int productQuantity = cursor.getInt(quantityColumnIndex);


        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        priceTextView.setText("Price: " + Integer.toString(productPrice));
        quantityTextView.setText("Quantity: " + Integer.toString(productQuantity));

        //Get the current quantity and make into an integer
        String currentQuantityString = cursor.getString(quantityColumnIndex);
        final int currentQuantity = Integer.valueOf(currentQuantityString);
        // Get the rows from the table with the ID
        final int productId = cursor.getInt(cursor.getColumnIndex(ProductEntry._ID));

        saleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Checking if the current quantity > 0, so that it will be decremented and updated
                if (currentQuantity > 0){
                    int newQuantity = currentQuantity - 1;

                    // Getting the URI and appending the ID of the current row
                    Uri quantityUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, productId);

                    // Updating the quantity with the new value
                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, newQuantity);
                    context.getContentResolver().update(quantityUri, values, null, null);
                }
                // If the quantity is 0, show a toast message that the product is out of stock. The
                // database will not be updated.
                else{
                    Toast.makeText(context, "This product is out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

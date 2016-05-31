package com.daffodilsw.whatsappcontact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

/**
 * Whats app contact Java module for RCT Native
 */
public class WhatsAppContact extends ReactContextBaseJavaModule {

    public WhatsAppContact(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "WhatsApp";
    }

    /**
     * Method for RCT Native to get all WhatsApp Contact
     *
     * @param callback Callback for function with List of contacts
     */
    @ReactMethod
    public void getAllWhatsAppContacts(Callback callback) {
        ContentResolver cr = getReactApplicationContext().getContentResolver();

        //RowContacts for filter Account Types
        Cursor contactCursor = cr.query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.RawContacts._ID,
                        ContactsContract.RawContacts.CONTACT_ID},
                ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
                new String[]{"com.whatsapp"},
                null);

        //ArrayList for Store WhatsApp Contact
        WritableArray writableArray = Arguments.createArray();

        if (contactCursor != null && contactCursor.getCount() > 0 && contactCursor.moveToFirst()) {
            do {
                //WhatsAppContactId for get Number,Name,Id ect...
                // from  ContactsContract.CommonDataKinds.Phone
                String whatsappContactId = contactCursor
                        .getString(contactCursor.getColumnIndex(
                                ContactsContract.RawContacts.CONTACT_ID));
                if (whatsappContactId != null) {
                    //Get Data from ContactsContract.CommonDataKinds.Phone of Specific CONTACT_ID
                    Cursor whatsAppContactCursor = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{whatsappContactId}, null);

                    if (whatsAppContactCursor != null) {
                        whatsAppContactCursor.moveToFirst();
                        String name = whatsAppContactCursor.getString(whatsAppContactCursor.
                                getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String number = whatsAppContactCursor.getString(whatsAppContactCursor.
                                getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        whatsAppContactCursor.close();

                        WhatsAppBean whatsAppBean = new WhatsAppBean();
                        whatsAppBean.setName(name);
                        whatsAppBean.setPhoneNo(number);


                        //Add Number to ArrayList
                        writableArray.pushMap(whatsAppBean.toMap());
                    }
                }
            } while (contactCursor.moveToNext());
            contactCursor.close();

        }

        // Return WriteAbleArray of WhatsApp contact list to RCTJs function in callback
        callback.invoke(writableArray);
    }

    /**
     * Data model to hold a Whats app contact
     */
    private class WhatsAppBean {
        private String name;
        private String phoneNo;

        public void setName(String name) {
            this.name = name;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        /**
         * Convert a WhatsApp contact object to WriteAble Map
         *
         * @return Writeable map of WhatsappContact of this
         */
        public WritableMap toMap() {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("name", name);
            writableMap.putString("phone_no", phoneNo);
            return writableMap;
        }
    }
}

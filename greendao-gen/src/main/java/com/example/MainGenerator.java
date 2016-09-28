package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MainGenerator {

    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final int DB_VERSION = 1;

    public static void main(String[] args) {
        Schema schema = new Schema( DB_VERSION, "com.example.aaron.greendaoexample.db");
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema, PROJECT_DIR + "\\app\\src\\main\\java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        Entity user = addUser(schema);
        Entity phoneList = addPhoneList(schema);

        Property userId = phoneList.addLongProperty("userId").notNull().getProperty();
        user.addToMany(phoneList, userId, "userPhone");
    }

    private static Entity addUser(final Schema schema) {
        Entity user = schema.addEntity("User");
        user.addIdProperty().primaryKey().autoincrement();
        user.addStringProperty("name").notNull();
        user.addIntProperty("age");
        user.addStringProperty("phone");
        return user;
    }

    private static Entity addPhoneList(final Schema schema) {
        Entity phone = schema.addEntity("PhoneList");
        phone.addIdProperty().primaryKey().autoincrement();
        phone.addStringProperty("phone").notNull();
        return phone;
    }

}

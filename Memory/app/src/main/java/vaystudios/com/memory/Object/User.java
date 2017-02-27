package vaystudios.com.memory.Object;

import android.support.design.internal.ParcelableSparseArray;

/**
 * Created by Devan on 2/25/2017.
 */

public class User {

    public String name;
    public String username;
    public String user_id;
    public String password;
    public String age;

    public User()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

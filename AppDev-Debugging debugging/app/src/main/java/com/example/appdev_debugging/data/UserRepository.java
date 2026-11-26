
package com.example.appdev_debugging.data;

import com.example.appdev_debugging.data.model.User;

public interface UserRepository {

    User findByEmail(String email);

    boolean exists(String email);

    boolean add(User user);
}

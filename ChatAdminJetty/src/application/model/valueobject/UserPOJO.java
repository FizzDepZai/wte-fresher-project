/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.valueobject;

/**
 *
 * @author biendltb
 */
public class UserPOJO {
    
    // Id of user
    public int userId;

    //password
    public String password;

    // Username
    public String userName;
    

    // First name
    public String firstName;
    
    // Last name
    public String lastName;
    
    // Birthday
    public String birthday;
    
    // Email
    public String email;
   
    public UserPOJO(int userId, String userName, String firstName, String lastName,
            String birthday, String email) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
    }

    public UserPOJO() {
    }
    
            
    
}

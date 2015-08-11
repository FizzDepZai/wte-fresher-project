/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author tript 
 * Create on 10:18:39 AM Dec 17, 2013
 */
public class SettingUtility {

    public static String getTimeNow() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // tạo 1 đối tượng có định dạng thời gian yyyy-MM-dd HH:mm:ss
        Date date = new Date(); // lấy thời gian hệ thống
        String stringDate = dateFormat.format(date);
        System.out.println(stringDate);
        return stringDate;
    }
}

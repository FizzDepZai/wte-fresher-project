/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.valueobject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class specificRoomReportPOJO {
    List<EachRoomDataForReport> atATime = new ArrayList<>();
    public List<EachRoomDataForReport> getAtATime() {
        return atATime;
    }
    public void add(EachRoomDataForReport x){
        atATime.add(x);
    }
}

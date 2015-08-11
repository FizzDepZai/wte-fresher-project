/* 
 * File:   SinhVien.cpp
 * Author: tript
 * 
 * Created on December 8, 2013, 4:17 PM
 */

#include "ini/TestSinhVien.h"

SinhVien::SinhVien(int id,string name, string birthday, int classCourseId) {
    this->birthday = birthday;
    this->name = name;
    this->classCourseId = classCourseId;
    this->id = id;
}

SinhVien::SinhVien(){
    
}

SinhVien::~SinhVien() {
}
SinhVien::SinhVien(const char* bytes){
    
}

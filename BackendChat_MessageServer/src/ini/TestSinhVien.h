/* 
 * File:   SinhVien.h
 * Author: tript
 *
 * Created on December 8, 2013, 4:17 PM
 */

#ifndef SINHVIEN_H
#define	SINHVIEN_H
#include <string>
using namespace std;

class SinhVien {
public:
    SinhVien(int id,string name ,string birthday,int classCourse);
    virtual ~SinhVien();
    SinhVien();
    explicit SinhVien(const char* bytes);
public:
    string name;
    string birthday;
    int classCourseId;
    int id;
};

#endif	/* SINHVIEN_H */


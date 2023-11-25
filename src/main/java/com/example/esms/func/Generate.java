package com.example.esms.func;

import com.example.esms.entity.*;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;

public class Generate {
    Faker faker = new Faker(new Locale("vi"));

    public ArrayList<Course> generateCourse(int total){
        ArrayList<Course> CourseArrayList = new ArrayList<>();
        int i = 0;
        do {
            Course course = new Course();
            course.setCourseId(faker.regexify("CO[0-9]{6}"));
            if (!CourseArrayList.contains(course)) {
                course.setCourseId(faker.animal().name());
                course.setCourseId(String.valueOf((float) faker.number().randomDouble(1, 1, 2)));
                CourseArrayList.add(course);
                i++;
            }
        } while (i != total);
        return CourseArrayList;
    }

    public ArrayList<ExamSlot> generateSlot(int total, String startDay){
        ArrayList<ExamSlot> arrSlots = new ArrayList<>();
        String[] date = startDay.split("/");
        int totalDay = total/4 + ((total%4!=0)?1:0);
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int  year = Integer.parseInt(date[2]);
        String[] time ={"7h-9h15","9h30-11h45","12h30-14h45","15h-17h15"};
        for (int i = 0; i < totalDay; i++) {
            for (int j = 0; j < 4; j++) {
                boolean test=true;
                ExamSlot examSlot = new ExamSlot();
                do {
                    String id = faker.regexify("SL[0-9]{6}");
                    examSlot.setExamSlotId(id);
                    test = arrSlots.contains(examSlot);
                }while (test);
                String dateNow = (day>9?Integer.toString(day):"0"+Integer.toString(day))+"/";
                dateNow += (month>9?Integer.toString(month):"0"+Integer.toString(month))+"/"+Integer.toString(year);
                examSlot.setDate(dateNow);
                examSlot.setHour(2.25f);
                examSlot.setTime(time[j]);
                arrSlots.add(examSlot);
            }
            if(!((month%2!=0&&month<=7)||(month%2==0&&month>7))&&day==30){
                day = 1;
                month++;
            }else if(day==31){
                day=1;
                month++;
            }else if(month==2&&day==28&&year%4!=0){
                day =1;
                month++;
            }else if(month==2&&day==29&&year%4==0){
                day=1;
                month++;
            }
            else{
                day++;
            }
            if(month>12){
                month=1;
                year++;
            }
        }

        return arrSlots;
    }
    public ArrayList<ExamSchedule> generateExamSchedule(int total){
        ArrayList<ExamSchedule> ExamScheduleArrayList = new ArrayList<>();
        int i = 0;
        do {
            ExamSchedule examSchedule = new ExamSchedule();
            examSchedule.setExamScheduleId(faker.regexify("ES[0-9]{6}"));
            if (!ExamScheduleArrayList.contains(examSchedule)) {
                i++;
                ExamScheduleArrayList.add(examSchedule);
            }
        } while (i != total);

        return ExamScheduleArrayList;
    }

    public ArrayList<Room> generateClassrooms(int total){
        ArrayList<Room> classroomArrayList = new ArrayList<>();
        int i = 0;
        do {
            Room classroom = new Room();
            classroom.setRoomId(faker.regexify("[0-4]{1}[0-3]{1}[0-9]{1}"));
            if (!classroomArrayList.contains(classroom)) {
                classroomArrayList.add(classroom);
                i++;
            }
        } while (i != total);
        return classroomArrayList;
    }
}

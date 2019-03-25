package com.company;

public class Main {

    public static void main(String[] args) {

    }

    private static String make_query(int num,String parameters){
        String res;
        if(num==1){
            res="select EMPLOYEE.ENAME from" +
                    "EMPLOYEE,WORKS_ON where" +
                    "EMPLOYEE.ESSN=WORKS_ON.ESSN";
            int pno=Integer.parseInt(parameters);
            res+=(" and WORKS_ON.PNO="+pno+";");
        }else if(num==2){
            res="select ENAME from" +
                    "EMPLOYEE,WORKS_ON,PROJECT" +
                    "where EMPLOYEE.ESSN=WORKS_ON.ESSN" +
                    "AND WORKS_ON.PNO=PROJECT.PNO" +
                    "WHERE PNAME='"+parameters+"';";
        }else if(num==3){
            res="SELECT ENAME,ADDRESS" +
                    "FROM DEPARTMENT,EMPLOYEE" +
                    "WHERE DEPARTMENT.DNO=EMPLOYEE.DNO" +
                    "AND DNAME='"+parameters+"';";
        }else if(num==4){
            String[] strings=parameters.split(" ");
            String DNAME=strings[0];
            String SALARY=strings[1];
            res="SELECT ENAME,ADDRESS" +
                    "FROM DEPARTMENT,EMPLOYEE" +
                    "WHERE DEPARTMENT.DNO=EMPLOYEE.DNO" +
                    "AND DNAME='"+DNAME+"' AND SALARY<"+SALARY+";";
        }else if (num==5){
            res="SELECT ENAME FROM EMPLOYEE WHERE" +
                    "NOT EXISTS (" +
                    "SELECT * FROM WORKS_ON WHERE EMPLOYEE.ESSN" +
                    "=WORKS_ON.ESSN AND PNO='"+parameters+"');";
        }else if(num==6){
            res="SELECT ENAME,DNAME FROM" +
                    "DEPARTMENT,EMPLOYEE T1,EMPLOYEE T2" +
                    "WHERE DEPARTMENT.DNO=T1.DNO" +
                    "AND T1.SUPERSSN=T2.SUPERSSN AND " +
                    "T2.ENAME='"+parameters+"';";
        }
    }
}

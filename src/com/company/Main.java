package com.company;

import org.junit.Test;

import java.sql.*;

public class Main {

    /**
     * 1：参加了项目编号为%PNO%的项目的员工号，其中%PNO%为C语言编写的程序的输入参数；
     * 2：参加了项目名为%PNAME%的员工名字，其中%PNAME%为C语言编写的程序的输入参数；
     * 3：在%DNAME%工作的所有工作人员的名字和地址，其中%DNAME%为C语言编写的程序的输入参数；
     * 4：在%DNAME%工作且工资低于%SALARY%元的员工名字和地址，其中%DNAME%和%SALARY%为C语言编写的程序的输入参数；
     * 5：没有参加项目编号为%PNO%的项目的员工姓名，其中%PNO%为C语言编写的程序的输入参数；
     * 6：由%ENAME%领导的工作人员的姓名和所在部门的名字，，其中%ENAME%为C语言编写的程序的输入参数；
     * 7：至少参加了项目编号为%PNO1%和%PNO2%的项目的员工号，其中%PNO1%和%PNO2%为C语言编写的程序的输入参数；
     * 8：员工平均工资低于%SALARY%元的部门名称，其中%SALARY%为C语言编写的程序的输入参数；
     * 9：至少参与了%N%个项目且工作总时间不超过%HOURS%小时的员工名字，其中%N%和%SALARY%为C语言编写的程序的输入参数；
     * @param args 传入参数
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //company_query –q <Number> -p [Parameters]
        String driver="com.mysql.jdbc.Driver";
        //URL指定要访问数据库的名称company
        String url="jdbc:mysql://localhost:3306/COMPANY";
        String user="root";
        String password="98a-042-b-6";
        Class.forName(driver);//加载驱动程序
        Connection connection= DriverManager.getConnection(url,user,password);
        String sql="SELECT * from EMPLOYEE";
        Statement statement=connection.createStatement();
        ResultSet resultSet=null;
        switch (args[2]){
            case "1":
                resultSet=statement.executeQuery(make_query(1,"1"));
                while (resultSet.next()){
                    System.out.println("员工号");
                    System.out.println(resultSet.getString(1));
                }
                break;
        }
        return;
    }
    private static String make_query(int num,String parameters){
        String res="";
        if(num==1){
            res="select EMPLOYEE.ESSN from " +
                    "EMPLOYEE,WORKS_ON where " +
                    "EMPLOYEE.ESSN=WORKS_ON.ESSN";
            long pno=Integer.parseInt(parameters);
            res+=(" and WORKS_ON.PNO="+pno+";");
        }else if(num==2){
            res="select ENAME from " +
                    "EMPLOYEE,WORKS_ON,PROJECT" +
                    " where EMPLOYEE.ESSN=WORKS_ON.ESSN" +
                    " AND WORKS_ON.PNO=PROJECT.PNO" +
                    " WHERE PNAME='"+parameters+"';";
        }else if(num==3){
            res="SELECT ENAME,ADDRESS " +
                    "FROM DEPARTMENT,EMPLOYEE" +
                    " WHERE DEPARTMENT.DNO=EMPLOYEE.DNO " +
                    "AND DNAME='"+parameters+"';";
        }else if(num==4){
            String[] strings=parameters.split(" ");
            String DNAME=strings[0];
            String SALARY=strings[1];
            res="SELECT ENAME,ADDRESS" +
                    " FROM DEPARTMENT,EMPLOYEE" +
                    " WHERE DEPARTMENT.DNO=EMPLOYEE.DNO" +
                    " AND DNAME='"+DNAME+"' AND SALARY<"+SALARY+";";
        }else if (num==5){
            res="SELECT ENAME FROM EMPLOYEE WHERE" +
                    "NOT EXISTS (" +
                    "SELECT * FROM WORKS_ON WHERE EMPLOYEE.ESSN" +
                    "=WORKS_ON.ESSN AND PNO="+parameters+");";
        }else if(num==6){
            res="SELECT ENAME,DNAME FROM " +
                    "DEPARTMENT,EMPLOYEE T1,EMPLOYEE T2 " +
                    "WHERE DEPARTMENT.DNO=T1.DNO" +
                    " AND T1.SUPERSSN=T2.SUPERSSN AND " +
                    "T2.ENAME='"+parameters+"';";
        }else if(num==7){
            String[] strings=parameters.split(" ");
            String PNO1=strings[0];
            String PNO2=strings[1];
            res="SELECT E.ESSN FROM WORKS_ON E,WORKS_ON M,EMPLOYEE" +
                    " WHERE E.PNO="+PNO1 +
                    " AND M.PNO="+PNO2 +
                    "E.ESSN=M.ESSN;";
        }else if(num==8){
            res="SELECT DNAME FROM " +
                    "DEPARTMENT,EMPLOYEE WHERE DEPARTMENT.DNO=EMPLOYEE.DNO GROUP BY" +
                    " DNAME HAVING(AVG(SALARY)<"+parameters+");";
        }else if(num==9){
            String[] strings=parameters.split(" ");
           res="SELECT ENAME FROM(SELECT EMPLOYEE.ESSN,ENAME FROM EMPLOYEE" +
                   ", WORKS_ON WHERE EMPLOYEE.ESSN=WORKS_ON.ESSN" +
                   " GROUP BY(EMPLOYEE.ESSN) HAVING(COUNT(*)>="+ strings[0]+" AND SUM(HOURS)<="+strings[1]+")) AS " +
                   "TABLE1";
        }
        return res;
    }
    @Test
    public void testMakeQuery(){
        System.out.println(make_query(1,"2000000001"));
        System.out.println(make_query(2,"项目2"));
        System.out.println(make_query(3,"办公室部"));
        System.out.println(make_query(4,"办公室部 200000"));
        System.out.println(make_query(5,"1"));
        System.out.println(make_query(6,"张红"));
        System.out.println(make_query(7,"1 5"));
        System.out.println(make_query(8,"20000"));
        System.out.println(make_query(9,"2 8"));
    }
    @Test
    public void testStatement() throws SQLException, ClassNotFoundException {
        //驱动器的名称
        String driver="com.mysql.jdbc.Driver";
        //URL指定要访问数据库的名称company
        String url="jdbc:mysql://localhost:3306/COMPANY";
        String user="root";
        String password="98a-042-b-6";
        //声明connection对象
        Class.forName(driver);//加载驱动程序
        Connection connection= DriverManager.getConnection(url,user,password);
        String sql="SELECT * from EMPLOYEE";
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql);
        while (resultSet.next()){
           // System.out.println(resultSet.getString("ENAME"));
            System.out.println(resultSet);
        }
    }
}

//package DB;

import java.io.BufferedReader;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

import java.io.FileReader;

public class Finalproject {
    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        Connection conn;
        Statement stm;
        ResultSet r = null;

        int batchSize = 20;
        ArrayList<Integer> items = new ArrayList<Integer>();
        String[] chk = new String[6];
        String[] table = {"pharmacy","hospital","promenade","ShoppingMall","communityservicecenter","culturalfacility"};
        String query = "";
        //String query2 ="";
        int idx = 0;
        int cnt=0;
        PreparedStatement pstmt;
        PreparedStatement pstmt2;
        //Statement stmt;
        String sql;
        String[] district = new String[3];


        String url = "jdbc:postgresql://localhost/postgres";
        String username = "kimhwi";
        String password = "9509";

        try {

            conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            Scanner scan = new Scanner(System.in);
            while(true){
                System.out.println(
                        "Hello, our service has three functions.\n" +
                                "1. Information on the real estate agent's office in the place where the desired convenience facility is located\n" +
                                "2. Location of English-speaking pharmacies\n" +
                                "3. A place where you can enjoy shopping, walking trails, cultural facilities, etc.\n" +
                                "\n" +
                                "Please select a service." +
                                "\nenter 1 or 2 or 3" +
                                "\nif you want finsh service input another key");
                int mode = scan.nextInt();

                if(mode == 1){
                    cnt=0;
                    idx=0;


                    stmt.execute("delete from myplace ");


                    sql ="drop trigger R1 on myplace; " +
                            "create or replace function t1() returns trigger as $R1$\n" +
                            "begin\n" +
                            "IF not exists(select * from realestate where administrativebuilding = New.administrativebuilding) " +
                            "THEN " +
                            "insert into myplace select Administrativebuilding from realestate where(" +
                            "5>abs(num - (select num from seoul_AB where administrativebuilding = New.administrativebuilding)));" +
                            "raise notice 'your place has heen replaced = %',New.administrativebuilding;"+
                            "return null;" +
                            "END IF;" +

                            "return new;\n" +
                            "end;\n" +
                            "$R1$ language 'plpgsql';\n" +
                            "create trigger R1\n" +
                            "before insert on myplace \n" +
                            "for each row\n" +
                            "execute procedure t1();";
//

                    stmt.execute(sql);
//
                    System.out.println("" +

                            "                                                        Gangbuk-gu   Dobong-gu    Nowon-gu\n\n" +
                            "       Eunpyeong-gu    Seodaemun-gu    Jongno-gu      Seongbuk-gu      Dongdaemun-gu    Jungnang-gu\n\n"+
                            "Gangseo-gu   Mapo-gu    Yongsan-gu    Jung-gu     Seongdong-g    Gwangjin-gu   Gangdong-gu\n\n" +
                            "     Yangcheon-gu    Yeongdeungpo-gu    Dongjak-gu    Seocho-gu   Gangnam-gu   Songpa-gu\n\n" +
                            "       Guro-gu   Geumcheon-gu    Gwanak-gu\n\n" +


                            "selcet count of district (1 ~ 3)\n");
                    int disc = scan.nextInt();

                    while(true){
                        if(disc == 1){
                            scan.nextLine();
                            System.out.println("selcet first palce\n");
                            district[0] = scan.nextLine();
                            break;
                        }
                        if(disc == 2){
                            scan.nextLine();
                            System.out.println("selcet first palce\n");
                            district[0] = scan.nextLine();
                            System.out.println("selcet second palce\n");
                            district[1] = scan.nextLine();
                            break;
                        }
                        if(disc == 3){
                            scan.nextLine();
                            System.out.println("selcet first palce\n");
                            district[0] = scan.nextLine();
                            System.out.println("selcet second palce\n");
                            district[1] = scan.nextLine();
                            System.out.println("selcet third palce\n");
                            district[2] = scan.nextLine();
                            break;
                        }
                        else{
                            System.out.println("worng number \n" +
                                    "selcet count of district (1 ~ 3)\n");
                            disc = scan.nextInt();
                        }
                    }
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Answer the following questions. Matching may not be possible if you answer YES to all questions and narrow regional selection. \n");

                    System.out.println("Would you like to have an English-speaking pharmacy nearby?");
                    chk[idx] = sc.nextLine();
                    idx++;
                    System.out.println("Do you want a licensed tourist medical hospital nearby?");
                    chk[idx] = sc.nextLine();
                    idx++;
                    System.out.println("Would you like to have a walking course nearby??");
                    chk[idx] = sc.nextLine();
                    idx++;
                    System.out.println("Do you want a shopping center nearby?");
                    chk[idx] = sc.nextLine();
                    idx++;
                    System.out.println("Do you want a Community Service Center nearby?");
                    chk[idx] = sc.nextLine();
                    idx++;
                    System.out.println("Do you want a Cultural Facility nearby?");
                    chk[idx] = sc.nextLine();
                    idx++;


                    for(int i = 0; i < chk.length;i++) {
                        if(chk[i].equals("Y") || chk[i].equals("y")) {
                            cnt++;
                            items.add(i);
                        }
                    }


                    switch(cnt) {
                        case 1:
                            query =
                                    "insert into myplace Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?;\r\n";
                            //Select * from realestate where AdministrativeBuilding in ()
                            pstmt = conn.prepareStatement(query);

                            if(disc == 1){
                                pstmt.setString(1,district[0]);
                                pstmt.execute();
                                SQLWarning sqlWarning = pstmt.getWarnings();

                                while (sqlWarning != null) {
                                    String message = sqlWarning.getMessage();
                                    System.out.println(message);
                                    sqlWarning = sqlWarning.getNextWarning();
                                }
                            }

                            if(disc == 2 ){
                                for(int i = 0; i < 2; i++) {
                                    pstmt.setString(1,district[i]);
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }

                            if(disc == 3 ){
                                for(int i = 0; i < 3; i++) {
                                    pstmt.setString(1,district[i]);
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }
                            for(int k = 0 ; k < 6 ; k++){
                                System.out.print("this is "+table[k]+" = ");
                                query =
                                        "select distinct *  from "+table[k] +" where "+table[k]+".AdministrativeBuilding in (Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?) limit 1\r\n";

                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 1; j++) {
                                        pstmt.setString(j+1,district[i]);
                                        //    pstmt2.setString(j+1,district[i]);
                                    }

                                    pstmt.execute();
                                    r = pstmt.executeQuery();
                                    while (r.next()) {
                                        System.out.println( r.getString(1) + "" + r.getString(2) + "" + r.getString(3));

                                    }
                                }
                                System.out.println(" ");
                            }

                            break;
                        case 2:
                            query = "insert into myplace (AdministrativeBuilding) Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                    + "intersect\r\n" +
                                    "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =? \n";

                            pstmt = conn.prepareStatement(query);
                            if(disc == 1 ){
                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 2; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }

                            if(disc == 2 ){
                                for(int i=0; i< 2; i++) {
                                    for (int j=0; j < 2; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }

                            if(disc == 3 ){
                                for(int i=0; i< 3; i++) {
                                    for (int j=0; j < 2; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }
                            for(int k = 0 ; k < 6 ; k++){
                                System.out.print("this is "+table[k]+" = ");
                                query =
                                        "select distinct *  from "+table[k] +" where "+table[k]+".AdministrativeBuilding in (Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?) limit 1" ;
                                pstmt = conn.prepareStatement(query);

                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 2; j++) {
                                        pstmt.setString(j+1,district[i]);
                                        //    pstmt2.setString(j+1,district[i]);
                                    }

                                    pstmt.execute();
                                    r = pstmt.executeQuery();
                                    while (r.next()) {
                                        System.out.println( r.getString(1) + "" + r.getString(2) + "" + r.getString(3));

                                    }
                                }
                                System.out.println(" ");
                            }
                            break;
                        case 3:
                            query = "insert into myplace (AdministrativeBuilding) Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                    + "intersect\r\n" +
                                    "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                    + "intersect\r\n"
                                    + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?\r\n";

                            pstmt = conn.prepareStatement(query);
                            if(disc == 1 ){
                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 3; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }

                            if(disc == 2 ){
                                for(int i=0; i< 2; i++) {
                                    for (int j=0; j < 3; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }

                            if(disc == 3 ){
                                for(int i=0; i< 3; i++) {
                                    for (int j=0; j < 3; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }
                            for(int k = 0 ; k < 6 ; k++){
                                System.out.print("this is "+table[k]+" = ");
                                query =
                                        "select distinct *  from "+table[k] +" where "+table[k]+".AdministrativeBuilding in (Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?) limit 1" ;
                                pstmt = conn.prepareStatement(query);

                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 3; j++) {
                                        pstmt.setString(j+1,district[i]);

                                    }

                                    pstmt.execute();
                                    r = pstmt.executeQuery();
                                    while (r.next()) {
                                        System.out.println( r.getString(1) + "" + r.getString(2) + "" + r.getString(3));

                                    }
                                }
                                System.out.println(" ");
                            }
                            break;
                        case 4:
                            query = "insert into myplace (AdministrativeBuilding) Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                    + "intersect\r\n" +
                                    "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                    + "intersect\r\n"
                                    + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?\r\n"
                                    + "intersect\r\n"
                                    + "Select AdministrativeBuilding From "+table[items.get(3)]+" Where AdministrativeDistrict =?\r\n";
                            pstmt = conn.prepareStatement(query);
                            if(disc == 1 ){
                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 4; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }

                            if(disc == 2 ){
                                for(int i=0; i< 2; i++) {
                                    for (int j=0; j < 4; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }

                            if(disc == 3 ){
                                for(int i=0; i< 3; i++) {
                                    for (int j=0; j < 4; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }
                            for(int k = 0 ; k < 6 ; k++){
                                System.out.print("this is "+table[k]+" = ");
                                query =
                                        "select distinct *  from "+table[k] +" where "+table[k]+".AdministrativeBuilding in (Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(3)]+" Where AdministrativeDistrict =?) limit 1" ;
                                pstmt = conn.prepareStatement(query);

                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 4; j++) {
                                        pstmt.setString(j+1,district[i]);

                                    }

                                    pstmt.execute();
                                    r = pstmt.executeQuery();
                                    while (r.next()) {
                                        System.out.println( r.getString(1) + "" + r.getString(2) + "" + r.getString(3));

                                    }
                                }
                                System.out.println(" ");
                            }
                            break;
                        case 5:
                            query =
                                    "insert into myplace (AdministrativeBuilding) Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[items.get(3)]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[items.get(4)]+" Where AdministrativeDistrict =?;";


                            pstmt = conn.prepareStatement(query);
                            if(disc == 1 ){
                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 5; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }

                            }

                            if(disc == 2 ){
                                for(int i=0; i< 2; i++) {
                                    for (int j=0; j < 5; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }

                            if(disc == 3 ){
                                for(int i=0; i< 3; i++) {
                                    for (int j=0; j < 5; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }
                            for(int k = 0 ; k < 6 ; k++){
                                System.out.print("this is "+table[k]+" = ");
                                query =
                                        "select distinct *  from "+table[k] +" where "+table[k]+".AdministrativeBuilding in " +
                                                "(Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(3)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(4)]+" Where AdministrativeDistrict =?)" ;
                                pstmt = conn.prepareStatement(query);

                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 5; j++) {
                                        pstmt.setString(j+1,district[i]);
                                        //    pstmt2.setString(j+1,district[i]);
                                    }

                                    pstmt.execute();
                                    r = pstmt.executeQuery();
                                    while (r.next()) {
                                        System.out.println( r.getString(1) + "" + r.getString(2) + "" + r.getString(3));
                                        //System.out.printf("%s\n", ad);
                                    }
                                }
                                System.out.println(" ");
                            }
                            break;
                        case 6:
                            query =//"insert into myplace Select AdministrativeBuilding From "+table[0]+" Where AdministrativeDistrict =?\r\n"+
                                    "insert into myplace (AdministrativeBuilding) Select AdministrativeBuilding From "+table[items.get(0)]+"" +
                                            " Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[1]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[2]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[3]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[4]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[5]+" Where AdministrativeDistrict =?\r\n;";                       ;
                            pstmt = conn.prepareStatement(query);
                            if(disc == 1 ){
                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 6; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }

                            if(disc == 2 ){
                                for(int i=0; i< 2; i++) {
                                    for (int j=0; j < 6; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }

                            if(disc == 3 ){
                                for(int i=0; i< 3; i++) {
                                    for (int j=0; j < 6; j++) {
                                        pstmt.setString(j+1,district[i]);
                                    }
                                    pstmt.execute();
                                    SQLWarning sqlWarning = pstmt.getWarnings();

                                    while (sqlWarning != null) {
                                        String message = sqlWarning.getMessage();
                                        System.out.println(message);
                                        sqlWarning = sqlWarning.getNextWarning();
                                    }
                                }
                            }
                            for(int k = 0 ; k < 6 ; k++){
                                System.out.print("this is "+table[k]+" = ");
                                query =
                                        "select distinct *  from "+table[k] +" where "+table[k]+".AdministrativeBuilding in " +
                                                "(Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(3)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(4)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(5)]+" Where AdministrativeDistrict =?) limit 1" ;
                                pstmt = conn.prepareStatement(query);

                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 6; j++) {
                                        pstmt.setString(j+1,district[i]);
                                        //    pstmt2.setString(j+1,district[i]);
                                    }

                                    pstmt.execute();
                                    r = pstmt.executeQuery();
                                    while (r.next()) {
                                        System.out.println( r.getString(1) + "" + r.getString(2) + "" + r.getString(3));
                                    }
                                }
                                System.out.println(" ");
                            }
                            break;
                        default:
                            System.out.println("올바르지 않은 입력입니다.");
                            break;
                    }
                    r=stmt.executeQuery("select distinct * from realestate, myplace where realestate.AdministrativeBuilding = myplace.AdministrativeBuilding");
                    int tuplenum = 1;
                    while(r.next()) {
                        System.out.println(tuplenum + " " + r.getString(5) + "" + r.getString(3) + "" + r.getString(2) + "" + r.getString(4)+ "" + r.getString(1));
                        tuplenum++;
                    }
                }else if(mode == 2) {
                    idx=0;
                    System.out.println("This is an emergency pharmacy locator service. Answer the following questions.");
                    System.out.println("Please select a area.");
                    String pharmacyQuery="";
                    scan.nextLine();
                    String place="";
                    place = scan.nextLine();
                    System.out.println("Are you looking for an English-speaking pharmacy?");
                    scan.nextLine();
                    String english="";
                    english = scan.nextLine();

                    pharmacyQuery = "select * From pharmacy Where Administrativebuilding = ? ;";
                    pstmt = conn.prepareStatement(pharmacyQuery);
                    pstmt.setString(1,place);
                    r = pstmt.executeQuery();
                    while (r.next()) {
                        String ad = r.getString(1)+"\t"+r.getString(2)+"\t"+r.getString(3)+"\t"+r.getString(4);
                        System.out.printf("%s\n", ad);
                    }


                } else if(mode == 3) {
                    idx=0;
                    Scanner sc = new Scanner(System.in);
                    System.out.println("service3");
                    scan.nextLine();
                    System.out.println("" +

                            "                                                        Gangbuk-gu   Dobong-gu    Nowon-gu\n\n" +
                            "       Eunpyeong-gu    Seodaemun-gu    Jongno-gu      Seongbuk-gu      Dongdaemun-gu    Jungnang-gu\n\n"+
                            "Gangseo-gu   Mapo-gu    Yongsan-gu    Jung-gu     Seongdong-g    Gwangjin-gu   Gangdong-gu\n\n" +
                            "     Yangcheon-gu    Yeongdeungpo-gu    Dongjak-gu    Seocho-gu   Gangnam-gu   Songpa-gu\n\n" +
                            "       Guro-gu   Geumcheon-gu    Gwanak-gu\n\n" +


                            "");
                    System.out.println("selcet place\n");
                    district[0] = scan.nextLine();
                    System.out.println("Answer the following questions. Matching may not be possible if you answer YES to all questions and narrow regional selection. \\n\\n\" +\n");

                    System.out.println("Would you like to have an English-speaking pharmacy nearby?");
                    chk[idx] = sc.nextLine();
                    idx++;
                    System.out.println("Do you want a licensed tourist medical hospital nearby?");
                    chk[idx] = sc.nextLine();
                    idx++;
                    System.out.println("Would you like to have a walking course nearby??");
                    chk[idx] = sc.nextLine();
                    idx++;
                    System.out.println("Do you want a shopping center nearby?");
                    chk[idx] = sc.nextLine();
                    idx++;
                    System.out.println("Do you want a Community Service Center nearby?");
                    chk[idx] = sc.nextLine();
                    idx++;
                    System.out.println("Do you want a Cultural Facility nearby?");
                    chk[idx] = sc.nextLine();
                    idx++;
                    cnt=0;
                    for(int i = 0; i < chk.length;i++) {
                        if(chk[i].equals("Y") || chk[i].equals("y")) {
                            cnt++;
                            items.add(i);
                        }
                    }

                    switch(cnt) {
                        case 1:
                            System.out.print("AdministrativeBuilding is ");
                            query =
                                    "Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?  limit 1;\r\n";
                            pstmt = conn.prepareStatement(query);
                            System.out.println(" ");
                            for(int i=0; i< 1; i++) {
                                for (int j=0; j < 1; j++) {
                                    pstmt.setString(j+1,district[i]);
                                    //    pstmt2.setString(j+1,district[i]);
                                }

                                pstmt.execute();
                                r = pstmt.executeQuery();
                                while (r.next()) {
                                    String ad = r.getString(1);
                                    System.out.printf("%s\n", ad);
                                }
                            }
                            for(int k = 0 ; k < 6 ; k++){
                                System.out.print("this is "+table[k]+" = ");
                                query =
                                        "select distinct *  from "+table[k] +" where "+table[k]+".AdministrativeBuilding in (Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?) limit 1\r\n";

                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 1; j++) {
                                        pstmt.setString(j+1,district[i]);
                                        //    pstmt2.setString(j+1,district[i]);
                                    }

                                    pstmt.execute();
                                    r = pstmt.executeQuery();
                                    while (r.next()) {
                                        String ad = r.getString(1);
                                        System.out.printf("%s\n", ad);
                                    }
                                }
                                System.out.println(" ");
                            }
                            break;
                        case 2:
                            System.out.print("AdministrativeBuilding is ");
                            query =
                                    "Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =? \n"
                                            + "intersect\n"
                                            + "Select AdministrativeBuilding From "+table[items.get(1)] +" Where AdministrativeDistrict =? limit 1\n;"
                            ;
                            pstmt = conn.prepareStatement(query);
                            System.out.println(" ");
                            for(int i=0; i< 1; i++) {
                                for (int j=0; j < 2; j++) {
                                    pstmt.setString(j+1,district[i]);
                                    //    pstmt2.setString(j+1,district[i]);
                                }

                                pstmt.execute();
                                r = pstmt.executeQuery();
                                while (r.next()) {
                                    String ad = r.getString(1);
                                    System.out.printf("%s\n", ad);
                                }
                            }
                            for(int k = 0 ; k < 6 ; k++){
                                System.out.print("this is "+table[k]+" = ");
                                query =
                                        "select distinct *  from "+table[k] +" where "+table[k]+".AdministrativeBuilding in (Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?) limit 1" ;
                                pstmt = conn.prepareStatement(query);

                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 2; j++) {
                                        pstmt.setString(j+1,district[i]);
                                        //    pstmt2.setString(j+1,district[i]);
                                    }

                                    pstmt.execute();
                                    r = pstmt.executeQuery();
                                    while (r.next()) {
                                        String ad = r.getString(1);
                                        System.out.printf("%s\n", ad);
                                    }
                                }
                                System.out.println(" ");
                            }
                            break;
                        case 3:
                            System.out.print("AdministrativeBuilding is ");
                            query =  "Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                    + "intersect\r\n"
                                    + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                    + "intersect\r\n"
                                    + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =? limit 1;\r\n";
                            pstmt = conn.prepareStatement(query);
                            System.out.println(" ");
                            for(int i=0; i< 1; i++) {
                                for (int j=0; j < 3; j++) {
                                    pstmt.setString(j+1,district[i]);
                                    //    pstmt2.setString(j+1,district[i]);
                                }

                                pstmt.execute();
                                r = pstmt.executeQuery();
                                while (r.next()) {
                                    String ad = r.getString(1);
                                    System.out.printf("%s\n", ad);
                                }
                            }
                            for(int k = 0 ; k < 6 ; k++){
                                System.out.print("this is "+table[k]+" = ");
                                query =
                                        "select distinct *  from "+table[k] +" where "+table[k]+".AdministrativeBuilding in (Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?) limit 1" ;
                                pstmt = conn.prepareStatement(query);

                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 3; j++) {
                                        pstmt.setString(j+1,district[i]);

                                    }

                                    pstmt.execute();
                                    r = pstmt.executeQuery();
                                    while (r.next()) {
                                        String ad = r.getString(1);
                                        System.out.printf("%s\n", ad);
                                    }
                                }
                                System.out.println(" ");
                            }
                            break;
                        case 4:
                            System.out.print("AdministrativeBuilding is ");
                            query = "Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                    + "intersect\r\n"
                                    + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                    + "intersect\r\n"
                                    + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?\r\n"
                                    + "intersect\r\n"
                                    + "Select AdministrativeBuilding From "+table[items.get(3)]+" Where AdministrativeDistrict =?\r\n limit 1;";
                            pstmt = conn.prepareStatement(query);
                            System.out.println(" ");
                            for(int i=0; i< 1; i++) {
                                for (int j=0; j < 4; j++) {
                                    pstmt.setString(j+1,district[i]);
                                    //    pstmt2.setString(j+1,district[i]);
                                }

                                pstmt.execute();
                                r = pstmt.executeQuery();
                                while (r.next()) {
                                    String ad = r.getString(1);
                                    System.out.printf("%s\n", ad);
                                }
                            }
                            for(int k = 0 ; k < 6 ; k++){
                                System.out.print("this is "+table[k]+" = ");
                                query =
                                        "select distinct *  from "+table[k] +" where "+table[k]+".AdministrativeBuilding in (Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(3)]+" Where AdministrativeDistrict =?) limit 1" ;
                                pstmt = conn.prepareStatement(query);

                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 4; j++) {
                                        pstmt.setString(j+1,district[i]);
                                        //    pstmt2.setString(j+1,district[i]);
                                    }

                                    pstmt.execute();
                                    r = pstmt.executeQuery();
                                    while (r.next()) {
                                        String ad = r.getString(1);
                                        System.out.printf("%s\n", ad);
                                    }
                                }
                                System.out.println(" ");
                            }
                            break;
                        case 5:
                            System.out.print("AdministrativeBuilding is ");
                            query =
                                    "Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[items.get(3)]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[items.get(4)]+" Where AdministrativeDistrict =? limit 1";
                            pstmt = conn.prepareStatement(query);
                            System.out.println(" ");
                            for(int i=0; i< 1; i++) {
                                for (int j=0; j < 5; j++) {
                                    pstmt.setString(j+1,district[i]);
                                    //    pstmt2.setString(j+1,district[i]);
                                }

                                pstmt.execute();
                                r = pstmt.executeQuery();
                                while (r.next()) {
                                    String ad = r.getString(1);
                                    System.out.printf("%s\n", ad);
                                }
                            }
                            for(int k = 0 ; k < 6 ; k++){
                                System.out.print("this is "+table[k]+" = ");
                                query =
                                        "select distinct *  from "+table[k] +" where "+table[k]+".AdministrativeBuilding in (Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(3)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(4)]+" Where AdministrativeDistrict =?) limit 1" ;
                                pstmt = conn.prepareStatement(query);

                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 5; j++) {
                                        pstmt.setString(j+1,district[i]);
                                        //    pstmt2.setString(j+1,district[i]);
                                    }

                                    pstmt.execute();
                                    r = pstmt.executeQuery();
                                    while (r.next()) {
                                        String ad = r.getString(1);
                                        System.out.printf("%s\n", ad);
                                    }
                                }
                                System.out.println(" ");
                            }
                            break;
                        case 6:
                            System.out.print("AdministrativeBuilding is ");
                            query =//"insert into myplace Select AdministrativeBuilding From "+table[0]+" Where AdministrativeDistrict =?\r\n"+
                                    "insert into myplace Select administrativebuilding From "+table[0]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[1]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[2]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[3]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[4]+" Where AdministrativeDistrict =?\r\n"
                                            + "intersect\r\n"
                                            + "Select AdministrativeBuilding From "+table[5]+" Where AdministrativeDistrict =?\r\n limit 1;";                       ;
                            pstmt = conn.prepareStatement(query);
                            System.out.println(" ");
                            for(int i=0; i< 1; i++) {
                                for (int j=0; j < 6; j++) {
                                    pstmt.setString(j+1,district[i]);
                                    //    pstmt2.setString(j+1,district[i]);
                                }

                                pstmt.execute();
                                r = pstmt.executeQuery();
                                while (r.next()) {
                                    String ad = r.getString(1);
                                    System.out.printf("%s\n", ad);
                                }
                            }
                            for(int k = 0 ; k < 6 ; k++){
                                System.out.print("this is "+table[k]+" = ");
                                query =
                                        "select distinct *  from "+table[k] +" where "+table[k]+".AdministrativeBuilding in (Select AdministrativeBuilding From "+table[items.get(0)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(1)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(2)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(3)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(4)]+" Where AdministrativeDistrict =?\r\n"
                                                + "intersect\r\n"
                                                + "Select AdministrativeBuilding From "+table[items.get(5)]+" Where AdministrativeDistrict =?) limit 1" ;
                                pstmt = conn.prepareStatement(query);

                                for(int i=0; i< 1; i++) {
                                    for (int j=0; j < 6; j++) {
                                        pstmt.setString(j+1,district[i]);
                                        //    pstmt2.setString(j+1,district[i]);
                                    }

                                    pstmt.execute();
                                    r = pstmt.executeQuery();
                                    while (r.next()) {
                                        String ad = r.getString(1);
                                        System.out.printf("%s\n", ad);
                                    }
                                }
                                System.out.println(" ");
                            }

                            break;

                        default:
                            System.out.println("올바르지 않은 입력입니다.");
                            break;
                    }


                }
            else{
                return;
                }
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

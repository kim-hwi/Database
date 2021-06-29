import java.sql.*;
import java.util.Scanner;


public class SqlTest3
{
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "kimhwi";
        String password = "9509";
        Connection connect;
        connect = DriverManager.getConnection(url, user, password);
        ResultSet rs;
        Statement stmt = connect.createStatement();
        String sql;
        char input;
        String res;
        try {
            Scanner scan = new Scanner(System.in); System.out.println("SQL Programming Test");
            System.out.println("Trigger test 1"); // Trigger R2 생성
           sql = "create or replace function trig1() returns trigger as $R2$\n" +
                    "begin\n" +
                    "delete from Apply where sID = Old.sID;\n" +
                    "return New;\n" +
                    "end;\n" +
                    "$R2$ language 'plpgsql';\n" +
                    "\n" +
                    "create trigger R2\n" +
                    "after delete on Student\n " +
                    "for each row " +
                    "execute procedure trig1();";
            stmt.execute(sql);
            sql = "delete from Student where GPA < 3.5";
            stmt.execute(sql);

            rs = stmt.executeQuery("select * from Student order by sID");
            int tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" + rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getFloat(3) + "\t" + rs.getInt(4)); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
                tuplenum++;
            }
// Delete문 실행

            System.out.println("Continue? (Enter 1 for continue)");
            res = scan.nextLine();
            input = res.charAt(0);
            if(input != '1' ) {return ;}
            tuplenum = 1;
            rs = stmt.executeQuery("select * from Apply order by sID, cName, major");
            while(rs.next()) {
                System.out.println(tuplenum + "\t" + rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" +  "\t" + rs.getString(4)); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
                tuplenum++;
            }
// Query 2 실행하고 결과는 적절한 Print문을 이용해 Display
// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)
            System.out.println("Continue? (Enter 1 for continue)");
            res = scan.nextLine();
            input = res.charAt(0);
            if(input != '1' ) {return ;}

            System.out.println("Trigger test 2");

            sql = "create or replace function trig2() returns trigger as $R4$\n" +

                    "begin \n" +
                    "IF  exists(select * from College where cName = New.cName) THEN\n" +
                    "return null; ELSE\n" +
                    "return New; END IF;" +
                    "return new; \n" +
                    "end\n" +
                    "$R4$ language 'plpgsql';\n" +
                    "\n" +

                    "create trigger R4\n" +
                    "before insert on college \n for each row \n" +
                    "execute procedure trig2();";
            stmt.execute(sql);


            sql = "insert into college (cname,state,enrollment) values ('UCLA','CA',20000)";
            stmt.execute(sql);

            sql = "insert into college values ('MIT','hello',10000)";
            stmt.execute(sql);
            tuplenum = 1;
            rs = stmt.executeQuery("select * from College order by cName");
            while(rs.next()) {
                System.out.println(tuplenum + "\t" + rs.getString(1) + "\t" + rs.getString(2) + "\t" +  "\t" + rs.getInt(3)); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
                tuplenum++;
            }
// Trigger R4 생성
// Insert문 실행
// Query 3 실행하고 결과는 적절한 Print문을 이용해 Display
// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)
            System.out.println("Continue? (Enter 1 for continue)");
            res = scan.nextLine();
            input = res.charAt(0);
            if(input != '1' ) {return ;}
            System.out.println("View test 1");

            sql = "create view CSEE as select sID,cName,major from Apply where major = 'CS' or major = 'EE'";
            stmt.execute(sql);
            rs = stmt.executeQuery("select * from CSEE order by sID, cName, major;\n");
            tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" + tuplenum + "\t" + rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3)); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
                tuplenum++;
            }
            System.out.println("\n");
// View CSEE 생성
// Query 4 실행하고 결과는 적절한 Print문을 이용해 Display
// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)
            System.out.println("Continue? (Enter 1 for continue)");
            res = scan.nextLine();
            input = res.charAt(0);
            if(input != '1' ) {return ;}
            System.out.println("View test 2");
            sql = "create or replace function trig3() returns trigger as $CSEEinsert$\n" +
                    "begin\n" +
                    "\tinsert into Apply (sID,cName,major,decision) values(new.sID , new.cName , new.major,null); return null;\n" +
                    "end;\n" +
                    "$CSEEinsert$ language 'plpgsql';\n" +
                    "\n" +
                    "create trigger CSEEinsert\n" +
                    "instead of insert on CSEE for each row " +
                    "execute procedure trig3();";
            stmt.execute(sql);
            sql = "insert into CSEE values (333, 'UCLA', 'biology');";
            stmt.execute(sql);
            tuplenum = 1;
            rs = stmt.executeQuery("select * from CSEE order by sID, cName, major;");
            while(rs.next()) {
                System.out.println(tuplenum + "\t" + rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" ); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
                tuplenum++;
            }

// Trigger CSEEinsert 생성

// Insert문 실행
// Query 5 실행하고 결과는 적절한 Print문을 이용해 Display
// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)
            System.out.println("Continue? (Enter 1 for continue)");
            res = scan.nextLine();
            input = res.charAt(0);
            if(input != '1' ) {return ;}
            rs = stmt.executeQuery("select * from Apply order by sID, cName, major;");
            tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" + rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
                tuplenum++;
            }
// Query 6 실행하고 결과는 적절한 Print문을 이용해 Display

// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)
            System.out.println("Continue? (Enter 1 for continue)");
            res = scan.nextLine();
            input = res.charAt(0);
            if(input != '1' ) {return ;}

            System.out.println("View test 3");

            sql = "insert into CSEE values (333, 'UCLA', 'CS');";
            stmt.execute(sql);
// Insert문 실행
            rs = stmt.executeQuery("select * from CSEE order by sID, cName, major;");
            tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" + rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" ); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
                tuplenum++;
            }
// Query 7 실행하고 결과는 적절한 Print문을 이용해 Display
// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)
            System.out.println("Continue? (Enter 1 for continue)");
            res = scan.nextLine();
            input = res.charAt(0);
            if(input != '1' ) {return ;}
            rs = stmt.executeQuery("select * from Apply order by sID, cName, major;");
            tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" + rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
                tuplenum++;
            }
// Query 8 실행하고 결과는 적절한 Print문을 이용해 Display
// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)
        } catch(SQLException ex) {
            throw ex; }
    } }
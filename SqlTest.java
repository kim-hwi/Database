import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class SqlTest
{
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "kimhwi";
        String password = "9509";
        Connection connect;
        connect = DriverManager.getConnection(url, user, password);
        try {
            Scanner scan = new Scanner(System.in); System.out.println("SQL Programming Test");
            System.out.println("Connecting PostgreSQL database");
            try{
                Class.forName("org.postgresql.Driver");

                try
                {
                    if(connect != null) {
                        System.out.println("Connection successful!!");
                    }

                    else {
                        throw new SQLException("no connection...");
                    }
                } catch (SQLException ex) {
                    throw ex;
                }
            }
            catch(ClassNotFoundException x){
                System.out.println("Class not Found!");
            }
                        String sql = "CREATE TABLE apply(";
                        sql+="sID int,";
                        sql+="cName varchar(20),";
                        sql+="major varchar(20),";
                        sql+="decision char)";

                        Statement stmt = connect.createStatement();
                        stmt.execute(sql);

                        sql = "CREATE TABLE college(";
                        sql+="cName varchar(20),";
                        sql+="state char(2),";
                        sql+="enrollment int)";

                        stmt = connect.createStatement();
                        stmt.execute(sql);

                        sql = "CREATE TABLE student(";
                        sql+="sID int,";
                        sql+="sName varchar(20),";
                        sql+="GPA numeric(2,1),";
                        sql+="sizeHS int)";

                        stmt = connect.createStatement();
                        stmt.execute(sql);
//
            System.out.println("Inserting tuples to College, Student, Apply relations"); // 3개 테이블에 튜플 생성: Insert문 이용
                        sql =
                                "insert into College values ('Stanford', 'CA', 15000); " +
                                "insert into College values ('Berkeley', 'CA', 36000); " +
                                "insert into College values ('MIT', 'MA', 10000); " +
                                "insert into College values ('Cornell', 'NY', 21000);" +
                                "insert into Student values (123, 'Amy', 3.9, 1000); " +
                                "insert into Student values (234, 'Bob', 3.6, 1500); " +
                                "insert into Student values (345, 'Craig', 3.5, 500); " +
                                "insert into Student values (456, 'Doris', 3.9, 1000); " +
                                "insert into Student values (567, 'Edward', 2.9, 2000); " +
                                "insert into Student values (678, 'Fay', 3.8, 200); " +
                                "insert into Student values (789, 'Gary', 3.4, 800); " +
                                "insert into Student values (987, 'Helen', 3.7, 800); " +
                                "insert into Student values (876, 'Irene', 3.9, 400); " +
                                "insert into Student values (765, 'Jay', 2.9, 1500); " +
                                "insert into Student values (654, 'Amy', 3.9, 1000); " +
                                "insert into Student values (543, 'Craig', 3.4, 2000);" +
                                "insert into Apply values (123, 'Stanford', 'CS', 'Y');" +
                                "insert into Apply values (123, 'Stanford', 'EE', 'N');" +
                                "insert into Apply values (123, 'Berkeley', 'CS', 'Y');" +
                                "insert into Apply values (123, 'Cornell', 'EE', 'Y');" +
                                "insert into Apply values (234, 'Berkeley', 'biology', 'N'); " +
                                "insert into Apply values (345, 'MIT', 'bioengineering', 'Y'); " +
                                "insert into Apply values (345, 'Cornell', 'bioengineering', 'N'); " +
                                "insert into Apply values (345, 'Cornell', 'CS', 'Y');" +
                                "insert into Apply values (345, 'Cornell', 'EE', 'N');" +
                                "insert into Apply values (678, 'Stanford', 'history', 'Y'); " +
                                "insert into Apply values (987, 'Stanford', 'CS', 'Y');" +
                                "insert into Apply values (987, 'Berkeley', 'CS', 'Y');" +
                                "insert into Apply values (876, 'Stanford', 'CS', 'N');" +
                                "insert into Apply values (876, 'MIT', 'biology', 'Y');" +
                                "insert into Apply values (876, 'MIT', 'marine biology', 'N'); " +
                                "insert into Apply values (765, 'Stanford', 'history', 'Y'); " +
                                "insert into Apply values (765, 'Cornell', 'history', 'N'); " +
                                "insert into Apply values (765, 'Cornell', 'psychology', 'Y'); " +
                                "insert into Apply values (543, 'MIT', 'CS', 'N');";

                        stmt = connect.createStatement();
                        stmt.execute(sql);


// JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결


            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();


            System.out.println("Query 1");
            ResultSet rs;
            stmt = connect.createStatement();

            rs = stmt.executeQuery("select * from College");

            while(rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3));
            }


// Query 1을 실행: Select문 이용
// Query 처리 결과는 적절한 Print문을 이용해 Display ...

            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("Query 2");
            rs = stmt.executeQuery("select * from student");
            while(rs.next()) {
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4));
            }

            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("Query 3");
            rs = stmt.executeQuery("select * from Apply;");
            while(rs.next()) {
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
            }

            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("Query 4");
            rs = stmt.executeQuery("select cName, major, min(GPA), max(GPA) from Student, Apply\n" +
                    "where Student.sID = Apply.sID\n" +
                    "group by cName, major\n" +
                    "having min(GPA) > 3.0\n" +
                    "order by cName, major;");
            while(rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
            }
            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("Query 5");
            rs = stmt.executeQuery("select distinct cName\n" +
                    "from Apply A1\n" +
                    "where 6 > (select count(*) from Apply A2 where A2.cName = A1.cName);");
            while(rs.next()) {
                System.out.println(rs.getString(1) );
            }

// Query 2 ~ Query 5에 대해 Query 1과 같은 방식으로 실행: Select문 이용
// Query 처리 결과는 적절한 Print문을 이용해 Display
//...

// Query 6을 실행: Select문 이용
// 사용자에게 major1, major2 값으로 CS, EE 입력 받음 // 입력 받은 값을 넣어 Query를 처리하고
// 결과는 적절한 Print문을 이용해 Display

            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("Query 6");
            sql ="select sID, sName\n" +
                    "from Student\n" +
                    "where sID = any (select sID from Apply where major =?)\n" +
                    "and not sID = any (select sID from Apply where major =?);";

            System.out.println("CS?");
            String cs = scan.nextLine();
            System.out.println("EE?");
            String ee = scan.nextLine();

            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1,cs);
            pstmt.setString(2,ee);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                String sn = rs.getString(2);

                System.out.printf("%s %s\n", id, sn);
            }



// Query 7을 실행: Select문 이용
// 사용자에게 major, cName 값으로 CS, Stanford 입력 받음 // 입력 받은 값을 넣어 Query를 처리하고
// 결과는 적절한 Print문을 이용해 Display


            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("Query 7");
            sql ="select sName, GPA\n" +
                    "from Student natural join Apply\n" +
                    "where major = ? and cName = ?;";

            System.out.println("CS?");
            cs = scan.nextLine(); // goodsName에 담는다.
            System.out.println("Stanford?");
            String Stanford = scan.nextLine(); // goodsName에 담는다.

            pstmt = connect.prepareStatement(sql);
            pstmt.setString(1,cs);
            pstmt.setString(2,Stanford);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString(1);
                String sn = rs.getString(2);
                System.out.printf("%s %s\n", id, sn);
            }

            try {

                if(rs != null) rs.close();

                if(stmt != null) stmt.close();

                if(connect != null) connect.close();

            } catch (Exception e2) {

                // TODO: handle exception

            }


        } catch(SQLException ex) {
            throw ex; }


    }
}

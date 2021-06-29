import java.sql.*;
import java.util.Scanner;

public class SqlTest4
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
            System.out.println("Recursive test 1");
            rs = stmt.executeQuery("with recursive " +
                    " Ancestor(a,d) as " +
                    " (select parent as a, child as d from ParentOf" +
                    " union " +
                    "select Ancestor.a, ParentOf.child as d " +
                    "from Ancestor, ParentOf " +
                    "where Ancestor.d = ParentOf.parent)" +
                    "select a from Ancestor where d = 'Frank' order by a ");
            int tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" +rs.getString(1)); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
                tuplenum++;
            }

//
//
//
            //stmt.execute(sql);
//
//
//

            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("Recursive test 2");
            rs = stmt.executeQuery("with recursive " +
                    " Superior as " +
                    " (select * from Manager" +
                    " union " +
                    "select S.mID, M.eID " +
                    "from Superior S, Manager M " +
                    "where S.eID = M.mID) " +
                    "select sum(salary) " +
                    "from Employee " +
                    "where ID in " +
                    "(select mgrID from Project where name = 'X' " +
                    "union " +
                    "select eID from Project, Superior " +
                    "where Project.name = 'X' and Project.mgrID = Superior.mID)");
            tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" +rs.getString(1)); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
                tuplenum++;
            }
//// Recursive query 2 실행하고 결과는 적절한 Print문을 이용해 Display
//// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Recursive test 1” 참조)
            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("Recursive test 3");
            rs = stmt.executeQuery("with recursive " +
                    " FromA(dest,total) as " +
                    " (select dest, cost as total from  Flight where orig = 'A' " +
                    " union " +
                    "select F.dest, cost + total as total " +
                    "from FromA FA, Flight F " +
                    "where FA.dest = F.orig) " +
                    "select * from FromA");
            tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" +rs.getString(1)+ "\t" +rs.getInt(2)); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
                tuplenum++;
            }
//// Recursive query 3 실행하고 결과는 적절한 Print문을 이용해 Display
//// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Recursive test 1” 참조)
            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("Recursive test 4");
            rs = stmt.executeQuery("with recursive " +
                    " FromA(dest,total) as " +
                    " (select dest, cost as total from  Flight where orig = 'A' " +
                    " union " +
                    "select F.dest, cost + total as total " +
                    "from FromA FA, Flight F " +
                    "where FA.dest = F.orig) " +
                    "select min(total) from FromA where dest ='B'");
            tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" +rs.getInt(1)); //getInt(1)은 컬럼의 1번째 값을 Int형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
                tuplenum++;
            }
//// Recursive query 4 실행하고 결과는 적절한 Print문을 이용해 Display
//            Recursive query 1 실행하고 결과는 적절한 Print문을 이용해 Display Tuple print시 Tuple 번호도 함께 print
//            예)
//            cName state ------------------------------------
//                    1 Stanford CA 2 MIT MA
//// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Recursive test 1” 참조)
//            System.out.println(“Continue? (Enter 1 for continue)”); scan.nextLine();
//            System.out.println(“OLAP test 1”);
            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("OLAP test 1");
            rs = stmt.executeQuery("" +
                    "select storeID, itemID, custID, sum(price) " +
                    "from Sales " +
                    "group by cube(storeID, itemID, custID)" +
                    "order by storeID, itemID, custID");
            tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" +rs.getString(1)+ "\t" +rs.getString(2)+ "\t" +rs.getString(3)+ "\t" +rs.getInt(4));
                tuplenum++;
            }

//// OLAP query 1 실행하고 결과는 적절한 Print문을 이용해 Display
//// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Recursive test 1” 참조)
            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("OLAP test 2");
            rs = stmt.executeQuery("" +
                    "select storeID, itemID, custID, sum(price) " +
                    "from Sales F " +
                    "group by itemID, cube(storeID,custID)" +
                    "order by storeID, itemID, custID");
            tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" +rs.getString(1)+ "\t" +rs.getString(2)+ "\t" +rs.getString(3)+ "\t" +rs.getInt(4));
                tuplenum++;
            }
//// OLAP query 2 실행하고 결과는 적절한 Print문을 이용해 Display
//// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Recursive test 1” 참조)
            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("OLAP test 3");
            rs = stmt.executeQuery("" +
                    "select storeID, itemID, custID, sum(price) " +
                    "from Sales F " +
                    "group by  rollup(storeID,itemID,custID) " +
                    "order by storeID, itemID, custID");
            tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" +rs.getString(1)+ "\t" +rs.getString(2)+ "\t" +rs.getString(3)+ "\t" +rs.getInt(4));
                tuplenum++;
            }
//// OLAP query 3 실행하고 결과는 적절한 Print문을 이용해 Display
//// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Recursive test 1” 참조)
            System.out.println("Continue? (Enter 1 for continue)"); scan.nextLine();
            System.out.println("OLAP test 4");
            rs = stmt.executeQuery("" +
                    "select state,county,city, sum(price) " +
                    "from Sales F, Store S " +
                    "where F.storeID = S.storeID " +
                    "group by s.state,s.county,s.city ,rollup(s.storeID) " +
                    "order by state, county, city");
            tuplenum = 1;
            while(rs.next()) {
                System.out.println(tuplenum + "\t" +rs.getString(1)+ "\t" +rs.getString(2)+ "\t" +rs.getString(3)+ "\t" +rs.getInt(4));
                tuplenum++;
            }
//// OLAP query 4 실행하고 결과는 적절한 Print문을 이용해 Display
//// Tuple print시 Tuple 번호도 함께 print (예시는 위 “Recursive test 1” 참조)
        } catch(SQLException ex) {
            throw ex; }
    } }

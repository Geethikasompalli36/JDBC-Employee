import java.sql.*;
import java.util.*;

public class file2 {

    // CREATE TABLE
    public static void create(Statement st) throws Exception {

        st.executeUpdate("DROP TABLE IF EXISTS e1");

        st.executeUpdate(
                "CREATE TABLE e1(" +
                        "id INT PRIMARY KEY," +
                        "name VARCHAR(20)," +
                        "job_role VARCHAR(20)," +
                        "salary INT," +
                        "hire_date DATE)"
        );

        System.out.println("Table created successfully");
    }

    // SHOW TABLE
    public static void showTable(Statement st) throws Exception {

        ResultSet rs = st.executeQuery("SELECT * FROM e1");

        System.out.println("\n================ EMPLOYEE TABLE ================");
        System.out.printf("%-5s %-10s %-12s %-10s %-12s\n",
                "ID", "NAME", "JOB", "SALARY", "HIRE_DATE");

        System.out.println("------------------------------------------------");

        while (rs.next()) {
            System.out.printf("%-5d %-10s %-12s %-10d %-12s\n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("job_role"),
                    rs.getInt("salary"),
                    rs.getDate("hire_date"));
        }

        System.out.println("================================================\n");
    }

    // INSERT
    public static void insert(Connection con, Scanner sc, Statement st) throws Exception {

        System.out.print("Enter id: ");
        int id = sc.nextInt();

        System.out.print("Enter name: ");
        String name = sc.next();

        System.out.print("Enter job role: ");
        String job = sc.next();

        System.out.print("Enter salary: ");
        int salary = sc.nextInt();

        System.out.print("Enter hire date (YYYY-MM-DD): ");
        String date = sc.next();

        PreparedStatement ps =
                con.prepareStatement(
                        "INSERT INTO e1 (id, name, job_role, salary, hire_date) VALUES (?,?,?,?,?)"
                );

        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, job);
        ps.setInt(4, salary);
        ps.setDate(5, java.sql.Date.valueOf(date));

        ps.executeUpdate();

        System.out.println("Record inserted successfully");

        showTable(st);
    }

    // UPDATE
    public static void update(Connection con, Scanner sc, Statement st) throws Exception {

        System.out.print("Enter id: ");
        int id = sc.nextInt();

        System.out.print("Enter new salary: ");
        int salary = sc.nextInt();

        PreparedStatement ps =
                con.prepareStatement("UPDATE e1 SET salary=? WHERE id=?");

        ps.setInt(1, salary);
        ps.setInt(2, id);

        ps.executeUpdate();

        System.out.println("Updated successfully");

        showTable(st);
    }

    // DELETE
    public static void del(Connection con, Scanner sc, Statement st) throws Exception {

        System.out.print("Enter id to delete: ");
        int id = sc.nextInt();

        PreparedStatement ps =
                con.prepareStatement("DELETE FROM e1 WHERE id=?");

        ps.setInt(1, id);

        ps.executeUpdate();

        System.out.println("Deleted successfully");

        showTable(st);
    }

    // COLUMN DISPLAY
    public static void columnsDisplay(Connection con, Statement st, Scanner sc) throws Exception {

        System.out.print("Enter column name: ");
        String col = sc.next();

        ResultSet rs = st.executeQuery("SELECT " + col + " FROM e1");

        System.out.println("\n===== COLUMN: " + col.toUpperCase() + " =====");

        while (rs.next()) {
            System.out.println(rs.getString(1));
        }

        System.out.println("====================================\n");
    }

    // DISPLAY
    public static void display(Statement st) throws Exception {
        showTable(st);
    }

    public static void main(String[] args) {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employee",
                    "root",
                    "S.Teja@07"
            );

            Statement st = con.createStatement();
            Scanner sc = new Scanner(System.in);

            System.out.println("DATABASE CONNECTED SUCCESSFULLY");

            while (true) {

                System.out.println("\n1.Create 2.Insert 3.Display 4.Update 5.Delete 6.Column Display 7.Exit");
                System.out.print("Enter choice: ");
                int ch = sc.nextInt();

                switch (ch) {

                    case 1:
                        create(st);
                        break;

                    case 2:
                        insert(con, sc, st);
                        break;

                    case 3:
                        display(st);
                        break;

                    case 4:
                        update(con, sc, st);
                        break;

                    case 5:
                        del(con, sc, st);
                        break;

                    case 6:
                        columnsDisplay(con, st, sc);
                        break;

                    case 7:
                        con.close();
                        System.out.println("Exited successfully");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
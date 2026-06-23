import java.sql.*;

public class TaskManagement {

    private static final String URL = "jdbc:postgresql://localhost:5432/todo_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "S1mpL0rd";

    // ====================== THÊM CÔNG VIỆC ======================
    public void addTask(String taskName, String status) {
        String sql = "CALL add_task(?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, taskName);
            stmt.setString(2, status);
            stmt.execute();
            System.out.println("Thêm công việc thành công!");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm công việc: " + e.getMessage());
        }
    }

    // ====================== LIỆT KÊ CÔNG VIỆC ======================
    public void listTasks() {
        String sql = "SELECT * FROM list_tasks()";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== DANH SÁCH CÔNG VIỆC ===");
            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                System.out.printf("ID: %-4d | %-55s | %s%n",
                        rs.getInt("id"),
                        rs.getString("task_name"),
                        rs.getString("status"));
            }
            if (!hasData) {
                System.out.println("Chưa có công việc nào.");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách: " + e.getMessage());
        }
    }

    // ====================== CẬP NHẬT TRẠNG THÁI ======================
    public void updateTaskStatus(int id, String status) {
        String sql = "CALL update_task_status(?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, status);
            stmt.execute();
            System.out.println("Cập nhật trạng thái thành công!");

        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    // ====================== XÓA CÔNG VIỆC ======================
    public void deleteTask(int id) {
        String sql = "CALL delete_task(?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id);
            stmt.execute();
            System.out.println("Xóa công việc thành công!");

        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa: " + e.getMessage());
        }
    }

    // ====================== TÌM KIẾM THEO TÊN ======================
    public void searchTaskByName(String keyword) {
        String sql = "SELECT * FROM search_task_by_name(?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, keyword);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n=== KẾT QUẢ TÌM KIẾM ===");
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.printf("ID: %-4d | %-55s | %s%n",
                            rs.getInt("id"),
                            rs.getString("task_name"),
                            rs.getString("status"));
                }
                if (!found) {
                    System.out.println("Không tìm thấy công việc nào.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }

    // ====================== THỐNG KÊ ======================
    public void taskStatistics() {
        String sql = "SELECT * FROM task_statistics()";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                System.out.println("\n=== THỐNG KÊ CÔNG VIỆC ===");
                System.out.println("Tổng số công việc     : " + rs.getLong("total_tasks"));
                System.out.println("Đã hoàn thành         : " + rs.getLong("completed"));
                System.out.println("Chưa hoàn thành       : " + rs.getLong("pending"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi thống kê: " + e.getMessage());
        }
    }
}
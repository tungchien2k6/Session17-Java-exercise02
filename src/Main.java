import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManagement taskManager = new TaskManagement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = getValidChoice(scanner);

            try {
                switch (choice) {
                    case 1:
                        addTaskMenu(taskManager, scanner);
                        break;
                    case 2:
                        taskManager.listTasks();
                        break;
                    case 3:
                        updateTaskStatusMenu(taskManager, scanner);
                        break;
                    case 4:
                        deleteTaskMenu(taskManager, scanner);
                        break;
                    case 5:
                        searchTaskMenu(taskManager, scanner);
                        break;
                    case 6:
                        taskManager.taskStatistics();
                        break;
                    case 0:
                        System.out.println("Cảm ơn bạn đã sử dụng chương trình quản lý công việc!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
                }
            } catch (Exception e) {
                System.out.println("Lỗi nhập liệu: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("          QUẢN LÝ CÔNG VIỆC (TO-DO LIST)");
        System.out.println("=".repeat(50));
        System.out.println("1. Thêm công việc");
        System.out.println("2. Liệt kê công việc");
        System.out.println("3. Cập nhật trạng thái công việc");
        System.out.println("4. Xóa công việc");
        System.out.println("5. Tìm kiếm công việc theo tên");
        System.out.println("6. Thống kê công việc");
        System.out.println("0. Thoát");
        System.out.println("=".repeat(50));
        System.out.print("Nhập lựa chọn: ");
    }

    private static int getValidChoice(Scanner scanner) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 0 && choice <= 6) {
                    return choice;
                } else {
                    System.out.print("Vui lòng nhập số từ 0 đến 6: ");
                }
            } catch (Exception e) {
                System.out.print("Vui lòng nhập số hợp lệ: ");
            }
        }
    }

    // ====================== THÊM CÔNG VIỆC ======================
    private static void addTaskMenu(TaskManagement manager, Scanner scanner) {
        System.out.print("Nhập tên công việc: ");
        String taskName = scanner.nextLine().trim();

        if (taskName.isEmpty()) {
            System.out.println("Tên công việc không được để trống!");
            return;
        }

        System.out.print("Nhập trạng thái (chưa hoàn thành / đã hoàn thành): ");
        String status = scanner.nextLine().trim().toLowerCase();

        // Xử lý linh hoạt nhiều cách nhập
        if (status.contains("hoàn thành") || status.contains("done") || status.contains("xong")) {
            status = "đã hoàn thành";
        } else {
            status = "chưa hoàn thành";
        }

        manager.addTask(taskName, status);
    }

    // ====================== CẬP NHẬT TRẠNG THÁI ======================
    private static void updateTaskStatusMenu(TaskManagement manager, Scanner scanner) {
        System.out.print("Nhập ID công việc cần cập nhật: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Nhập trạng thái mới (chưa hoàn thành / đã hoàn thành): ");
        String status = scanner.nextLine().trim().toLowerCase();

        if (status.contains("hoàn thành") || status.contains("done") || status.contains("xong")) {
            status = "đã hoàn thành";
        } else {
            status = "chưa hoàn thành";
        }

        manager.updateTaskStatus(id, status);
    }

    // ====================== XÓA CÔNG VIỆC ======================
    private static void deleteTaskMenu(TaskManagement manager, Scanner scanner) {
        System.out.print("Nhập ID công việc cần xóa: ");
        int id = Integer.parseInt(scanner.nextLine());
        manager.deleteTask(id);
    }

    // ====================== TÌM KIẾM CÔNG VIỆC ======================
    private static void searchTaskMenu(TaskManagement manager, Scanner scanner) {
        System.out.print("Nhập từ khóa tìm kiếm: ");
        String keyword = scanner.nextLine().trim();
        if (keyword.isEmpty()) {
            System.out.println("Từ khóa không được để trống!");
            return;
        }
        manager.searchTaskByName(keyword);
    }
}
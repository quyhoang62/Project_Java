# Course Registration App (Java Swing)

Ứng dụng Đăng ký học phần mô phỏng hệ thống đăng ký học phần của trường đại học.  
Dữ liệu được lưu hoàn toàn **in-memory** (không dùng cơ sở dữ liệu). Giao diện được xây dựng bằng **Java Swing** với hai vai trò chính:

- Sinh viên (SV)  
- Phòng Đào tạo (PĐT)

---

## Tính năng chính

### 1. Dành cho Sinh viên
- Đăng nhập bằng **Mã số sinh viên (MSSV)** và mật khẩu.  
- Tạo tài khoản sinh viên mới trực tiếp trên giao diện.  
- Chọn học kỳ và đăng ký các học phần đang mở.  
- Xem danh sách học phần đã đăng ký và tổng số tín chỉ.  
- Xóa học phần trước khi gửi chính thức.  
- Gửi đăng ký chính thức cho học kỳ đã chọn.

### 2. Dành cho Phòng Đào tạo
- Đăng nhập bằng tài khoản quản trị mặc định.  
- Quản lý học phần (Thêm / Cập nhật / Xóa): mã học phần, tên, số tín chỉ.  
- Mở hoặc khóa đăng ký theo từng học kỳ.  
- Thiết lập lớp học phần theo chương trình đào tạo (CTĐT).  
- Xem số lượng sinh viên đã đăng ký theo học phần.

---

## Công nghệ sử dụng

- Java 17+  
- Swing UI  
- FlatLaf (theme khuyến nghị, hỗ trợ bo góc – hiện đại)

Ứng dụng tự động sử dụng `FlatMacLightLaf` nếu có, hoặc rơi về `System Look & Feel` nếu không.

---

## Cách chạy chương trình

### 1. Mở bằng IDE
- Mở project trong IntelliJ IDEA, Eclipse hoặc NetBeans.  
- Chạy class `CourseRegistrationApp` (có hàm `main`).

### 2. Chạy bằng dòng lệnh
```bash
javac CourseRegistrationApp.java
java CourseRegistrationApp

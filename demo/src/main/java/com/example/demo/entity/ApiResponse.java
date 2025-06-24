package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private T data; // Dữ liệu trả về từ API
    private String status; // Trạng thái của phản hồi, có thể là "success" hoặc "error"
    private String message; // Thông điệp mô tả ngắn gọn về kết quả của yêu cầu
    private String errorCode; // Mã lỗi nếu có lỗi xảy ra
    private LocalDateTime timestamp = LocalDateTime.now(); // Thời điểm phản hồi được tạo ra

    public ApiResponse(HttpStatus httpStatus, String message, T data, String errorCode) {
        // Xác định trạng thái dựa trên mã trạng thái HTTP: nếu mã là 2xx thì là "success", ngược lại là "error"
        this.status = httpStatus.is2xxSuccessful() ? "success" : "error";
        this.message = message; // Gán thông điệp cho phản hồi
        this.data = data; // Gán dữ liệu cho phản hồi
        this.errorCode = errorCode; // Gán mã lỗi cho phản hồi nếu có
        this.timestamp = LocalDateTime.now(); // Gán thời điểm hiện tại cho phản hồi
    }
}
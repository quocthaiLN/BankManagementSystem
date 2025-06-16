/*
 * Thuật toán hash sử dụng SHA-256 (họ SHA-2): từ chuỗi có độ dài bất kì --> chuỗi nhị phân 256-bit <=> chuỗi hex 64 kí tự.
 * Ngoài ra còn có MD5 và SHA-1 nhưng đã lỗi thời.
 * Hoạt động: giả sử mật khẩu "abc"
 * - Chuyển "abc" sang nhị phân: 01100001 01100010 01100011
 * - Thêm bit 1 vào cuối
 * - padding bit 0 vào cuối cho đến khi đủ 448 bit = 512 bit - 64 bit
 * - 64 bit cuối sẽ chứa độ dài "abc" sau khi được chuyển nhị phân tức 24
 * 
 * - compression function: chia 512 bit thành 16 word độ dài 32 bit
 * - sau đó mở trọng thành 64 word theo công thức ddc định nghĩa...
 * - khởi tạo 8 biến hash: h0 = a, h1 = b, h2 = c,..., h7 = h
 * - xử lí 64 vòng lặp đc định nghĩa ta được a,b,c,...,h mới
 * - sau đó lấy h0 = h0 + a, h1 = h1 + b, ...
 * 
 * - kết quả: nối h0 đến h7
 * 
 * Thêm salt vào mật khẩu trước khi hash để tăng tính bảo mật tránh Rainbow table: từ bảng hash có sẵn và hash bị rò rỉ rồi dò ra mật khẩu thật 
 */

import java.security.MessageDigest; // abtract class thuộc package java.security sử dụng để thực hiện SHA-256
import java.nio.charset.StandardCharsets; // chuyển chuỗi sang bytes theo chuẩn unicode
import java.security.NoSuchAlgorithmException; // sử dụng để báo lỗi thuật toán mã hóa, băm, hoặc sinh khóa không được hỗ trợ
import java.security.SecureRandom; // class này dùng để sinh số ngẫu nhiên phục vụ bảo mật, khó đoán hơn Ramdom bình thường

public class PasswordHasher {

    private static int lengthSalt = 16;

    public static String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();// Khởi tạo đối tượng class
        byte saltBytes[] = new byte[lengthSalt]; // tạo mảng bytes chứa salt
        secureRandom.nextBytes(saltBytes); // điền vào saltBytes bằng dữ liệu vừa sinh được, nextBytes() dùng để sinh
                                           // salt
        return bytesToHex(saltBytes);
    }

    public static String hashing(String input) {
        try {

            String salt = generateSalt();
            String saltedInput = input + salt;
            // Gọi 1 instance của SHA-256 trong class MessageDigest, nếu không tồn tại
            // SHA-256 sẽ throw lỗi
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // getBytes(): chuyển String sang mảng byte
            // StandardCharsets.UTF_8: để chương trình chuyển đúng form không phụ thuộc vào
            // thiết bị
            // digest(byte[]): thực hiện SHA-256
            byte encodeHash[] = digest.digest(saltedInput.getBytes(StandardCharsets.UTF_8));

            // hash xong thì encodeHash thuộc dạng binary ta chuyển thành string hex để lưu
            // cho dễ
            return bytesToHex(encodeHash);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Invalid cyptal algorithm!", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        // hash.length trả về số bytes và cứ 1 bytes ta có đc 2 chữ số hex
        // dùng StringBuilder để khi append không tạo String mới
        StringBuilder hexString = new StringBuilder(2 * hash.length);

        for (byte b : hash) {
            // Chuyển byte thành hex (2 chữ số)
            String hex = Integer.toHexString(0xff & b); // ép kiểu thành số nguyên dương không dấu
            if (hex.length() == 1)
                hexString.append('0'); // bổ sung số 0 nếu cần
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static void main(String[] args) {
        String input = "abc";
        System.out.println(hashing(input));
    }

}

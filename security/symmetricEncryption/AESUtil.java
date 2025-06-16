/*
 * Các thông tin nhạy cảm như STK, CCCD, SĐT,... cần được mã hóa
 * Sử dụng mã hóa đối xứng: AES-128
 * - Biến plaintext 16 bytes --> ciphertext 16 bytes
 * - plaintext 16 bytes biểu diễn thành ma trận 4x4:
 * Plaintext (hex):
 * 32 88 31 e4
 * 43 5a 31 37
 * f6 30 98 07
 * a8 8d a2 34
 * - mỗi ô được gọi là 1 state
 * - trải qua 11 round biến đổi với thao tác: SubBytes, ShiftRows, MixCollumns, AddRoundKey,
 * mỗi round sẽ sử dụng 1 key sinh ra từ key ban đầu.
 * - round0: AddRoundKey
 * - round1->9: SubBytes + ShiftRows + MixColumns + XOR
 * - round10: Không có MixColumns
 * - cuối cùng sẽ ra ciphertext
 * - Cơ chế giải mã, làm ngược lại vs mã hóa
 */

/*
 * Phân phối key
 * - Trong AES cần có key chung cho cả server và client
 * --> cần phân phối key này 1 cách an toàn
 * --> Diffie-Hellman Key Exchange (DH hoặc ECDH)
 * Nguyên lí:
 * - Hai bên thỏa thuận một số nguyên tố lớn p và một cơ số g (công khai).
 * - Mỗi bên chọn một số bí mật:
 * + A chọn a, tính A = g^a mod p → gửi A
 * + B chọn b, tính B = g^b mod p → gửi B
 * - Sau đó:
 * + A tính (B)^a mod p
 * + B tính (A)^b mod p
 * --> Cả hai đều tính ra cùng một giá trị: g^(ab) mod p
 * --> Đây chính là khóa chung.
 * Vì sao ko SHA-256 luôn? --> vì còn phải lấy data ra rồi đọc nữa 
 * --> cái này liên quan đến HTTPS
 */

/*
 * Để đảm bảo mỗi lần encrypt sẽ cho ra 1 String khác thì ta thêm IV mang tính ngẫu nhiên,
 * cần IV ở bước decript để decode chính xác
 * ==> làm cách nào để biết IV để decrypt? ==> lưu IV thành 16 bytes đầu của kết quả
 */

//Cipher là lớp trung tâm trong JCA để thực hiện các thao tác mã hóa và giải mã(Encryption/Decryption).
//Hỗ trợ nhiều thuật toán như AES,DES,RSA...thông qua tên thuật toán(transformation string),ví dụ:"AES/CBC/PKCS5Padding".
import javax.crypto.Cipher;

import javax.crypto.SecretKey;

// Đại diện cho Initialization Vector cần dùng khi sử dụng AES ở chế độ CBC
import javax.crypto.spec.IvParameterSpec;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
//Base64 dùng để mã hóa hoặc giải mã dữ liệu nhị phân (byte[]) thành chuỗi Base64 và ngược lại.
import java.util.Base64;

public class AESUtil {
    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding"; // xác định chế độ mã hóa
    private static final int IV_LENGTH = 16;
    // AES: thuật toán
    // CBC: chế độ mã hóa khối - Cipher Block Chaining
    // PKCS5Padding: thêm padding nếu plain text không đủ chia hết cho 16 byte

    public static String encrypt(String plainText, SecretKey key) throws Exception {
        // tạo đối tượng mã hóa AES-CBC
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);

        // tạo IV ngẫu nhiên và đảm bảo tính bảo mật
        // ! cần trả về IV để có thể giải mã
        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[IV_LENGTH];
        random.nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        // chuẩn bị vào mã hóa truyền key và iv vào
        cipher.init(Cipher.ENCRYPT_MODE/* mã hóa đầu vào */, key, iv);

        // Chuyển String thành mảng bytes xử lý toàn bộ mảng byte đầu vào
        // Áp dụng thuật toán được chỉ định ở dòng khai báo cipher
        // Trả về mảng byte kết quả là được mã hóa hay giải mã chỉ định ở dòng init()
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));

        // thêm IV vào đầu mảng bytes
        ByteBuffer buffer = ByteBuffer.allocate(ivBytes.length + encrypted.length);
        buffer.put(ivBytes);
        buffer.put(encrypted);

        // Chuyển về kiểu Base64: 1 kiểu quy ước 4 bit sẽ thành 1 kí tự nào đó, chủ yếu
        // để dễ đọc
        return Base64.getEncoder().encodeToString(buffer.array()/*
                                                                 * do buffer đang kiểu ByteBuffer nên cần chuyển về
                                                                 * bytes[]
                                                                 */);
    }

    public static String decrypt(String encryptedString, SecretKey key) throws Exception {
        // Giải mã Base64 → mảng bytes chứa [IV][CipherText]
        byte[] allBytes = Base64.getDecoder().decode(encryptedString);

        // Tách IV (16 byte đầu)
        ByteBuffer buffer = ByteBuffer.wrap(allBytes);
        byte[] ivBytes = new byte[IV_LENGTH];
        buffer.get(ivBytes); // đọc 16 byte đầu làm IV

        // Tách phần còn lại là CipherText
        byte[] cipherBytes = new byte[buffer.remaining()];
        buffer.get(cipherBytes); // đọc phần còn lại

        // Khởi tạo Cipher với IV và Key
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        // Giải mã → plainText
        byte[] decryptedBytes = cipher.doFinal(cipherBytes);
        return new String(decryptedBytes, "UTF-8");
    }

}
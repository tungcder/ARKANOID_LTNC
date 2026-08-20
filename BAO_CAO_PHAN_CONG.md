# 📋 BÁO CÁO PHÂN CÔNG CÔNG VIỆC VÀ ĐÓNG GÓP THÀNH VIÊN
## ĐỒ ÁN: XÂY DỰNG TRÒ CHƠI ARKANOID (JAVAFX)
**Trường Đại học Công nghệ – Đại học Quốc gia Hà Nội (UET - VNU)**

---

## 1. THÔNG TIN CHUNG
- **Tên đề tài**: Trò chơi Arkanoid Thế Hệ Mới (Arkanoid JavaFX Game)
- **Học phần**: Lập trình Nâng cao / Lập trình Hướng đối tượng (OOP)
- **Nền tảng công nghệ**: Java 17, JavaFX 17, Apache Maven
- **Nhóm sinh viên thực hiện**:

| STT | Họ và Tên | Mã Sinh Viên | Email | Vai trò trong nhóm |
| :---: | :--- | :---: | :--- | :--- |
| 1 | **Đào Ngọc Duy** | **24022637** | 24022637@vnu.edu.vn | Nhóm trưởng |
| 2 | **Triệu Tuấn Thành** | **24020310** | 24020310@vnu.edu.vn | Thành viên |
| 3 | **Hoàng Thanh Tùng** | **24020351** | 24020351@vnu.edu.vn | Thành viên |

---

## 2. BẢNG TỔNG HỢP PHÂN CÔNG NHIỆM VỤ & ĐÓNG GÓP

| STT | Thành viên | Nhiệm vụ chính | Các Class / Module phụ trách | Kết quả | Tỉ lệ đóng góp |
| :---: | :--- | :--- | :--- | :---: | :---: |
| 1 | **Đào Ngọc Duy**<br>*(24022637)* | • Thiết kế kiến trúc Core Engine và vòng lặp trò chơi (`AnimationTimer`).<br>• Phát triển mô hình vật lý quả bóng (`Ball`), thuật toán *Sub-stepping* chống xuyên thấu gạch, phản xạ nảy linh hoạt trên thanh hứng (`Paddle`).<br>• Xây dựng hệ thống lưu/tải trạng thái game (`GameSaveManager`, `GameState`).<br>• Cải tiến hệ thống Bảng xếp hạng High Score (`HighScore`, `HighScoreManager`), thiết kế modal popup nhập tên kỷ lục (`HighScoreNameDialog`).<br>• Xử lý sự kiện đóng cửa sổ an toàn (`setupWindowCloseHandler`) và quản lý luồng dữ liệu. | `ArkanoidGame.java`<br>`GamePanel.java`<br>`entities/ball/Ball.java`<br>`entities/paddle/Paddle.java`<br>`entities/data/GameState.java`<br>`entities/data/GameSaveManager.java`<br>`entities/data/HighScore.java`<br>`entities/data/HighScoreManager.java`<br>`ui/HighScoreNameDialog.java`<br>`GameConstants.java` | Hoàn thành **100%** | **33.3%** |
| 2 | **Triệu Tuấn Thành**<br>*(24020310)* | • Xây dựng hệ thống phân cấp các loại gạch (`Brick`, `BrickWeak`, `BrickMedium`, `BrickStrong`, `BrickUnbreakable`, `BrickPowerup`, `BrickMove`).<br>• Áp dụng mẫu thiết kế Factory Pattern (`BrickFactory`) để khởi tạo gạch theo định dạng bản đồ.<br>• Thiết kế toàn bộ hệ thống Vật phẩm (`Item`, `BuffManager`), triển khai 5 loại Buff và 4 loại Debuff với hiệu ứng tương ứng.<br>• Xây dựng module đọc và phân tích cấu trúc màn chơi từ file CSV (`MapLoader`, `MapManager`), quản lý chuyển màn. | `entities/brick/Brick.java`<br>`entities/brick/BrickFactory.java`<br>`entities/brick/BrickGrid.java`<br>`entities/brick/BrickWeak.java`<br>`entities/brick/BrickMedium.java`<br>`entities/brick/BrickStrong.java`<br>`entities/brick/BrickUnbreakable.java`<br>`entities/brick/BrickMove.java`<br>`entities/brick/BrickPowerup.java`<br>`entities/item/Item.java`<br>`entities/item/BuffManager.java`<br>`entities/item/Buff/*`<br>`entities/item/DeBuff/*`<br>`entities/map/MapLoader.java`<br>`entities/map/MapManager.java` | Hoàn thành **100%** | **33.3%** |
| 3 | **Hoàng Thanh Tùng**<br>*(24020351)* | • Thiết kế giao diện đồ họa người dùng (UI) và chuyển đổi các màn hình (`MainMenu`, `GameOverScreen`, `GameCompleteScreen`, `HighScoreScreen`).<br>• Xây dựng hệ thống Quản lý Chủ đề (`ThemeManager`) hỗ trợ 3 Themes (Ocean, Space, Pyramid) kèm bộ tài nguyên tương ứng.<br>• Xây dựng màn hình và logic Cài đặt âm thanh/chủ đề (`SettingScreen`, `SettingManager`).<br>• Quản lý hệ thống phát âm thanh SFX và BGM (`SoundManager`).<br>• Thiết kế thanh trạng thái trong game (`GameStatusPanel`), tính toán điểm số/combo (`Score`).<br>• Biên tập tài liệu, quay và dựng video demo game. | `entities/menu/MainMenu.java`<br>`GameOverScreen.java`<br>`GameCompleteScreen.java`<br>`HighScoreScreen.java`<br>`Setting/SettingScreen.java`<br>`Setting/SettingManager.java`<br>`ThemeManager.java`<br>`SoundManager.java`<br>`GameStatusPanel.java`<br>`entities/data/Score.java`<br>`README.md` & Video Demo | Hoàn thành **100%** | **33.4%** |

---

## 3. CHI TIẾT ĐÓNG GÓP KỸ THUẬT CỦA TỪNG THÀNH VIÊN

### 3.1. Đào Ngọc Duy (MSV: 24022637) - Nhóm trưởng
- **Kiến trúc Game Loop & Điều phối (`GamePanel.java`, `ArkanoidGame.java`)**:
  - Xây dựng vòng lặp chính của trò chơi dựa trên `javafx.animation.AnimationTimer` đạt tần số quét ổn định 60 FPS.
  - Quản lý đồng bộ vòng đời thực thể (Update -> Collision Check -> Render -> HUD Refresh).
  - Tích hợp logic xử lý sự kiện đóng cửa sổ (`setupWindowCloseHandler`) tự động lưu trạng thái khi người dùng thoát game đột ngột.
- **Nâng cấp Vật lý Chuyển động & Va chạm (`Ball.java`, `Paddle.java`)**:
  - Triển khai giải thuật **Sub-stepping** chia nhỏ quãng đường di chuyển của bóng khi đạt tốc độ cao thành các bước $\le 3\text{px}$, loại bỏ hoàn toàn lỗi bóng bay xuyên gạch (tunneling).
  - Cải tiến công thức phản xạ trên thanh hứng: bảo toàn vận tốc tổng hợp $S = \sqrt{dx^2 + dy^2}$, cho phép người chơi điều hướng góc nảy linh hoạt từ $-60^\circ$ đến $+60^\circ$ tùy vị trí tiếp xúc.
  - Xử lý cơ chế dính bóng/phát bóng mượt mà (`attachToPaddle`, `releaseFromPaddle`).
- **Hệ thống Lưu trữ & Bảng Xếp Hạng (`GameSaveManager.java`, `HighScoreManager.java`)**:
  - Xây dựng mô hình lưu/tải ván đấu (`GameState`), bảo lưu điểm, mạng, thời gian, vị trí bóng/paddle và mảng gạch còn lại.
  - Thiết kế hộp thoại Cyberpunk `HighScoreNameDialog` cho phép người chơi nhập tên ghi danh khi đạt điểm cao trong Top 10.

---

### 3.2. Triệu Tuấn Thành (MSV: 24020310) - Thành viên
- **Hệ thống Gạch & Mẫu thiết kế Factory (`entities/brick/*`)**:
  - Thiết kế cấu trúc phân cấp kế thừa từ lớp cha trừu tượng `Brick.java`.
  - Triển khai mẫu thiết kế **Factory Method** trong `BrickFactory.java` giúp ánh xạ ký tự bản đồ CSV thành các đối tượng gạch tương ứng một cách linh hoạt, dễ mở rộng.
  - Xây dựng `BrickMove` kết hợp giao diện `Movable` tạo ra các chướng ngại vật di động tuần hoàn.
  - Xây dựng `BrickGrid` quản lý mảng 2 chiều các viên gạch, tối ưu hóa quá trình duyệt va chạm và kiểm tra điều kiện qua màn (`isLevelComplete`).
- **Hệ thống Vật phẩm Buff & Debuff (`entities/item/*`)**:
  - Xây dựng lớp cơ sở `Item.java` đa hình với phương thức `apply(Paddle, Ball)`.
  - Triển khai 5 Buff có lợi: `Buff_BiggerPaddle`, `Buff_BiggerBall`, `Buff_SlowerBall`, `Buff_ExplosiveBall` (nổ diện rộng quanh tâm va chạm), `Buff_ExtraLives`.
  - Triển khai 4 Debuff thử thách: `DeBuff_SmallerPaddle`, `DeBuff_SmallerBall`, `DeBuff_FastBall`, `DeBuff_ReversePaddle` (đảo ngược phím điều khiển).
  - Xây dựng `BuffManager` quản lý thời gian hiệu lực và hủy bỏ hiệu ứng khi hết hạn.
- **Hệ thống Nạp Bản đồ (`MapLoader.java`, `MapManager.java`)**:
  - Viết module đọc file định dạng CSV từ thư mục `resources/Levels/` (`Map1.csv` $\rightarrow$ `Map4.csv`).
  - Quản lý chuyển đổi mượt mà giữa các cấp độ chơi.

---

### 3.3. Hoàng Thanh Tùng (MSV: 24020351) - Thành viên
- **Thiết kế Giao diện Người dùng (UI/UX) & Màn hình Trò chơi**:
  - Thiết kế Menu chính (`MainMenu.java`) với các hiệu ứng hover, chuyển cảnh vào ván chơi mới, tiếp tục game đã lưu hoặc mở cài đặt.
  - Thiết kế màn hình Thua cuộc (`GameOverScreen.java`) và Chiến thắng (`GameCompleteScreen.java`) với hiệu ứng chữ phát sáng (DropShadow) và hoạt ảnh Scale/Fade Transition.
  - Thiết kế màn hình Bảng vàng (`HighScoreScreen.java`) hiển thị danh sách Top 10 kỷ lục với huy chương Vàng, Bạc, Đồng và phân tách màu sắc trực quan.
- **Hệ thống Đa Chủ Đề (Theme System) & Cài đặt (`ThemeManager.java`, `SettingManager.java`)**:
  - Xây dựng cơ chế tải động hình ảnh theo 3 chủ đề: **Ocean**, **Space**, **Pyramid**.
  - Thiết kế màn hình Cài đặt (`SettingScreen.java`) cho phép người chơi điều chỉnh thanh trượt âm lượng và chuyển đổi theme ngay trong game.
  - Lưu và tải cấu hình người dùng vào file dữ liệu cục bộ.
- **Hệ thống Âm thanh & Tính điểm (`SoundManager.java`, `Score.java`, `GameStatusPanel.java`)**:
  - Tích hợp phát nhạc nền lặp vô hạn (BGM) và hiệu ứng tức thời (SFX) cho từng tương tác: va chạm gạch, nảy paddle, hoàn thành màn, game over, victory.
  - Thiết kế thanh HUD hiển thị trực tiếp Mạng sống, Điểm số, Cấp độ và đếm lùi thời gian Buff/Debuff.
  - Viết thuật toán tính điểm thưởng Combo khi phá nhiều gạch liên tiếp.
  - Thực hiện ghi hình demo, dựng video giới thiệu và hoàn thiện tài liệu dự án.

---

## 4. ĐÁNH GIÁ TỔNG KẾT & TINH THẦN LÀM VIỆC NHÓM
1. **Tổ chức và Phối hợp**:
   - Nhóm sử dụng Git/GitHub để quản lý mã nguồn, chia nhánh tính năng (`feature branches`) rõ ràng và tích hợp qua Pull Requests.
   - Các thành viên trao đổi thường xuyên, hỗ trợ phản biện và tối ưu hóa code lẫn nhau.
2. **Chất lượng Sản phẩm**:
   - Trò chơi vận hành mượt mà, ổn định, không phát sinh lỗi ngoại lệ (Exceptions).
   - Áp dụng đầy đủ các tính chất hướng đối tượng (Đóng gói, Kế thừa, Đa hình, Trừu tượng) cùng các Design Patterns chuẩn mực.
3. **Mức độ hoàn thành**:
   - Tất cả các mục tiêu đề ra ban đầu đều đã được hoàn thành xuất sắc (**100% tiến độ**).

---

<br>
<div align="right">
  <i>Hà Nội, ngày 20 tháng 08 năm 2026</i><br><br>
  <b>ĐẠI DIỆN NHÓM SINH VIÊN</b><br><br><br>
  <b>Đào Ngọc Duy</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b>Triệu Tuấn Thành</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b>Hoàng Thanh Tùng</b>
</div>

# BasicShopApp

Project: Shop App (using basic android knowledge)
- Programming language: Java
- Database online: Firebase Google
- Utils: Glide, Swipe To Delete
- MinSDKVersion: 19
- Gradle version: 6.5

Chức năng App:
Mô tả: Một ứng dụng cho phép user tạo tài khoản/đăng nhập, đăng
sản phẩm mình muốn bán, và đặt mua sản phẩm từ những người khác.
1. Tạo tài khoản:
Validation:
- Họ/Tên: required field
- Email: required field, email format pattern
- Password: required field, hạn chế 8-20 kí tự, phải có ít nhất 1
kí tự từ a-z, ít nhât một kí tự từ A-Z, ít nhất một kí tự đặc biệt
và ít nhất 1 kí tự số
- Phải tick chọn đồng ý các điều khoản khi tạo tài khoản

2. Đăng nhập:
Validation
- Email: required field, email format pattern, email phải tồn tại
trong hệ thống
- Password: required field, hạn chế 8-20 kí tự, phải có ít nhất 1
kí tự từ a-z, ít nhât một kí tự từ A-Z, ít nhất một kí tự đặc biệt
và ít nhất 1 kí tự số

3. Trang cá nhân:
- Sau khi đăng nhập lần đầu tiên, sẽ xuất hiện một màn hình để
nhập thông tin. Những lần truy cập app tiếp theo, nếu đã nhập
thông tin cá nhân thì sẽ không hiện màn hình này
- Thông tin cá nhân bao gồm: Tên, giới tính, email, sđt, địa chỉ,
avatar
- Có thể chỉnh sửa profile thông qua giao diện

4. Thêm sản phẩm:
- Bấm dấu cộng ở góc trên bên phải để mở giao diện ‘Thêm sản phẩm’

- Giao diện thêm sản phẩm và sau khi nhấn nút tạo sản phẩm:

- Có thể xóa sản phẩm sau khi tạo
- Sau khi sản phẩm được tạo, sản phẩm đó sẽ xuất hiện ở màn hình
‘Trang chủ’

5. Thêm sản phẩm vào giỏ hàng:
- Từ màn mình trang chủ, bấm vào sản phẩm muốn mua để vào giao
diện chi tiết sản phẩm, sau đó nhấn nút ‘Thêm vào giỏ hàng’

- Sau khi đã thêm 1 sản phẩm vào giỏ hàng, thì khi xem chi tiết
sản phẩm của sản phẩm đó, button “Thêm vào giỏ hàng” sẽ biến mất
và hiện “Vào giỏ hàng” thay thế

Có thể tiếp tục chọn thêm sản phẩm khác, hoặc tiến hành đặt hàng
ở giao diện giỏ hàng của tôi


6. Đặt hàng:
- Nhấn icon Cart ở góc trên bên phải để vào giao diện “giỏ hàng
của tôi”

- Ở màn hình giao diện của tôi, có thể xóa đơn hàng hoặc tăng/giảm
số lượng sản phẩm
- Nếu số lượng vượt quá số lượng còn lại của sản phẩm, một toast
message ở bottom sẽ hiện thông báo “Sản phẩm vượt quá số lượng”.
Nếu số lượng giảm xuống 0, sản phẩm sẽ bị xóa khỏi giỏ hàng

7. Thanh toán:
- Chọn thanh toán và nhập địa chỉ đặt hàng

- Sau khi đặt hàng, đơn hàng nằm ở giao diện “Giỏ hàng”

8. Firebase database screenshot:


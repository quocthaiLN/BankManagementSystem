CREATE DATABASE bankingdb;
USE bankingdb;

CREATE TABLE Customer (
	customer_id int NOT NULL auto_increment, -- primary key
    name nvarchar(100) NOT NULL UNIQUE,
    birth_date date,
    gender nvarchar(20), -- check constraint
    identity_number varchar(50) UNIQUE NOT NULL,
    phone varchar(50),
    address nvarchar(200),
    email varchar(100),
    type nvarchar(100), -- check constraint
    status nvarchar(100), -- check constraint
    register_date date,
    CONSTRAINT PK_Customer PRIMARY KEY(customer_id),
    CONSTRAINT CHK_Customer_gender check(gender IN (N'Nam', N'Nữ')),
    CONSTRAINT CHK_Customer_type check(type IN (N'cá nhân', N'doanh nghiệp')),
    CONSTRAINT CHK_Customer_status check(status IN (N'mở', N'đóng', N'khóa'))
);


CREATE TABLE Account (
	account_id INT NOT NULL AUTO_INCREMENT, -- primary key
	customer_id INT,
	branch_id VARCHAR(20), -- branch: chi nhánh
	account_type NVARCHAR(50),
	currency VARCHAR(10),
	balance DECIMAL(18,2), -- balance: số dư
	status NVARCHAR(50),
	open_date DATE,
	close_date DATE,
	CONSTRAINT PK_Account PRIMARY KEY(account_id),
	CONSTRAINT CHK_Account_type CHECK (account_type IN (N'thanh toán', N'tiết kiệm')),
	CONSTRAINT CHK_Account_status CHECK (status IN (N'mở', N'đóng', N'khóa'))
);

CREATE TABLE Transaction (
	transaction_id VARCHAR(20) NOT NULL, -- primary key
	from_account INT,
	to_account INT,
	transaction_type NVARCHAR(50),
	amount DECIMAL(18,2),
	currency VARCHAR(10),
	date DATE,
	status NVARCHAR(50),
	CONSTRAINT PK_Transaction PRIMARY KEY(transaction_id),
	CONSTRAINT CHK_Transaction_type CHECK (transaction_type IN (N'chuyển tiền', N'rút tiền', N'nạp tiền', N'nhận tiền')),
	CONSTRAINT CHK_Transaction_status CHECK (status IN (N'thành công', N'thất bại', N'chờ xử lý'))
);

CREATE TABLE Loan (
	loan_id VARCHAR(20) NOT NULL, -- primary key
	customer_id INT,
	loan_type NVARCHAR(100),
	amount DECIMAL(18,2),
	currency VARCHAR(10),
	interest_rate DECIMAL(5,2),
	start_date DATE,
	end_date DATE,
	status NVARCHAR(50),
	collateral_id VARCHAR(20), -- collateral: vật thế chấp
	CONSTRAINT PK_Loan PRIMARY KEY(loan_id),
	CONSTRAINT CHK_Loan_type CHECK (loan_type IN (N'vay tiêu dùng', N'vay mua nhà', N'vay thế chấp', N'vay sản xuất kinh doanh')),
	CONSTRAINT CHK_Loan_status CHECK (status IN (N'đang vay', N'quá hạn', N'tất toán'))
);
-- collateral: thế chấp
CREATE TABLE Collateral (
	collateral_id VARCHAR(20) NOT NULL, -- primary key
	owner_id INT,
	asset_type NVARCHAR(100), -- asset: tài sản
	estimated_value DECIMAL(18,2),
	currency VARCHAR(10),
	status NVARCHAR(50),
	CONSTRAINT PK_Collateral PRIMARY KEY(collateral_id),
	CONSTRAINT CHK_Collateral_status CHECK (status IN (N'đang cầm cố', N'giải chấp', N'thanh lý'))
);

CREATE TABLE Employee (
	employee_id VARCHAR(20) NOT NULL, -- primary key
	full_name NVARCHAR(100) UNIQUE,
	branch_id VARCHAR(20),
	role NVARCHAR(100),
	status NVARCHAR(50),
	created_at DATE,
	CONSTRAINT PK_Employee PRIMARY KEY(employee_id),
	CONSTRAINT CHK_Employee_status CHECK (status IN (N'đang hoạt động', N'nghỉ việc', N'bị khóa'))
);

CREATE TABLE Branch (
	branch_id VARCHAR(20) NOT NULL, -- primary key
	branch_name NVARCHAR(100),
	address NVARCHAR(200),
	status NVARCHAR(50),
	CONSTRAINT PK_Branch PRIMARY KEY(branch_id),
	CONSTRAINT CHK_Branch_status CHECK (status IN (N'mở', N'đóng'))
);

CREATE TABLE Currency (
	currency_code VARCHAR(10) NOT NULL, -- primary key
	currency_name NVARCHAR(50),
	rate_to_usd DECIMAL(10,6),
	CONSTRAINT PK_Currency PRIMARY KEY(currency_code)
);

create table user_account (
	username varchar(100) NOT NULL PRIMARY KEY,
    password varchar(100) NOT NULL,
    user_type nvarchar(100),
    reference_id INT NOT NULL, -- Không dùng FK trỏ tới bảng nào hết, vì có 2 bảng nhân viên và khách hàng -> Xử lý việc này bằng java.
    CONSTRAINT CHK_user_acount_type CHECK (user_type IN ('customer', 'employee'))
);

-- =================== Foreign key ==============
-- -------- account ------------
ALTER TABLE account
ADD CONSTRAINT FK_account_customer 
FOREIGN KEY (customer_id) references customer(customer_id);

alter table account
add constraint FK_account_branch
foreign key (branch_id) references branch(branch_id);

alter table account
add constraint FK_account_currency
foreign key (currency) references currency(currency_code);
-- -------- transaction ------------
alter table transaction
add constraint FK_transaction_account_from
foreign key (from_account) references account(account_id);

alter table transaction
add constraint FK_transaction_account_to
foreign key (to_account) references account(account_id);

alter table transaction
add constraint FK_transaction_currency
foreign key (currency) references currency(currency_code);

-- -------- loan ------------
alter table loan
add constraint FK_loan_customer
foreign key (customer_id) references customer(customer_id);

alter table loan
add constraint FK_loan_collateral
foreign key (collateral_id) references collateral(collateral_id);

alter table loan
add constraint FK_loan_currency
foreign key (currency) references currency(currency_code);

-- -------- collateral ------------
alter table collateral
add constraint FK_collateral_customer
foreign key (owner_id) references customer(customer_id);

alter table collateral
add constraint FK_collateral_currency
foreign key (currency) references currency(currency_code);

-- -------- employee ------------
alter table employee
add constraint FK_employee_branch
foreign key (branch_id) references branch(branch_id);

-- =============== insert data =================
-- --------- customer -------------
INSERT INTO Customer (name, birth_date, gender, identity_number, phone, address, email, type, status, register_date)
VALUES
(N'Nguyễn Văn A', '1990-05-12', N'Nam', '123456789', '0901234567', N'123 Lê Lợi, Q1, TP.HCM', 'anguyen@gmail.com', N'cá nhân', N'mở', '2023-01-10'),

(N'Trần Thị B', '1985-09-23', N'Nữ', '987654321', '0938765432', N'456 Trần Hưng Đạo, Q5, TP.HCM', 'btran@gmail.com', N'doanh nghiệp', N'khóa', '2022-12-01'),

(N'Lê Quốc Cường', '1995-11-02', N'Nam', '112233445', '0912345678', N'789 Nguyễn Huệ, Q1, TP.HCM', 'cuongle@gmail.com', N'cá nhân', N'mở', '2024-03-05'),

(N'Phạm Thị Duyên', '2000-03-18', N'Nữ', '556677889', '0987654321', N'321 Cách Mạng Tháng 8, Q10, TP.HCM', 'duyenpham@gmail.com', N'cá nhân', N'đóng', '2023-07-15');
-- --------- currency ----------
INSERT INTO Currency (currency_code, currency_name, rate_to_usd)
VALUES
('VND', N'Việt Nam Đồng', 0.000042),
('USD', N'Đô la Mỹ', 1.0),
('EUR', N'Euro', 1.08),
('CNY', N'Nhân dân tệ', 0.14);

-- -------- branch ----------
INSERT INTO Branch (branch_id, branch_name, address, status)
VALUES
('CN01', N'Chi nhánh Quận 1', N'12 Nguyễn Huệ, Quận 1, TP.HCM', N'mở'),
('CN02', N'Chi nhánh Quận 3', N'45 Võ Văn Tần, Quận 3, TP.HCM', N'mở'),
('CN03', N'Chi nhánh Hà Nội', N'89 Trần Duy Hưng, Cầu Giấy, Hà Nội', N'đóng'),
('CN04', N'Chi nhánh Đà Nẵng', N'78 Bạch Đằng, Hải Châu, Đà Nẵng', N'mở'),
('CN05', N'Chi nhánh Cần Thơ', N'23 Lý Tự Trọng, Ninh Kiều, Cần Thơ', N'đóng');

-- ----------- account -------------
INSERT INTO Account (customer_id, branch_id, account_type, currency, balance, status, open_date, close_date)
VALUES 
(1, 'CN01', N'thanh toán', 'VND', 15000000.00, N'mở', '2023-01-15', NULL),
(2, 'CN02', N'tiết kiệm', 'USD', 2000.00, N'mở', '2022-11-01', NULL),
(3, 'CN01', N'thanh toán', 'VND', 750000.00, N'khóa', '2021-08-10', '2024-01-05'),
(1, 'CN03', N'tiết kiệm', 'EUR', 500.00, N'mở', '2024-03-01', NULL),
(4, 'CN01', N'thanh toán', 'VND', 3200000.00, N'đóng', '2023-07-20', '2024-02-01');

-- ------------- employee ------------
INSERT INTO Employee (employee_id, full_name, branch_id, role, status, created_at)
VALUES
('NV001', N'Nguyễn Văn A', 'CN01', N'Giao dịch viên', N'đang hoạt động', '2023-02-15'),
('NV002', N'Trần Thị B', 'CN02', N'Quản lý chi nhánh', N'đang hoạt động', '2022-08-10'),
('NV003', N'Lê Hoàng C', 'CN03', N'Nhân viên tín dụng', N'nghỉ việc', '2021-04-05'),
('NV004', N'Phạm Mai D', 'CN04', N'Kế toán viên', N'đang hoạt động', '2023-11-20'),
('NV005', N'Đỗ Quốc E', 'CN05', N'Chuyên viên hỗ trợ', N'bị khóa', '2020-06-01');
select * from employee;

-- ------------- collateral ----------
INSERT INTO Collateral (collateral_id, owner_id, asset_type, estimated_value, currency, status)
VALUES 
('CL001', 1, N'Nhà ở', 1500000000.00, 'VND', N'đang cầm cố'),
('CL002', 2, N'Ô tô', 700000000.00, 'VND', N'giải chấp'),
('CL003', 3, N'Sổ tiết kiệm', 10000.00, 'USD', N'thanh lý'),
('CL004', 1, N'Đất nền', 1200000000.00, 'VND', N'đang cầm cố'),
('CL005', 4, N'Cổ phiếu', 500000000.00, 'VND', N'giải chấp');


-- ----------- transaction --------------
INSERT INTO Transaction (transaction_id, from_account, to_account, transaction_type, amount, currency, date, status)
VALUES 
('T001', 1, 2, N'chuyển tiền', 500000.00, 'VND', '2024-10-10', N'thành công'),
('T002', 2, NULL, N'rút tiền', 1000000.00, 'VND', '2024-11-15', N'thành công'),
('T003', NULL, 3, N'nạp tiền', 200000.00, 'USD', '2024-12-01', N'thành công'),
('T004', 3, 4, N'chuyển tiền', 150000.00, 'VND', '2025-01-05', N'thất bại'),
('T005', 4, 1, N'chuyển tiền', 300000.00, 'VND', '2025-02-20', N'chờ xử lý'),
('T006', 2, 3, N'chuyển tiền', 500.00, 'USD', '2025-03-01', N'thành công'),
('T007', NULL, 1, N'nạp tiền', 1000000.00, 'VND', '2025-03-10', N'thành công'),
('T008', 1, NULL, N'rút tiền', 250000.00, 'VND', '2025-03-15', N'thành công');

-- -------- loan ------------
INSERT INTO Loan (loan_id, customer_id, loan_type, amount, currency, interest_rate, start_date, end_date, status, collateral_id)
VALUES 
('LN001', 1, N'vay tiêu dùng', 50000000, 'VND', 9.5, '2024-01-15', '2026-01-15', N'đang vay', 'CL001'),
('LN002', 2, N'vay mua nhà', 1500000000, 'VND', 8.2, '2023-06-01', '2033-06-01', N'đang vay', 'CL002'),
('LN003', 3, N'vay sản xuất kinh doanh', 750000000, 'USD', 7.0, '2022-03-10', '2027-03-10', N'quá hạn', 'CL003'),
('LN004', 4, N'vay thế chấp', 200000000, 'VND', 6.8, '2021-12-20', '2024-12-20', N'tất toán', 'CL004');

-- -------- user_account --------------
INSERT INTO user_account (username, password, user_type, reference_id)
VALUES
-- Tài khoản khách hàng
('cus01', 'pass01', 'customer', 1),
('cus02', 'pass02', 'customer', 2),
('cus03', 'pass03', 'customer', 3),
('cus04', 'pass04', 'customer', 4),
-- Tài khoản nhân viên
('emp01', 'admin01', 'employee', 1),
('emp02', 'admin02', 'employee', 2),
('emp03', 'admin03', 'employee', 3);

select * from account;
select * from branch;
select * from collateral;
select * from currency;
select * from customer;
select * from employee;
select * from loan;
select * from transaction;
select * from user_account;
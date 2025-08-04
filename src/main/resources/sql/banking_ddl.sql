CREATE DATABASE bankingdb;
USE bankingdb;

CREATE TABLE Customer (
	customer_id  VARCHAR(20) NOT NULL, -- primary key
    name nvarchar(100) NOT NULL ,
    birth_date date,
    gender nvarchar(20), -- check constraint
    identity_number varchar(50) UNIQUE NOT NULL,
    phone varchar(50) UNIQUE,
    address nvarchar(200),
    email varchar(100) UNIQUE,
    type nvarchar(100), -- check constraint
    status nvarchar(100), -- check constraint
    register_date date,
    CONSTRAINT PK_Customer PRIMARY KEY(customer_id),
    CONSTRAINT CHK_Customer_gender check(gender IN (N'Nam', N'Nữ')),
    CONSTRAINT CHK_Customer_type check(type IN (N'cá nhân', N'doanh nghiệp')),
    CONSTRAINT CHK_Customer_status check(status IN (N'mở', N'đóng', N'khóa'))
);


CREATE TABLE Account (
	account_id  VARCHAR(20) NOT NULL, -- primary key
	customer_id VARCHAR(20) NOT NULL,
	branch_id VARCHAR(20), -- branch: chi nhánh
	account_type NVARCHAR(50),
	currency VARCHAR(10),
	balance DECIMAL(18,2) default 0, -- balance: số dư
	status NVARCHAR(50),
	open_date DATE NOT NULL,
	close_date DATE,
	CONSTRAINT PK_Account PRIMARY KEY(account_id),
	CONSTRAINT CHK_Account_type CHECK (account_type IN (N'thanh toán', N'tiết kiệm')),
	CONSTRAINT CHK_Account_status CHECK (status IN (N'mở', N'đóng', N'khóa'))
);

CREATE TABLE Transaction (
	transaction_id VARCHAR(20) NOT NULL, -- primary key
	from_account VARCHAR(20) ,
	to_account VARCHAR(20),
	transaction_type NVARCHAR(50),
	amount DECIMAL(18,2),
	currency VARCHAR(10),
	date DATE NOT NULL,
	status NVARCHAR(50),
	CONSTRAINT PK_Transaction PRIMARY KEY(transaction_id),
	CONSTRAINT CHK_Transaction_type CHECK (transaction_type IN (N'chuyển tiền', N'rút tiền', N'nạp tiền', N'nhận tiền')),
	CONSTRAINT CHK_Transaction_status CHECK (status IN (N'thành công', N'thất bại', N'chờ xử lý'))
);

CREATE TABLE Loan (
	loan_id VARCHAR(20) NOT NULL, -- primary key
	customer_id VARCHAR(20) NOT NULL,
	loan_type NVARCHAR(100),
	amount DECIMAL(18,2) CHECK(AMOUNT >= 0),
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
	owner_id VARCHAR(20) NOT NULL,
	asset_type NVARCHAR(100), -- asset: tài sản
	estimated_value DECIMAL(18,2),
	currency VARCHAR(10),
	status NVARCHAR(50),
	CONSTRAINT PK_Collateral PRIMARY KEY(collateral_id),
	CONSTRAINT CHK_Collateral_status CHECK (status IN (N'đang cầm cố', N'giải chấp', N'thanh lý'))
);

CREATE TABLE Employee (
	employee_id VARCHAR(20) NOT NULL, -- primary key
	full_name NVARCHAR(100),
	branch_id VARCHAR(20),
	role NVARCHAR(100),
	status NVARCHAR(50),
	created_at DATE,
    phone varchar(50) UNIQUE,
	address nvarchar(200),
	email varchar(100) UNIQUE,

	username varchar(100),
	CONSTRAINT PK_Employee PRIMARY KEY(employee_id),
	CONSTRAINT CHK_Employee_status CHECK (status IN (N'đang hoạt động', N'nghỉ việc', N'bị khóa'))
);

CREATE TABLE Branch (
	branch_id VARCHAR(20) NOT NULL, -- primary key
	branch_name NVARCHAR(100) UNIQUE,
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


-- new: account_authen -- 
create table account_authen (
	username varchar(100) NOT NULL PRIMARY KEY,
    password varchar(100) NOT NULL,
    user_type nvarchar(100),
    account_id  VARCHAR(20) ,
    salt VARCHAR(33),
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

alter table employee
add constraint FK_employee_accAuthen
foreign key (username) references account_authen(username);

-- -------- account_authen  ------------
alter table account_authen
add constraint FK_accAuthen_account
foreign key (account_id) references account(account_id);

-- THEM CAC BANG XU LY THEM ID KHONG TRUNG // CAC BANG NAY KHONG CO KHOA NGOAI NEN KHONG CAN QUAN TAM TOI DK
CREATE TABLE CUSTOMER_ID_TABLE(
	CUSTOMER_ID INT NOT NULL auto_increment,
    CONSTRAINT PK_CUSTOMER_ID_TABLE PRIMARY KEY(CUSTOMER_ID)
);

CREATE TABLE EMPLOYEE_ID_TABLE(
	EMPLOYEE_ID INT NOT NULL auto_increment,
    CONSTRAINT PK_EMPLOYEE_ID_TABLE PRIMARY KEY(EMPLOYEE_ID)
);

CREATE TABLE ACCOUNT_ID_TABLE(
	ACCOUNT_ID INT NOT NULL auto_increment,
    CONSTRAINT PK_ACCOUNT_ID_TABLE PRIMARY KEY(ACCOUNT_ID)
);

CREATE TABLE COLLATERAL_ID_TABLE(
	COLLATERAL_ID INT NOT NULL auto_increment,
    CONSTRAINT PK_COLLATERAL_ID_TABLE PRIMARY KEY(COLLATERAL_ID)
);

CREATE TABLE LOAN_ID_TABLE(
	LOAN_ID INT NOT NULL auto_increment,
    CONSTRAINT PK_LOAN_ID_TABLE PRIMARY KEY(LOAN_ID)
);

CREATE TABLE BRANCH_ID_TABLE(
	BRANCH_ID INT NOT NULL auto_increment,
    CONSTRAINT PK_BRANCH_ID_TABLE PRIMARY KEY(BRANCH_ID)
);

CREATE TABLE TRANSACTION_ID_TABLE(
	TRANSACTION_ID INT NOT NULL auto_increment,
    CONSTRAINT PK_TRANSACTION_ID_TABLE PRIMARY KEY(TRANSACTION_ID)
);

-- ---------------------------------- các hàm xử lý ---------------------------------------------
-- trigger them customer
DELIMITER $$
CREATE TRIGGER CUSTOMER_ID_AUTO
BEFORE INSERT ON CUSTOMER
FOR EACH ROW
BEGIN
	DECLARE ID_AUTO INT;
    INSERT INTO CUSTOMER_ID_TABLE VALUES();
    SET ID_AUTO = last_insert_id();
    SET NEW.CUSTOMER_ID = LPAD(ID_AUTO, 10, '0');
END $$
DELIMITER ;

-- trigger them account
DELIMITER $$
CREATE TRIGGER ACCOUNT_ID_AUTO
BEFORE INSERT ON ACCOUNT
FOR EACH ROW
BEGIN
	DECLARE ID_AUTO INT;
    INSERT INTO ACCOUNT_ID_TABLE VALUES();
    SET ID_AUTO = last_insert_id();
    SET NEW.ACCOUNT_ID = LPAD(ID_AUTO, 10, '0');
END $$
DELIMITER ;

-- trigger them employee
DELIMITER $$
CREATE TRIGGER EMPLOYEE_ID_AUTO
BEFORE INSERT ON EMPLOYEE
FOR EACH ROW
BEGIN
	DECLARE ID_AUTO INT;
    INSERT INTO EMPLOYEE_ID_TABLE VALUES();
    SET ID_AUTO = last_insert_id();
    SET NEW.EMPLOYEE_ID = LPAD(ID_AUTO, 10, '0');
END $$
DELIMITER ;

-- trigger them COLLATERAL
DELIMITER $$
CREATE TRIGGER COLLATERAL_ID_AUTO
BEFORE INSERT ON COLLATERAL
FOR EACH ROW
BEGIN
	DECLARE ID_AUTO INT;
    INSERT INTO COLLATERAL_ID_TABLE VALUES();
    SET ID_AUTO = last_insert_id();
    SET NEW.COLLATERAL_ID = LPAD(ID_AUTO, 10, '0');
END $$
DELIMITER ;

-- trigger them TRANSACTION
DELIMITER $$
CREATE TRIGGER TRANSACTION_ID_AUTO
BEFORE INSERT ON TRANSACTION
FOR EACH ROW
BEGIN
	DECLARE ID_AUTO INT;
    INSERT INTO TRANSACTION_ID_TABLE VALUES();
    SET ID_AUTO = last_insert_id();
    SET NEW.TRANSACTION_ID = LPAD(ID_AUTO, 10, '0');
END $$
DELIMITER ;

-- trigger them LOAN
DELIMITER $$
CREATE TRIGGER LOAN_ID_AUTO
BEFORE INSERT ON LOAN
FOR EACH ROW
BEGIN
	DECLARE ID_AUTO INT;
    INSERT INTO LOAN_ID_TABLE VALUES();
    SET ID_AUTO = last_insert_id();
    SET NEW.LOAN_ID = LPAD(ID_AUTO, 10, '0');
END $$
DELIMITER ;

-- trigger them BRANCH
DELIMITER $$
CREATE TRIGGER BRANCH_ID_AUTO
BEFORE INSERT ON BRANCH
FOR EACH ROW
BEGIN
	DECLARE ID_AUTO INT;
    INSERT INTO BRANCH_ID_TABLE VALUES();
    SET ID_AUTO = last_insert_id();
    SET NEW.BRANCH_ID = LPAD(ID_AUTO, 10, '0');
END $$
DELIMITER ;

-- =============== insert data =================
-- --------- customer -------------
insert into customer(name, birth_date, gender, identity_number, phone, address, email, type, status, register_date) values
(N'Nguyễn Văn A', '1990-05-12', N'Nam', '123456789', '0901234567', N'123 Lê Lợi, Q1, TP.HCM', 'anguyen@gmail.com', N'cá nhân', N'mở', '2023-01-10'),

(N'Trần Thị B', '1985-09-23', N'Nữ', '987654321', '0938765432', N'456 Trần Hưng Đạo, Q5, TP.HCM', 'btran@gmail.com', N'doanh nghiệp', N'khóa', '2022-12-01'),

(N'Lê Quốc Cường', '1995-11-02', N'Nam', '112233445', '0912345678', N'789 Nguyễn Huệ, Q1, TP.HCM', 'cuongle@gmail.com', N'cá nhân', N'mở', '2024-03-05'),

(N'Phạm Thị Duyên', '2000-03-18', N'Nữ', '556677889', '0987654321', N'321 Cách Mạng Tháng 8, Q10, TP.HCM', 'duyenpham@gmail.com', N'cá nhân', N'đóng', '2023-07-15');
-- select * from customer;

-- --------- currency ----------
INSERT INTO Currency (currency_code, currency_name, rate_to_usd)
VALUES
('VND', N'Việt Nam Đồng', 0.000042),
('USD', N'Đô la Mỹ', 1.0),
('EUR', N'Euro', 1.08),
('CNY', N'Nhân dân tệ', 0.14);
-- select * from currency;

-- -------- branch ----------
INSERT INTO Branch (branch_name, address, status)
VALUES
(N'Chi nhánh Quận 1', N'12 Nguyễn Huệ, Quận 1, TP.HCM', N'mở'),
(N'Chi nhánh Quận 3', N'45 Võ Văn Tần, Quận 3, TP.HCM', N'mở'),
(N'Chi nhánh Hà Nội', N'89 Trần Duy Hưng, Cầu Giấy, Hà Nội', N'đóng'),
(N'Chi nhánh Đà Nẵng', N'78 Bạch Đằng, Hải Châu, Đà Nẵng', N'mở'),
(N'Chi nhánh Cần Thơ', N'23 Lý Tự Trọng, Ninh Kiều, Cần Thơ', N'đóng');
-- select * from branch;

-- ----------- account -------------
INSERT INTO Account (customer_id, branch_id, account_type, currency, balance, status, open_date, close_date)
VALUES 
('0000000001', '0000000001', N'thanh toán', 'VND', 15000000.00, N'mở', '2023-01-15', NULL),
('0000000002', '0000000002', N'tiết kiệm', 'USD', 2000.00, N'mở', '2022-11-01', NULL),
('0000000003', '0000000001', N'thanh toán', 'VND', 750000.00, N'khóa', '2021-08-10', '2024-01-05'),
('0000000004', '0000000003', N'tiết kiệm', 'EUR', 500.00, N'mở', '2024-03-01', NULL),
('0000000004', '0000000001', N'thanh toán', 'VND', 3200000.00, N'đóng', '2023-07-20', '2024-02-01');
-- select * from account;

-- ------------- collateral ----------
INSERT INTO Collateral (owner_id, asset_type, estimated_value, currency, status)
VALUES 
('0000000001', N'Nhà ở', 1500000000.00, 'VND', N'đang cầm cố'),
('0000000002', N'Ô tô', 700000000.00, 'VND', N'giải chấp'),
('0000000003', N'Sổ tiết kiệm', 10000.00, 'USD', N'thanh lý'),
('0000000001', N'Đất nền', 1200000000.00, 'VND', N'đang cầm cố'),
('0000000004', N'Cổ phiếu', 500000000.00, 'VND', N'giải chấp');
-- SELECT * FROM COLLATERAL;


-- ----------- transaction --------------
INSERT INTO Transaction (from_account, to_account, transaction_type, amount, currency, date, status)
VALUES 
('0000000001', '0000000002', N'chuyển tiền', 500000.00, 'VND', '2024-10-10', N'thành công'),
('0000000002', NULL, N'rút tiền', 1000000.00, 'VND', '2024-11-15', N'thành công'),
(NULL, '0000000003', N'nạp tiền', 200000.00, 'USD', '2024-12-01', N'thành công'),
('0000000003', '0000000004', N'chuyển tiền', 150000.00, 'VND', '2025-01-05', N'thất bại'),
('0000000004', '0000000001', N'chuyển tiền', 300000.00, 'VND', '2025-02-20', N'chờ xử lý'),
('0000000002', '0000000003', N'chuyển tiền', 500.00, 'USD', '2025-03-01', N'thành công'),
(NULL, '0000000001', N'nạp tiền', 1000000.00, 'VND', '2025-03-10', N'thành công'),
('0000000001', NULL, N'rút tiền', 250000.00, 'VND', '2025-03-15', N'thành công');
-- SELECT * FROM TRANSACTION;

-- -------- loan ------------
INSERT INTO Loan (customer_id, loan_type, amount, currency, interest_rate, start_date, end_date, status, collateral_id)
VALUES 
('0000000001', N'vay tiêu dùng', 50000000, 'VND', 9.5, '2024-01-15', '2026-01-15', N'đang vay', '0000000001'),
('0000000002', N'vay mua nhà', 1500000000, 'VND', 8.2, '2023-06-01', '2033-06-01', N'đang vay', '0000000002'),
('0000000003', N'vay sản xuất kinh doanh', 750000000, 'USD', 7.0, '2022-03-10', '2027-03-10', N'quá hạn', '0000000003'),
('0000000004', N'vay thế chấp', 200000000, 'VND', 6.8, '2021-12-20', '2024-12-20', N'tất toán', '0000000004');
-- SELECT * FROM LOAN;

-- -------- user_authen --------------
INSERT INTO account_authen (username, password, user_type, account_id, salt)
VALUES
-- Tài khoản khách hàng
('cus01', 'pass01', 'customer', '0000000001', 'salt01'),
('cus02', 'pass02', 'customer', '0000000002', 'salt02'),
('cus03', 'pass03', 'customer', '0000000003', 'salt03'),
('cus04', 'pass04', 'customer', '0000000004', 'salt04'),

-- Tài khoản nhân viên
('nguyenvana', 'admin01', 'employee', NULL, 'salt11'),
('tranthib',   'admin02', 'employee', NULL, 'salt12'),
('lehoangc',   'admin03', 'employee', NULL, 'salt13'),
('phammaid',   'admin04', 'employee', NULL, 'salt14'),
('doquoce',    'admin05', 'employee', NULL, 'salt15');
-- SELECT * FROM account_authen;


-- ------------- employee ------------
INSERT INTO Employee (full_name, branch_id, role, status, created_at, phone, address, email, username)
VALUES
(N'Nguyễn Văn A', '0000000001', N'Giao dịch viên', N'đang hoạt động', '2023-02-15', '0901234567', N'123 Lê Lợi, Q.1, TP.HCM', 'nguyenvana@example.com', 'nguyenvana'),
(N'Trần Thị B', '0000000002', N'Quản lý chi nhánh', N'đang hoạt động', '2022-08-10', '0912345678', N'45 Trần Hưng Đạo, Q.5, TP.HCM', 'tranthib@example.com', 'tranthib'),
(N'Lê Hoàng C', '0000000003', N'Nhân viên tín dụng', N'nghỉ việc', '2021-04-05', '0923456789', N'67 Nguyễn Trãi, Q.10, TP.HCM', 'lehoangc@example.com', 'lehoangc'),
(N'Phạm Mai D', '0000000004', N'Kế toán viên', N'đang hoạt động', '2023-11-20', '0934567890', N'89 Lý Tự Trọng, Q.3, TP.HCM', 'phammaid@example.com', 'phammaid'),
(N'Đỗ Quốc E', '0000000005', N'Chuyên viên hỗ trợ', N'bị khóa', '2020-06-01', '0945678901', N'12 Phạm Ngũ Lão, Q.1, TP.HCM', 'doquoce@example.com', 'doquoce');
-- SELECT * FROM employee;

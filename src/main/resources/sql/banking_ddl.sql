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
	open_date DATE,
	close_date DATE,
	CONSTRAINT PK_Account PRIMARY KEY(account_id),
	CONSTRAINT CHK_Account_type CHECK (account_type IN (N'thanh toán', N'tiết kiệm')),
	CONSTRAINT CHK_Account_status CHECK (status IN (N'mở', N'đóng', N'khóa'))
);

CREATE TABLE Transaction (
	transaction_id VARCHAR(20) NOT NULL, -- primary key
	from_account VARCHAR(20) NOT NULL,
	to_account VARCHAR(20),
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
    phone varchar(50),
	address nvarchar(200),
	email varchar(100),
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
    account_id  VARCHAR(20) NOT NULL,
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

insert into customer(name, birth_date, gender, identity_number, phone, address, email, type, status, register_date) values
(N'Nguyễn Văn A', '1990-05-12', N'Nam', '123456789', '0901234567', N'123 Lê Lợi, Q1, TP.HCM', 'anguyen@gmail.com', N'cá nhân', N'mở', '2023-01-10'),

(N'Trần Thị B', '1985-09-23', N'Nữ', '987654321', '0938765432', N'456 Trần Hưng Đạo, Q5, TP.HCM', 'btran@gmail.com', N'doanh nghiệp', N'khóa', '2022-12-01'),

(N'Lê Quốc Cường', '1995-11-02', N'Nam', '112233445', '0912345678', N'789 Nguyễn Huệ, Q1, TP.HCM', 'cuongle@gmail.com', N'cá nhân', N'mở', '2024-03-05'),

(N'Phạm Thị Duyên', '2000-03-18', N'Nữ', '556677889', '0987654321', N'321 Cách Mạng Tháng 8, Q10, TP.HCM', 'duyenpham@gmail.com', N'cá nhân', N'đóng', '2023-07-15');

select * from customer;

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

-- Cài đặt phân quyền
-- mã role và tên role
CREATE TABLE Roles (
    role_id     INT PRIMARY KEY AUTO_INCREMENT,
    role_name   VARCHAR(50) UNIQUE NOT NULL
);

-- user nào có role nào
ALTER TABLE account_authen
ADD UNIQUE (account_id);

CREATE TABLE User_Roles (
    user_id     VARCHAR(20) NOT NULL,
    role_id     INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES account_authen(account_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

-- đặc quyền: mã đặc quyền và tên đặc quyền
CREATE TABLE Permissions (
    permission_id INT PRIMARY KEY AUTO_INCREMENT,
    permission_name VARCHAR(100) UNIQUE NOT NULL
);

-- với vai trò này thì có đặc quyền gì
CREATE TABLE Role_Permissions (
    role_id INT,
    permission_id INT,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(permission_id) ON DELETE CASCADE
);

-- kế thừa trong vai trò: Admin được thực hiện tất cả những gì customer làm được
CREATE TABLE Role_Hierarchy (
    parent_role_id INT NOT NULL,
    child_role_id INT NOT NULL,
    PRIMARY KEY (parent_role_id, child_role_id),
    FOREIGN KEY (parent_role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (child_role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);




-- Lấy tất cả các đặc quyền của một user
DELIMITER //

CREATE PROCEDURE get_all_permissions_of_user(IN input_user_id VARCHAR(20))
BEGIN
    WITH RECURSIVE inherited_roles AS (
        -- Bước khởi đầu: lấy tất cả role_id của user
        SELECT role_id AS current_role_id
        FROM user_roles
        WHERE user_id = input_user_id

        UNION

        -- Bước đệ quy: lấy các child role từ vai trò cha
        SELECT rh.child_role_id
        FROM role_hierarchy rh
        JOIN inherited_roles ir ON rh.parent_role_id = ir.current_role_id
    )
    -- Truy xuất các permission_name
    SELECT DISTINCT p.permission_name
    FROM inherited_roles ir
    JOIN role_permissions rp ON ir.current_role_id = rp.role_id
    JOIN Permissions p ON p.permission_id = rp.permission_id;
END;
//
DELIMITER ;



-- Thêm dữ liệu vào phân quyền
insert into Roles(role_name) values
("CUSTOMER"),
("EMPOYEE"),
("ADMIN");




insert into Permissions(permission_name) values
("VIEW_CUSTOMER_INFO"),
("CREATE_ACCOUNT"),
("DELETE_ACCOUNT"),
("DEPOSIT_FUNDS"),
("WITHDRAW_FUNDS"),
("TRANSFER_FUNDS"),
("VIEW_TRANSACTION_HISTORY"),
("VIEW_ALL_USER");



insert into Role_Hierarchy values
(1, 3),
(2, 3);



insert into Role_Permissions values
(1, 1),
(2, 1),
(1, 2),
(2, 2),
(3, 3),
(1, 4),
(2, 4),
(1, 5),
(1, 6), 
(1, 7),
(2, 7),
(3, 8);

SELECT * FROM Roles;
select * from Permissions;
select * from Role_Hierarchy;
select * from Role_Permissions;

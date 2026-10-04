create database neobank_db;

use neobank_db;
UPDATE users SET role = 'Admin' WHERE email ='admin@gmail.com';


select * from users;
select * from accounts;
select * from transactions;
select * from account_opening_requests;

select * from budgets;

select * from bills;
select * from rewards;


select * from loan_products;
select * from loan_applications;
select * from loan_accounts;
select * from loan_repayments;

select * from system_audit_log;




DESC transactions;



SELECT admin_remarks FROM loan_applications;


-- Sprint 2 : Adding category option while doing the transaction.
ALTER TABLE transactions ADD COLUMN category VARCHAR(50) NOT NULL;

ALTER TABLE loan_applications DROP COLUMN admin_remarks;




SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE loan_repayments;
TRUNCATE TABLE loan_accounts;
TRUNCATE TABLE loan_applications;

SET FOREIGN_KEY_CHECKS = 1;






ALTER TABLE transactions
MODIFY COLUMN category ENUM(
'ENTERTAINMENT',
'GROCERIES',
'OTHER',
'RENT',
'TRANSFER',
'UTILITIES',
'LOAN_REPAYMENT'
);




DELETE FROM system_audit_log;

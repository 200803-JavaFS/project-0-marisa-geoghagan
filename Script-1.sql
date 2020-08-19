DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users(
	user_id SERIAL PRIMARY KEY,
	user_name VARCHAR(30),
	pass_word VARCHAR(30),
	user_type INTEGER,
	user_status VARCHAR(30),
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	update_at TIMESTAMP,
	updated_by INTEGER
);

DROP TABLE IF EXISTS accounts CASCADE;

CREATE TABLE accounts(
	account_id SERIAL PRIMARY KEY,
	owner1 INTEGER NOT NULL REFERENCES users(user_id),
	owner2 INTEGER REFERENCES users(user_id),
	account_type BOOLEAN,
	amount NUMERIC(8,2) NOT NULL,
	account_status VARCHAR(30) NOT NULL,
	update_at TIMESTAMP,
	updated_by INTEGER
);

CREATE OR REPLACE FUNCTION trigger_set_time() RETURNS TRIGGER 
AS $$
BEGIN
	NEW.update_at = NOW();
	RETURN NEW; 
END;
$$ LANGUAGE plpgsql; 

CREATE TRIGGER set_time1 BEFORE INSERT ON users FOR EACH ROW
	EXECUTE PROCEDURE trigger_set_time();
CREATE TRIGGER set_time1 BEFORE INSERT ON accounts FOR EACH ROW
	EXECUTE PROCEDURE trigger_set_time();

CREATE TRIGGER set_time2 BEFORE DELETE ON users FOR EACH ROW
	EXECUTE PROCEDURE trigger_set_time();
CREATE TRIGGER set_time2 BEFORE DELETE ON accounts FOR EACH ROW
	EXECUTE PROCEDURE trigger_set_time();

CREATE TRIGGER set_time3 BEFORE UPDATE ON users FOR EACH ROW
	EXECUTE PROCEDURE trigger_set_time();
CREATE TRIGGER set_time3 BEFORE UPDATE ON accounts FOR EACH ROW
	EXECUTE PROCEDURE trigger_set_time();
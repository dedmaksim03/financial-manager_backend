BEGIN;

create table roles(
	id smallserial primary key,
	name varchar
);

create table users(
	id bigserial primary key,
	username varchar,
	email varchar,
	password varchar,
	role_id smallint references roles(id)
);

create table categories_types(
	id smallserial primary key,
	name varchar
);

create table accounts(
	id bigserial primary key,
	name varchar,
	sum numeric,
	user_id bigint references users(id)
);

create table categories(
	id bigserial primary key,
	name varchar,
	root_id bigint references categories(id),
	type_id smallint references categories_types(id),
	user_id bigint references users(id)
);

create table actions(
	id bigserial primary key,
	date date,
	sum numeric,
	message text,
	category_id bigint references categories(id),
	account_id bigint references accounts(id),
	user_id bigint references users(id)
);

insert into roles values
	(1, 'ROLE_USER'), 
	(2, 'ROLE_ADMIN');

insert into users values
	(1, 'ded_maksim', null, '$2y$05$DslGfQVKiwBF6Xk2fTA.KemwMMPhiCAQIEE5saVPY4dD6QX7lOGpC', 1),
	(2, 'admin', null, '$2y$05$DslGfQVKiwBF6Xk2fTA.KemwMMPhiCAQIEE5saVPY4dD6QX7lOGpC', 2),
	(3, 'test_user', null, '$2y$05$DslGfQVKiwBF6Xk2fTA.KemwMMPhiCAQIEE5saVPY4dD6QX7lOGpC', 1);

insert into categories_types values
	(1, 'Расход'),
	(2, 'Доход');

insert into accounts values
	(1, 'Тестовый счет', 50000, 1),
	(2, 'Основная карта', 20000, 3);

insert into categories values
	(1, 'Продукты', null, 1, 1), 
	(2, 'Продукты', null, 1, 3);

insert into actions values
	(1, current_date, 500, 'Тестовый расход', 1, 1, 1);

COMMIT;
CREATE TYPE "media_type" AS ENUM (
  'cd',
  '1LP',
  '2LP'
);

CREATE TYPE "order_state" AS ENUM (
  'created',
  'payed',
  'shipping',
  'canceled',
  'completed'
);

CREATE TYPE "pay_method" AS ENUM (
  'credit_card',
  'transfer',
  'paypal'
);

CREATE TYPE "pay_status" AS ENUM (
  'pending',
  'completed',
  'failed'
);

CREATE TABLE "artist" (
  "id" uuid PRIMARY KEY,
  "name" varchar(128)
);

CREATE TABLE "album" (
  "id" uuid PRIMARY KEY,
  "artist_id" uuid,
  "title" varchar(128),
  "release_date" date,
  "duration" time,
  "tracks" smallserial
  CHECK (tracks > 0)
  CHECK (duration > '00:00:00')
);

CREATE TABLE "physical_copy" (
  "id" uuid PRIMARY KEY,
  "album_id" uuid,
  "stock" smallserial,
  "price" decimal,
  "media_type" media_type
  CHECK (price >= 0)
  CHECK (stock >= 0)
);

CREATE TABLE "customer" (
  "id" uuid PRIMARY KEY,
  "email" varchar(64) UNIQUE,
  "first_name" varchar(128),
  "last_name" varchar(128),
  "date_of_birth" date,
  "password_hash" varchar(64)
  CHECK (date_of_birth <= CURRENT_DATE - INTERVAL '13 years')
);

CREATE TABLE "order" (
  "id" uuid PRIMARY KEY,
  "customer_id" uuid,
  "date" timestamp without time zone,
  "total" decimal,
  "state" order_state,
  CHECK (total >= 0),
  CHECK (date <= CURRENT_TIMESTAMP)
);

CREATE TABLE "order_item" (
  "order_id" uuid,
  "physical_copy_id" uuid,
  "quantity" smallserial,
  CHECK (quantity > 0),
  PRIMARY KEY ("order_id", "physical_copy_id")
);

CREATE TABLE "payment" (
  "id" uuid PRIMARY KEY,
  "order_id" uuid,
  "date" timestamp without time zone,
  "amount" decimal,
  "method" pay_method,
  "status" pay_status,
  "reference" varchar(64) UNIQUE,
  CHECK (amount >= 0)
);

ALTER TABLE "album" ADD FOREIGN KEY ("artist_id") REFERENCES "artist" ("id") ON DELETE RESTRICT;

ALTER TABLE "physical_copy" ADD FOREIGN KEY ("album_id") REFERENCES "album" ("id") ON DELETE RESTRICT;

ALTER TABLE "order" ADD FOREIGN KEY ("customer_id") REFERENCES "customer" ("id") ON DELETE RESTRICT;

ALTER TABLE "order_item" ADD FOREIGN KEY ("order_id") REFERENCES "order" ("id") ON DELETE RESTRICT;

ALTER TABLE "order_item" ADD FOREIGN KEY ("physical_copy_id") REFERENCES "physical_copy" ("id") ON DELETE RESTRICT;

ALTER TABLE "payment" ADD FOREIGN KEY ("order_id") REFERENCES "order" ("id") ON DELETE RESTRICT;

ALTER TABLE "artist" ALTER COLUMN id SET DEFAULT gen_random_uuid();

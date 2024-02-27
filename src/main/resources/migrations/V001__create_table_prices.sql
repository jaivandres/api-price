CREATE TABLE public.customers (
	id varchar NOT NULL,
	created_at timestamptz NOT NULL DEFAULT now(),
	updated_at timestamptz NULL,
	CONSTRAINT customers_pkey PRIMARY KEY (id)
);

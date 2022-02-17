INSERT INTO public.publisher (id, name) VALUES (10, 'Roma Publishing');
SELECT pg_catalog.setval('public.publisher_id_seq', 11, true);
INSERT INTO public.book
    (id, acquire_date, acquiring_employee_id, acquiring_method, comment, invoice_symbol, joint_publication, language,
    place_of_origin, price, publisher_id, registry_number, series_name, status, title, volume, year)
    VALUES
    ('c0a80015-7d8c-1d2f-817d-8c2e93df2200', '2003-01-02', '28319c80-449d-11ec-81d3-0242ac130003', 0, NULL, NULL,
     false, 0, 'Warsaw', NULL, 10, 1, NULL, 0, 'Red Mars', '1', 2002);
INSERT INTO public.book
    (id, acquire_date, acquiring_employee_id, acquiring_method, comment, invoice_symbol, joint_publication, language,
    place_of_origin, price, publisher_id, registry_number, series_name, status, title, volume, year)
    VALUES
    ('c0a80015-7d8c-1d2f-817d-8c2e93df2210', '2004-03-07', '28319c80-449d-11ec-81d3-0242ac130103', 0, NULL, NULL,
     false, 0, 'Warsaw', NULL, 10, 1, NULL, 0, 'Green Mars', '1', 2003);

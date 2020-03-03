--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

-- Started on 2020-03-03 16:56:27

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 207 (class 1259 OID 16439)
-- Name: notices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notices (
    id integer NOT NULL,
    description character varying(200) NOT NULL,
    user_id integer NOT NULL,
    status character varying(20) NOT NULL,
    created timestamp without time zone NOT NULL
);


ALTER TABLE public.notices OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16437)
-- Name: notices_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notices_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.notices_id_seq OWNER TO postgres;

--
-- TOC entry 2871 (class 0 OID 0)
-- Dependencies: 206
-- Name: notices_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notices_id_seq OWNED BY public.notices.id;


--
-- TOC entry 205 (class 1259 OID 16431)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    role character varying(20) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16429)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_id_seq OWNER TO postgres;

--
-- TOC entry 2872 (class 0 OID 0)
-- Dependencies: 204
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 209 (class 1259 OID 16447)
-- Name: types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.types (
    id integer NOT NULL,
    type character varying(20) NOT NULL
);


ALTER TABLE public.types OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16445)
-- Name: type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.type_id_seq OWNER TO postgres;

--
-- TOC entry 2873 (class 0 OID 0)
-- Dependencies: 208
-- Name: type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.type_id_seq OWNED BY public.types.id;


--
-- TOC entry 210 (class 1259 OID 16453)
-- Name: type_notices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.type_notices (
    notice_id integer NOT NULL,
    type_id integer NOT NULL
);


ALTER TABLE public.type_notices OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16466)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_roles (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16423)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    name character varying(20) NOT NULL,
    last_name character varying(20) NOT NULL,
    password character varying(70) NOT NULL,
    email character varying(50) NOT NULL,
    status character varying(20) NOT NULL,
    confirm_registration character varying(6)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16421)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 2874 (class 0 OID 0)
-- Dependencies: 202
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 2715 (class 2604 OID 16442)
-- Name: notices id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notices ALTER COLUMN id SET DEFAULT nextval('public.notices_id_seq'::regclass);


--
-- TOC entry 2714 (class 2604 OID 16434)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 2716 (class 2604 OID 16450)
-- Name: types id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.types ALTER COLUMN id SET DEFAULT nextval('public.type_id_seq'::regclass);


--
-- TOC entry 2713 (class 2604 OID 16426)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 2861 (class 0 OID 16439)
-- Dependencies: 207
-- Data for Name: notices; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.notices (id, description, user_id, status, created) VALUES (52, 'Sell Mercedes s350 w222. Contancts with me: 097-777-77-00 ', 8, 'APPROVED', '2020-03-03 16:33:40.294');
INSERT INTO public.notices (id, description, user_id, status, created) VALUES (55, 'Buy iPhone 11 pro Max 256gb. Contacts with me: 097-97-777-11-00', 8, 'APPROVED', '2020-03-03 16:34:52.667');
INSERT INTO public.notices (id, description, user_id, status, created) VALUES (54, 'Buy Mercedes w222 s560 2019. Contancts with me: 097-777-11-00', 8, 'APPROVED', '2020-03-03 16:34:59.136');
INSERT INTO public.notices (id, description, user_id, status, created) VALUES (53, 'Sell iPhone 5s 32gb. Contancts with me: 097-777-77-00 ', 8, 'APPROVED', '2020-03-03 16:35:06.671');
INSERT INTO public.notices (id, description, user_id, status, created) VALUES (56, 'Sell laptop pacbook pro 2019 256gb. Contacts with me: 097-111-22-33', 15, 'APPROVED', '2020-03-03 16:35:37.761');
INSERT INTO public.notices (id, description, user_id, status, created) VALUES (57, 'Buy Nokia 3310. Please, contacts with me: 097-111-22-11. It''s very important for me to find this for as gifts.', 15, 'APPROVED', '2020-03-03 16:35:55.35');


--
-- TOC entry 2859 (class 0 OID 16431)
-- Dependencies: 205
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles (id, role) VALUES (1, 'User');
INSERT INTO public.roles (id, role) VALUES (2, 'Moderator');
INSERT INTO public.roles (id, role) VALUES (3, 'Admin');


--
-- TOC entry 2864 (class 0 OID 16453)
-- Dependencies: 210
-- Data for Name: type_notices; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.type_notices (notice_id, type_id) VALUES (52, 34);
INSERT INTO public.type_notices (notice_id, type_id) VALUES (52, 3);
INSERT INTO public.type_notices (notice_id, type_id) VALUES (55, 35);
INSERT INTO public.type_notices (notice_id, type_id) VALUES (55, 19);
INSERT INTO public.type_notices (notice_id, type_id) VALUES (54, 34);
INSERT INTO public.type_notices (notice_id, type_id) VALUES (54, 19);
INSERT INTO public.type_notices (notice_id, type_id) VALUES (53, 35);
INSERT INTO public.type_notices (notice_id, type_id) VALUES (53, 3);
INSERT INTO public.type_notices (notice_id, type_id) VALUES (56, 35);
INSERT INTO public.type_notices (notice_id, type_id) VALUES (56, 3);
INSERT INTO public.type_notices (notice_id, type_id) VALUES (57, 35);
INSERT INTO public.type_notices (notice_id, type_id) VALUES (57, 19);


--
-- TOC entry 2863 (class 0 OID 16447)
-- Dependencies: 209
-- Data for Name: types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.types (id, type) VALUES (19, 'For Buy');
INSERT INTO public.types (id, type) VALUES (3, 'For Sell');
INSERT INTO public.types (id, type) VALUES (34, 'Cars');
INSERT INTO public.types (id, type) VALUES (35, 'Electronics');


--
-- TOC entry 2865 (class 0 OID 16466)
-- Dependencies: 211
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_roles (user_id, role_id) VALUES (15, 1);
INSERT INTO public.user_roles (user_id, role_id) VALUES (15, 2);
INSERT INTO public.user_roles (user_id, role_id) VALUES (8, 1);
INSERT INTO public.user_roles (user_id, role_id) VALUES (8, 2);
INSERT INTO public.user_roles (user_id, role_id) VALUES (8, 3);


--
-- TOC entry 2857 (class 0 OID 16423)
-- Dependencies: 203
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (id, name, last_name, password, email, status, confirm_registration) VALUES (8, 'Peter', 'Fediuk', '2902ea1c1cc2c770a29e08fbfe422e0a', 'petrofediuk004@gmail.com', 'CREATED', NULL);
INSERT INTO public.users (id, name, last_name, password, email, status, confirm_registration) VALUES (15, 'Andriy', 'Paziuk', '2902ea1c1cc2c770a29e08fbfe422e0a', 'paziuk_andiy@gmail.com', 'CREATED', NULL);


--
-- TOC entry 2875 (class 0 OID 0)
-- Dependencies: 206
-- Name: notices_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notices_id_seq', 57, true);


--
-- TOC entry 2876 (class 0 OID 0)
-- Dependencies: 204
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 3, true);


--
-- TOC entry 2877 (class 0 OID 0)
-- Dependencies: 208
-- Name: type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.type_id_seq', 35, true);


--
-- TOC entry 2878 (class 0 OID 0)
-- Dependencies: 202
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 86, true);


--
-- TOC entry 2723 (class 2606 OID 16444)
-- Name: notices notices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notices
    ADD CONSTRAINT notices_pkey PRIMARY KEY (id);


--
-- TOC entry 2721 (class 2606 OID 16436)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 2725 (class 2606 OID 16452)
-- Name: types type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.types
    ADD CONSTRAINT type_pkey PRIMARY KEY (id);


--
-- TOC entry 2719 (class 2606 OID 16428)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2717 (class 1259 OID 16479)
-- Name: users_email_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX users_email_uindex ON public.users USING btree (email);


--
-- TOC entry 2726 (class 2606 OID 16456)
-- Name: type_notices type_notices_notice_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.type_notices
    ADD CONSTRAINT type_notices_notice_id_fkey FOREIGN KEY (notice_id) REFERENCES public.notices(id);


--
-- TOC entry 2727 (class 2606 OID 16461)
-- Name: type_notices type_notices_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.type_notices
    ADD CONSTRAINT type_notices_type_id_fkey FOREIGN KEY (type_id) REFERENCES public.types(id);


--
-- TOC entry 2729 (class 2606 OID 16474)
-- Name: user_roles user_roles_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 2728 (class 2606 OID 16469)
-- Name: user_roles user_roles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


-- Completed on 2020-03-03 16:56:27

--
-- PostgreSQL database dump complete
--


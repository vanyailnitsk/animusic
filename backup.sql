--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5
-- Dumped by pg_dump version 14.5

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

--
-- Name: animusic; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE animusic WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';


\connect animusic

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
-- Name: anime; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.anime (
    id integer NOT NULL,
    description character varying(255),
    folder_name character varying(255),
    release_year integer,
    studio character varying(255),
    title character varying(255)
);


--
-- Name: anime_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.anime_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: soundtrack; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.soundtrack (
    id integer NOT NULL,
    anime_title character varying(255),
    original_title character varying(255),
    path_to_file character varying(255),
    type smallint,
    anime_id integer NOT NULL,
    anime_name character varying(255)
);


--
-- Name: soundtrack_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.soundtrack_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Data for Name: anime; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.anime (id, description, folder_name, release_year, studio, title) FROM stdin;
1	\N	Hunter_x_Hunter	2011	\N	Hunter x Hunter
2	\N	Naruto_Shippuden	2007	\N	Naruto Shippuden
3	\N	Attack_on_Titan	2013	\N	Attack on Titan
52		Chainsaw_Man	\N	MAPPA	Chainsaw Man
53		Vinland_Saga	\N		Vinland Saga
54		Tokyo_Ghoul	\N		Tokyo Ghoul
102		Jujutsu_Kaisen	\N	MAPPA	Jujutsu Kaisen
152		Promised_Neverland	\N		Promised Neverland
153		Black_Clover	2017		Black Clover
154		Demon_Slayer	\N		Demon Slayer
155		Death_Note	\N		Death Note
156		Code_Geass	\N		Code Geass
\.


--
-- Data for Name: soundtrack; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.soundtrack (id, anime_title, original_title, path_to_file, type, anime_id, anime_name) FROM stdin;
2	Opening 5	FLOW-Sign	Naruto_Shippuden/Opening 5.mp3	0	2	\N
3	Opening 3	Blue Bird	Naruto_Shippuden/Opening 3.mp3	0	2	\N
4	Opening 1	Guren no Yumiya	Attack_on_Titan/Opening 1.mp3	0	3	\N
52	Opening 9	Lovers	Naruto_Shippuden/Opening 9.mp3	0	2	\N
102	Opening 1	KICKBACK	Chainsaw_Man/Opening 1.mp3	0	52	\N
103	Opening 1	MUKANJYO	Vinland_Saga/Opening 1.mp3	0	53	\N
104	Opening 2	Dark Crow	Vinland_Saga/Opening 2.mp3	0	53	\N
105	Opening 1	Unravel	Tokyo_Ghoul/Opening 1.mp3	0	54	\N
107	Opening 2	Jiyuu no Tsubasa	Attack_on_Titan/Opening 2.mp3	0	3	\N
108	Opening 3	Shinzou wo Sasageyo!	Attack_on_Titan/Opening 3.mp3	0	3	\N
109	Opening 4	Red Swan	Attack_on_Titan/Opening 4.mp3	0	3	\N
110	Opening 5	Shoukei to Shikabane no Michi	Attack_on_Titan/Opening 5.mp3	0	3	\N
111	Opening 6	My War	Attack_on_Titan/Opening 6.mp3	0	3	\N
112	Opening 7	The Rumbling	Attack_on_Titan/Opening 7.mp3	0	3	\N
152	Ending 28	Niji	Naruto_Shippuden/Ending 28.mp3	1	2	\N
153	Opening 15	Guren	Naruto_Shippuden/Opening 15.mp3	0	2	\N
203	Opening 1	Kaikai Kitan	Jujutsu_Kaisen/Opening 1.mp3	0	102	\N
252	Ending 5	Hyori Ittai	Hunter_x_Hunter/Ending 5.mp3	1	1	\N
302	Ending 6	Hyori Ittai	Hunter_x_Hunter/Ending 6.mp3	1	1	\N
1	Ending 2	Hunting For Your Dream	Hunter_x_Hunter/Ending 2.mp3	1	1	\N
303	Opening 2	Vivid Vice	Jujutsu_Kaisen/Opening 2.mp3	0	102	\N
304	Opening 3	Where Our Blue Is	Jujutsu_Kaisen/Opening 3.mp3	0	102	\N
305	Opening 1	Touch off	Promised_Neverland/Opening 1.mp3	0	152	\N
306	Opening 1	Haruka Mirai	Black_Clover/Opening 1.mp3	0	153	\N
307	Opening 2	PAiNT it BLACK	Black_Clover/Opening 2.mp3	0	153	\N
308	Opening 3	Black Rover	Black_Clover/Opening 3.mp3	0	153	\N
309	Opening 4	Guess Who Is Back	Black_Clover/Opening 4.mp3	0	153	\N
310	Itachi Uchiha Theme	Many Nights	Naruto_Shippuden/Itachi Uchiha Theme.mp3	2	2	\N
311	Phantom Troupe Theme	Requiem Aranea	Hunter_x_Hunter/Phantom Troupe Theme.mp3	2	1	\N
312	Opening 1	Departure!	Hunter_x_Hunter/Opening 1.mp3	0	1	\N
313	Opening 1	Gurenge	Demon_Slayer/Opening 1.mp3	0	154	\N
314	Opening 1	the WORLD	Death_Note/Opening 1.mp3	0	155	\N
317	Opening 12	Moshimo	Naruto_Shippuden/Opening 12.mp3	0	2	\N
318	Opening 1	COLORS	Code_Geass/Opening 1.mp3	0	156	\N
319	Opening 7	JUSTadICE	Black_Clover/Opening 7.mp3	0	153	\N
352	Opening 10	Black Catcher	Black_Clover/Opening 10.mp3	0	153	\N
353	Obito's Theme	Naruto Shippuden OST	Naruto_Shippuden/Obito's Theme.mp3	2	2	\N
354	Tragic	Naruto Shippuden OST	Naruto_Shippuden/Tragic.mp3	3	2	\N
355	Bauklötze	Bauklötze	Attack_on_Titan/Bauklötze.mp3	3	3	\N
356	YouSeeBIGGIRL/T:T	YouSeeBIGGIRL/T:T	Attack_on_Titan/YouSeeBIGGIRL/T:T.mp3	3	3	\N
402	Opening 1	Hero's Come Back!	Naruto_Shippuden/Opening 1.mp3	0	2	\N
\.


--
-- Name: anime_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.anime_seq', 201, true);


--
-- Name: soundtrack_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.soundtrack_seq', 451, true);


--
-- Name: anime anime_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.anime
    ADD CONSTRAINT anime_pkey PRIMARY KEY (id);


--
-- Name: soundtrack soundtrack_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.soundtrack
    ADD CONSTRAINT soundtrack_pkey PRIMARY KEY (id);


--
-- Name: soundtrack uk_45nn6kh5nksi38bb479cddv3u; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.soundtrack
    ADD CONSTRAINT uk_45nn6kh5nksi38bb479cddv3u UNIQUE (path_to_file);


--
-- Name: anime uk_54eaowtdrykgn24dd0g93dcy4; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.anime
    ADD CONSTRAINT uk_54eaowtdrykgn24dd0g93dcy4 UNIQUE (title);


--
-- Name: anime uk_gi6g8jjcql5ohu4svoa08ilxq; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.anime
    ADD CONSTRAINT uk_gi6g8jjcql5ohu4svoa08ilxq UNIQUE (folder_name);


--
-- Name: soundtrack fkjbk0ljqrhm9n5m5jrqpcm9tsb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.soundtrack
    ADD CONSTRAINT fkjbk0ljqrhm9n5m5jrqpcm9tsb FOREIGN KEY (anime_id) REFERENCES public.anime(id);


--
-- Name: DATABASE animusic; Type: ACL; Schema: -; Owner: -
--

GRANT ALL ON DATABASE animusic TO postgres;


--
-- PostgreSQL database dump complete
--


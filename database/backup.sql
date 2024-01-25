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
    title character varying(255),
    banner_image_path character varying(255),
    card_image_path character varying(255)
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
-- Name: playlist; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.playlist (
    id bigint NOT NULL,
    image_url character varying(255),
    name character varying(255),
    type smallint,
    anime_id integer
);


--
-- Name: playlist_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.playlist_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: playlist_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.playlist_id_seq OWNED BY public.playlist.id;


--
-- Name: playlist_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.playlist_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: playlist_soundtrack; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.playlist_soundtrack (
    soundtrack_id integer NOT NULL,
    playlist_id bigint NOT NULL
);


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
-- Name: playlist id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.playlist ALTER COLUMN id SET DEFAULT nextval('public.playlist_id_seq'::regclass);


--
-- Data for Name: anime; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.anime (id, description, folder_name, release_year, studio, title, banner_image_path, card_image_path) FROM stdin;
1	\N	Hunter_x_Hunter	2011	\N	Hunter x Hunter	\N	\N
2	\N	Naruto_Shippuden	2007	\N	Naruto Shippuden	\N	\N
53		Vinland_Saga	\N		Vinland Saga	\N	\N
54		Tokyo_Ghoul	\N		Tokyo Ghoul	\N	\N
102		Jujutsu_Kaisen	\N	MAPPA	Jujutsu Kaisen	\N	\N
152		Promised_Neverland	\N		Promised Neverland	\N	\N
153		Black_Clover	2017		Black Clover	\N	\N
154		Demon_Slayer	\N		Demon Slayer	\N	\N
155		Death_Note	\N		Death Note	\N	\N
402		Naruto	2002	Studio Pierrot	Naruto	Naruto/banner.jpeg	Naruto/card.jpeg
52		Chainsaw_Man	\N	MAPPA	Chainsaw Man	Chainsaw_Man/banner.webp	\N
3	\N	Attack_on_Titan	2013	\N	Attack on Titan	Attack_on_Titan/banner.webp	\N
\.


--
-- Data for Name: playlist; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.playlist (id, image_url, name, type, anime_id) FROM stdin;
102	/	Openings	\N	1
105	/	Openings	\N	2
109	/	Openings	\N	3
111	/	Openings	\N	52
112	/	Openings	\N	53
113	/	Openings	\N	54
114	/	Openings	\N	102
115	/	Openings	\N	152
116	/	Openings	\N	153
117	/	Openings	\N	154
118	/	Openings	\N	155
103	/	Endings	\N	1
107	/	Endings	\N	2
104	/	Themes	\N	1
108	/	Themes	\N	2
106	/	Scene songs	\N	2
110	/	Scene songs	\N	3
152	/	Endings	\N	3
302	/	Openings	\N	402
\.


--
-- Data for Name: playlist_soundtrack; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.playlist_soundtrack (soundtrack_id, playlist_id) FROM stdin;
1152	111
1202	302
1	103
2	105
3	105
4	109
52	105
103	112
104	112
105	113
107	109
108	109
109	109
110	109
111	109
112	109
153	105
203	114
252	103
302	103
303	114
304	114
305	115
306	116
307	116
308	116
309	116
310	108
311	104
312	102
313	117
314	118
317	105
319	116
352	116
353	108
354	106
355	110
356	110
402	105
603	106
702	152
752	152
852	112
902	108
1010	110
152	107
1053	107
\.


--
-- Data for Name: soundtrack; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.soundtrack (id, anime_title, original_title, path_to_file, type, anime_id, anime_name) FROM stdin;
2	Opening 5	FLOW-Sign	Naruto_Shippuden/Opening 5.mp3	0	2	\N
3	Opening 3	Blue Bird	Naruto_Shippuden/Opening 3.mp3	0	2	\N
4	Opening 1	Guren no Yumiya	Attack_on_Titan/Opening 1.mp3	0	3	\N
52	Opening 9	Lovers	Naruto_Shippuden/Opening 9.mp3	0	2	\N
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
319	Opening 7	JUSTadICE	Black_Clover/Opening 7.mp3	0	153	\N
352	Opening 10	Black Catcher	Black_Clover/Opening 10.mp3	0	153	\N
353	Obito's Theme	Naruto Shippuden OST	Naruto_Shippuden/Obito's Theme.mp3	2	2	\N
354	Tragic	Naruto Shippuden OST	Naruto_Shippuden/Tragic.mp3	3	2	\N
355	Bauklötze	Bauklötze	Attack_on_Titan/Bauklötze.mp3	3	3	\N
402	Opening 1	Hero's Come Back!	Naruto_Shippuden/Opening 1.mp3	0	2	\N
356	YouSeeBIGGIRL/T:T	YouSeeBIGGIRL/T:T	Attack_on_Titan/YouSeeBIGGIRL.mp3	3	3	\N
603	Hyouhaku + Kokuten	Naruto Shippuden OST	Naruto_Shippuden/Hyouhaku + Kokuten.mp3	3	2	\N
752	Ending 2	great escape	Attack_on_Titan/Ending 2.mp3	1	3	\N
702	Ending 4	Requiem der Morgenröte	Attack_on_Titan/ Ending 4.mp3	1	3	\N
852	Opening 4	Paradox	Vinland_Saga/Opening 4.mp3	0	53	\N
902	Kaguya Theme Extended	Otsutsuki Kaguya the Goddess	Naruto_Shippuden/Kaguya Theme Extended.mp3	2	2	\N
1010	Final Ending Theme (Mikasa)	To You 2000…or…20000 Years From Now…	Attack_on_Titan/Final Ending Theme (Mikasa).mp3	3	3	\N
1053	Ending 6	Broken Youth	Naruto_Shippuden/Ending 6.mp3	1	2	\N
1152	Opening 1	KICK BACK	Chainsaw_Man/Opening 1.mp3	0	52	\N
1202	Opening 1	R★O★C★K★S	Naruto/Opening 1.mp3	0	402	\N
\.


--
-- Name: anime_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.anime_seq', 501, true);


--
-- Name: playlist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.playlist_id_seq', 1, false);


--
-- Name: playlist_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.playlist_seq', 351, true);


--
-- Name: soundtrack_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.soundtrack_seq', 1251, true);


--
-- Name: anime anime_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.anime
    ADD CONSTRAINT anime_pkey PRIMARY KEY (id);


--
-- Name: playlist playlist_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.playlist
    ADD CONSTRAINT playlist_pkey PRIMARY KEY (id);


--
-- Name: soundtrack soundtrack_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.soundtrack
    ADD CONSTRAINT soundtrack_pkey PRIMARY KEY (id);


--
-- Name: anime uk_298qhdp0ob1v29v6bkiiwmp5t; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.anime
    ADD CONSTRAINT uk_298qhdp0ob1v29v6bkiiwmp5t UNIQUE (banner_image_path);


--
-- Name: anime uk_54eaowtdrykgn24dd0g93dcy4; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.anime
    ADD CONSTRAINT uk_54eaowtdrykgn24dd0g93dcy4 UNIQUE (title);


--
-- Name: anime uk_7ts88f2cvi4tyibvh17gmqmbb; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.anime
    ADD CONSTRAINT uk_7ts88f2cvi4tyibvh17gmqmbb UNIQUE (card_image_path);


--
-- Name: anime uk_gi6g8jjcql5ohu4svoa08ilxq; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.anime
    ADD CONSTRAINT uk_gi6g8jjcql5ohu4svoa08ilxq UNIQUE (folder_name);


--
-- Name: playlist_soundtrack fk_playlist; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.playlist_soundtrack
    ADD CONSTRAINT fk_playlist FOREIGN KEY (playlist_id) REFERENCES public.playlist(id);


--
-- Name: playlist_soundtrack fk_soundtrack; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.playlist_soundtrack
    ADD CONSTRAINT fk_soundtrack FOREIGN KEY (soundtrack_id) REFERENCES public.soundtrack(id);


--
-- Name: soundtrack fkjbk0ljqrhm9n5m5jrqpcm9tsb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.soundtrack
    ADD CONSTRAINT fkjbk0ljqrhm9n5m5jrqpcm9tsb FOREIGN KEY (anime_id) REFERENCES public.anime(id);


--
-- Name: playlist fkme5db1cotvt9moswpurta3xk3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.playlist
    ADD CONSTRAINT fkme5db1cotvt9moswpurta3xk3 FOREIGN KEY (anime_id) REFERENCES public.anime(id);


--
-- Name: DATABASE animusic; Type: ACL; Schema: -; Owner: -
--

GRANT ALL ON DATABASE animusic TO postgres;


--
-- PostgreSQL database dump complete
--


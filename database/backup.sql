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
-- Name: image_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.image_seq
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
    audio_file character varying(255),
    anime_id integer NOT NULL,
    type smallint,
    image_file character varying(255)
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
3	\N	Attack_on_Titan	2013	\N	Attack on Titan	banner.webp	card.webp
52		Chainsaw_Man	\N	MAPPA	Chainsaw Man	banner.webp	card.webp
53		Vinland_Saga	\N		Vinland Saga	banner.webp	card.webp
2	\N	Naruto_Shippuden	2007	\N	Naruto Shippuden	banner.webp	card.webp
102		Jujutsu_Kaisen	\N	MAPPA	Jujutsu Kaisen	banner.webp	card.webp
152		Promised_Neverland	\N		Promised Neverland	banner.webp	card.webp
154		Demon_Slayer	\N		Demon Slayer	banner.webp	card.webp
402		Naruto	2002	Studio Pierrot	Naruto	banner.webp	card.webp
54		Tokyo_Ghoul	\N		Tokyo Ghoul	banner.webp	card.webp
153		Black_Clover	2017		Black Clover	banner.webp	card.webp
155		Death_Note	\N		Death Note	banner.webp	card.webp
1	\N	Hunter_x_Hunter	2011	\N	Hunter × Hunter	banner.webp	card.webp
\.


--
-- Data for Name: playlist; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.playlist (id, image_url, name, anime_id) FROM stdin;
102	/	Openings	1
105	/	Openings	2
109	/	Openings	3
111	/	Openings	52
112	/	Openings	53
113	/	Openings	54
114	/	Openings	102
115	/	Openings	152
116	/	Openings	153
117	/	Openings	154
118	/	Openings	155
103	/	Endings	1
107	/	Endings	2
104	/	Themes	1
108	/	Themes	2
106	/	Scene songs	2
110	/	Scene songs	3
152	/	Endings	3
302	/	Openings	402
352	/	Endings	154
402	/	Scene songs	1
\.


--
-- Data for Name: playlist_soundtrack; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.playlist_soundtrack (soundtrack_id, playlist_id) FROM stdin;
1202	302
1	103
1902	103
1952	402
4	109
103	112
104	112
105	113
107	109
108	109
109	109
110	109
111	109
112	109
1854	111
402	105
1852	105
1853	105
2	105
52	105
203	114
317	105
1855	105
1856	108
902	108
353	108
1857	108
312	102
303	114
304	114
305	115
306	116
307	116
308	116
309	116
311	104
313	117
314	118
319	116
352	116
354	106
355	110
356	110
603	106
702	152
752	152
852	112
1010	110
152	107
1053	107
\.


--
-- Data for Name: soundtrack; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.soundtrack (id, anime_title, original_title, audio_file, anime_id, type, image_file) FROM stdin;
603	Hyouhaku + Kokuten	Naruto Shippuden OST	Hyouhaku + Kokuten.mp3	2	\N	\N
1202	Opening 1	R★O★C★K★S	Opening 1.mp3	402	\N	\N
402	Opening 1	Hero's Come Back!	Opening 1.mp3	2	\N	\N
1852	Opening 16	Silhouette	Opening 16.ogg	2	\N	\N
1853	Opening 3	Blue Bird	Opening 3.ogg	2	\N	\N
1854	Opening 1	KICK BACK	Opening 1.ogg	52	\N	\N
1856	Pain's Theme Song	Girei	Pain's Theme Song.ogg	2	\N	\N
1857	Itachi Theme Song	Senya	Itachi Theme Song.ogg	2	\N	\N
1	Ending 2	Hunting For Your Dream	Ending 2.ogg	1	\N	\N
1902	Ending 5-6	Hyori Ittai	Ending 5-6.ogg	1	\N	\N
4	Opening 1	Guren no Yumiya	Opening 1.mp3	3	\N	Opening 1.webp
1855	Opening 15	Guren	Opening 15.ogg	2	\N	Opening 15.jpg
1952	Kusari Yarou	Hunter × Hunter OST	Kusari Yarou.mp3	1	\N	Kusari Yarou.jpeg
1053	Ending 6	Broken Youth	Ending 6.mp3	2	\N	Ending 6.jpg
104	Opening 2	Dark Crow	Opening 2.mp3	53	\N	\N
105	Opening 1	Unravel	Opening 1.mp3	54	\N	\N
107	Opening 2	Jiyuu no Tsubasa	Opening 2.mp3	3	\N	\N
110	Opening 5	Shoukei to Shikabane no Michi	Opening 5.mp3	3	\N	\N
303	Opening 2	Vivid Vice	Opening 2.mp3	102	\N	\N
311	Phantom Troupe Theme	Requiem Aranea	Phantom Troupe Theme.mp3	1	\N	\N
111	Opening 6	My War	Opening 6.mp3	3	\N	\N
304	Opening 3	Where Our Blue Is	Opening 3.mp3	102	\N	\N
305	Opening 1	Touch off	Opening 1.mp3	152	\N	\N
306	Opening 1	Haruka Mirai	Opening 1.mp3	153	\N	\N
203	Opening 1	Kaikai Kitan	Opening 1.mp3	102	\N	\N
112	Opening 7	The Rumbling	Opening 7.mp3	3	\N	\N
2	Opening 5	FLOW-Sign	Opening 5.mp3	2	\N	\N
314	Opening 1	the WORLD	Opening 1.mp3	155	\N	\N
313	Opening 1	Gurenge	Opening 1.mp3	154	\N	\N
103	Opening 1	MUKANJYO	Opening 1.mp3	53	\N	\N
109	Opening 4	Red Swan	Opening 4.mp3	3	\N	\N
308	Opening 3	Black Rover	Opening 3.mp3	153	\N	\N
312	Opening 1	Departure!	Opening 1.mp3	1	\N	\N
108	Opening 3	Shinzou wo Sasageyo!	Opening 3.mp3	3	\N	\N
309	Opening 4	Guess Who Is Back	Opening 4.mp3	153	\N	\N
152	Ending 28	Niji	Ending 28.mp3	2	\N	\N
52	Opening 9	Lovers	Opening 9.mp3	2	\N	\N
307	Opening 2	PAiNT it BLACK	Opening 2.mp3	153	\N	\N
1010	Final Ending Theme (Mikasa)	To You 2000…or…20000 Years From Now…	Final Ending Theme (Mikasa).mp3	3	\N	\N
852	Opening 4	Paradox	Opening 4.mp3	53	\N	\N
317	Opening 12	Moshimo	Opening 12.mp3	2	\N	\N
354	Tragic	Naruto Shippuden OST	Tragic.mp3	2	\N	\N
902	Kaguya Theme Extended	Otsutsuki Kaguya the Goddess	Kaguya Theme Extended.mp3	2	\N	\N
356	YouSeeBIGGIRL/T:T	YouSeeBIGGIRL/T:T	YouSeeBIGGIRL.mp3	3	\N	\N
319	Opening 7	JUSTadICE	Opening 7.mp3	153	\N	\N
352	Opening 10	Black Catcher	Opening 10.mp3	153	\N	\N
752	Ending 2	great escape	Ending 2.mp3	3	\N	\N
702	Ending 4	Requiem der Morgenröte	Ending 4.mp3	3	\N	\N
353	Obito's Theme	Naruto Shippuden OST	Obito's Theme.mp3	2	\N	\N
355	Bauklötze	Bauklötze	Bauklötze.mp3	3	\N	\N
\.


--
-- Name: anime_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.anime_seq', 501, true);


--
-- Name: image_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.image_seq', 1, false);


--
-- Name: playlist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.playlist_id_seq', 1, false);


--
-- Name: playlist_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.playlist_seq', 451, true);


--
-- Name: soundtrack_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.soundtrack_seq', 2001, true);


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


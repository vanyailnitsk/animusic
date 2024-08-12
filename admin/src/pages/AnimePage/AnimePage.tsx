import React, {useContext, useEffect, useState} from 'react';
import {getAnimeInfo} from "../../services/api/anime";
import {useNavigate, useParams} from "react-router-dom";
import Albums from "../../components/Albums/Albums";
import {IAlbums} from "../../models/Albums";
import {IAnime} from "../../models/Anime";
import styles from './AnimePage.module.css'
import {Context} from "../../index";
import {EDIT_ANIME} from "../../Forms/vatietyForms";
import {observer} from "mobx-react";

const AnimePage = () => {
    const {id} = useParams()
    const {contentStore, editBannerStore} = useContext(Context)
    const [animeData, setAnimeData] = useState<IAnime | null>(null)
    const [albums, setAlbums] = useState<IAlbums[]>([])
    const navigate = useNavigate();
    const [color,setColor] = useState('')
    useEffect(() => {
        getAnimeInfo(id)
            .then(response => {
                setAnimeData(response.data);
                contentStore.setCurrentContentType(EDIT_ANIME)
                setColor(response.data.banner.color)
                return response.data.albums
            })
            .then(albums => {
                setAlbums(albums)
            })
            .catch((error) => console.log(error))
    }, [id]);
    const handleNavigate = (albumId : number) => {
        navigate(`/album-manage/${albumId}`)
    }
    return (
        <div className={styles.anime__page__wrapper}>
            <div className={styles.anime__banner}>
                <div className={styles.overlay}></div>
                {typeof editBannerStore.bannerImage === 'string' && editBannerStore.bannerImage?
                    <img src={editBannerStore.bannerImage} alt=""/>
                    :(animeData?.banner?.image?.source && (
                        <img
                            src={animeData?.banner.image.source} alt=""
                        />
                    ))
                }

                {animeData &&
                    <div className={styles.anime__title}>
                        <h1>{animeData.title}</h1>
                    </div>
                }
            </div>
            {editBannerStore.color?
                <div style={{background:`linear-gradient(to bottom, ${editBannerStore.color}, #121212`}} className={styles.anime__page__bottom__rgb}></div>
                :<div style={{background:`linear-gradient(to bottom, ${color}, #121212`}} className={styles.anime__page__bottom__rgb}></div>
            }
            {animeData &&
                <div>
                    <Albums albums={albums} handleNavigate={handleNavigate}/>
                </div>
            }
        </div>
    );
};

export default observer(AnimePage);
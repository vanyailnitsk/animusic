import React, {useEffect, useState} from 'react';
import {getAnimeInfo} from "../../services/api/anime";
import {useNavigate, useParams} from "react-router-dom";
import playPlaylist from '../../icons/play-playlist.png'
import Albums from "../../components/Albums/Albums";
import {storageUrl} from "../../services/api/consts";
import {IAlbums} from "../../models/Albums";
import {IAnime} from "../../models/Anime";
import styles from './AnimePage.module.css'
import AlbumsSkeleton from "../../components/Albums/Skeleton/AlbumsSkeleton";
import Skeleton from "react-loading-skeleton";

const AnimePage = () => {
    const {id} = useParams()
    const [animeData, setAnimeData] = useState<IAnime | null>(null)
    const [albums, setAlbums] = useState<IAlbums[]>([])
    const navigate = useNavigate();
    const [color,setColor] = useState('')
    const [isLoadingImage, setIsLoadingImage] = useState(true)
    useEffect(() => {
        getAnimeInfo(id)
            .then(response => {
                setAnimeData(response.data);
                setColor(response.data.banner.color)
                return response.data.albums
            })
            .then(albums => {
                setAlbums(albums)
            })
            .catch((error) => console.log(error))
    }, [id]);

    const handleNavigate = (albumId : number) => {
        navigate(`/album/${albumId}`)
    }
    return (
        <div className={styles.anime__page__wrapper}>
            <div className={styles.anime__banner}>
                {isLoadingImage?
                    <Skeleton style={{height:'35vh',borderTopRightRadius:7,borderTopLeftRadius:7,position:"absolute",top:0}}/>
                : null
                }
                <div className={styles.overlay}></div>
                <img
                    src={storageUrl+animeData?.banner.image.source} alt=""
                    onLoad={() => setIsLoadingImage(false)}
                />
                {!isLoadingImage && animeData &&
                    <div className={styles.anime__title}>
                        <h1>{animeData.title}</h1>
                    </div>

                }
            </div>
            <div style={{background:`linear-gradient(to bottom, ${color}, #121212`}} className={styles.anime__page__bottom__rgb}></div>
            {isLoadingImage?
               <div>
                   <AlbumsSkeleton/>
               </div>
                :null
            }
            {!isLoadingImage && animeData &&
                <div>
                    <div className={styles.album__actions}>
                        <button className={styles.play__album}>
                            <img src={playPlaylist} alt=""/>
                        </button>
                        <button className={styles.follow__button}>
                            <span>Follow</span>
                        </button>
                    </div>
                    <Albums albums={albums} handleNavigate={handleNavigate}/>
                </div>
            }
        </div>
    );
};

export default AnimePage;
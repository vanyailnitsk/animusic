import React, {useEffect, useState} from 'react';
import SoundtrackList from "../../components/SoundtrackList/SoundtrackList";
import {useNavigate, useParams} from "react-router-dom";
import {getAlbumById} from "../../services/api/tracks";
import styles from '../AlbumPage/AlbumPage.module.css'
import {storageUrl} from "../../services/api/consts";
import {Album} from "../../models/Albums";

const AlbumPage = () => {
    const animeRoute = '/anime/'
    const {id}  = useParams()
    const [album, setAlbum] = useState<Album | null>(null)
    const navigate = useNavigate()
    const [colors,setColors] = useState({colorLight:'',colorDark:''})
    const bannerUrl = storageUrl+album?.coverArt?.image.source
    const [isLoadingImage, setIsLoadingImage] = useState<boolean>(false)
    useEffect(() => {
        getAlbumById(id)
            .then(album => {
                setAlbum(album.data)
                setColors({colorLight:album.data.coverArt.colors.colorLight, colorDark:album.data.coverArt.colors.colorDark})
                return album.data
            })
            .catch(error => {
                console.log(error)
            })
    }, [id]);
    return (
        <div className={styles.album__page__wrapper}>
           <div className={styles.album__page__header} style={{background:`linear-gradient(to bottom, ${colors.colorLight}, ${colors.colorDark}`}}>
               <div className={styles.album__page__header__content}>
                   {album?.coverArt && (
                       <div className={styles.album__image}>
                           <img
                               src={storageUrl + (album?.coverArt.image.source) } alt="Banner"
                               onLoad={() => setIsLoadingImage(false)}
                           />
                       </div>
                   )}
                   {album && (
                   <div className={styles.album__main__info}>
                       <span className={styles.type__content}>Album</span>
                       <div className={styles.album__name}>{album?.name}</div>

                           <div>
                               <span className={styles.anime__title} onClick={() => navigate(animeRoute+album?.anime.id)}>{album?.anime.title}</span>
                               <span> • </span>
                               <span style={{fontSize:14}}>{`${album?.soundtracks.length} tracks`}</span>
                           </div>
                   </div>
                   )}
               </div>
           </div>
            <div style={{background:`linear-gradient(to bottom, ${colors.colorDark}, #121212`}} className={styles.album__page__bottom_rgb}></div>
            {!isLoadingImage && album &&
                    <SoundtrackList soundtracks={album.soundtracks} />
            }
        </div>
    );
};

export default AlbumPage;
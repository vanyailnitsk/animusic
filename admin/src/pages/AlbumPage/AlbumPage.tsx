import React, {useContext, useEffect, useState} from 'react';
import SoundtrackList from "../../components/SoundtrackList/SoundtrackList";
import {useNavigate, useParams} from "react-router-dom";
import {getAlbumById} from "../../services/api/tracks";
import styles from '../AlbumPage/AlbumPage.module.css'
import {Album} from "../../models/Albums";
import {EDIT_ALBUM} from "../../Forms/vatietyForms";
import {Context} from "../../index";
import {observer} from "mobx-react";

const AlbumPage = () => {
    const {contentStore} = useContext(Context)
    const animeRoute = '/anime/'
    const {editAlbumStore} = useContext(Context)
    const {id}  = useParams()
    const [album, setAlbum] = useState<Album | null>(null)
    const navigate = useNavigate()
    const [colors,setColors] = useState({colorLight:'',colorDark:''})
    const bannerUrl = album?.coverArt?.image.source
    const [isLoadingImage, setIsLoadingImage] = useState<boolean>(false)
    useEffect(() => {
        contentStore.setCurrentContentType(EDIT_ALBUM)
        getAlbumById(id)
            .then(album => {
                setAlbum(album.data)
                setColors({colorLight:album.data.coverArt.colors.colorLight, colorDark:album.data.coverArt.colors.colorDark})
                console.log(album.data.coverArt)
                return album.data
            })
            .catch(error => {
                console.log(error)
            })
    }, []);
    useEffect(() => {
        if(editAlbumStore.colorLight){
            setColors({colorLight: editAlbumStore.colorLight, colorDark: editAlbumStore.colorDark})
        }
    }, [editAlbumStore.colorLight]);
    console.log(colors)
    return (
        <div className={styles.album__page__wrapper}>
           <div className={styles.album__page__header} style={{background:`linear-gradient(to bottom, ${colors.colorLight}, ${colors.colorDark}`}}>
               <div className={styles.album__page__header__content}>
                   {editAlbumStore.card? (
                       <div className={styles.album__image}>
                           <img
                               src={editAlbumStore.card} alt="Banner"
                               onLoad={() => setIsLoadingImage(false)}
                           />
                       </div>
                   )
                   :(album?.coverArt && (
                           <div className={styles.album__image}>
                               <img
                                   src={(album?.coverArt.image.source)} alt="Banner"
                                   onLoad={() => setIsLoadingImage(false)}
                               />
                           </div>
                       ))
                   }
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
            {album &&
                <div>
                    <SoundtrackList soundtracks={album.soundtracks} />
                </div>
            }
        </div>
    );
};

export default observer(AlbumPage);
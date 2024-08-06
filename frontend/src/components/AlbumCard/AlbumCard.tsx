import React, {useState} from 'react';
import "./AlbumCard.module.css"
import styles from './AlbumCard.module.css'
import playButton from '../../icons/playButton.png'
import {AlbumCardProps} from "../../models/Albums";
import Skeleton from "react-loading-skeleton";
import {storageUrl} from "../../services/api/consts";

const AlbumCard = ({name,id,handleNavigate,image} : AlbumCardProps) => {
    const [loadingImg,setLoadingImg] = useState(true)
    return (
        <div className={styles.album__card__wrapper} onClick={() => handleNavigate(id)}>
            {loadingImg?
                <Skeleton style={{width:152,height:152,position:'absolute',top:0,marginTop:13}}/>
                :null
            }
            <div className={styles.album__card__image_wrapper}>
                <img src={storageUrl + image} alt="" onLoad={() => setLoadingImg(false)}
                     className={styles.album__card__image}/>
                <div className={styles.album__card__actions}>
                    <img src={playButton} alt="" className={styles.album__card__actions_img}/>
                </div>
            </div>
            <span>{name}</span>
        </div>
    );
};

export default AlbumCard;
import {useState} from 'react';
import fav from '@/shared/icons/favourites.jpg'
import styles from './FavoriteTracks.module.css'
import {useNavigate} from "react-router-dom";
import Skeleton from "react-loading-skeleton";
import {COLLECTION} from "@/shared/consts";

export const FavoriteTracks = () => {
    const navigate = useNavigate()
    const [loading,setLoading] = useState<boolean>(true)
    return (
        <div className={styles.favorite__tracks__wrapper} onClick={() => navigate(COLLECTION)}>
            <div className={styles.favorite__tracks__content}>
                {loading?
                    <Skeleton style={{width:50,height:50,position:'absolute',left:0,top:0}}/>
                    : null
                }
                <img src={fav} alt="" onLoad={() => setLoading(false)}/>
                <div className={styles.favorite__tracks__name}>
                    Favorite tracks
                </div>
            </div>
        </div>
    );
};


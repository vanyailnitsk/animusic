import {useContext, useState} from 'react';
import fav from '@/shared/icons/icons8-избранное-500.png'
import styles from './FavoriteTracks.module.css'
import {useNavigate} from "react-router-dom";
import Skeleton from "react-loading-skeleton";
import {COLLECTION} from "@/shared/consts";
import {Context} from "@/main.tsx";
import {enqueueSnackbar, VariantType} from "notistack";

export const FavoriteTracks = () => {
    const navigate = useNavigate()
    const {userStore} = useContext(Context)
    const [loading,setLoading] = useState<boolean>(true)
    const handleError = (variant:VariantType)  => {
        enqueueSnackbar('You need to sign in!', {variant});
    };
    const handleGoToCollection = () => {
        if(!userStore.isAuth){
            handleError('error')
        }
        else {
            navigate(COLLECTION)
        }
    }
    return (
        <div className={styles.favorite__tracks__wrapper} onClick={handleGoToCollection}>
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


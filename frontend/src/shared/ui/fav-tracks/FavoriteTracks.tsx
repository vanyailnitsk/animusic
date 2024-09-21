import {useState} from 'react';
import fav from '@/shared/assets/icons/icons8-избранное-500.png'
import styles from './FavoriteTracks.module.css'
import {useNavigate} from "react-router-dom";
import Skeleton from "react-loading-skeleton";
import {COLLECTION} from "@/shared/consts";
import {enqueueSnackbar, VariantType} from "notistack";
import {useAppSelector} from "@/shared/lib/store";
import {selectUser} from "@/entities/user";

export const FavoriteTracks = () => {
    const navigate = useNavigate()
    const user = useAppSelector(selectUser)
    const [loading, setLoading] = useState<boolean>(true)
    const handleError = (variant: VariantType) => {
        enqueueSnackbar('You need to sign in!', {variant});
    };
    const handleGoToCollection = () => {
        if (user) {
            navigate(COLLECTION)
        } else {
            handleError('error')
        }
    }
    return (
        <div className={styles.favorite__tracks__wrapper} onClick={handleGoToCollection}>
            <div className={styles.favorite__tracks__content}>
                {loading ?
                    <Skeleton style={{width: 50, height: 50, position: 'absolute', left: 0, top: 0}}/>
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


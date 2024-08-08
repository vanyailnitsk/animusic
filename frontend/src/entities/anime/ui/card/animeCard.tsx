import {useState} from 'react';
import styles from './anime-card.module.css'
import Skeleton from 'react-loading-skeleton'
import 'react-loading-skeleton/dist/skeleton.css'
import {useNavigate} from "react-router-dom";

interface AnimeCardProps {
    id: number;
    title: string;
    cardImageUrl: string;
}

export const AnimeCard = ({cardImageUrl, title, id}: AnimeCardProps) => {
    const navigate = useNavigate();
    const [loading,setLoading] = useState<boolean>(true)
    return (
        <div className={styles.anime__card} onClick={() => navigate('/anime/' + id)}>
            {loading?
                (
                    <div style={{position:"absolute"}}>
                        <Skeleton style={{width:290,height:160,position:'absolute',left:14,right:14,top:14}}/>
                    </div>
                )
                : null
            }
            <img src={cardImageUrl} alt="" onLoad={() => setLoading(false)}/>
            <span>{title}</span>
        </div>
    );
};


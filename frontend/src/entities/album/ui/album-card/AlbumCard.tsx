import styles from './album-card.module.css'
import playButton from '@/shared/icons/playButton.png'
import {useNavigate} from "react-router-dom";
import {clsx} from "clsx";

interface AlbumCardProps {
    name: string
    id: number
    imageUrl: string
    show: boolean
    handleLoadCardImage: () => void
}

export const AlbumCard = ({name, id, imageUrl, show, handleLoadCardImage}: AlbumCardProps) => {
    const navigate = useNavigate()
    return (
        <div className={clsx(styles.album__card__wrapper, show ? styles.album__card__show : styles.album__card__hidden)}
             onClick={() => navigate('/album/' + id)}>
            <div className={styles.album__card__image_wrapper}>
                <img src={imageUrl} alt="" onLoad={handleLoadCardImage}
                     onError={handleLoadCardImage}
                     className={styles.album__card__image}/>
                <div className={styles.album__card__actions}>
                    <img src={playButton} alt="" className={styles.album__card__actions_img}/>
                </div>
            </div>
            <span>{name}</span>
        </div>
    );
};


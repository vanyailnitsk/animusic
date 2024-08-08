import {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import styles from './anime-page.module.css'
import {getAnimeInfo, IAnime} from "@/entities/anime";
import {storageUrl} from "@/shared/api";
import {AlbumList} from "@/widgets/albums-list";
import Skeleton from "react-loading-skeleton";

export const AnimePage = () => {
    const {id} = useParams()
    const [isLoading, setIsLoading] = useState({banner: true, albums: true})
    const [animeData, setAnimeData] = useState<IAnime | null>(null)
    const [color, setColor] = useState('')
    useEffect(() => {
        id && getAnimeInfo(id)
            .then(response => {
                setAnimeData(response.data);
                setColor(response.data.banner.color)
            })
            .catch((error) => console.log(error))
    }, [id]);
    const handleAlbumsLoading = () => {
        setIsLoading(prevState => ({...prevState, albums: false}))
    }
    return (
        <div className={styles.anime__page__wrapper}>
            <div className={styles.anime__banner}>
                <div className={styles.overlay}></div>
                {isLoading.banner && <Skeleton style={{
                    height: '35vh',
                    borderTopRightRadius: 7,
                    borderTopLeftRadius: 7,
                    position: "absolute",
                    top: 0
                }}/>}
                <img
                    src={storageUrl + animeData?.banner.image.source} alt=""
                    onLoad={() => setIsLoading({...isLoading, banner: false})}
                />
                {!isLoading.banner &&
                    <div className={styles.anime__title}>
                        <h1>{animeData?.title}</h1>
                    </div>

                }
            </div>
            <div style={{background: `linear-gradient(to bottom, ${color}, #121212`}}
                 className={styles.anime__page__bottom__rgb}></div>
            {id && <AlbumList id={id} isAlbumsLoading={isLoading.albums && isLoading.banner}
                              setAlbumsLoading={handleAlbumsLoading}/>}
        </div>
    );
};


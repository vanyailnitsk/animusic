import styles from './albums-list.module.css'
import {AlbumCard, getAlbumsByAnimeId} from "@/entities/album";
import {storageUrl} from "@/shared/api";
import Skeleton from "react-loading-skeleton";
import {useFetching} from "@/shared/lib";
import {useEffect, useState} from "react";

interface AlbumListProps {
    id: string;
    isAlbumsLoading: boolean
    setAlbumsLoading: () => void
}

export const AlbumList = ({id, isAlbumsLoading, setAlbumsLoading}: AlbumListProps) => {
    const {data, error, isLoading} = useFetching(async () => await getAlbumsByAnimeId(id), [id])
    const [countLoadedCardImg, setCountLoadedCardImg] = useState(0)
    useEffect(() => {
        if (countLoadedCardImg === data?.length) {
            setAlbumsLoading()
        }
    }, [countLoadedCardImg, data]);
    if (error) {
        return <div>{error}</div>
    }
    const handleLoadCardImage = () => {
        setCountLoadedCardImg(countLoadedCardImg + 1)
    }

    return (
        <div className={styles.albums}>
            {(isLoading || isAlbumsLoading) && [...Array(4)].map((_, index) => (
                <div className={styles.album__card__skeleton} key={index}>
                    <Skeleton className={styles.album__card__skeleton__img}/>
                    <Skeleton className={styles.album__card__skeleton__title}/>
                </div>
            ))}
            {data?.map((album) =>
                <AlbumCard
                    name={album.name}
                    id={album.id}
                    key={album.id}
                    imageUrl={storageUrl + album.coverArt?.image.source}
                    show={!isAlbumsLoading}
                    handleLoadCardImage={handleLoadCardImage}
                />)}
        </div>
    );
};


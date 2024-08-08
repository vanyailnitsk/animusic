import styles from './anime-cards-list.module.css'
import {AnimeCard, getAllAnime} from "@/entities/anime";
import {useFetching} from "@/shared/lib";
import {storageUrl} from "@/shared/api";

export const AnimeCardsList = () => {
    const {data, error} = useFetching(async () => await getAllAnime(), [])
    if (error) {
        return <div>{error}</div>
    }
    return (
        <div className={styles.cards__list__wrapper}>
            {data && data.map(card =>
                <AnimeCard id={card.id} cardImageUrl={storageUrl + card.cardImage.source} title={card.title}/>
            )}
        </div>
    );
};


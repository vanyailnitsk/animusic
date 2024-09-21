import styles from './anime-cards-list.module.css'
import {AnimeCard, getAllAnime} from "@/entities/anime";
import {useFetching} from "@/shared/lib";

export const AnimeCardsList = () => {
    const {data, error} = useFetching(async () => await getAllAnime(), [])
    if (error) {
        return <div>{error}</div>
    }
    return (
        <div className={styles.cards__list__wrapper}>
            {data && data.map((card,index) =>
                <AnimeCard id={card.id} cardImageUrl={card.cardImage.source} title={card.title} key={index}/>
            )}
        </div>
    );
};


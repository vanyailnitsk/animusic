import styles from './anime-cards-list.module.css'
import {AnimeCard, IAnime} from "@/entities/anime";
import {useFetching} from "@/shared/lib/fetching";

interface AnimeCardListProps {
    animeCards: IAnime[]
}

export const AnimeCardsList = ({animeCards}: AnimeCardListProps) => {
    const {} = useFetching(async () =>)
    return (
        <div className={styles.cards__list__wrapper}>
            {animeCards.map(card =>
                <AnimeCard card={card} key={card.id}/>
            )}
        </div>
    );
};


import React from 'react';
import AnimeCard from "../AnimeCard/AnimeCard";
import './CardsList.css'
import {CardListProps} from "../../models/AnimeCards";

const CardsList = ({animeCards} : CardListProps) => {
    return (
        <div className='cardslist__wrapper'>
            {animeCards.map(card =>
                <AnimeCard card={card} key={card.id}/>
            )}
        </div>
    );
};

export default CardsList;
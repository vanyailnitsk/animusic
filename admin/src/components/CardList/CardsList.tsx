import React from 'react';
import AnimeCard from "../AnimeCard/AnimeCard";
import './CardsList.css'
import {CardListProps} from "../../models/AnimeCards";
import AnimeNameCard from "../AnimeName/AnimeNameCard";

const CardsList = ({animeCards} : CardListProps) => {
    console.log(animeCards)
    return (
        <div className='cardslist__wrapper'>
            {animeCards.map(card =>
                <AnimeNameCard name={card.title} id={card.id} key={card.id}/>
            )}
        </div>
    );
};

export default CardsList;